package cn.bonoon.controllers;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.core.LocationPointService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.PointEmbeddable;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 地图编辑
 *
 */
@Controller
@RequestMapping("s/cl/ditu/editor")
public class DituCountyController extends AbstractPanelController {
	private final StringBuilder inc;

	public DituCountyController() {
		inc = new StringBuilder();
		inc.append("<script charset=\"utf-8\" src=\"http://map.qq.com/api/js?v=2.exp&libraries=drawing\"></script>");//加载腾讯地图api
	}

	
	@Autowired
	private LocationPointService locationPointService;

	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		model.addIncludes(inc);
		LogonUser user = getUser();
		Long oid = user.getOwnerId();// unit
		// name:揭东县示范片区 oid:445221000000
		boolean isModelArea = true;
		ModelAreaPointEntity modelAreapoint = locationPointService
				.findModelAreaPointByCurrUser(oid);// 根据当前县取出示范片区
		model.addObject("modelAreapoint", modelAreapoint);
		if (modelAreapoint != null) {//1.如果已经编辑过的，则加载地图的编辑信息
			model.addObject("cityName", modelAreapoint.getModelArea().getCityName());
			model.addObject("countyName", modelAreapoint.getModelArea().getCounty());
			model.addObject("modelAreadesc", modelAreapoint.getDescribe());
			model.addObject("modelArea", modelAreapoint.getModelArea());
			
			model.addObject("pic", modelAreapoint.getPicture());
			
			//只有示范片区编辑过,才能编辑村子
			List<NewRuralEntity> nrurals = locationPointService.findAllNewRuralBymodelAreaId(modelAreapoint.getModelArea().getId());
			List<PeripheralRuralEntity> prurals = locationPointService.findAllPeripheralRuralBymodelAreaId(modelAreapoint.getModelArea().getId());
			model.addObject("nrurals", nrurals);
			model.addObject("prurals", prurals);
		} else {
			// 2.未编辑过,根据县的名称,在地图上定位到该县.这是需要保存县的搜索名称和搜索后返回的坐标
			ModelAreaEntity modelArea = locationPointService
					.findModelAreaByCurrUser(oid);
			if (modelArea != null) {
				model.addObject("cityName", modelArea.getCityName());
				model.addObject("countyName", modelArea.getCounty());
				model.addObject("modelAreadesc", modelArea.getIntroduce());
				model.addObject("modelArea", modelArea);
			}else{//3.这个县不属于示范片区,让他定位到广东省
				isModelArea = false;
			}
		}
		model.addObject("isModelArea", isModelArea);
		event.setVmPath("ditu/cl-editor");
	}
	
	//----------------------------------------------------------------------
	
	/*
	 * 加载片区编辑div
	 */
