package cn.bonoon.controllers.optimize;

import static cn.bonoon.util.DoubleHelper.add;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.controllers.AbstractModelAreaViewController;
import cn.bonoon.controllers.inofs.BaseIndustryAreaInfo;
import cn.bonoon.controllers.inofs.BaseModelAreaInfo;
import cn.bonoon.controllers.inofs.BasePeripheralRuralInfo;
import cn.bonoon.controllers.inofs.BaseProjectInfo;
import cn.bonoon.core.AdministrationCoreRuralService;
import cn.bonoon.core.AdministrationUncoreRuralService;
import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.AdministrationUncoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.IndustryAreaEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.NewRuralEntity;
import cn.bonoon.entities.PeripheralRuralEntity;
import cn.bonoon.entities.ProjectEntity;
import cn.bonoon.entities.ProjectReportItem;
import cn.bonoon.entities.ResidentialEnvironmentEntity;
import cn.bonoon.entities.RuralExpertGroupEntity;
import cn.bonoon.entities.RuralUnitEntity;
import cn.bonoon.entities.RuralWorkGroupEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.events.ReadEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.models.PanelModel;

@Controller
@RequestMapping("s/pl/newrural/area")
public class ModelAreaViewController extends AbstractModelAreaViewController{

	
	
	@Autowired
	private ModelAreaService modelAreaService;
	
	
	@Autowired
	private AdministrationCoreRuralService administrationCoreRuralService;
	@Autowired
	private AdministrationUncoreRuralService administrationUncoreRuralService;
	//季度报表用到
	@Autowired
	private LocalQuarterReportService localQuarterReportService;
	
