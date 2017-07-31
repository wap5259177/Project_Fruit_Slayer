//package cn.bonoon.services;
//
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import cn.bonoon.core.ProjectReportManagerService;
//import cn.bonoon.entities.InvestmentInfo;
//import cn.bonoon.entities.ModelAreaEntity;
//import cn.bonoon.entities.ProjectEntity;
//import cn.bonoon.entities.ProjectReportEntity;
//import cn.bonoon.entities.ProjectReportItem;
//import cn.bonoon.kernel.support.services.AbstractService;
//import cn.bonoon.util.DoubleHelper;
//@Service
//@Transactional(readOnly = true)
//public class ProjectReportManagerServiceImpl extends AbstractService<ProjectReportEntity> implements ProjectReportManagerService{
//
//	@Override
//	@Transactional
//	public void back(Long id) {
//		ProjectReportEntity pro = __get(id);
//		pro.setStatus(0);
//		pro.setBack(1);
//		entityManager.merge(pro);
//	}
//
//	@Override
//	@Transactional
//	public void capital(Long id) {
//		
//		ProjectReportEntity pr = __get(id);
//		if(pr == null)throw new RuntimeException("该月度没有项目月报可上报");
//		InvestmentInfo in = pr.getInvestment();
//		if(null == in){
//			in = new InvestmentInfo();
//		}else{
//			in.clear();
//		}
//		int lc = 0;
//		double spend = 0D;
//		for(ProjectReportItem it : pr.getReports()){
//			in.sum(it.getInvestment().sumSelf());
//			lc += it.getLabourCount();
//			spend = DoubleHelper.add(spend, it.getSpend());
//			
//			entityManager.merge(it);
//		}
//
//		pr.setInvestment(in);
//		pr.setLabourCount(lc);
//		pr.setSpend(spend);
//		entityManager.merge(pr);
//	}
//
//	@Override
//	@Transactional
//	public void submit(Long id) {
//		ProjectReportEntity pr = __get(id);
//		if(pr == null)throw new RuntimeException("该月度没有项目月报可上报");
//		InvestmentInfo in = pr.getInvestment();
//		if(null == in){
//			in = new InvestmentInfo();
//		}else{
//			in.clear();
//		}
//		if(null != pr.getBack() && pr.getBack().intValue() == 1){
//			
//		}else{
//			
//		}
//		int lc = 0;
//		double spend = 0D;
//		for(ProjectReportItem it : pr.getReports()){
//			in.sum(it.getInvestment().sumSelf());
//			lc += it.getLabourCount();
//			spend = DoubleHelper.add(spend, it.getSpend());
//			
//			entityManager.merge(it);
//			
//			//对项目进行累加
//			ProjectEntity pe = it.getProject();
//			pe.getTotalInvestment().sum(in);
//			Integer slc = pe.getSumLabourCount();
//			if(slc == null) slc = 0;
//			slc += it.getLabourCount();
//			pe.setSumLabourCount(slc);
//			Double ss = pe.getSumSpend();
//			if(ss == null) ss = 0D;
//			ss += it.getSpend();
//			pe.setSumSpend(ss);
//			entityManager.merge(pe);
//		}
//
//		pr.setInvestment(in);
//		pr.setLabourCount(lc);
//		pr.setSpend(spend);
//		pr.setStatus(1);
//		entityManager.merge(pr);
//		
//		//累加到片区
//		ModelAreaEntity ma = pr.getModelArea();
//		InvestmentInfo mainv = ma.getInvestment();
//		if(null == mainv){
//			mainv = new InvestmentInfo();
//		}else{
//			mainv.clear();
//		}
//		mainv.sum(in);
//		ma.setInvestment(mainv);
//		entityManager.merge(ma);
//	}
//
//	@Override
//	public List<ProjectReportItem> reportItem(Long id) {
//		String ql = "select x from ProjectReportItem x where x.report.id=?";
//		return __list(ProjectReportItem.class, ql,id);
//		
//	}
//}
