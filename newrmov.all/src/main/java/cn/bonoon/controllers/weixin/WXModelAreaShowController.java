package cn.bonoon.controllers.weixin;







import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.BaseRuralService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.core.PeripheraRuralService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.web.controllers.AbstractController;

@Controller
@RequestMapping("m/show/mas")
public class WXModelAreaShowController extends AbstractController{
	@Autowired
	protected ModelAreaService modelAreaService;
	@Autowired
	protected NewRuralService newRuralService;
	@Autowired
	protected PeripheraRuralService peripheraRuralService;
	@Autowired
	protected BaseRuralService baseRuralService ;
	@RequestMapping("index.do")
	public String index(Model model){
		
		return "show/index";
	}
	@RequestMapping("index1.do")
	public String index1(Model model){
		
		return "show/timelist";
	}
	/**
	 * 显示示范片列表信息
	 * @param model
	 * @param batch
	 * @return
	 */
	@RequestMapping("load.batch")
	public String loadPlace(Model model,Integer batch2){
		String batch=null;
		if(batch2==0){
			batch="请选择";
		}else if(batch2==1){
			batch="一";
		}else if(batch2==2){
			batch="二";
		}else{
			batch="三";
		}
		StringBuilder place = new StringBuilder();
		List<Object[]> list=modelAreaService.getplace(batch);
		model.addAttribute("list",list);
		if(batch.equals("请选择") || batch=="请选择"){
			place.append("<p style='text-align:center;padding-top:50px;font-size:20px;'>请选择批次!</p>");
		}else{
			place.append("<div id='_tbody' style='font-size:19px;padding-left:5px;'><nav><ul data-role='listview' data-filter='true' data-filter-placeholder='搜索示范片区...'>");
			for(Object[] obj : list){
				place.append("<li><a href='loadTown?id=").append(obj[0]).append("'>");
				place.append(obj[1]).append("</a></li>");
		    }
			place.append("</ul></nav></div>");
		}
		model.addAttribute("batch1",batch).addAttribute("layout", "layout-weixin.vm");
		model.addAttribute("place",place).addAttribute("layout", "layout-weixin.vm");
		return "show/index";
	}
	/**
	 * 显示对应示范片主体村和非主体村列表
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("loadTown")
	public String loadTown(HttpServletRequest request, Model model,Long id){
		HttpSession session=request.getSession();
		session.setAttribute("ids",id);
		StringBuilder ss = new StringBuilder();
		
		//TODO 这里展示的时候需要修改的，行政村->自然村
		List<Object[]> listNewRural= Collections.emptyList(); //modelAreaService.getNewRurals(id);
		List<Object[]> listPreRural=Collections.emptyList(); //modelAreaService.getPeripheralRurals(id);
		Long mfId=modelAreaService.getNewRuralFirstId(id);
		Long mlId=modelAreaService.getNewRuralLastId(id);
		Long pfId=modelAreaService.getPeripheralFirstId(id);
		Long plId=modelAreaService.getPeripheralLastId(id);
		String name=modelAreaService.getPlaceName(id);
		ss.append("<section id='ma'><div data-role='header'><a data-role='button' data-icon='back' data-rel='back'>返回</a><h1>").append(name).append("</h1></div>");
		ss.append("<div data-role='content'><div data-role='collapsible' data-collapsed='false'><h1>主体村</h1>");
		ss.append("<nav><ul data-role='listview' data-inset='true' data-filter='true' data-filter-placeholder='搜索主体村...'>");
		for(Object[] obj : listNewRural){
			//ss.append("<nav><ul data-role='listview'><li style='height:52px;'><a href='loadTownInfo3?id=").append(obj[0]).append("'>");
			ss.append("<li><a href='loadTownInfo?mfId=").append(mfId).append("&mlId=").append(mlId).append("&id=").append(obj[0]).append("'>");
			ss.append(obj[1]).append("</a></li>");
		}
		ss.append("</ul></nav></div>");
		ss.append("<div data-role='collapsible'><h1>非主体村</h1>");
		ss.append("<nav><ul data-role='listview' data-inset='true' data-filter='true' data-filter-placeholder='搜索辐射村...'>");
		for(Object[] it : listPreRural){
			//ss.append("<nav><ul data-role='listview'><li style='height:52px;'><a href='loadTownInfo4?id=").append(it[0]).append("'>");
			ss.append("<li><a href='loadTownInfo1?pfId=").append(pfId).append("&plId=").append(plId).append("&id=").append(it[0]).append("'>");
			ss.append(it[1]).append("</a></li>");
		}
		ss.append("</ul></nav></div></div></section>");
		model.addAttribute("ss", ss).addAttribute("layout", "layout-weixin.vm");
		return "show/index2";
	}
	
	/**
	 * 显示核心村相片信息
	 * @param model
	 * @param id
	 * @param extId
	 * @param index
	 * @return
	 */
	@RequestMapping("loadTownInfo")
    public String loadTownInfo(HttpServletRequest request,Model model, Long id,String buildType, Integer index,Long mfId,Long mlId){
      try {
       StringBuilder s = new StringBuilder();
	   String name=null;
	   List<FileEntity> files=null;
	   FileEntity file = null;
	   boolean hideNextPage=false;
	   boolean hidePrevPage=false;
	   if(buildType==null){
		   buildType="建设前";
	   }
	   if(null==index){
		   index = 1;
	   }
	   int total=0;
	   if(null!=id){
		   if(index == 0){
			   if(buildType.equals("建设前")){
				   buildType = "建设中";
				   index = 1;
			   }
			   else if(buildType.equals("建设中")){
				   buildType = "建设后";
				   index = 1;	   
			   }
			   else if(buildType.equals("建设后")){
				   buildType = "建设前";
				   id+=1;
				   index = 1;
			   }
			   files = newRuralService.medias(id, 0, buildType);
			   total = files.size();
		   }
		   else if(index == -1){
			   if(buildType.equals("建设前")){
				   id=id-1;
				   index = 1;
			   }
			   else if(buildType.equals("建设中")){
				   buildType = "建设前";
				   index = 1;	
			   }
			   else if(buildType.equals("建设后")){
				   buildType = "建设中";
				   index = 1;	
			   }  
			   files = newRuralService.medias(id, 0, buildType);
			   total = files.size();
			   index = total;
			   if(buildType.equals("建设前") && id.equals(mfId) && index==1){
				   hidePrevPage=true;
			   }
		   }
		   else{
			   files = newRuralService.medias(id, 0, buildType);
			   total = files.size();
			   if(index == 1){
				   if(id.equals(mfId) && buildType.equals("建设前")){
					   hidePrevPage = true;
				   }
			   }
			   else if(index == total){
				   if(id.equals(mlId) && buildType.equals("建设后")){
					   hideNextPage = true;
				   }
			   }
			  
		   }
		   Integer prev=index - 1;
		   Integer next=index + 1;
		   if (null!=files && !files.isEmpty()) {
			   if(buildType.equals("建设后") && id.equals(mlId) && index==total){
				   hideNextPage=true;
			   }
			   for (int i = 1; i <= total; i++) {
				   if(i == index){
					   file = files.get(index-1);
				   }
			   }
		       if(total!=0){
	     	       if(index==total && index==1){
		        	   next=0;
		        	   prev = -1;
		           }
	     	       else if(index==total){
		        	   next=0;
		           }
	     	       else if(index==1){
	     	    	   prev = -1;
	     	       }
	  		   }   
		   }else{
			   index=0;
			   if(buildType.equals("建设后") && id.equals(mlId)){
				   hideNextPage=true;
			   }
			   if(buildType.equals("建设前") && id.equals(mfId) && index==0){
				   hidePrevPage=true;
			   }
     	       if(index==0){
     	    	   next=0;
     	    	   prev = -1;
     	       }
		   }
		   name=modelAreaService.getNewRural(id);
	       if(!hidePrevPage){
	    	   s.append("<a data-role='button' style='float:left;display:inline-block;width:20%' data-icon='arrow-l' href='loadTownInfo?mlId="+mlId+"&mfId="+mfId+"&buildType="+buildType+"&id="+id+"&index="+prev+"'>上一张</a>&nbsp&nbsp"); 
	       }
	       if(!hideNextPage){
	    	   s.append("<a data-role='button' style='float:right;display:inline-block;width:20%' data-icon='arrow-r' href='loadTownInfo?mfId="+mfId+"&mlId="+mlId+"&buildType="+buildType+"&id="+id+"&index="+next+"'>下一张</a>&nbsp&nbsp"); 
	       }
	       s.append("</div>");
	   }
	   
       s.append("<div data-role='footer'><p style='text-align:center;line-height:20px;height:20px;width:100%;font-size:18px;'><a style='float:left;padding-left:20px;text-decoration:none;' href='loadTownInfo?mfId="+mfId+"&mlId="+mlId+"&buildType=建设前&id="+id+"'>建设前</a>");
       s.append("<a style='text-align:center;text-decoration:none;' href='loadTownInfo?mfId="+mfId+"&mlId="+mlId+"&buildType=建设中&id="+id+"'>建设中</a>"); 
       s.append("<a style='float:right;text-decoration:none;padding-right:20px;' href='loadTownInfo?mfId="+mfId+"&mlId="+mlId+"&buildType=建设后&id="+id+"'>建设后</a></p></div><p style='height:5px;'></p>");  
       model.addAttribute("name",name);
       model.addAttribute("buildType",buildType);
       model.addAttribute("id",id);
       model.addAttribute("total",total);
       model.addAttribute("file",file);
       model.addAttribute("index",index);
       model.addAttribute("sss", s).addAttribute("layout", "layout-weixin.vm");
		
	} catch (Throwable t) {
		t.printStackTrace();
	}
	   return "show/index3";
    }
  
