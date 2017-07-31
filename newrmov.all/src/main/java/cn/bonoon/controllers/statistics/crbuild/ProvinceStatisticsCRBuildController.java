package cn.bonoon.controllers.statistics.crbuild;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.bytecode.buildtime.spi.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.controllers.EmpowerVerification;
import cn.bonoon.core.CRBuildQuarterReportService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.StatisticsCRBuildService;
import cn.bonoon.core.plugins.LoginService;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterBatch;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterEntity;
import cn.bonoon.entities.ruralBuild.ModelAreaCRBuildQuarterItem;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/pls/sms/crbuild")
public class ProvinceStatisticsCRBuildController
extends
AbstractGridController<ModelAreaCRBuildQuarterEntity, ProvinceStatisticsCRBuildItem> {

	private StatisticsCRBuildService statisticsCRBuildService;

	private ModelAreaService modelAreaService;
	
	private CRBuildQuarterReportService crBuildQuarterReportService;
	
	@Autowired
	public ProvinceStatisticsCRBuildController(
			StatisticsCRBuildService statisticsCRBuildService,ModelAreaService modelAreaService,CRBuildQuarterReportService crBuildQuarterReportService) {
		super(statisticsCRBuildService);
		this.statisticsCRBuildService = statisticsCRBuildService;
		this.modelAreaService=modelAreaService;
		this.crBuildQuarterReportService = crBuildQuarterReportService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false,// 去除删除按钮
	//	detailClass = ProvinceStatisticsCRBuildDetail.class,
	updateClass = ProvinceStatisticsCRBuildEditor.class, insertClass = ProvinceStatisticsCRBuildEditor.class, editStatus = 0)
	protected StatisticsCRBuildService initLayoutGrid(
			LayoutGridRegister register) throws Exception {
		// register.button("刷新", "index.refresh", ButtonEventType.GET,
		// ButtonRefreshType.FINISH).status(0).ordinal(22);
		register.button("结束", "index.finish", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0).ordinal(23);
		register.button("查看", "index.watch", ButtonEventType.DIALOG).ordinal(50);
		register.button("删除", "index.deleteView", ButtonEventType.DIALOG).ordinal(50);

		
		// register.button("锁定/解锁", "index.lock", ButtonEventType.GET,
		// ButtonRefreshType.FINISH).status(0,100).ordinal(24);
		// register.button("打印", "index.print",
		// ButtonEventType.JUMP).ordinal(50);

		return statisticsCRBuildService;
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.refresh", method = GET)
	public JsonResult refreshItem(Long id){
		try{
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}

	@RequestMapping(value = "!{key}/index.deleteView")
	public ModelAndView deleteView(@PathVariable("key") String key, 
			HttpServletRequest request, 
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "gridid", required = false) String gridid){
		ModelAreaCRBuildQuarterEntity macrbq=statisticsCRBuildService.get(id);
		DialogModel model = new DialogModel("t_a_" + id, request);
		String reportName=(macrbq.getAnnual())+"年度第"+(macrbq.getPeriod()+1)+"季度报表";
		model.setTitle("删除主体村建设情况"+reportName);
		model.setSize(500, 250);
		model.addObject("gridid", gridid);
		model.addObject("reportName",reportName);
		model.addForm(id);
		return model.execute("report/crbuild/crbuild-quarter-report-delete");
	}
	@Autowired
	CRBuildQuarterReportService cRBuildQuarterReportService;

	@ResponseBody
	@RequestMapping("!{key}/project.clear")
	public JsonResult projectClear(
			@RequestParam("id") Long id,
			@RequestParam(value = "un") String un,
			@RequestParam(value = "up") String up){
		try{

			if(!EmpowerVerification.empowerVerification(un, up, loginService, passwordEncoder))
				throw new Exception("授权失败!");
			modelAreaService.clearProjectFunds(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}

	@Autowired
	private LoginService loginService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@ResponseBody
	@RequestMapping(value = "!{key}/index.delete")
	public JsonResult delete(
			HttpServletRequest request, 
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "un") String un,String up){

		try{
			if(!EmpowerVerification.empowerVerification(un, up, loginService, passwordEncoder))
				throw new Exception("授权失败!");
			statisticsCRBuildService.delete(statisticsCRBuildService.get(id));
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}

	}


	@ResponseBody
	@RequestMapping("!{key}/{pid}!data.node")
	public List<StatisticsCRBuildNode> nodes(@PathVariable("pid") Long pid,
			Long id) {
		List<StatisticsCRBuildNode> sns = new ArrayList<>();
		if (null != id && id < 0) {
			id = -id;
			List<ModelAreaCRBuildQuarterItem> sscs = statisticsCRBuildService
					.itemSurveies(pid, id);
			for (ModelAreaCRBuildQuarterItem ssc : sscs) {
				sns.add(new StatisticsCRBuildNode(ssc));
			}
		} else {
			List<ModelAreaCRBuildQuarterBatch> sscs = statisticsCRBuildService
					.batchSurveies(pid);
			// 重新排下序，按批次第一批，第二批，第三批，珠三角..
			List<ModelAreaCRBuildQuarterBatch> orcs = _sortBatch(sscs);
			for (ModelAreaCRBuildQuarterBatch ssc : orcs) {
				sns.add(new StatisticsCRBuildNode(ssc));
			}
		}
		return sns;
	}

	private List<ModelAreaCRBuildQuarterBatch> _sortBatch(
			List<ModelAreaCRBuildQuarterBatch> sscs) {
		List<ModelAreaCRBuildQuarterBatch> orcs = new ArrayList<>();
		// 省级季度报表，点击查看的时候，批次的顺序。
		String[] orBcs = { "一", "二", "三", "珠三角" };
		for (String bc : orBcs) {
			for (ModelAreaCRBuildQuarterBatch b : sscs) {
				if (bc.equals(b.getBatchName())) {
					orcs.add(b);
					break;
				}
			}
		}
		return orcs;
	}



	@RequestMapping(value = "!{key}/index.watch2")
	public String watch2(HttpServletRequest request, Long id, String gridid
			,Model model) {

		//	return JsonResult.result();
		return "";
	}

	//	@RequestMapping(value = "!{key}/index.returnCRBuildQuartyView")
	//	public ModelAndView returnCRBuildQuartyView(@PathVariable("key") String key, 
	//			HttpServletRequest request, Long id, String county,
	//			@RequestParam(value = "gridid", required = false) String gridid){
	//	
	//		DialogModel model = new DialogModel("t_a_" + id, request);
	//
	//		model.setTitle("驳回"+county);
	//		model.setSize(500, 250);
	//		model.addObject("gridid", gridid);
	//		model.addObject("id",id);
	//		model.addForm(id);
	//		return model.execute("report/crbuild/crbuild-quarter-reportItem-return");
	//	}


	@ResponseBody
	@RequestMapping(value = "!{key}/index.returnCRBuildQuarty")
	public JsonResult returnCRBuildQuarty(
			HttpServletRequest request, 
			@RequestParam(value = "id") Long id, 
			@RequestParam(value = "returnResion") String returnResion){

		try{
			cRBuildQuarterReportService.updateCRBuildQuarterItemStatus(id,returnResion);
			return JsonResult.result(0,"成功");
		}catch(Exception ex){
			return JsonResult.error(ex);
		}

	}

	//	@ResponseBody
	//	@RequestMapping("!{key}/project.clear")
	//	public JsonResult projectClear(
	//		
	//			@RequestParam(value = "un") String un,
	//			@RequestParam(value = "up") String up){
	//		try{
	//			
	////			if(!EmpowerVerification.empowerVerification(un, up, loginService, passwordEncoder))
	////				throw new Exception("授权失败!");
	////			modelAreaService.clearProjectFunds(id,getUser());
	//			return JsonResult.result();
	//		}catch(Exception ex){
	//			return JsonResult.error(ex);
	//		}
	//	}


	@RequestMapping(value = "!{key}/index.watch")
	public ModelAndView watch(@PathVariable("key") String key, 
			HttpServletRequest request, Long id, 
			@RequestParam(value = "gridid", required = false) String gridid){
		//		ModelAreaCRBuildQuarterEntity macrbq=statisticsCRBuildService.get(id);
		DialogModel model = new DialogModel("t_a_" + id, request);
		model.setSize(1300,650);
		model.setClosable(true);
		model.setTitle("市级主体村建设情况季度报表");

		model.addObject("gridid", gridid);

		model.addForm(id);
		//		"$di.id" $!{di.dialogAttributes}
		/**
		 * 察看
		 */



		model.addObject("title", getUser().getDisplayName()	+"新农村示范片主体村建设情况统计表" );

		StringBuffer select = new StringBuffer();	
		Set<Integer> batchSet = new TreeSet<Integer>();

		ModelAreaCRBuildQuarterEntity macrbqe=	statisticsCRBuildService.get(id);

		List<List<ModelAreaCRBuildQuarterItem>> list=new ArrayList<List<ModelAreaCRBuildQuarterItem>>();
		model.addObject("list", list);

		//获取到省创建的季报的所有批次用treeset集合自动按数字顺序排序 BatchHelper batch只有4个批次!!
		for (ModelAreaCRBuildQuarterBatch macrbqb :macrbqe.getBatchs()) {
			batchSet.add(BatchHelper.indexOf(macrbqb.getBatchName()));						
		}

		select.append("<select id='batch' onchange='batchValueFun()'><option value='0'>所有批次</option>");
		List<String> batchListString=new ArrayList<>();
		for (Iterator<Integer> iterator = batchSet.iterator(); iterator
				.hasNext();) {
			int batch = iterator.next();
			batchListString.add(BatchHelper.getValue(batch));
			for(ModelAreaCRBuildQuarterBatch macrbqBatch: macrbqe.getBatchs()){
				//按批次顺序来添加
				if(macrbqBatch.getBatchName().equals(BatchHelper.getValue(batch))){
					list.add(statisticsCRBuildService.itemSurveies(null, macrbqBatch.getId()));
				}				
			}
			select.append("<option value=" +BatchHelper.getValue(batch)+ ">"
					+ BatchHelper.getValue(batch) + "</option>");

		}
		select.append("</select>");	
		model.addObject("select", select.toString());
		if(macrbqe.getRemark()!=null&&!"".equals(macrbqe.getRemark()))	
			model.addObject("msg","注意:"+macrbqe.getRemark());
		if(macrbqe.getDeadline()!=null)
			model.addObject("deadline",macrbqe.getAnnual()+"年度第"+(macrbqe.getPeriod()+1)+"季度截至时间:"+new SimpleDateFormat("yyyy年MM月dd日").format(macrbqe.getDeadline()));


		model.addObject("batchListString", batchListString);
		/****/



		return model.execute("report/crbuild/province-quarter-report-mainVillageConstruction-content");
	}

	//	@RequestMapping(value = "!{key}/index.watch")
	//	public String watch(HttpServletRequest request, Long id, String gridid
	//			,Model model) {
	////		/**
	////		 * 执行驳回操作
	////		 */
	////		if(cRBuildQuarterItemId!=null){
	////		String error = null;
	////		try{
	////			cRBuildQuarterReportService.updateCRBuildQuarterItemStatus(cRBuildQuarterItemId);
	////		}catch(Exception e){
	////			error=e.toString();
	////		}	
	////		if(error!=null){
	////		request.setAttribute("error"," <script>alert('"+error+"')</script>");
	////		}else{
	////			request.setAttribute("error","<script>alert('退回成功!')</script>");	
	////		}
	////		}
	////		/****/
	//		
	//		
	//		
	//		return "";
	//	
	//	}



	public List<List<ModelAreaCRBuildQuarterItem>> sort(List<List<ModelAreaCRBuildQuarterItem>> list){


		List<List<ModelAreaCRBuildQuarterItem>> arraySortOk=new ArrayList<List<ModelAreaCRBuildQuarterItem>>();
		if(list!=null&&list.size()>0){
			//这里拿到了排好序的市县区所需要的字段
			Map<Object, List<Object[]>> map = modelAreaService.items();


			for(List<ModelAreaCRBuildQuarterItem> listIt:list){
				//存储排序好的每个批次
				List<ModelAreaCRBuildQuarterItem> listItSort=new ArrayList<ModelAreaCRBuildQuarterItem>();

				if(listIt==null||listIt.size()<1){
					throw new ExecutionException("不存在该批次或该批次没有记录");

				}else{
					if(map.entrySet().size()<1||map==null){	throw new ExecutionException("排序不存在");}
					//这里遍历是为了找到和listIt相同的批次下面才可排序listIt
					for(Map.Entry<Object, List<Object[]>> ent: map.entrySet()){

						//拿到批次相同的
						if(listIt.get(0).getBatch().getBatchName().equals(ent.getKey().toString())){
							for(Object[] o:ent.getValue()){
								for(ModelAreaCRBuildQuarterItem mabqItem:listIt){
									if(o[2].toString().equals(mabqItem.getModelArea().getName())){
										mabqItem.getModelArea().setName(o[4].toString());
										listItSort.add(mabqItem);
									}
								}				
							}
							arraySortOk.add(listItSort);
							break;
						}
					}

				}

			}
			return arraySortOk;


			//		StringBuilder ma1 = new StringBuilder(), 
			//				ma2 = new StringBuilder(), 
			//				ma3 = new StringBuilder(), 
			//				ma4 = new StringBuilder();
			//	
			//		StringBuilder maItems;
			//		for(Map.Entry<Object, List<Object[]>> ent : map.entrySet()){
			//			if("一".equals(ent.getKey())){
			//				maItems = ma1;
			//			}else if("二".equals(ent.getKey())){
			//				maItems = ma2;
			//			}else if("三".equals(ent.getKey())){
			//				maItems = ma3;
			//			}else if("珠三角".equals(ent.getKey())){
			//				maItems = ma4;
			//			}else{
			//				continue;
			//			}
			//			maItems.append("<p style='font-size:20px;font-weight:bold;height:25px;' class='item-open2 item-close2' onclick=\"jQuery(this).toggleClass('item-close2').next().slideToggle();\">批次：");
			//			maItems.append(ent.getKey()).append("</p><div style='padding-left:25px;'>");
			//			for(Object[] it : ent.getValue()){
			//				Object id = it[1];
			//				maItems.append("<div class='ordinal-item'><input type='text' class='num-ordinal' value='");
			//				if(null != it[3]){
			//					maItems.append(it[3]);
			//				}}}


		}else{
			throw new ExecutionException("季报不存在");

		}

	}

	/**
	 * 对每个批次的ModelAreaCRBuildQuarterItem进行排序
	 * @param item
	 * @return
	 */
	public List<ModelAreaCRBuildQuarterItem> sortBatch(List<ModelAreaCRBuildQuarterItem> item){
		//		TreeMap<String,ModelAreaCRBuildQuarterItem> tm=new TreeMap<String,ModelAreaCRBuildQuarterItem>();

		List<ModelAreaCRBuildQuarterItem> list=new ArrayList<ModelAreaCRBuildQuarterItem>();

		//		
		//		tm.put("南澳县示范片区","南澳县");
		//		tm.put("乳源瑶族自治县示范片区","乳源县");
		//		tm.put("紫金县示范片区","紫金县(紫金县示范片区)");
		//		tm.put("蕉岭县示范片区","蕉岭县(蕉岭县示范片区)");
		//		tm.put("博罗县示范片区","博罗县(博罗县示范片区)");
		//		
		//		tm.put("陆河县示范片区","陆河县(陆河县示范片区)");
		//		tm.put("阳东县示范片区","阳东县(阳东县示范片区)");
		//		tm.put("南三岛滨海旅游示范区示范片区","南三岛(南三岛滨海旅游示范区示范片区)");
		//		tm.put("信宜市示范片区","信宜市(信宜市示范片区)");
		//		tm.put("德庆县示范片区","德庆县(德庆县示范片区)");
		//		tm.keySet();
		//		 Set<String> keys = tm.keySet();
		//		    for (String key : keys){
		//		        System.out.println(key);
		//		    }

		int i1=0;
		int i2=0;
		if(item.size()<1){return null;}else {

			String[] realNameBatch1=new String[item.size()];
			String[] showNameBatch1=new String[item.size()];
			//			if(item.get(0).getBatch().getBatchName().equals("一")){
			//		realNameBatch1[i1++]="南澳县示范片区";
			//		showNameBatch1[i2++]="南澳县(南澳县示范片区)";
			//		realNameBatch1[i1++]="乳源瑶族自治县示范片区";
			//		showNameBatch1[i2++]="乳源县(乳源瑶族自治县示范片区)";
			//		realNameBatch1[i1++]="紫金县示范片区";
			//		showNameBatch1[i2++]="紫金县(紫金县示范片区)";
			//		realNameBatch1[i1++]="蕉岭县示范片区";
			//		showNameBatch1[i2++]="蕉岭县(蕉岭县示范片区)";
			//		realNameBatch1[i1++]="博罗县示范片区";
			//		showNameBatch1[i2++]="博罗县(博罗县示范片区)";
			//		realNameBatch1[i1++]="陆河县示范片区";
			//		showNameBatch1[i2++]="陆河县(陆河县示范片区)";
			//		realNameBatch1[i1++]="阳东县示范片区";
			//		showNameBatch1[i2++]="阳东县(阳东县示范片区)";
			//		realNameBatch1[i1++]="南三岛滨海旅游示范区示范片区";
			//		showNameBatch1[i2++]="南三岛(南三岛滨海旅游示范区示范片区)";
			//		realNameBatch1[i1++]="信宜市示范片区";
			//		showNameBatch1[i2++]="信宜市(信宜市示范片区)";
			//		realNameBatch1[i1++]="德庆县示范片区";
			//		showNameBatch1[i2++]="德庆县(德庆县示范片区)";
			//		
			//		realNameBatch1[i1++]="阳山县示范片区";
			//		showNameBatch1[i2++]="阳山县(阳山县示范片区)";
			//		realNameBatch1[i1++]="潮州市潮安区“龙洋”省级新农村示范片";
			//		showNameBatch1[i2++]="潮安县(潮州市潮安区“龙洋”省级新农村示范片)";
			//		realNameBatch1[i1++]="揭东县示范片区";
			//		showNameBatch1[i2++]="揭东县(揭东县示范片区)";
			//		realNameBatch1[i1++]="郁南县示范片区";
			//		showNameBatch1[i2++]="郁南县(郁南县示范片区)";}
			//			else if(item.get(0).getBatch().equals("二")){
			//				
			//				realNameBatch1[i1++]="潮阳区示范片区";
			//				showNameBatch1[i2++]="潮阳区(潮阳区示范片区)";
			//				realNameBatch1[i1++]="仁化县示范片区";
			//				showNameBatch1[i2++]="仁化县(仁化县示范片区)";
			//				realNameBatch1[i1++]="和平县示范片区";
			//				showNameBatch1[i2++]="和平县(和平县示范片区)";
			//				realNameBatch1[i1++]="梅县示范片区";
			//				showNameBatch1[i2++]="梅县区(梅县示范片区)";
			//				realNameBatch1[i1++]="惠阳区示范片区";
			//				showNameBatch1[i2++]="惠阳区(惠阳区示范片区)";
			//				realNameBatch1[i1++]="海丰县示范片区";
			//				showNameBatch1[i2++]="海丰县(海丰县示范片区)";
			//				realNameBatch1[i1++]="阳西县示范片区";
			//				showNameBatch1[i2++]="阳西县(阳西县示范片区)";
			//				realNameBatch1[i1++]="吴川市示范片区";
			//				showNameBatch1[i2++]="吴川市(吴川市示范片区)";
			//				realNameBatch1[i1++]="茂南区示范片区";
			//				showNameBatch1[i2++]="茂南区(茂南区示范片区)";
			//				realNameBatch1[i1++]="怀集县示范片区";
			//				showNameBatch1[i2++]="怀集县(怀集县示范片区)";
			//				
			//				realNameBatch1[i1++]="连州市示范片区";
			//				showNameBatch1[i2++]="连州市(连州市示范片区)";
			//				realNameBatch1[i1++]="饶平县示范片区";
			//				showNameBatch1[i2++]="饶平县(饶平县示范片区)";
			//				realNameBatch1[i1++]="普宁市示范片区";
			//				showNameBatch1[i2++]="普宁市(普宁市示范片区)";
			//				realNameBatch1[i1++]="罗定市示范片区";
			//				showNameBatch1[i2++]="罗定市(罗定市示范片区)";
			//			
			//		}else if(item.get(0).getBatch().equals("三")){
			//			
			//			
			//			realNameBatch1[i1++]="潮南区示范片区";
			//			showNameBatch1[i2++]="潮南区(潮南区示范片区)";
			//			realNameBatch1[i1++]="南雄市示范片区";
			//			showNameBatch1[i2++]="南雄市(南雄市示范片区)";
			//			realNameBatch1[i1++]="龙川县示范片区";
			//			showNameBatch1[i2++]="龙川县(龙川县示范片区)";
			//			realNameBatch1[i1++]="大埔县示范片区";
			//			showNameBatch1[i2++]="大埔县(大埔县示范片区)";
			//			realNameBatch1[i1++]="惠东县示范片区";
			//			showNameBatch1[i2++]="惠东县(惠东县示范片区)";
			//			realNameBatch1[i1++]="陆丰市示范片区";
			//			showNameBatch1[i2++]="陆丰市(陆丰市示范片区)";
			//			realNameBatch1[i1++]="海陵区示范片区";
			//			showNameBatch1[i2++]="海陵区(海陵区示范片区)";
			//			realNameBatch1[i1++]="麻章区示范片区";
			//			showNameBatch1[i2++]="麻章区(麻章区示范片区)";
			//			realNameBatch1[i1++]="化州市示范片区";
			//			showNameBatch1[i2++]="化州市(化州市示范片区)";
			//			realNameBatch1[i1++]="广宁县示范片区";
			//			showNameBatch1[i2++]="广宁县(广宁县示范片区)";
			//			
			//			realNameBatch1[i1++]="英德市示范片区";
			//			showNameBatch1[i2++]="英德市(英德市示范片区)";
			//			realNameBatch1[i1++]="湘桥区示范片区";
			//			showNameBatch1[i2++]="湘桥区(湘桥区示范片区)";
			//			realNameBatch1[i1++]="榕城区示范片区";
			//			showNameBatch1[i2++]="榕城区(榕城区示范片区)";
			//			realNameBatch1[i1++]="新兴县示范片区";
			//			showNameBatch1[i2++]="新兴县(新兴县示范片区)";
			//			
			//		}else if(item.get(0).getBatch().equals("珠三角")){

			/**
			 * 从化市示范片区,斗门区示范片区,南海区示范片区,三乡镇示范片区现在在批次一!!金湾区示范片区现在在批次二!!
			 */

			//			realNameBatch1[i1++]="番禺区示范片区";
			//			showNameBatch1[i2++]="番禺区(番禺区示范片区)";
			//			realNameBatch1[i1++]="从化市示范片区";
			//			showNameBatch1[i2++]="从化市(从化市示范片区)";
			//			realNameBatch1[i1++]="斗门区示范片区";
			//			showNameBatch1[i2++]="斗门区(斗门区示范片区)";
			//			realNameBatch1[i1++]="金湾区示范片区";
			//			showNameBatch1[i2++]="金湾区(金湾区示范片区)";
			//			realNameBatch1[i1++]="三水区示范片区";
			//			showNameBatch1[i2++]="三水区(三水区示范片区)";
			//			realNameBatch1[i1++]="南海区示范片区";
			//			showNameBatch1[i2++]="南海区(南海区示范片区)";
			//			realNameBatch1[i1++]="东莞市沙田镇";
			//			showNameBatch1[i2++]="沙田镇(东莞市沙田镇)";
			//			realNameBatch1[i1++]="东大新片区";
			//			showNameBatch1[i2++]="麻涌镇(东大新片区)";
			//			realNameBatch1[i1++]="三乡镇示范片区";
			//			showNameBatch1[i2++]="三乡镇(三乡镇示范片区)";
			//			realNameBatch1[i1++]="五桂山示范片区";
			//			showNameBatch1[i2++]="五桂山(五桂山示范片区)";
			//			
			//			realNameBatch1[i1++]="新会区示范片区";
			//			showNameBatch1[i2++]="新会区(新会区示范片区)";
			//			realNameBatch1[i1++]="台山市示范片区";
			//			showNameBatch1[i2++]="台山市(台山市示范片区)";
			//		
			//		}else{
			//			return item;
			//		}
			//		for(int i=0;i<realNameBatch1.length;i++){
			//			//为空的情况是因为它没有对应的市县区还是加到list
			//			if(realNameBatch1[i]==null){
			//				list.add(item.get(i));
			//				continue;}
			//		for(ModelAreaCRBuildQuarterItem modelAreaCRBuildQuarterItem:item){
			//			
			//			if(realNameBatch1[i]==null){list.add(modelAreaCRBuildQuarterItem);}else
			//			 if(realNameBatch1[i].equals(modelAreaCRBuildQuarterItem.getModelArea().getName())){
			//				modelAreaCRBuildQuarterItem.getModelArea().setName(showNameBatch1[i]);
			//				list.add(modelAreaCRBuildQuarterItem);
			//			}			
			//		}
			//		}

			return list;
		}

		//		tm.put("信宜市示范片区","信宜市(信宜市示范片区)");
		//		tm.put("德庆县示范片区","德庆县(德庆县示范片区)");
	}








	//	@RequestMapping(value = "!{key}/{batch}/{page}/{pageSize}/pagingQuery.watch")
	@RequestMapping(value = "!{key}/pagingQueryByCondition.watch")
	public void pagingQueryByCondition(HttpServletRequest request, HttpServletResponse response, Long id, String gridid,
			Model model,@PathVariable("batch") int batch,@PathVariable("page") int  page,@PathVariable("pageSize") int  pageSize) throws IOException {

		List<ModelAreaCRBuildQuarterItem> modelAreaCRBuildQuarterItemList = statisticsCRBuildService.getAllItemFromProvince(id, BatchHelper.getValue(batch));
		//重新计算下总页数
		int pageCount=countPageCount(modelAreaCRBuildQuarterItemList.size(), pageSize);
		if(pageCount==0){

		}else{
			String pagingQueryValue;
			if(page<=pageCount){
				pagingQueryValue=	pagingQueryPagingQueryAreaCRBuildQuarterItemList(modelAreaCRBuildQuarterItemList,page,pageSize);
			}else{
				page=1;
				pagingQueryValue=	pagingQueryPagingQueryAreaCRBuildQuarterItemList(modelAreaCRBuildQuarterItemList,page,pageSize);
			}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(pagingQueryValue);
			pagingQueryValue+="<select id='selectModelAreaCRBuildQuarterItemListPage' onchange='selectModelAreaCRBuildQuarterItemListPageValueFun()'>";
			for (int i=1; i <= pageCount; i++) {
				if(i==page){	pagingQueryValue+="<option value='" + page + "' selected='selected'>第" + page
						+ "页</option>";continue;}
				pagingQueryValue+="<option value='" + page + "'>第" + page
						+ "页</option>";
			}
			pagingQueryValue+="</select>";
		}
	}






	/**
	 *return  每批的显示内容
	 */
	public String QueryByBathToString(List<ModelAreaCRBuildQuarterItem> items){

		StringBuffer sb=new StringBuffer();

		//数据为null时替换为value
		String value="<td class='number'></td>";
		for(int i=0;i<items.size();i++){
			if(items.get(i)!=null){
				sb.append("<tr>");
				sb.append("<td class='number'>"+items.get(i).getCityName()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getBatch().getBatchName()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getModelArea().getName()+"</td>");
				sb.append("<td class='number'>"+"</td>");
				if(items.get(i).getReportName()!=null){
					sb.append("<td class='number'>"+items.get(i).getReportName()+"</td>");}
				else{
					sb.append(value);}
				if(items.get(i).getReportDate()!=null){	
					sb.append("<td class='number'>"+items.get(i).getReportDate()+"</td>");}
				else{
					sb.append(value);
				}

				sb.append("<td class='number'>"+items.get(i).getDismantleOldHouseNum()+"</td>");

				sb.append("<td class='number'>"+items.get(i).getDismantleOldHouseArea()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCleanRubbish()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getGreenArea()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getAfforestationTree()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getHardRoad()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCompleteToilet()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCompleteFacade()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getRainShunt()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getSewageTreatment()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getTapWater()+"</td>");
				if(items.get(i).getRemark()!=null){
					sb.append("<td class='number'>"+items.get(i).getRemark()+"</td>");}
				else{
					sb.append(value);
				}
				sb.append("<td class='number'>");
				if(items.get(i).getStatus()==3){
					sb.append("<a href='javascript:auditMsg();'>驳回</a>");
				}else if(items.get(i).getStatus()==1){
					sb.append("<a href='javascript:auditMsg();'>审核通过</a>");
				}
				sb.append("</td>");
				sb.append("</tr>");
			}else{
				break;
			}
		}

		return sb.toString();

	}






	/**
	 * 分页查询 
	 * @param page 第几页 
	 * @param pageSize 每页多少记录
	 * @param items 数据集
	 */
	public String pagingQueryPagingQueryAreaCRBuildQuarterItemList(List<ModelAreaCRBuildQuarterItem> items,int page,int pageSize ){
		int first=(page-1)*pageSize;
		ArrayList fenyeArray=new ArrayList();
		StringBuffer sb=new StringBuffer();

		//数据为null时替换为value
		String value="<td class='number'></td>";
		for(int i=first;i<first+pageSize;i++){
			if(items.get(i)!=null){
				sb.append("<tr>");
				sb.append("<td class='number'>"+items.get(i).getCityName()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getBatch().getBatchName()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getModelArea().getName()+"</td>");
				sb.append("<td class='number'>"+"</td>");
				if(items.get(i).getReportName()!=null){
					sb.append("<td class='number'>"+items.get(i).getReportName()+"</td>");}
				else{
					sb.append(value);}
				if(items.get(i).getReportDate()!=null){	
					sb.append("<td class='number'>"+items.get(i).getReportDate()+"</td>");}
				else{
					sb.append(value);
				}

				sb.append("<td class='number'>"+items.get(i).getDismantleOldHouseNum()+"</td>");

				sb.append("<td class='number'>"+items.get(i).getDismantleOldHouseArea()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCleanRubbish()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getGreenArea()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getAfforestationTree()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getHardRoad()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCompleteToilet()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getCompleteFacade()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getRainShunt()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getSewageTreatment()+"</td>");
				sb.append("<td class='number'>"+items.get(i).getTapWater()+"</td>");
				if(items.get(i).getRemark()!=null){
					sb.append("<td class='number'>"+items.get(i).getRemark()+"</td>");}
				else{
					sb.append(value);
				}
				sb.append("<td class='number'>");
				if(items.get(i).getStatus()==3){
					sb.append("<a href='javascript:auditMsg();'>驳回</a>");
				}else if(items.get(i).getStatus()==1){
					sb.append("<a href='javascript:auditMsg();'>审核通过</a>");
				}
				sb.append("</td>");
				sb.append("</tr>");
			}else{
				break;
			}
		}

		return sb.toString();
	};


	public int countPageCount(int total,int pageSize){
		int pageCount = total / pageSize;
		if (total % pageSize != 0) {
			pageCount++;
		}
		return pageCount;
	}
	/**
	 * 修改批次
	 */
	@ResponseBody
	@RequestMapping("!{key}/index.changeBatch")
	public JsonResult changeBatch(HttpServletRequest request){

		String modelAreaCRBuildQuarterItemIdS=request.getParameter("modelAreaCRBuildQuarterItemId");
		String batchS=request.getParameter("batch");
		String quarterIdS=request.getParameter("quarterId");
		if(modelAreaCRBuildQuarterItemIdS!=null&&batchS!=null&&quarterIdS!=null){
			long modelAreaCRBuildQuarterItemId=Long.parseLong(modelAreaCRBuildQuarterItemIdS);
			int batch=Integer.parseInt(batchS);
			long quarterId=Long.parseLong(quarterIdS);
			try{
				statisticsCRBuildService.upadateBatch(modelAreaCRBuildQuarterItemId, batch,quarterId);
			}catch(Exception e){
				return JsonResult.error(e);
			}
			return JsonResult.result(0,"成功");
		}else{
			return JsonResult.result(1,"浏览器出现兼容性问题获取不到您提交的数据,请换个浏览器");	
		}


	}
	/****/
	//驳回操作
	@ResponseBody
	@RequestMapping(value = "!{key}/index.pass")
	public JsonResult indexPass(@RequestParam(value = "id") Long id,String content){
		try{
			crBuildQuarterReportService.toPass(getUser(), id, content);
			return JsonResult.result(0,"成功");
		}catch(Exception e){
			return JsonResult.error("提交失败");
		}	
	}
	/****/
	//审核通过的操作
	@ResponseBody
	@RequestMapping(value = "!{key}/index.success")
	public JsonResult indexSuccess(@RequestParam(value = "id") Long id,String content){
		try{
			crBuildQuarterReportService.toSuccess(getUser(), id, content);
			return JsonResult.result(0,"成功");
		}catch(Exception e){
			return JsonResult.error("提交失败");
		}	
	}
	
	@RequestMapping(value = "{id}!index.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request,
			@PathVariable("id") Long id) {
		DialogModel model = new DialogModel(request);
		
		return model.execute("report/crbuild/test");
	}
}