	/**
	 * click newr modelarea ,show all batch's area
	 */
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		PanelModel model = event.getModel();
		model.addObject("layout", "layout-empty.vm");
		List<ModelAreaEntity> ma3 = modelAreaService.getModelArea("三");
		List<ModelAreaEntity> ma2 = modelAreaService.getModelArea("二");
		List<ModelAreaEntity> ma1 = modelAreaService.getModelArea("一");
		model.addObject("ma1", ma1).addObject("ma2", ma2).addObject("ma3", ma3);
		event.setVmPath("optimize2/province-view");
	}
	
	/**
	 * click someone into area 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/loadMarea.do")
	public String loadMarea(Model model,Long id){
		ModelAreaEntity ma = modelAreaService.get(id);
		model.addAttribute("ma", ma);
		//主体行政村
		List<AdministrationCoreRuralEntity> ars = modelAreaService.getAdminRural(id);
		//非主体行政村
		List<AdministrationUncoreRuralEntity> uars = modelAreaService.getUCAdminRural(id);
		List<Object[]> ias = modelAreaService.getIndustries(id);
		List<Object[]> pjs = modelAreaService.getProjects_type(id);//obj[0]:id obj[1]:name  obj[2]:projectType
		List<Object[]> pjs1 = new ArrayList<>();
		List<Object[]> pjs2 = new ArrayList<>();
		List<Object[]> pjs3 = new ArrayList<>();
		List<Object[]> pjs4 = new ArrayList<>();
		List<Object[]> pjs5 = new ArrayList<>();
		List<Object[]> pjs6 = new ArrayList<>();
		List<Object[]> pjs7 = new ArrayList<>();
		List<Object[]> pjs8 = new ArrayList<>();
		List<Object[]> pjs9 = new ArrayList<>();
		List<Object[]> pjs10 = new ArrayList<>();
		List<Object[]> pjs11 = new ArrayList<>();
		List<Object[]> pjs12= new ArrayList<>();
		
		_pjType(model, pjs, pjs1, pjs2, pjs3, pjs4, pjs5, pjs6, pjs7, pjs8,
				pjs9, pjs10, pjs11, pjs12);
		

		//季度报表
		//ModelAreaQuarterItem
		//ModelAreaEntity
		List<ModelAreaQuarterItem> aqItems = modelAreaService.getAreaQuarterItems(id);
		model.addAttribute("aqItems", aqItems);
		model.addAttribute("ars", ars).addAttribute("uars", uars).addAttribute("pjs", pjs).addAttribute("ias", ias);
		
		
		//完成人居环境整治村名单
		//6.综合整治
		List<Integer> listResAnnual = modelAreaService.getResidentialEnvironmentsGroupByAnnual(id);
		model.addAttribute("listResAnnual", listResAnnual);
		
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/area/area-view";
	}

	private void _pjType(Model model, List<Object[]> pjs, List<Object[]> pjs1,
			List<Object[]> pjs2, List<Object[]> pjs3, List<Object[]> pjs4,
			List<Object[]> pjs5, List<Object[]> pjs6, List<Object[]> pjs7,
			List<Object[]> pjs8, List<Object[]> pjs9, List<Object[]> pjs10,
			List<Object[]> pjs11, List<Object[]> pjs12) {
		for(Object[]o :pjs){
			String projectType = (String) o[2];
			if("规划设计".equals(projectType)){
				pjs1.add(o);
				model.addAttribute("pjs1", pjs1);
			}
			if("村庄环境整治（垃圾、污水处理等）".equals(projectType)){
				pjs2.add(o);
				model.addAttribute("pjs2", pjs2);
			}
			if("村居外立面整治".equals(projectType)){
				pjs3.add(o);
				model.addAttribute("pjs3", pjs3);
			}
			if("旧村旧房改造".equals(projectType)){
				pjs4.add(o);
				model.addAttribute("pjs4", pjs4);
			}
			if("文化传承保护".equals(projectType)){
				pjs5.add(o);
				model.addAttribute("pjs5", pjs5);
			}
			if("美化绿化建设".equals(projectType)){
				pjs6.add(o);
				model.addAttribute("pjs6", pjs6);
			}
			if("基础设施建设".equals(projectType)){
				pjs7.add(o);
				model.addAttribute("pjs7", pjs7);
			}
			if("连线工程项目".equals(projectType)){
				pjs8.add(o);
				model.addAttribute("pjs8", pjs8);
			}
			if("道路硬底化项目".equals(projectType)){
				pjs9.add(o);
				model.addAttribute("pjs9", pjs9);
			}
			if("村通自来水项目".equals(projectType)){
				pjs10.add(o);
				model.addAttribute("pjs10", pjs10);
			}
			if("卫厕改造项目".equals(projectType)){
				pjs11.add(o);
				model.addAttribute("pjs11", pjs11);
			}
			if("其他".equals(projectType)||"".equals(projectType)){
				pjs12.add(o);
				model.addAttribute("pjs12", pjs12);
			}
		}
	}
	
	//?
	protected Class<? extends BaseModelAreaInfo> maType(){
		return BaseModelAreaInfo.class;
	}
	@RequestMapping(value="!{key}/area_base.show")
	public String area_base(HttpServletRequest request,Model model,Long id){
		_showMa(request, model, id);
		ModelAreaEntity ma = modelAreaService.get(id);
		List<Object> base = new ArrayList<>();
//		base.add(ma.getRuralsArea());
		base.add(ma.getTownNumber());
		base.add(ma.getAdminVN());
		base.add(ma.getNaturalVN());
		base.add(ma.getVillagersGroup());
		base.add(ma.getPopHous());
		base.add(ma.getFarmerHous());
		base.add(ma.getSumTownPopu());
		base.add(ma.getSumFarmers());
		model.addAttribute("base", base);
		return "optimize2/area/county-base";
	}

	private void _showMa(HttpServletRequest request, Model model, Long id) {
		model.addAttribute("readonly", "readonly='readonly'");
		model.addAttribute("disabled", "disabled='true'");
		model.addAttribute("layout", "layout-empty.vm");
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			//1.台账信息
			model.addAttribute("form", modelAreaService.get(event, id, maType()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="!{key}/ma_situation.show")
	public String ma_situation(HttpServletRequest request,Model model,Long id){
		_showMa(request, model, id);
		
		Map<String,Object> map = modelAreaService.getMa_title_show(id);//或许不用,用sql查出这些村个数,entity里面有相应的字段对应
		
		model.addAttribute("map", map);
		List<Object> rdata = new ArrayList<>();
		rdata.add(map.get("acNum"));
		rdata.add(map.get("nrNum"));
		rdata.add(map.get("aucNum"));
		rdata.add(map.get("prNum"));
		model.addAttribute("rdata", rdata);
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/area/ma-situation";
	}
	
	//连片建设基本情况
	@RequestMapping(value="!{key}/contin_constru.show")
	public String contin_constru(HttpServletRequest request,Model model,Long id){
		_showMa(request, model, id);
		ModelAreaEntity ma = modelAreaService.get(id);
		List<Object> constructBase = new ArrayList<>();
		constructBase.add(ma.getPlanMark());//规划标识
		constructBase.add(ma.getPostCount());//驿站
		constructBase.add(ma.getViewPlatform());//观景台
		constructBase.add(ma.getIntroCard());//宣传牌介绍
		constructBase.add(ma.getGreenProject());//绿化工程
		constructBase.add(ma.getGreenRoad());//绿道
		constructBase.add(ma.getLineScale());//主体村之间连线连片规模
		model.addAttribute("construBase", constructBase);
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/area/contin-constru";
	}
	
	//收入
	@RequestMapping(value="!{key}/income.show")
	public String income(HttpServletRequest request,Model model,Long id){
		ModelAreaEntity ma = modelAreaService.get(id);
		
		//c  , v  柱状图的数据源
		List<Double> c = new ArrayList<>();
		c.add(ma.getCi3());
		c.add(ma.getCi4());
		c.add(ma.getCi5());
		c.add(ma.getCi6());
		c.add(ma.getCi7());
		List<Double> v = new ArrayList<>();
		v.add(ma.getVi3());
		v.add(ma.getVi4());
		v.add(ma.getVi5());
		v.add(ma.getVi6());
		v.add(ma.getVi7());
		model.addAttribute("c", c);
		model.addAttribute("v", v);
		_showMa(request, model, id);
		return "optimize2/area/income-view";
	}
	
	//点击某个主体行政村
	@RequestMapping(value="!{key}/arDetal.do")
	public String arDetal(HttpServletRequest request,Model model,Long id){
		AdministrationCoreRuralEntity ar = administrationCoreRuralService.get(id);
		List<NewRuralEntity> nrs = administrationCoreRuralService.getRuralByAdminRuralId(id);
		System.out.println( nrs.size());
		model.addAttribute("ar", ar).addAttribute("nrs", nrs);
		//anIncome,clIncome   柱状图的数据源
		List<Object> anIncome = new ArrayList<>();
		anIncome.add(ar.getAnnualIncome_13());
		anIncome.add(ar.getAnnualIncome_14());
		anIncome.add(ar.getAnnualIncome_15());
		anIncome.add(ar.getAnnualIncome_16());
		anIncome.add(ar.getAnnualIncome_17());
		List<Object> clIncome = new ArrayList<>();
		clIncome.add(ar.getCollectivityAnnulIncome_13()*10000);
		clIncome.add(ar.getCollectivityAnnulIncome_14()*10000);
		clIncome.add(ar.getCollectivityAnnulIncome_15()*10000);
		clIncome.add(ar.getCollectivityAnnulIncome_16()*10000);
		clIncome.add(ar.getCollectivityAnnulIncome_17()*10000);
		model.addAttribute("anIncome", anIncome).addAttribute("clIncome", clIncome);
		model.addAttribute("readonly", "readonly='readonly'");
		model.addAttribute("disabled", "disabled='true'");
		model.addAttribute("layout", "layout-empty.vm");
		
		List<RuralWorkGroupEntity> wgs = administrationCoreRuralService.workGroupsByAdminRural(id);//工作小组
		List<RuralUnitEntity> rus = administrationCoreRuralService.ruralUnitsByAdminRural(id);//规划设计单位
		List<RuralExpertGroupEntity> egs = administrationCoreRuralService.experGroupByAdminRural(id);//专家指导组
		
		model.addAttribute("wgs", wgs).addAttribute("egs", egs).addAttribute("rus", rus);
		return "optimize2/rural/ar-view";
	}
	
	
	//点击某个主体自然村
	@RequestMapping(value="!{key}/loadNr.info")
	public String loadNr_info(HttpServletRequest request,Model model,Long id){
		NewRuralEntity nr = newRuralService.get(id);
		
		
		//柱状图,民生问题调查  数据源
		List<Object> issues = new ArrayList<>();
		issues.add(nr.getFinishNatureVillageNum());
		issues.add(nr.getTeasedProjectNum());
		issues.add(nr.getTroubleshooting());
		issues.add(nr.getResolvedTroubleshooting());
		model.addAttribute("issues", issues);
		
		
		model.addAttribute("form", nr);
		model.addAttribute("readonly", "readonly='readonly'");
		model.addAttribute("disabled", "disabled='true'");
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/rural/newrural/nr-info";
	}
	
	//点击某个非主体行政村
	@RequestMapping(value="!{key}/uarDetal.do")
	public String uarDetal(HttpServletRequest request,Model model,Long id){
		AdministrationUncoreRuralEntity aucr = administrationUncoreRuralService.get(id);
		List<PeripheralRuralEntity> prs = administrationUncoreRuralService.getRuralByAdminRuralId(id);
		model.addAttribute("aucr", aucr).addAttribute("prs", prs);
		
		//anIncome 柱状图的数据源
		List<Object> anIncome = new ArrayList<>();
		anIncome.add(aucr.getAnnualIncome_13());
		anIncome.add(aucr.getAnnualIncome_14());
		anIncome.add(aucr.getAnnualIncome_15());
		anIncome.add(aucr.getAnnualIncome_16());
		anIncome.add(aucr.getAnnualIncome_17());
		model.addAttribute("anIncome", anIncome);
		model.addAttribute("readonly", "readonly='readonly'");
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/rural/aucr-view";
	}
	
	//点击某个非主体自然村
	@RequestMapping(value="!{key}/loadPr.info")
	public String loadPr_info(HttpServletRequest request,Model model,Long id){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'");
		
		
		
		
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			BasePeripheralRuralInfo form = peripheraRuralService.get(event, id, BasePeripheralRuralInfo.class);
			model.addAttribute("form", form);
			
			List<Object> p_issues = new ArrayList<>();
			p_issues.add(form.getFinishNatureVillageNum());
			p_issues.add(form.getTeasedProjectNum());
			p_issues.add(form.getTroubleshooting());
			p_issues.add(form.getResolvedTroubleshooting());
			model.addAttribute("p_issues", p_issues);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "optimize2/rural/perirural/pr-info";
	}
	
	//---------------------------------------------------------------------------------
	/**
	 * 点击某个项目
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/pjDetal.do")
	public String pjDetal(HttpServletRequest request,Model model,Long id){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'");
		LogonUser user = getUser();
		
		//prinvs  , tinvs  饼图的数据源
		List<Object> pinvs = new ArrayList<>();
		List<Object> tinvs = new ArrayList<>();
		
		ProjectEntity pj = projectService.get(id);
		pinvs.add(pj.getInvestment().getTotalFunds());
		pinvs.add(pj.getInvestment().getStateFunds());
		pinvs.add(pj.getInvestment().getSpecialFunds());
		pinvs.add(pj.getInvestment().getProvinceFunds());
		pinvs.add(pj.getInvestment().getCityFunds());
		pinvs.add(pj.getInvestment().getCountyFunds());
		pinvs.add(pj.getInvestment().getSocialFunds());
		pinvs.add(pj.getInvestment().getMassFunds());
		pinvs.add(pj.getInvestment().getOtherFunds());
		
		tinvs.add(pj.getTotalInvestment().getTotalFunds());
		tinvs.add(pj.getTotalInvestment().getStateFunds());
		tinvs.add(pj.getTotalInvestment().getSpecialFunds());
		tinvs.add(pj.getTotalInvestment().getProvinceFunds());
		tinvs.add(pj.getTotalInvestment().getCityFunds());
		tinvs.add(pj.getTotalInvestment().getCountyFunds());
		tinvs.add(pj.getTotalInvestment().getSocialFunds());
		tinvs.add(pj.getTotalInvestment().getMassFunds());
		tinvs.add(pj.getTotalInvestment().getOtherFunds());
		
		model.addAttribute("pinvs", pinvs).addAttribute("tinvs", tinvs);
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			model.addAttribute("form", projectService.get(event, id, BaseProjectInfo.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "optimize2/project/areaproject-base";
	}
	
	//showPjPic 点击查看项目图片  
	@RequestMapping(value="!{key}/showPjPic.do")
	public String showPjPic(HttpServletRequest request,Model model,Long id){
		List<FileEntity> pics = projectService.getPic(id);
		ProjectEntity pj = projectService.get(id);
		model.addAttribute("pj", pj);
		
		
		//list 存放 建设前,建设中,建设后的图片
		List<FileEntity> before = new ArrayList<FileEntity>();
		List<FileEntity> after = new ArrayList<FileEntity>();
		List<FileEntity> running = new ArrayList<FileEntity>();
		
		if(pics!=null && pics.size()>0){
			for(FileEntity f:pics){
				if("建设前".equals(f.getExtattr7())){
					before.add(f);
					model.addAttribute("before",before);
				}else if("建设后".equals(f.getExtattr7())){
					after.add(f);
					model.addAttribute("after", after);
				}else{
					running.add(f);
					model.addAttribute("runnning", running);
				}
			}
		}
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/project/areaproject-pic";
	}
	
	/**
	 * 点击查看项目的概况
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/pjGener.do")
	public String pjGener(HttpServletRequest request,Model model,Long id){
		/*
		 * 0-未开始
		 * 1-进行中
		 * 2-竣工
		 * 3-终止
		 */
		 int notStart = 0,running = 0,completion = 0,end = 0;
		 List<Object[]> pjs = modelAreaService.getProjects_type(id);//obj[0]:id obj[1]:name  obj[2]:projectType obj[3]状态     obj[4]项目所属村
			List<Object[]> pjs1 = new ArrayList<>();
			List<Object[]> pjs2 = new ArrayList<>();
			List<Object[]> pjs3 = new ArrayList<>();
			List<Object[]> pjs4 = new ArrayList<>();
			List<Object[]> pjs5 = new ArrayList<>();
			List<Object[]> pjs6 = new ArrayList<>();
			List<Object[]> pjs7 = new ArrayList<>();
			List<Object[]> pjs8 = new ArrayList<>();
			List<Object[]> pjs9 = new ArrayList<>();
			List<Object[]> pjs10 = new ArrayList<>();
			List<Object[]> pjs11 = new ArrayList<>();
			List<Object[]> pjs12= new ArrayList<>();
			
		_pjType(model, pjs, pjs1, pjs2, pjs3, pjs4, pjs5, pjs6, pjs7, pjs8,
				pjs9, pjs10, pjs11, pjs12);
		int status;
		/*String[] pjType = {"规划设计","村庄环境整治（垃圾、污水处理等）","村居外立面整治","旧村旧房改造",
				"文化传承保护","美化绿化建设","基础设施建设","连线工程项目",
				"道路硬底化项目","村通自来水项目","卫厕改造项目","其他"};*/
		
		int coreRuralNum=0,nocoreRuralNum=0;
		for(Object[] o:pjs){
			status = (int) o[3];
			if(0==status){notStart++;}
			if(1==status){running++;}
			if(2==status){completion++;}
			if(3==status){end++;}
			
			BaseRuralEntity rural = (BaseRuralEntity) o[4];
			if(rural instanceof NewRuralEntity){
				coreRuralNum++;
			}else if(rural instanceof PeripheralRuralEntity){
				nocoreRuralNum++;
			}
		}
		
		
		model.addAttribute("pjs1", pjs1).addAttribute("pjs2", pjs2).addAttribute("pjs3", pjs3).
			addAttribute("pjs4", pjs4).addAttribute("pjs5", pjs5)
			.addAttribute("pjs6", pjs6).addAttribute("pjs7", pjs7).
			addAttribute("pjs8", pjs8).addAttribute("pjs9", pjs9).addAttribute("pjs10", pjs10)
			.addAttribute("pjs11", pjs11).addAttribute("pjs12", pjs12);
		
		model.addAttribute("prjTotalNum", pjs.size());
		model.addAttribute("coreRuralNum", coreRuralNum).addAttribute("nocoreRuralNum", nocoreRuralNum);
		
		model.addAttribute("notStart", notStart).addAttribute("running", running).addAttribute("completion", completion).addAttribute("end", end);
		model.addAttribute("readonly", "readonly='readonly'");
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/project/areaproject-gener";
	}
	//-------------------------------------------------------------------------------------
	/**
	 * 点击某个产业发展
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/iaDetal.do")
	public String iaDetal(HttpServletRequest request,Model model,Long id){
		model.addAttribute("layout", "layout-empty.vm")
			.addAttribute("readonly", "readonly='readonly'")
			.addAttribute("disabled", "disabled='true'");
		LogonUser user = getUser();
		try{
			ReadEvent event = new ReadEvent(applicationContext, request, user);
			event.setScope(VisibleScope.GLOBAL);
			BaseIndustryAreaInfo ids = industryAreaService.get(event, id, BaseIndustryAreaInfo.class);
			model.addAttribute("form", ids);
			List<Object> menberHous = new ArrayList<>();
			List<Object> identifiNums = new ArrayList<>();
			menberHous.add(ids.getMemberHous());
			menberHous.add(ids.getNonMemberHous());
			identifiNums.add(ids.getAgriculPros());
			identifiNums.add(ids.getFreePollution());
			model.addAttribute("menberHous",menberHous).addAttribute("identifiNums", identifiNums);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "optimize2/indstry/areaindustry-base";
	}
	
	/**
	 * 查看产业发展的概况
	 * 
	 * 查看一个总的情况
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/iaGener.do")
	public String iaGener(HttpServletRequest request,Model model,Long id){
		model.addAttribute("layout", "layout-empty.vm");
		List<IndustryAreaEntity> ias = modelAreaService.getIndustries_(id);
		int t_menberHouse = 0;
		int t_nomenberHouse = 0;
		int t_regiTradeMark = 0;
		int t_agriculPros = 0;
		int t_freePollution = 0;
		
		for(IndustryAreaEntity in:ias){
			t_menberHouse += in.getMemberHous();
			t_nomenberHouse += in.getNonMemberHous();
			
			t_regiTradeMark += in.getRegiTradeMark();
			t_agriculPros +=  in.getAgriculPros();
			t_freePollution += in.getFreePollution();
		}
		
		//memberHouse,identifiNums  柱状图的数据源
		List<Object> menberHouse = new ArrayList<>();
		List<Object> identifiNums = new ArrayList<>();
		
		menberHouse.add(t_menberHouse);
		menberHouse.add(t_nomenberHouse);
		
		identifiNums.add(t_regiTradeMark);
		identifiNums.add(t_agriculPros);
		identifiNums.add(t_freePollution);
		model.addAttribute("t_menberHouse", t_menberHouse).addAttribute("t_nomenberHouse", t_nomenberHouse).
			addAttribute("t_regiTradeMark", t_regiTradeMark).addAttribute("t_agriculPros", t_agriculPros).
			addAttribute("t_freePollution", t_freePollution);
		
		model.addAttribute("readonly", "readonly=readonly");
		model.addAttribute("menberHouse", menberHouse).addAttribute("identifiNums", identifiNums);
		return "optimize2/indstry/areaindustry-gener";
	}
	
	/**
	 * 点击项目里面的查看项目月报,
	 * 		默认点击进去查看的是2015 年 的月报,在界面那里可以选择查看的年度
	 * 		int annual = 2015; 
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="!{key}/pj.report")
	public String pjReport(HttpServletRequest request,Model model,Long id){
		//项目的id   查出所有这个项目的月报,一个项目他可能会报很多次,就有多少个ProjectReportItem  -- 里面包含有个个资金,斥资金额,投工投劳数
		List<ProjectReportItem> items = projectService.getProjectReport(id);
		ProjectEntity pj = projectService.get(id);
		int annual = 2015;//年度   应该是参数传递过来的
		//int period = 1;//月度
		List<String> timelineData = new ArrayList<String>();//x轴  时间轴的时间
		String timePoint = "";
		for(int i=1;i<=12;i++){//2012-01
			if(i>9){
				timePoint = "'"+annual+"-"+i+"-01'";
			}else{
				timePoint = "'"+annual+"-0"+i+"-01'";
			}
			timelineData.add(timePoint);
		}
		List<ProjectReportItem> showItems = new ArrayList<>();//你选择好的要显示的那个年度的月报
		List<List<Object>> sees = new ArrayList<>();
		
		
		
		
		for(ProjectReportItem item :items){
			if(annual==item.getReport().getAnnual()){
				showItems.add(item);
			}
		}
		
		for(int i=1;i<=12;i++){
			List<Object> invs = new ArrayList<>();
			int falt = __setinvsdata(showItems, invs, i);
			if(falt==0){
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
			}
			sees.add(invs);
		}
		
		//这个项目的个个资金,总的
		//Object[] sumItem = projectService.getSumItem(id);
		model.addAttribute("timelineData", timelineData);
		model.addAttribute("sees", sees);
		model.addAttribute("pj", pj);
		model.addAttribute("annual", annual);
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/report/pj-month-report";
	}
	
	/**
	 * 选择某个年份后,查看这个年份的月报情况
	 * @param request
	 * @param model
	 * @param id
	 * @param annual
	 * @return
	 */
	@RequestMapping(value="!{key}/loadReport.do")
	public String pjReportLod(HttpServletRequest request,Model model,Long id,int annual){
		//项目的id   查出所有这个项目的月报,一个项目他可能会报很多次,就有多少个ProjectReportItem  -- 里面包含有个个资金,斥资金额,投工投劳数
		List<ProjectReportItem> items = projectService.getProjectReport(id);
		ProjectEntity pj = projectService.get(id);
		//int period = 1;//月度
		List<String> timelineData = new ArrayList<String>();//x轴  时间轴的时间
		String timePoint = "";
		for(int i=1;i<=12;i++){//2012-01
			if(i>9){
				timePoint = "'"+annual+"-"+i+"-01'";
			}else{
				timePoint = "'"+annual+"-0"+i+"-01'";
			}
			timelineData.add(timePoint);
		}
		List<ProjectReportItem> showItems = new ArrayList<>();//你选择好的要显示的那个年度的月报
		List<List<Object>> sees = new ArrayList<>();
		
		
		
		
		for(ProjectReportItem item :items){
			if(annual==item.getReport().getAnnual()){
				showItems.add(item);
			}
		}
		
		for(int i=1;i<=12;i++){
			List<Object> invs = new ArrayList<>();
			int falt = __setinvsdata(showItems, invs, i);
			if(falt==0){
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
				invs.add(0);
			}
			sees.add(invs);
		}
		
		//这个项目的个个资金,总的
		//Object[] sumItem = projectService.getSumItem(id);
		model.addAttribute("timelineData", timelineData);
		model.addAttribute("sees", sees);
		model.addAttribute("pj", pj);
		model.addAttribute("annual", annual);
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/report/month-report-content";
	}

	private int __setinvsdata(List<ProjectReportItem> showItems,
			List<Object> invs, int i) {
		for(ProjectReportItem it:showItems){
			if(it.getReport().getPeriod()+1==i){
				invs.add(it.getInvestment().getTotalFunds());
				invs.add(it.getInvestment().getStateFunds());	
				invs.add(it.getInvestment().getSpecialFunds());
				invs.add(it.getInvestment().getProvinceFunds());
				invs.add(it.getInvestment().getCityFunds());
				invs.add(it.getInvestment().getCountyFunds());
				invs.add(it.getInvestment().getSocialFunds());
				invs.add(it.getInvestment().getMassFunds());
				invs.add(it.getInvestment().getOtherFunds());
				return 1;
				//..
			}
		}
		return 0;
	}
	
	
	/**
	 * 点击某个季度报表
	 */
	
	@RequestMapping(value="!{key}/loadQuarterReport.do")
	public String pReport(HttpServletRequest request,Model model,Long id){
		ModelAreaQuarterItem item = localQuarterReportService.get(id);
		model.addAttribute("item", item);
		Integer start = item.getStartProject();
		if(start==null){
			model.addAttribute("start", 0);
		}else{
			model.addAttribute("start", start);
		}
		Integer finish = item.getFinishProject();
		if(finish==null){
			model.addAttribute("finish", 0);
		}else{
			model.addAttribute("finish", finish);
		}
		double stateFunds = item.getInvestment().getStateFunds();
		double provinceFunds = item.getInvestment().getProvinceFunds();
		double specialFunds = item.getInvestment().getSpecialFunds();
		double socialFunds = item.getInvestment().getSocialFunds();
		double cityFunds = item.getInvestment().getCityFunds();
		double countyFunds = item.getInvestment().getCountyFunds();
		double massFunds = item.getInvestment().getMassFunds();
		double otherFunds = item.getInvestment().getOtherFunds();
		double totalfunds = 0.0;
		totalfunds  = add(totalfunds,stateFunds);
		totalfunds  = add(totalfunds,provinceFunds);
		totalfunds  = add(totalfunds,specialFunds);
		totalfunds  = add(totalfunds,socialFunds);
		totalfunds  = add(totalfunds,cityFunds);
		totalfunds  = add(totalfunds,countyFunds);
		totalfunds  = add(totalfunds,massFunds);
		totalfunds  = add(totalfunds,otherFunds);
		
		model.addAttribute("totalfunds",totalfunds);
		
		
		//完成指标的村数量  柱状图的数据源  perPros
		List<Object> perPros = new ArrayList<Object>();
		perPros.add(item.getArFinishPlan());
		perPros.add(item.getNeedFinish().getNeedFinish1());
		perPros.add(item.getNeedFinish().getNeedFinish2());
		perPros.add(item.getNeedFinish().getNeedFinish3());
		perPros.add(item.getNeedFinish().getNeedFinish4());
		perPros.add(item.getNeedFinish().getNeedFinish5());
		perPros.add(item.getNeedFinish().getNeedFinish6());
		perPros.add(item.getNeedFinish().getNeedFinish7());
		perPros.add(item.getNeedFinish().getNeedFinish8());
		perPros.add(item.getNeedFinish().getNeedFinish9());
		
		
		//资金投入情况  柱状图 数据源
		List<Object> perInvs = new ArrayList<Object>();
		perInvs.add(stateFunds);
		perInvs.add(provinceFunds);
		perInvs.add(specialFunds);
		perInvs.add(socialFunds);
		perInvs.add(cityFunds);
		perInvs.add(countyFunds);
		perInvs.add(massFunds);
		perInvs.add(otherFunds);
		model.addAttribute("perPros", perPros).addAttribute("perInvs", perInvs);
		
		model.addAttribute("layout", "layout-empty.vm");
		return "optimize2/report/quarter-report";
	}
	
	
	/**
	 * 点击综合整治的某个年度
	 */
	@RequestMapping(value="!{key}/res.show")
	public String listRes(HttpServletRequest request,Model model,Long id,Integer annual){
		//6.综合整治
		List<ResidentialEnvironmentEntity> listRes = modelAreaService.getResidentialEnvironmentsByAnnual(id,annual);
		model.addAttribute("listRes", listRes);
		model.addAttribute("layout", "layout-empty.vm");
		model.addAttribute("id", id);
		model.addAttribute("annual", annual);
		return "optimize2/res-env/residential-items";
	}
	
	@RequestMapping(value="!{key}/res.json")
	@ResponseBody
	public List<ResEvo> JsonlistRes(HttpServletRequest request,Model model,Long id,Integer annual){
		List<ResidentialEnvironmentEntity> listRes = modelAreaService.getResidentialEnvironmentsByAnnual(id,annual);
		List<ResEvo> reses = new ArrayList<>();
		for(ResidentialEnvironmentEntity r:listRes){
			reses.add(new ResEvo(r));
		}
		
		return reses;
	}
	
	
	
	
}

class ResEvo{
	private Long id;
	private int ordinal;
	private int annual;
	private String naturalVillage;
	
	public ResEvo(ResidentialEnvironmentEntity r){
		this.id = r.getId();
		this.ordinal = r.getOrdinal();
		this.annual = r.getAnnual();
		this.naturalVillage = "<div style='display:inline-block;height:22px;width:70px;border-bottom: solid 1px #000000;'>"+r.getTownName()+"</div>"+"  "+
				"<div style='display:inline-block;height:22px;width:150px;border-bottom: solid 1px #000000;'>"+r.getVillageName()+"</div>"+"  "+
					"<div style='display:inline-block;height:22px;width:100px;border-bottom: solid 1px #000000;'>"+r.getNaturalVillage()+"</div>"+"<span style='color:gray;'>自然村(或村民小组)</span>";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
	public String getNaturalVillage() {
		return naturalVillage;
	}
	public void setNaturalVillage(String naturalVillage) {
		this.naturalVillage = naturalVillage;
	}
	public int getAnnual() {
		return annual;
	}
	public void setAnnual(int annual) {
		this.annual = annual;
	}
	
	
}