    /**
     * 显示非主体村相片信息
     * @param model
     * @param id
     * @param extId
     * @param index
     * @return
     */
	@RequestMapping("loadTownInfo1")
    public String loadTownInfo1(HttpServletRequest request,Model model, Long id,String buildType, Integer index,Long pfId,Long plId){

		try {
		       StringBuilder s = new StringBuilder();
			   String name=null;
			   List<FileEntity> files=null;
			   FileEntity file = null;
			   boolean hideNextPage=false;
			   boolean hidePrevPage=false;
			   if(buildType==null){
				   buildType="建设前";
			   }
			   if(null==index){
				   index = 1;
			   }
			   int total=0;
			   if(null!=id){
				   if(index == 0){
					   if(buildType.equals("建设前")){
						   buildType = "建设中";
						   index = 1;
					   }
					   else if(buildType.equals("建设中")){
						   buildType = "建设后";
						   index = 1;	   
					   }
					   else if(buildType.equals("建设后")){
						   buildType = "建设前";
						   id+=1;
						   index = 1;
					   }
					   files = peripheraRuralService.medias(id, 0, buildType);
					   total = files.size();
				   }
				   else if(index == -1){
					   if(buildType.equals("建设前")){
						   id=id-1;
					   }
					   else if(buildType.equals("建设中")){
						   buildType = "建设前";
						   index = 1;
					   }
					   else if(buildType.equals("建设后")){
						   buildType = "建设中";
						   index = 1;
					   }  
					   files = peripheraRuralService.medias(id, 0, buildType);
					   total = files.size();
					   index = total;
					   if(buildType.equals("建设前") && id.equals(pfId) && index==1){
						   hidePrevPage=true;
					   }
					  
				   }
				   else{
					   files = peripheraRuralService.medias(id, 0, buildType);
					   total = files.size();
					   if(index == 1){
						   if(id.equals(pfId) && buildType.equals("建设前")){
							   hidePrevPage = true;
						   }
					   }
					   else if(index == total){
						   if(id.equals(plId) && buildType.equals("建设后")){
							   hideNextPage = true;
						   }
					   }
				   }
				   Integer prev=index - 1;
				   Integer next=index + 1;
				   if (null!=files && !files.isEmpty()) {
					   if(buildType.equals("建设后") && id.equals(plId) && index==total){
						   hideNextPage=true;
					   }
					   for (int i = 1; i <= total; i++) {
						   if(i == index){
							   file = files.get(index-1);
						   }
					   }
				       if(total!=0){
			     	       if(index==total && index==1){
				        	   next=0;
				        	   prev = -1;
				           }
			     	       else if(index==total){
				        	   next=0;
				           }
			     	       else if(index==1){
			     	    	   prev = -1;
			     	       }
			  		   }   
				   }else{
					   index=0;
					   if(buildType.equals("建设后") && id.equals(plId)){
						   hideNextPage=true;
					   }
					   if(buildType.equals("建设前") && id.equals(pfId) && index==0){
						   hidePrevPage=true;
					   }
		     	       if(index==0){
		     	    	   next=0;
		     	    	   prev = -1;
		     	       }
				   }
				   name=modelAreaService.getPeripheral(id);
			       if(!hidePrevPage){
			    	   s.append("<a data-role='button' style='float:left;display:inline-block;width:20%' data-icon='arrow-l' href='loadTownInfo1?plId="+plId+"&pfId="+pfId+"&buildType="+buildType+"&id="+id+"&index="+prev+"'>上一张</a>&nbsp&nbsp"); 
			       }
			       if(!hideNextPage){
			    	   s.append("<a data-role='button' style='float:right;display:inline-block;width:20%' data-icon='arrow-r' href='loadTownInfo1?pfId="+pfId+"&plId="+plId+"&buildType="+buildType+"&id="+id+"&index="+next+"'>下一张</a>&nbsp&nbsp"); 
			       }
			       s.append("</div>");
			   }
			   
		       s.append("<div data-role='footer'><p style='text-align:center;line-height:20px;height:20px;width:100%;font-size:18px;'><a style='float:left;padding-left:20px;text-decoration:none;' href='loadTownInfo1?pfId="+pfId+"&plId="+plId+"&buildType=建设前&id="+id+"'>建设前</a>");
		       s.append("<a style='text-align:center;text-decoration:none;' href='loadTownInfo1?pfId="+pfId+"&plId="+plId+"&buildType=建设中&id="+id+"'>建设中</a>"); 
		       s.append("<a style='float:right;text-decoration:none;padding-right:20px;' href='loadTownInfo1?pfId="+pfId+"&plId="+plId+"&buildType=建设后&id="+id+"'>建设后</a></p></div><p style='height:5px;'></p>");  
		       model.addAttribute("name",name);
		       model.addAttribute("buildType",buildType);
		       model.addAttribute("id",id);
		       model.addAttribute("total",total);
		       model.addAttribute("file",file);
		       model.addAttribute("index",index);
		       model.addAttribute("sss", s).addAttribute("layout", "layout-weixin.vm");
				
			} catch (Throwable t) {
				t.printStackTrace();
			}
			   return "show/index3";
		    }
	
	
	
	
	/**
	 *时间轴-显示示范片列表信息
	 * @param model
	 * @param batch
	 * @return
	 */
	@RequestMapping("load1.batch")
	public String loadPlace1(Model model,Integer batch2){
		String batch=null;
		if(batch2==1){
			batch="一";
		}else if(batch2==2){
			batch="二";
		}else{
			batch="三";
		}
		StringBuilder place = new StringBuilder();
		List<Object[]> list=modelAreaService.getplace(batch);
		model.addAttribute("list",list);
		place.append(" <div class='ui-grid-a'>");
	    for(Object[] obj : list){
		   place.append("<div class='ui-block-b'><div class='ui-bar ui-bar-a'><a href='loadTownInfo5?id=").append(obj[0]).append("'>");
		   place.append(obj[1]).append("</a></div></div>");
		}
	    place.append("</div>");
		model.addAttribute("batch",batch).addAttribute("layout", "layout-weixin.vm");
		model.addAttribute("place",place).addAttribute("layout", "layout-weixin.vm");
		return "show/timelist1";
	}
	