//	@RequestMapping(value = "!{mid}/loadMapointEditor.do",method = {RequestMethod.POST})
//	public String loadMapointEditor(Long id,Model model){
//			ModelAreaPointEntity mapoint = locationPointService.findModelAreaPointById(id);
//			model.addAttribute("modelAreapoint", mapoint);
//			return "ditu/ma-content-editor";
//	}
	
	/*
	 * 加载村子编辑div
	 */
	@RequestMapping(value = "!{mid}/loadRuralEditor.do",method = {RequestMethod.POST})
	public String loadRuralEditor(Long id,Model model){
		
		RuralPointEntity ruralPoint = locationPointService.findRuralPointByRuralId(id);
		BaseRuralEntity rural = locationPointService.findRuralById(id);
		ModelAreaPointEntity mapoint = locationPointService.findModelAreaPointByCurrUser(getUser().getOwnerId());
		
		if(rural instanceof NewRuralEntity){
			model.addAttribute("rural_title", "核心村名");
		}else{
			model.addAttribute("rural_title","非主体村名");
		}
		model.addAttribute("rural", rural);
		model.addAttribute("map", mapoint);
		model.addAttribute("ruralPoint", ruralPoint);
		
		return "ditu/r-content-editor";
	}
	
	
	/*
	 * 示范片编辑后提交异步请求
	 */
	@Autowired
	private FileManager fileManager;
	@ResponseBody
	@RequestMapping(value = "!{mid}/save.do",method = {RequestMethod.POST})
	public String saveModelAreaPont(Long modelareapointid, Long modelareaid, String serchname,
			String title,Double lng,Double lat,String desc,MultipartFile pic,String polygonStr){
		PointEmbeddable point = new PointEmbeddable();//示范片点的坐标
		
			/*
			 * 第一次编辑时(插入),做判断:如果片区风貌图为null(即没有上传),则抛出异常,提示要上传图片
			 * 第二次编辑时(更新),不做判断.
			 */
//			if (null == pic || pic.isEmpty()) {
//				try {
//					throw new Exception("请选择需要上传的文件！");
//				} catch (Exception e) {
//					e.printStackTrace();
//					return "2";
//				}
//			}
			String mapPath = "/modelarea/area_" + modelareaid +"/other";
			try {
				if(modelareapointid==null){//说明要保存,插入数据
					ModelAreaEntity ma = locationPointService.findModelAreaById(modelareaid);
					ModelAreaPointEntity modelAreaPointEntity = new ModelAreaPointEntity();
					if (null != pic && !pic.isEmpty()) {
						FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, pic);
	
						modelAreaPointEntity.setPicture(fi.getRelativePath());//						try {
	//							throw new Exception("请选择需要上传的文件！");
	//						} catch (Exception e) {
	//							e.printStackTrace();
	//							return "2";//未上传文件
	//						}
					}
					modelAreaPointEntity.setModelArea(ma);
					modelAreaPointEntity.setDescribe(desc);
					modelAreaPointEntity.setName(serchname);
					modelAreaPointEntity.setTitle(title);
					
					point.setLatitude(lat);
					point.setLongitude(lng);
					modelAreaPointEntity.setPoint(point );
					
					//Set<PointEmbeddable> polygon = new HashSet<PointEmbeddable>();//示范片的范围
					//strChangeToSet(polygon, polygonStr);
					modelAreaPointEntity.setPolygon(polygonStr);
					locationPointService.commitModelAreaPoint(modelAreaPointEntity);
					return "0";//插入
				}else{//编辑:		跟新操作	
					
					
					
					
					ModelAreaPointEntity modelap = locationPointService.findModelAreaPointById(modelareapointid);
					if (null != pic && !pic.isEmpty()) {
						FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, pic);
						modelap.setPicture(fi.getRelativePath());
					}

					
					modelap.setDescribe(desc);
					point.setLatitude(lat);
					point.setLongitude(lng);
					modelap.setPoint(point);
					//后面加进来的
					modelap.setPolygon(polygonStr);
					locationPointService.updateModelAreaPoint(modelap);
					return "1";//更新
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return "3";//系统错误

			} catch (IOException e) {
				e.printStackTrace();
				return "3";
			}
		
		
	}
	
	/*
	 * 村子编辑后提交异步请求
	 * //这里我们不用考虑示范区是否编辑过,因为在前面的方法中我们已经处理过了,只有编辑过的示范区才会有显示村子的编辑信息
	 */
	@ResponseBody
	@RequestMapping(value = "!{mid}/ruralcommit.do",method = {RequestMethod.POST})
	public String ruralCommit(Double lon,Double lat,String serchName,String title,
			String describe,MultipartFile picture,Long id){
		try {
		
		//通过村子的Id查找村位置(ruralpoint),如果为空说明是第一次编辑,要插入.如果不为空,说明是更新村位置的信息.
		RuralPointEntity ruralPoint = locationPointService.findRuralPointByRuralId(id);
//		if (null == picture || picture.isEmpty()) {
//			try {
//				throw new Exception("请选择需要上传的文件！");
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "3";
//			}
//		}
		if(ruralPoint==null){//创建
			ruralPoint = new RuralPointEntity();
			ModelAreaPointEntity map = locationPointService.findModelAreaPointByCurrUser(getUser().getOwnerId());//可以编辑村信息了,说明这个示范片区肯定是存在的,并且已经编辑过.
			ModelAreaEntity ma = locationPointService.findModelAreaByCurrUser(getUser().getOwnerId());
			BaseRuralEntity rural = locationPointService.findRuralById(id);
			String mapPath = "/modelarea/area_" + ma.getId() +"/other";
			
			if (null != picture && !picture.isEmpty()) {
				FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, picture);
				ruralPoint.setPicture(fi.getRelativePath());
			}
			
			
			//FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, picture);
			ruralPoint.setModelAreaPoint(map);
			ruralPoint.setModelArea(ma);
			ruralPoint.setRural(rural);
			ruralPoint.setName(serchName);
			ruralPoint.setTitle(title);
			//ruralPoint.setPicture(fi.getRelativePath());
			ruralPoint.setDescribe(describe);
			PointEmbeddable point = new PointEmbeddable();
			point.setLatitude(lat);
			point.setLongitude(lon);
			ruralPoint.setPoint(point);
			locationPointService.commitRuralPoint(ruralPoint);
			return "1";
		}else{//更新
			String mapPath = "/modelarea/area_" + ruralPoint.getModelArea().getId() +"/other";
			//FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, picture);
			
			if (null != picture && !picture.isEmpty()) {
				FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, picture);
				ruralPoint.setPicture(fi.getRelativePath());
			}
			
			ruralPoint.setName(serchName);
			ruralPoint.setTitle(title);
			//ruralPoint.setPicture(fi.getRelativePath());
			ruralPoint.setDescribe(describe);
			PointEmbeddable point = new PointEmbeddable();
			point.setLatitude(lat);
			point.setLongitude(lon);
			ruralPoint.setPoint(point);
			locationPointService.updateRuralPoint(ruralPoint);
			return "2";
		}		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	//查看示范片的范围
