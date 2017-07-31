package cn.bonoon.controllers.province;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.core.LocationPointService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.PointEmbeddable;
import cn.bonoon.entities.RuralPanoramaEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pl/ditu/panorama")
public class ProvincePanoramaController extends AbstractModelAreaViewController{
private final StringBuilder inc;
	
	@Autowired
	private ModelAreaService modelAreaService;
	@Autowired
	private LocationPointService locationPointService;
	
	public ProvincePanoramaController(){
		inc = new StringBuilder();
		inc.append("<script charset=\"utf-8\" src=\"http://map.qq.com/api/js?v=2.exp\"></script>");
	}
	
	
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		model.addIncludes(inc);
		//1.取出所有批次的示范片区
		//String[] batchs = {"一", "二", "三"};
		String batch1 = "", batch2 = "", batch3 = "", batch4 = "";
		/*
		 * 示范片县里面,包括已编辑过的,和未编辑过的.
		 */
		List<ModelAreaEntity> mas = modelAreaService.find(" and x.deleted=false");//查出示范片县
		List<ModelAreaPointEntity> mps = locationPointService.getAll();//查出创建了示范片县,且编辑过.
		NEXT : for(ModelAreaEntity ma : mas){
			String batch = ma.getBatch();//获取示范片的批次
			if(StringHelper.isNotEmpty(batch)){//不为null,且不等于""
				ModelAreaPointEntity map = null;
				for(ModelAreaPointEntity mp : mps){
					if(mp.getModelArea().getId().equals(ma.getId())){
						map = mp;
						break;
					}
				}
				switch (batch) {
				case "一":
					batch1 += __read(ma, map);
					break;
				case "二":
					batch2 += __read(ma, map);
					break;
				case "三":
					batch3 += __read(ma, map);
					break;
				case "珠三角":
					batch4 += __read(ma, map);
					break;
				default: break NEXT;
				}
			}
		}
		model.addObject("batch1", batch1)
			.addObject("batch2", batch2)
			.addObject("batch3", batch3)
			.addObject("batch4", batch4);
		model.addObject("mapoints", mps);
		
		/*
		 * 查出所有的村子,村子点
		 * 
		 */
		
		List<RuralPointEntity> rps = locationPointService.findAllRuralPoint();
		List<RuralPointEntity> nruralPoints = new ArrayList<RuralPointEntity>();
		List<RuralPointEntity> pruralPoints = new ArrayList<RuralPointEntity>();
		if(rps!=null&&rps.size()>0){
			for(RuralPointEntity rp:rps){
				if(rp.getRural() instanceof NewRuralEntity){
					//核心村
					nruralPoints.add(rp);
					
				}
				if(rp.getRural() instanceof PeripheralRuralEntity){
					//辐射村
					pruralPoints.add(rp);
				}
			}
		}
		model.addObject("nruralPoints", nruralPoints);
		model.addObject("pruralPoints", pruralPoints);
		

		/*
		 * TODO:查出所有的全景点
		 * 		隐藏
		 * 		地图尺寸放大到一定程度,显示全景点图标
		 * 		点击全景点图标,弹出Infowindow框可更新全景点图片
		 */
		List<RuralPanoramaEntity> ruralPanos = locationPointService.findAllRuralPano();
		model.addObject("ruralPanos", ruralPanos);
		
		event.setVmPath("ditu/pl-editor");
		
		
		
	}

	/**
	 * 双击地图,load村子全景编辑内容
	 * @param mapid
	 * @param latitude
	 * @param longitude
	 * @param model
	 * @return
	 */
	@RequestMapping(value="!{key}/loadPanoramaEditor.do")
	public String loadPanoramaEditor(Long mapid,Long panoid,Double latitude,Double longitude,Model model){
		model.addAttribute("id", mapid);
		//片区点下的所有村子点
		List<RuralPointEntity> rps = locationPointService.findRuralPointByMapointId(mapid);
		ModelAreaPointEntity map = locationPointService.findModelAreaPointById(mapid);
		RuralPanoramaEntity ruralPano = locationPointService.findRuralPanoById(panoid);
		model.addAttribute("ruralPano", ruralPano);
		model.addAttribute("map", map);
		model.addAttribute("rps", rps);
		model.addAttribute("latitude", latitude);
		model.addAttribute("longitude", longitude);
		
		return "ditu/panorama-content-editor";
	}
	
	@Autowired
	private FileManager fileManager;
	/**
	 * 保存全景图
	 * @param ruralid
	 * @param mapid
	 * @param thumbnail
	 * @param panorama
	 * @param latitude
	 * @param longitude
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="!{key}/savePanorama.do")
	public Map<String,Object> savePanorama(Long ruralpanoid,Long ruralid,Long mapid,MultipartFile thumbnail,MultipartFile panorama,Double latitude,Double longitude,Model model){
		String mapPath = "/modelarea/area_" + mapid;// + "/other"
		mapPath += "/rural/rural_" + ruralid+"/panorama";
		Map<String ,Object> _map = new HashMap<String,Object>();
		if(ruralpanoid==null){//插入
			RuralPanoramaEntity ruralpano = new RuralPanoramaEntity();
			try {
				if (null != panorama && !panorama.isEmpty()) {
					FileInfo p_fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, panorama);
					ruralpano.setPanorama(p_fi.getRelativePath());//全景图
				}
				if (null != thumbnail && !thumbnail.isEmpty()) {
					FileInfo t_fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, thumbnail);
					ruralpano.setThumbnail(t_fi.getRelativePath());//缩略图
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			RuralPointEntity ruralpoint = locationPointService.findRuralPointByRuralId(ruralid);
			ruralpano.setModelArea(ruralpoint.getModelArea());//片区
			ruralpano.setModelAreaPoint(ruralpoint.getModelAreaPoint());//片区点
			
			BaseRuralEntity rural = locationPointService.findRuralById(ruralid);
			ruralpano.setRural(rural);//村子
			PointEmbeddable p  = new PointEmbeddable();
			p.setLatitude(latitude);
			p.setLongitude(longitude);
			ruralpano.setPoint(p);//全景点
			locationPointService.savePanoramaPoint(ruralpano);
			
			_map.put("panoramaUrl", ruralpano.getPanorama());
			_map.put("operate", "0");
			
//			return ruralpano.getPanorama();
		}else{
			RuralPanoramaEntity ruralpano = locationPointService.findRuralPanoById(ruralpanoid);
			try {
				if (null != panorama && !panorama.isEmpty()) {
					FileInfo p_fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, panorama);
					ruralpano.setPanorama(p_fi.getRelativePath());//全景图
				}
				if (null != thumbnail && !thumbnail.isEmpty()) {
					FileInfo t_fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, thumbnail);
					ruralpano.setThumbnail(t_fi.getRelativePath());//缩略图
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			BaseRuralEntity rural = locationPointService.findRuralById(ruralid);
			ruralpano.setRural(rural);
			locationPointService.savePanoramaPoint(ruralpano);
			//跟新
			_map.put("panoramaUrl", ruralpano.getPanorama());
			_map.put("operate", "1");
		}
		return _map;
	}
	
	
	private String __read(ModelAreaEntity ma, ModelAreaPointEntity map){
		if(map == null){
			return "<li>[" + ma.getCityName() + "]" + ma.getName() + "</li>";
		}
		return "<li>[" + ma.getCityName() + "]<a href='#' onclick=\"modelAreaChange(" + ma.getId() + "," + map.getId() + ");return false;\">" + ma.getName() + "</a></li>";
	}
	
}