	public class Info implements Comparable<Info>{
		private String viName;
		private String viType;
		private String path;
		private String docName;
		private String remake;
		private String ext;
		private String time;
		int i=0;
		public String getViName() {
			return viName;
		}
		public void setViName(String viName) {
			this.viName = viName;
		}
		public String getViType() {
			return viType;
		}
		public void setViType(String viType) {
			this.viType = viType;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getDocName() {
			return docName;
		}
		public void setDocName(String docName) {
			this.docName = docName;
		}
		public String getRemake() {
			return remake;
		}
		public void setRemake(String remake) {
			this.remake = remake;
		}
		public String getExt() {
			return ext;
		}
		public void setExt(String ext) {
			this.ext = ext;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		@Override
		public int compareTo(Info i) {
			return i.time.compareTo(this.time);
		}
		
	}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 显示对应片区所属村的信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("loadTownInfo5")
    public String loadTownInfo5(HttpServletRequest request,Model model,Long id){
		StringBuilder sb = new StringBuilder();
		String name=modelAreaService.getPlaceName(id);
		String time=null;
		String viName=null;
		String key = null;
		@SuppressWarnings("unchecked")
		Map<String, List<Info>> picMap = new LinkedMap();
		List<Info> ifs = new ArrayList<>();
		List<?> list= baseRuralService.findAllMedias(id);
		if(list.size()>0){
		 for(Object obj : list){
			Object[] objs = (Object[]) obj;
			time = objs[0]==null?sd.format(objs[8]):sd.format(objs[0]);
			viName=(String)objs[2];
			Info i=new Info();
			i.setViName(viName);
			i.setViType((String)objs[3]);
			i.setPath((String)objs[4]);
			i.setDocName((String)objs[5]);
			i.setRemake((String)objs[6]);
			i.setExt((Integer)objs[7] == 0?"图片":"视频");
			i.setTime(time);
			ifs.add(i);
		 }
		 Collections.sort(ifs);
		 for (Info i : ifs) {
				key = i.time +","+ i.viName;
				List<Info> picList = picMap.get(key);
				if(null == picList){
					picList = new ArrayList<>();
				}
				picList.add(i);
				picMap.put(key, picList);
		 }
//		 Object[] keys=picMap.keySet().toArray();
//		 Arrays.sort(keys);
		 for (String o : picMap.keySet()) {
		    String[] t=o.split(",");
			sb.append("<ul data-role='listview' data-inset='true'>");
			List<Info> tls = picMap.get(o);
			sb.append("<li data-role='list-divider'>").append(t[0]).append("</li>");
			sb.append("<li><h2>").append(t[1]).append("</h2><div style='width:300px;height:100%;'>");
			boolean isPic=false;
			boolean isVideo=false;
			
			for (Info tc : tls) {
				if(tc.getExt().equals("图片")){
					isPic=true;
					sb.append("<p style='width:75px;height:50px;padding-left:10px;float:left;'><a href='").append(tc.getPath()).append("' target='_blank'><img width='70px;' height='50px;' src='").append(tc.getPath()).append("'/></a></p>");
				}else{
					isVideo=true;
					sb.append("<p style='width:75px;height:50px;padding-left:10px;float:left;text-align:center;'><a style='width:70px;line-height:50px;height:50px;' target='_blank' href='").append(tc.getPath()).append("'>").append(tc.getDocName()).append("</a></p>");	
				}
			}
			sb.append("<p class='ui-li-aside'>上传了 "); 
			if(isPic&&isVideo)
				sb.append("图片和视频");
			else if(isPic)
				sb.append("图片");
			else
				sb.append("视频");
			sb.append("</p></div></li></ul>");
		 }
		}else{
		   sb.append("<div style='text-align:center;padding-top:80px;'><h3>暂无动态信息！</h3></div>");	
		}
		model.addAttribute("name",name);
		model.addAttribute("ss",sb).addAttribute("layout", "layout-weixin.vm");
		return "show/timelist2";
	}
}
