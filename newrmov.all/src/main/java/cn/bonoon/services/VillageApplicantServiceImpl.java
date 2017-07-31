package cn.bonoon.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.bonoon.core.ImportPicture;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.entities.VillageEvaluatePointEntity;
import cn.bonoon.entities.VillagePictureEntity;
import cn.bonoon.entities.VillageProjectEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractService;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.util.DoubleHelper;

@Service
@Transactional(readOnly = true)
public class VillageApplicantServiceImpl extends AbstractService<VillageApplicantEntity> implements VillageApplicantService{

	@Override
	protected VillageApplicantEntity __save(OperateEvent event, VillageApplicantEntity entity) {
		__edit(event, entity);
		return __attribute(event, super.__save(event, entity));
	}
	
	@Override
	protected VillageApplicantEntity __update(OperateEvent event, VillageApplicantEntity entity) {

		__exec("delete from VillageEvaluatePointEntity where applicant.id=?", entity.getId());
		__exec("delete from VillageProjectEntity where applicant.id=?", entity.getId());
		__edit(event, entity);
		return __attribute(event, super.__update(event, entity));
	}

	private void __edit(OperateEvent event, VillageApplicantEntity entity){
		PlaceEntity pl = entity.getPlace();
		if(event.is("applicant-submit")){
			entity.setStatus(WAIT_AUDIT);
			//检查完整性
			Assert.notNull(pl, "必须选择一个村！");
			Assert.hasText(entity.getContactName(), "联系人不允许为空！");
			Assert.hasText(entity.getContactPhone(), "联系电话不允许为空！");
			Date aat = entity.getApplicantAt();
			Assert.notNull(aat, "申报日期不允许为空！");
			Calendar cal = Calendar.getInstance();
			cal.setTime(aat);
			int ty = cal.get(Calendar.YEAR);//2013/2014
			int year = entity.getYear();
			year++;
			Assert.isTrue(year == ty, "申报日期为" + year + "年内！");
		}
		if(null != pl){
			entity.setName(pl.getName());
			PlaceEntity pt = pl.getParent();
			if(null != pt){//镇
				entity.setTownName(pt.getName());
				pt = pt.getParent();
				if(null != pt){//县
					entity.setCountyName(pt.getName());
					pt = pt.getParent();
					if(null != pt){
						entity.setCityId(pt.getId());
						entity.setCityName(pt.getName());
					}
				}
			}
		}
		double total = DoubleHelper.add(
				entity.getInvestmentLocal(),
				entity.getInvestmentNation(),
				entity.getInvestmentOther(),
				entity.getInvestmentProvince(),
				entity.getInvestmentSelf(),
				entity.getInvestmentSocial());
		entity.setTotalInvestment(total);
		entity.getCountyOpinion().setAuditorId(event.getId());
		entity.getCountyOpinion().setAuditorName(event.getUsername());
	}
	