//	@ResponseBody
//	@RequestMapping(value = "!{mid}/showMapointScope.do",method = {RequestMethod.POST})
//	public List<PointEmbeddable> showMapointScope(Long id){
//		List<RuralPointEntity> rps = locationPointService.findRuralPointByMapointId(id);
//		List<PointEmbeddable> points = new ArrayList<PointEmbeddable>();
//		if(rps!=null){
//			for(RuralPointEntity r:rps){
//				points.add(r.getPoint());
//			}
//		}
//		return points;
//	}
	
	//查看核心村图标的位置
	@ResponseBody
	@RequestMapping(value = "!{mid}/showRuralMarker.do",method = {RequestMethod.POST})
	public List<PointEmbeddable> showNruralMarker(Long id,String type){
		List<RuralPointEntity> rps = locationPointService.findRuralPointByMapointId(id);
		List<PointEmbeddable> pPoints = new ArrayList<PointEmbeddable>();
		List<PointEmbeddable> nPoints = new ArrayList<PointEmbeddable>();
		if(rps!=null){//片区下面创建有村子
			for(RuralPointEntity r:rps){
				if(r.getRural() instanceof NewRuralEntity){
					nPoints.add(r.getPoint());
				}else if(r.getRural() instanceof PeripheralRuralEntity){
					pPoints.add(r.getPoint());
				}
			}
			if("核心村".equals(type) &&nPoints!=null){
				return nPoints;
			}else if("非主体村".equals(type) && pPoints!=null){
				return pPoints;
			}else{
				return null;
			}
		}else{//片区下面没有创建村子
			return null;
		}
	}
	
	
	/*
	 * 查看示范片是创建有多边形覆盖物
	 */
//	@ResponseBody
//	@RequestMapping(value = "!{mid}/findMaPolygon.do",method = {RequestMethod.POST})
//	public String findMaPolygon(Long id){
//		ModelAreaPointEntity map = locationPointService.findModelAreaPointById(id);
////		if(map!=null){
////			if(map.getPolygon()!=null&&map.getPolygon().size()>0){
////				
////			}
////		}
//		return map.getPolygon();
//	}
	
	
//	public void strChangeToSet(Set<PointEmbeddable> polygon,String str){
//		String[] pg = str.split(";");
//		String[] latLng = null;
//		for(int i=0;i<pg.length;i++){
//			PointEmbeddable p = new PointEmbeddable();
//			if(pg[i]!=null&&!"".equals(pg[i])){
//				latLng= pg[i].split(",");
//				p.setLatitude(Double.valueOf(latLng[0].trim()));
//				p.setLongitude(Double.valueOf(latLng[1].trim()));
//				polygon.add(p);
//			}
//		}
//	}
	
}
