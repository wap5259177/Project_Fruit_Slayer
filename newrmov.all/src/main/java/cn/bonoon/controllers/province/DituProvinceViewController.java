package cn.bonoon.controllers.province;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.core.LocationPointService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ProjectReportService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaPointEntity;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.RuralPanoramaEntity;
import cn.bonoon.entities.RuralPointEntity;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.PanelModel;


@Controller
@RequestMapping("s/pl/ditu")
public class DituProvinceViewController extends AbstractModelAreaViewController{
	
	
	private final StringBuilder inc;
	
	
	@Autowired
	private ModelAreaService modelAreaService;
	@Autowired
	private LocationPointService locationPointService;
	@Autowired
	private ProjectReportService projectReportService;
	
	public DituProvinceViewController(){
		inc = new StringBuilder();
		inc.append("<script charset=\"utf-8\" src=\"http://map.qq.com/api/js?v=2.exp\"></script>");
	}
	private final String monthArray[] = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
	private class $FundsInfo{
		Map<Integer, Double> data = new HashMap<>();
		String name;//片区名
		void add(Object[] ds){
			if(null == name){
				name = ds[1].toString();
			}
			Integer key = (int)ds[2] * 100 + (int)ds[3];
			Double d = data.get(key);
			if(null != d){
				d += (double)ds[4];
			}else{
				d = (double)ds[4];
			}
			data.put(key, d);
		}
		//生成统计表的数据
		Double statistics(StringBuilder cd, int y, int m, int offset, Double sum){
			int mm = m + offset;
			if(mm >= 12){
				y++;
				mm -= 12;
			}
			Integer key = y * 100 + mm;
			Double d = data.get(key);
			if(null == d){
				d = 0d;
			}
			if(offset > 0){
				cd.append(',');
			}
			d += sum;
			cd.append(d);
			return d;
		}
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
		List<ModelAreaEntity> mas = modelAreaService.find(" and x.deleted=false order by ordinalModel asc ");//查出示范片县
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
					//非主体村
					pruralPoints.add(rp);
				}
			}
		}
		model.addObject("nruralPoints", nruralPoints);
		model.addObject("pruralPoints", pruralPoints);
		
		
		/*
		 * 查出所有的村全景点
		 */
		List<RuralPanoramaEntity> allPanos = locationPointService.findAllRuralPano();
		model.addObject("allPanos", allPanos);
		event.setVmPath("ditu/pl-view");
		//event.setVmPath("ditu/rural-panorama");
	}
	
	/*
	 * 如果创建了示范片点,就让他可点击.否则不能点击.
	 */
	private String __read(ModelAreaEntity ma, ModelAreaPointEntity map){
		if(map == null){
			return "<li>[" + ma.getCityName() + "]" + ma.getName() + "</li>";
		}
		return "<li>[" + ma.getCityName() + "]<a href='#' onclick=\"modelAreaChange(" + ma.getId() + "," + map.getId() + ");return false;\">" + ma.getName() + "</a></li>";
	}
	

	@RequestMapping(value = "!{mid}/loadStatistics.do")
	public String loadStatistics(Long id,Model model){

		//model.addAttribute("layout", "layout-empty.vm");
//		batch = StringHelper.get(batch, "一");
		
		int[] am = projectReportService.lastMonthly("一");
		if(null != am){
			int lastyear = am[0], lastmonth = am[1];
			Calendar cal = Calendar.getInstance();
			cal.set(lastyear, lastmonth, 1);
			cal.add(Calendar.YEAR, -1);
			int startyear = cal.get(Calendar.YEAR);
			int startMonth = cal.get(Calendar.MONTH);
			List<Object[]> list = projectReportService.getFundsByModelId(id, startyear, startMonth, lastyear, lastmonth);

			$FundsInfo fi = new $FundsInfo();
			for (Object[] ds : list) {
				fi.add(ds);
			}
			//取名称
			StringBuilder countyData = new StringBuilder();
			StringBuilder monthBuilder = new StringBuilder();
			countyData.append(",{name:'").append(fi.name).append("',type:'line',tooltip : {trigger: 'item'}, data: [");
//			boolean up = "1".equals(calType);
			Double sum = 0D;
			for(int i = 0; i < 12; i++){
				sum = fi.statistics(countyData, startyear, startMonth, i, sum);
//				if(up){
//					sum += cur;
//				}
				if(i == 0){
					monthBuilder.append("'").append(startyear).append("年");
				}else{
					monthBuilder.append(",'");
				}
				int mm = startMonth + i;
				boolean ny = true;
				if(mm >= 12){
					if(ny){
						int yy = startyear + 1;
						monthBuilder.append(yy).append("年");
						ny = false;
					}else{
					}
					mm -= 12;
				}else if(i > 0){
				}
				monthBuilder.append(monthArray[mm]).append("'");
			}
			countyData.append("]}");

//			countyNames.append("]");
			StringBuilder jsContent = new StringBuilder();
			jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
			jsContent.append("var option = {title:{text:'投入情况:',subtext:'省专项资金：'},tooltip : {'trigger':'axis'},");
			jsContent.append("legend: {x:'right',data:'").append(fi.name).append("',},xAxis : [{type : 'category',splitLine : {show : false},");
			jsContent.append("data : [").append(monthBuilder).append("]}],yAxis : [{type : 'value'}],series:[")
			.append(countyData.substring(1)).append("]};myChart.setOption(option);");
			
			
//			model.addAttribute("names", countyNames.substring(1));
//			model.addAttribute("countydata", countyData);
//			model.addAttribute("keyset", maps.keySet());
			model.addAttribute("jsContent", jsContent);
//			model.addAttribute("countyTable", countyTable);
		}else{
			JOptionPane.showMessageDialog(null,"没有数据");
		}
		return "ditu/ma-windowinfo";
	}
	
	@RequestMapping(value = "!{mid}/loadMapInfoWindow.do")
	public String loadMapInfoWindow(Long id,String type,Model model){
		
//		if("rural".equals(type)){
//			return "ditu/rural-windowinfo";
//		}
//		if("modelArea".equals(type)){

			int[] am = projectReportService.lastMonthly("一");
			if(null != am){
				int lastyear = am[0], lastmonth = am[1];
				Calendar cal = Calendar.getInstance();
				cal.set(lastyear, lastmonth, 1);
				cal.add(Calendar.YEAR, -1);
				int startyear = cal.get(Calendar.YEAR);
				int startMonth = cal.get(Calendar.MONTH);
				List<Object[]> list = projectReportService.getFundsByModelId(id, startyear, startMonth, lastyear, lastmonth);

				$FundsInfo fi = new $FundsInfo();
				for (Object[] ds : list) {
					fi.add(ds);
				}
				//取名称
				StringBuilder countyData = new StringBuilder();
				StringBuilder monthBuilder = new StringBuilder();
				countyData.append(",{name:'省专项资金',type:'line',tooltip : {trigger: 'item'}, data: [");
//				boolean up = "1".equals(calType);
				Double sum = 0D;
					boolean ny = true;
				for(int i = 0; i < 12; i++){
					sum = fi.statistics(countyData, startyear, startMonth, i, sum);
//					if(up){
//						sum += cur;
//					}
					if(i == 0){
						monthBuilder.append("'").append(startyear).append("年");
					}else{
						monthBuilder.append(",'");
					}
					int mm = startMonth + i;
					if(mm >= 12){
						if(ny){
							int yy = startyear + 1;
							monthBuilder.append(yy).append("年");
							ny = false;
						}
						mm -= 12;
					}else if(i > 0){
					}
					monthBuilder.append(monthArray[mm]).append("'");
				}
				countyData.append("]}");

//				countyNames.append("]");
				StringBuilder jsContent = new StringBuilder();
				jsContent.append("var myChart = echarts.init(document.getElementById('hc_line'));");
				jsContent.append("var option = {title:{text:'投入情况:',subtext:'").append(fi.name).append("：'},tooltip : {'trigger':'axis'},");
				jsContent.append("legend: {x:'right',data:['省专项资金']},xAxis : [{type : 'category',splitLine : {show : false},axisLabel :{show:true,interval:'auto',rotate:45},");
				jsContent.append("data : [").append(monthBuilder).append("]}],yAxis : [{type : 'value',axisLabel : {formatter: function (value) {return value + '万元';}}}],series:[")
				.append(countyData.substring(1)).append("]};myChart.setOption(option);");
				
				
//				model.addAttribute("names", countyNames.substring(1));
//				model.addAttribute("countydata", countyData);
//				model.addAttribute("keyset", maps.keySet());
				model.addAttribute("jsContent", jsContent);
//				model.addAttribute("countyTable", countyTable);
			}else{
				JOptionPane.showMessageDialog(null,"没有数据");
			}
			return "ditu/ma-windowinfo";
//		}
//		return "";
	}

	public class File implements Comparable<File>{
        private Integer year;
        private Date time;
        private String name;
        private String remake;
        private String mapPath;
        private int type;
        
		@Override
		public int compareTo(File o) {
			
			return o.time.compareTo(this.time);
		}
		public Integer getYear() {
			return year;
		}
		public void setYear(Integer year) {
			this.year = year;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRemake() {
			return remake;
		}
		public void setRemake(String remake) {
			this.remake = remake;
		}
		public String getMapPath() {
			return mapPath;
		}
		public void setMapPath(String mapPath) {
			this.mapPath = mapPath;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
	public class TimeClass implements Comparable<TimeClass>{
	    private Integer year;
		@SuppressWarnings("unchecked")
		private Map<String, List<File>> fileMap = new LinkedMap();
		List<File> fi=new ArrayList<>();
		private void set(File f){
			Date time = f.getTime();
			String issueAt = sdf.format(time);
			List<File> list = fileMap.get(issueAt);
			if(null == list)
				list = new ArrayList<>();
			list.add(f);
			fileMap.put(issueAt, list);
			
		}
		

		@Override
		public int compareTo(TimeClass o) {
			
			return this.year.compareTo(o.year);
		}

		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}
		
		
	}
	
//	private static final Comparator<FileEntity> COMPARATOR = new Comparator<FileEntity>() {
//	       public int compare(FileEntity f1, FileEntity f2) {
//		        return f1.compareTo(f2);
//	      
//	       }
//	};
	/**
	 * 显示核心村相片和視頻信息
	 * @param model
	 * @param id
	 * @param buildType
	 * @return
	 */
	@RequestMapping(value = "!{key}/loadTownInfo.detail")
    public String loadTownInfo3(HttpServletRequest request,Model model,Long id){
       StringBuilder sb = new StringBuilder();
       StringBuilder sb2 = new StringBuilder();
	   List<FileEntity> files=null;      //查询出的file集合
	   List<File> fi=new ArrayList<>();
	   Date time1=null;  //创建日期
	   @SuppressWarnings("unchecked")
	   Map<Integer, TimeClass> picMap = new LinkedMap();
	   int yearKey = 0;
	   if(null!=id){
		   files = newRuralService.mediasTime(id);
	   }
	   if(files.size()>0){
		   for(FileEntity fis : files){
			   Date time = fis.getIssueAt()==null?fis.getCreateAt():fis.getIssueAt();
			   Calendar c1 = Calendar.getInstance();
			   c1.setTime(time);
			   File fe=new File();
			   fe.setYear(c1.get(Calendar.YEAR));
			   fe.setTime(time);
			   fe.setName(fis.getName());
			   fe.setMapPath(fis.getMapPath());
			   fe.setRemake(fis.getRemark());
			   fe.setType(fis.getExtattr1());
			   fi.add(fe);
		   }
		   Collections.sort(fi);   
		   for(File f : fi){
			   time1 = f.getTime();
			   Calendar c1 = Calendar.getInstance();
			   c1.setTime(time1);
			   yearKey = c1.get(Calendar.YEAR);
			   TimeClass tc = picMap.get(yearKey);
			   if(null == tc){
				   tc = new TimeClass();
			   }
			   tc.setYear(yearKey);
			   tc.set(f);
			   picMap.put(yearKey, tc);
		 }
		
	    sb2.append("<ul class='event_year' style='position:fixed;'>");
	    for (Integer year : picMap.keySet()) {
		   Calendar c1 = Calendar.getInstance();
		   Integer i=c1.get(Calendar.YEAR);
		   if(year.equals(i)){
			   sb2.append("<li class='current'><label for='").append(year).append("'>").append(year).append("</label></li>");
		   }else{
			   sb2.append("<li><label for='").append(year).append("'>").append(year).append("</label></li>");
		   }
	    }
	    sb2.append("</ul>");
	    sb.append("<ul class='event_list'>");
	    for (Integer year : picMap.keySet()) {
	       TimeClass tc = picMap.get(year);
		   sb.append("<div><h3 id='").append(tc.getYear()).append("'>").append(year).append("</h3>");
		   
		   for (String issueAt : tc.fileMap.keySet()) {
			   sb.append("<li><span>").append(issueAt).append("</span><p><span id='box'>");
			   List<File> ft = tc.fileMap.get(issueAt);
//			   if(ft.size()>0){
//				   Collections.sort(ft, COMPARATOR);
//			   }
			   for (File f : ft) {
				   if(f.getType() == 0){
					   //class='bupabutton' onclick='show(this)'
					   sb.append("<a href='").append(f.getMapPath()).append("' target='_blank'><img style='padding-left:10px;' width='80px;' height='50px;' title='").append(f.getRemake()).append("' src='").append(f.getMapPath()).append("'></a>");
				   }else{
					   sb.append("<a style='padding-left:10px;' target='_blank' href='").append(f.getMapPath()).append("'>").append(f.getName()).append("</a>");
				   }
					   
			   }
			   sb.append("</span></p></li>");
		   }
		   sb.append("</div>");
	    }
	    sb.append("</ul>");
	   }else{
		   sb.append("<p style='padding-left:200px;padding-top:90px;font-size:20px;font-weight:bold;'>当前没有图片和视频信息!</p>");
	   }
	   model.addAttribute("sss", sb.toString());
	   model.addAttribute("ss", sb2.toString()).addAttribute("layout", "layout-empty.vm");
	   
	   return "show/timeline";
	  
    }
	
	/**
	 * 显示非主体村相片和視頻信息
	 * @param model
	 * @param id
	 * @param buildType
	 * @return
	 */
	
	@RequestMapping(value = "!{key}/loadTownInfo1.detail")
    public String loadTownInfo4(HttpServletRequest request,Model model,Long id,String buildType){
	   StringBuilder sb = new StringBuilder();
       StringBuilder sb2 = new StringBuilder();
	   List<FileEntity> files=null;      //查询出的file集合
	   List<File> fi=new ArrayList<>();
	   Date time1=null;  //创建日期
	   int yearKey = 0;
	   @SuppressWarnings("unchecked")
	   Map<Integer, TimeClass> picMap = new LinkedMap();
	   if(null!=id){
	      files = peripheraRuralService.mediasTime(id);
	   }
	   if(files.size()>0){
		   for(FileEntity fis : files){
			   Date time = fis.getIssueAt()==null?fis.getCreateAt():fis.getIssueAt();
			   Calendar c1 = Calendar.getInstance();
			   c1.setTime(time);
			   File fe=new File();
			   fe.setYear(c1.get(Calendar.YEAR));
			   fe.setTime(time);
			   fe.setName(fis.getName());
			   fe.setMapPath(fis.getMapPath());
			   fe.setRemake(fis.getRemark());
			   fe.setType(fis.getExtattr1());
			   fi.add(fe);
		   }
		   Collections.sort(fi);   
		   for(File f : fi){
			   time1 = f.getTime();
			   Calendar c1 = Calendar.getInstance();
			   c1.setTime(time1);
			   yearKey=c1.get(Calendar.YEAR);
			   TimeClass tc = picMap.get(yearKey);
			   if(null == tc){
				   tc = new TimeClass();
			   }
			   tc.setYear(yearKey);
			   tc.set(f);
			   picMap.put(yearKey, tc);
		   	}    
	    sb2.append("<ul class='event_year'>");
	    for (Integer year : picMap.keySet()) {
		   Calendar c1 = Calendar.getInstance();
		   Integer i=c1.get(Calendar.YEAR);
		   if(year.equals(i)){
			   sb2.append("<li class='current'><label for='").append(year).append("'>").append(year).append("</label></li>");
		   }else{
			   sb2.append("<li><label for='").append(year).append("'>").append(year).append("</label></li>");
		   }
	    }
	    sb2.append("</ul>");
	    sb.append("<ul class='event_list'>");
	    for (Integer year : picMap.keySet()) {
		   sb.append("<div><h3 id='").append(year).append("'>").append(year).append("</h3>");
		   TimeClass tc = picMap.get(year);
		   for (String issueAt : tc.fileMap.keySet()) {
			   sb.append("<li><span>").append(issueAt).append("</span><p><span id='box'>");
			   List<File> ft = tc.fileMap.get(issueAt);
//			   if(ft.size()>0){
//				   Collections.sort(ft, COMPARATOR);
//			   }
			   for (File f : ft) {
				   if(f.getType() == 0){
					   //class='bupabutton' onclick='show(this)'
					   sb.append("<a href='").append(f.getMapPath()).append("' target='_blank'><img style='padding-left:10px;' width='80px;' height='50px;' title='").append(f.getRemake()).append("' src='").append(f.getMapPath()).append("'></a>");
				   }else{
					   sb.append("<a style='padding-left:10px;' target='_blank' href='").append(f.getMapPath()).append("'>").append(f.getName()).append("</a>");
				   }
					   
			   }
			   sb.append("</span></p></li>");
		   }
		   sb.append("</div>");
	    }
	    sb.append("</ul>");
	   }else{
		   sb.append("<p style='padding-left:200px;padding-top:90px;font-size:20px;font-weight:bold;'>当前没有图片和视频信息!</p>");
	   }
	   model.addAttribute("sss", sb.toString());
	   model.addAttribute("ss", sb2.toString()).addAttribute("layout", "layout-empty.vm");
	   
	   return "show/timeline";
	  
    }
	
	
	/**
	 * TODO:
	 * 查看村子全景
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "!{key}/loadPanorama.do")
    public String loadPanorama(Long id,Model model){
		
		/*
		 * 通过村子id 查找 他下面的所有全景图片和缩略图
		 * 		一个村子下面会有多张全景图
		 */
		List<RuralPanoramaEntity> ruralPanos = locationPointService.findRuralPanoByRuralId(id);
		model.addAttribute("ruralPanos", ruralPanos);
		return "ditu/rural-panorama";
	}
	
}