	private VillageApplicantEntity __attribute(OperateEvent event, VillageApplicantEntity entity){
		String[] codes = event.getStringArray("codes");
		double ts = 0, ta = 0;
		VillageEvaluatePointEntity ep;
		double ab12=0, ab34=0;
		String chkAb12="AB1AB2";
		String chkAb34="AB3AB4";
		for(String code : codes){
			ep = new VillageEvaluatePointEntity();
			ep.setApplicant(entity);
			ep.setCode(code);
			Double ca = event.getDouble(code + "-audit");
			Double cs = event.getDouble(code + "-self");
			Double cm = event.getDouble(code + "-value");
			
			if(null != ca){
				if(ca >= 0 && ca <= cm){
					ep.setPointAudit(ca);
				}else{
					throw new RuntimeException(code + "/审核(0-" + cm + ")" + ca + "分超过限定范围！");
				}
				ta = DoubleHelper.add(ta, ca);
				if(chkAb12.contains(code)){
					ab12 = DoubleHelper.add(ab12, ca);
				}else if(chkAb34.contains(code)){
					ab34 = DoubleHelper.add(ab34, ca);
				}
			}
			if(null != cs){
				if(cs >= 0 && cs <= cm){
					ep.setPointSelf(cs);
				}else{
					throw new RuntimeException(code + "/自评(0-" + cm + ")" + cs + "分超过限定范围！");
				}
				ts = DoubleHelper.add(ts, cs);
				if(chkAb12.contains(code)){
					ab12 = DoubleHelper.add(ab12, cs);
				}else if(chkAb34.contains(code)){
					ab34 = DoubleHelper.add(ab34, cs);
				}
			}
			entityManager.persist(ep);
		}
		if(ab12 > 0 && ab34 > 0){
			throw new RuntimeException("“珠三角”和“其他地区”只能选其中一项填写，不能两项都有填！");
		}
		ep = new VillageEvaluatePointEntity();
		ep.setApplicant(entity);
		ep.setCode("E");
		ep.setPointAudit(ta);
		ep.setPointSelf(ts);
		ep.setPointMax(100);
		entityManager.persist(ep);
		
		//处理项目
		String[] projects = event.getStringArray("projects");
		if(null != projects){
			VillageProjectEntity tpe;
			for(String project : projects){
				String name = event.getString("projectName-" + project);
				if(StringHelper.isEmpty(name)){ continue; }
				tpe = new VillageProjectEntity();
				tpe.setApplicant(entity);
				Double pb = event.getDouble("projectBudget-" + project),
						pd = event.getDouble("projectDone-" + project);
				if(null != pb){ tpe.setBudget(pb); }
				if(null != pd){ tpe.setDoneInvestment(pd); }
				tpe.setContent(event.getString("projectContent-" + project));
				tpe.setCreateAt(event.now());
				tpe.setCreatorId(event.getId());
				tpe.setCreatorName(event.getUsername());
				tpe.setExecuteTime(event.getString("projectAt-" + project));
				tpe.setExecutor(event.getString("projectExecutor-" + project));
				tpe.setName(name);
				tpe.setOwnerId(event.getOwnerId());
				entityManager.persist(tpe);
			}
		}

		boolean smt = entity.getStatus() == WAIT_AUDIT;
		//读取图片
		ImportPicture iptr = (ImportPicture)event.getSource();
		int count = 0;
		int picCount = __first(Number.class, "select count(x) from VillagePictureEntity x where x.applicant.id=?", entity.getId()).intValue();
		if(null != iptr && iptr.getPictures() != null && iptr.getPictures().length > 0){
//			picCount += iptr.getPictures().length;
//			if(smt && picCount < 5){
//				throw new RuntimeException("至少上传5张名镇名村建设成果的相片,才可提交!");
//			}
//			try{
//				FileInfo[] fis = fileManager.save("pictures/village/village_" + entity.getId(), DirectoryStrategy.NONE, FilenameStrategy.MD5, iptr.getPictures());
//				for(FileInfo fi : fis){
//					if(null != fi){
//						VillagePictureEntity tpi = new VillagePictureEntity();
//						tpi.setApplicant(entity);
//						tpi.setName(fi.getOriginalFilename());
//						tpi.setPath(fi.getRelativePath());
//						entityManager.persist(tpi);
//					}
//				}
//			}catch(Exception ex){
//				ex.printStackTrace();
//			}
				try{
					FileInfo[] fis = fileManager.save("pictures/village/village_" + entity.getId(), DirectoryStrategy.NONE, FilenameStrategy.MD5, iptr.getPictures());
					for(FileInfo fi : fis){
						if(null != fi){
							count++;
							VillagePictureEntity tpi = new VillagePictureEntity();
							tpi.setApplicant(entity);
							tpi.setName(fi.getOriginalFilename());
							tpi.setPath(fi.getRelativePath());
							entityManager.persist(tpi);
						}
					}
			    }catch(Exception ex){
					ex.printStackTrace();
				 }
				picCount += count;
				if(smt && picCount < 5){
					throw new RuntimeException("至少上传5张名镇名村建设成果的相片,才可提交!");
				}
		}else if(smt && picCount < 5){
			throw new RuntimeException("至少上传5张名镇名村建设成果的相片,才可提交!");
		 }
		return entity;
	}

	@Autowired
	private FileManager fileManager;
	
	@Override
	@Transactional
	public void auditPass(OperateEvent event, Long id, String content1, Date at1, String content2, Date at2) {
		VillageApplicantEntity tae = __get(id);
		tae.setStatus(FINISH);
		tae.getCityOpinion().setAuditorId(event.getId());
		tae.getCityOpinion().setAuditorName(event.getUsername());
		tae.getCityOpinion().setOpinion(content1);
		tae.getCityOpinion().setOpinionAt(at1);
		tae.getCityGroup().setAuditorId(event.getId());
		tae.getCityGroup().setAuditorName(event.getUsername());
		tae.getCityGroup().setOpinion(content2);
		tae.getCityGroup().setOpinionAt(at2);
		entityManager.merge(tae);
	}

	@Override
	@Transactional
	public void auditReject(OperateEvent event, Long id, String content) {
		VillageApplicantEntity tae = __get(id);
		tae.setStatus(REJECT);
		tae.setRejectAt(event.now());
		tae.setRejectContent(content);
		tae.setRejectUid(event.getId());
		entityManager.merge(tae);
	}

	@Override
	public List<VillageEvaluatePointEntity> evaluatePoints(Long id) {
		String ql = "select x from VillageEvaluatePointEntity x where x.applicant.id=?";
		return __list(VillageEvaluatePointEntity.class, ql, id);
	}

	@Override
	public List<VillageProjectEntity> projects(Long id) {
		String ql = "select x from VillageProjectEntity x where x.applicant.id=? order by x.id asc";
		return __list(VillageProjectEntity.class, ql, id);
	}
	
	@Override
	public List<VillagePictureEntity> picutres(Long id) {
		String ql = "select x from VillagePictureEntity x where x.applicant.id=? order by x.id asc";
		return __list(VillagePictureEntity.class, ql, id);
	}

	@Override
	public List<PlaceEntity> towns(IOperator event) {
		
		Long tid = event.getOwnerId();//所在的县的地区的ID
		//了出我的县下的所有镇的信息
		String ql = "select x from PlaceEntity x where x.parent.id=?";
		return __list(PlaceEntity.class, ql, tid);
	}
	
	@Override
	@Transactional
	public void deletePicture(IOperator user, Long vid, Long pid) {
		VillagePictureEntity entity = entityManager.find(VillagePictureEntity.class, pid);
		if(null != entity 
				&& entity.getApplicant().getId().equals(vid) 
				&& entity.getApplicant().getOwnerId() == user.toOwnerId()){
			entityManager.remove(entity);
		}
	}
}
