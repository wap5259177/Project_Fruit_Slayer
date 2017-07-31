package cn.bonoon.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.area.uncommitted.ModelAreaEditor;
import cn.bonoon.controllers.felicity.FelicityCountyInserter;
import cn.bonoon.core.ApplicantStatus;
import cn.bonoon.core.FelicityCountyService;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.entities.CommissionerEntity;
import cn.bonoon.entities.FelicityCountyEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.handler.impl.StandardAutoManager;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.events.OperateEvent;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.BaseButtonResolver;
import cn.bonoon.kernel.web.handlers.DialogInsertHandler;
import cn.bonoon.kernel.web.handlers.EmptyHandlerRegister;
import cn.bonoon.kernel.web.handlers.OperateEditHandler;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/cl")
public class CLHEmbedController extends BaseHEmbedController{
	
	@Autowired
	private ModelAreaService modelAreaService;
	
	@Autowired
	private FelicityCountyService felicityCountyService;
	
	@Autowired
	private StandardAutoManager autoManager;
	
	private String basePath;
	private OperateEditHandler<ModelAreaEntity> maoiHandler;
	private DialogInsertHandler<ModelAreaEntity> madiHandler;
	private StringBuilder mabHtml = new StringBuilder();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		basePath = toUrl("/s/cl");
		final String reurl = "var __rd=eval('(' + __resultData.data + ')');if(__rd.status===0){jQuery('#cl-ma-show').load('" + basePath + "/after.ma',{id:__rd.id});}";
		maoiHandler = new OperateEditHandler<ModelAreaEntity>(modelAreaService, autoManager, ModelAreaEditor.class){
			@Override
			protected JsonResult result(OperateEvent event, Object result) {
				ModelAreaEntity mae = (ModelAreaEntity)result;
				return JsonResult.result("{status:" + mae.getStatus() + ",id:" + mae.getId() + "}");
			}
		};
		maoiHandler.resetUrl(basePath + "/madi/insert.save");
		madiHandler = new DialogInsertHandler<>(new EmptyHandlerRegister(autoManager) {
			@Override
			public String refreshParentComponenet() {
				return reurl;//刷新一小块
			}
		}, maoiHandler);
		BaseButtonResolver bbr = new BaseButtonResolver();
		madiHandler.set("上报备案", true);
		madiHandler.register(bbr);
		bbr.setUrl(basePath + "/madi/insert.do");
		bbr.setName("上报备案");
		bbr.setBupabutton(false);
		bbr.setIconCls(null);
		bbr.setClick("return openDialog(this);");
		bbr.button(mabHtml, "btn-maab");
		
		//幸福村居
		final String felicity = "var __rd=eval('(' + __resultData.data + ')');if(__rd.status===0){jQuery('#cl-fv-show').load('" + basePath + "/after.fv',{id:__rd.id});}";
		fcoiHandler = new OperateEditHandler<FelicityCountyEntity>(felicityCountyService, autoManager, FelicityCountyInserter.class){
			@Override
			protected JsonResult result(OperateEvent event, Object result) {
				FelicityCountyEntity fce = (FelicityCountyEntity)result;
				return JsonResult.result("{status:" + fce.getStatus() + ",id:" + fce.getId() + "}");
			}
		};
		fcoiHandler.resetUrl(basePath + "/fcdi/insert.save");
		fcdiHandler = new DialogInsertHandler<>(new EmptyHandlerRegister(autoManager) {
			@Override
			public String refreshParentComponenet() {
				return felicity;//刷新一小块
			}
		}, fcoiHandler);
		bbr = new BaseButtonResolver();
		fcdiHandler.set("创建封面", true);
		fcdiHandler.register(bbr);
		bbr.setUrl(basePath + "/fcdi/insert.do");
		bbr.setName("创建封面");
		bbr.setBupabutton(false);
		bbr.setIconCls(null);
		bbr.setClick("return openDialog(this);");
		bbr.button(fcbHtml, "btn-fcab");
	}

	private OperateEditHandler<FelicityCountyEntity> fcoiHandler;
	private DialogInsertHandler<FelicityCountyEntity> fcdiHandler;
	private StringBuilder fcbHtml = new StringBuilder();
	
//	@RequestMapping("mad/{id}.detail")
//	public String madd(){
//		return "";
//	}
	
	@RequestMapping("after.fv")
	public String afterFv(Model model, Long id){
		LogonUser user = getUser();
		Long oid = user.getOwnerId();
		__loadFV(model, oid);
		return "cl-save-after-fv";
	}
	
	@RequestMapping("after.ma")
	public String afterMa(Model model, Long id){
		LogonUser user = getUser();
		List<Object[]> nrs = modelAreaService.newRurals(user);
		List<Object[]> prs = modelAreaService.peripheralRurals(user);
		int i = 1;
		StringBuilder sb = new StringBuilder();
		if(!nrs.isEmpty()){
			sb.append("<div class='embed-content-title item-open2 item-close2' onclick=\"jQuery(this).toggleClass('item-close2').next().slideToggle();\">主体村</div><div>");
			for(Object[] it : nrs){
				sb.append("<div class='embed-content-item'>").append(i++).append(". ").append(it[4]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					sb.append(it[3]);
				}				
				sb.append("</div>");
			}
			sb.append("</div><div style='padding-top:10px;'></div>");
		}
		if(!prs.isEmpty()){
			i = 1;
			sb.append("<div class='embed-content-title item-open2 item-close2' onclick=\"jQuery(this).toggleClass('item-close2').next().slideToggle();\">非主体村</div><div>");
			for(Object[] it : prs){
				sb.append("<div class='embed-content-item'>").append(i++).append(". ").append(it[4]).append(' ').append(it[1]).append(' ');
				if(null != it[3]){
					sb.append(it[3]);
				}				
				sb.append("</div>");
			}
			sb.append("</div><div style='padding-top:10px;'></div>");
		}
		model.addAttribute("html", sb);
		return "cl-save-after-ma";
	}
	
	@RequestMapping("fcdi/insert.do")
	public ModelAndView fcdi(HttpServletRequest request){
		DialogModel dm = new DialogModel(fcdiHandler.getKey(), request);
		try{
			DialogEvent de = new DialogEvent(applicationContext, "", request, null, getUser());
			return fcdiHandler.execute(dm, de);
		}catch(Exception ex){
			ex.printStackTrace();
			dm.addObject("error", ex.getMessage());
			return dm.execute("dialog-error");
		}
	}

	@ResponseBody
	@RequestMapping("fcdi/insert.save")
	public Object fcdi(HttpServletRequest request, FelicityCountyInserter editor){
		OperateEvent oe = new OperateEvent(applicationContext, request, editor, getUser());
		return fcoiHandler.execute(oe);
	}
	
	//点击上报备案按钮
	@RequestMapping("madi/insert.do")
	public ModelAndView madi(HttpServletRequest request){
		DialogModel dm = new DialogModel(madiHandler.getKey(), request);
		try{
			DialogEvent de = new DialogEvent(applicationContext, "", request, null, getUser());
			return madiHandler.execute(dm, de);
		}catch(Exception ex){
			ex.printStackTrace();
			dm.addObject("error", ex.getMessage());
			return dm.execute("dialog-error");
		}
	}

	//点击创建按钮
	@ResponseBody
	@RequestMapping("madi/insert.save")
	public Object madi(HttpServletRequest request, ModelAreaEditor editor){
		OperateEvent oe = new OperateEvent(applicationContext, request, editor, getUser());
		return maoiHandler.execute(oe);
	}
	
	@Override
	protected ItemInfo __menuReport() {
		return moduleManager.find("CL_SYSTEM", "2", "1", "1");
	}
	
	@Override
	protected ItemInfo __menuSupplement() {
		return moduleManager.find("CL_SYSTEM", "2", "1", "5");
	}
	
	@Override
	protected ItemInfo __menuQuarterReport() {
		return moduleManager.find("CL_SYSTEM", "16", "1");
	}
	
	
	private void __loadFV(Model model, Long oid){
		try{
			List<String> summary = felicityCountyService.summary(oid);
			String fcmsg;
			if(summary.isEmpty()){
				fcmsg = "<div class='embed-content-message'>您还未初始化原中央苏区县，请先[" + fcbHtml + "]<br/>创建幸福村居(新农村)连片示范的台账封面，谢谢！！</div>";
			}else{
				fcmsg = "<div class='embed-content-title'>幸福村居：</div>";
				int i = 1;
				for (String it : summary) {
					fcmsg += "<div class='embed-content-item'>" + i++ + ". " + it + "</div><div style='padding-top:10px;'></div>";
				}
			}
			model.addAttribute("fcmsg", fcmsg);
		}catch(Exception ex){
			log.error(ex.getMessage());
			model.addAttribute("fcmsg", "<div class='embed-content-message'>无法读取幸福村居的填报情况！</div>");
		}
	}
	
	@RequestMapping("host.embed")
	public String get(Model model){ // TODO module_type, 0:县级用户  1:市级用户  2:省级用户
		LogonUser user = getUser();
		Long oid = user.getOwnerId();
		CommissionerEntity f = modelAreaService.getByUser(user);
		
		if(f == null){
			 model.addAttribute("aaa",0);
		}
		
		String key = modelAreaService.getKey(user.getId());
		if(key.contains("MA,")){
			try {
				List<Object[]> summary = modelAreaService.summary(oid);
				String msg = "";
				if (summary.isEmpty()) {
					// 还没有填报片区，应该先填写片区信息
					msg = "<div class='embed-content-message'>您还没有初始化示范片区，<br/>请先进入功能[" + mabHtml + "]<br/>创建示范片区的台账封面，谢谢！！</div>";
				} else {
					int size = summary.size();
					if (size == 1) {
						Object[] it = summary.get(0);
						msg = __parser(it);
						msg += "<div style='padding-top:10px;'></div>";
					} else {
						for (Object[] it : summary) {
							msg += __parser(it);
							msg += "<div style='padding-top:10px;'></div>";
						}
					}
					model.addAttribute("showDetail", true);
				}
				model.addAttribute("msg", msg);
			} catch (Exception ex) {
				log.error(ex.getMessage());
				model.addAttribute("msg", "<div class='embed-content-message'>无法读取示范片区的填报情况！</div>");
			}
		}else{
			model.addAttribute("msg", "<div class='embed-content-message'>您未开通新农村连片示范建设工程！</div>");
		}
		if(key.contains("FV,")){
			__loadFV(model, oid);
		}else{
			model.addAttribute("fcmsg", "<div class='embed-content-message'>您未开通幸福村居功能！</div>");
		}
		if(key.contains("TV,")){
			try{
				String vcmsg = "<div class='embed-content-message'>名村名镇；正在开发中...</div>";
				model.addAttribute("vcmsg", vcmsg);
			}catch(Exception ex){
				log.error(ex.getMessage());
				model.addAttribute("vcmsg", "<div class='embed-content-message'>无法读取名村名镇的填报情况！</div>");
			}
		}else{
			model.addAttribute("vcmsg", "<div class='embed-content-message'>您未开通名村名镇功能！</div>");
		}
		
		//文档上报
		__embedReport(user, model);
		return "cl-host";
	}
	
	private String __button(String id, String id2){
		ItemInfo ii;
		if(null == id2){
			ii = moduleManager.find("CL_SYSTEM", "1", id);
		}else{
			ii = moduleManager.find("CL_SYSTEM", "1", id, id2);
		}
		return "[<a href='"
					+ ii.getHref() + "' title='进入"
					+ ii.getName() + "'>"
					+ ii.getName() + "</a>]";
	}

	private String __parser(Object[] it) {
		Long aid = (Long)it[0];
		ItemInfo ii = moduleManager.find("CL_SYSTEM", "1", "4");
		String msg = "<div class='embed-content-title'>示范片区[<a href='"
						+ ii.getHref() + "' title='进入"
						+ ii.getName() + "' style='color:blue;'>"
						+ ii.getName() + "</a>]";
		if(check(it)){
			//已经填写一个片区并且已经提交
			msg += "已经完成提交！</div>";
		}else{
			//已经填写一个片区，但还没完成提交
			List<Object[]> nrs = modelAreaService.nrurals(aid);
			msg += "等待提交:</div><div class='embed-content-item'>1. ";
			int total = 0, count0 = 0, count1 = 0;
			boolean cansubmit = false;
			for(Object[] it1 : nrs){
				int t1 = ((Number)it1[0]).intValue(), 
						t2 = ((Number)it1[1]).intValue();
				if(t1 == 1){
					count1 += t2;
				}else if(t1 == 0){
					count0 += t2;
				}else{
					continue;
				}
				total += t2;
			}
			if(total == 0){
				//必须先填写主体村
				msg += "必须先填写主体村";
			}else{
				if(count0 == total){
					msg += "有" + total + "个主体村全<font color='red'>未确认</font>过！";
				}else if(count1 == total){
					msg += "有" + total + "个主体村全部<font color='red'>已经确认</font>过！继续添加";
					cansubmit = true;
				}else{
					msg += "有" + total + "个主体村，其中<font color='red'>未确认</font>有" + count0 + "个！";
				}
			}
			msg += __button("1", "1");
			List<Object[]> prs = modelAreaService.prurals(aid);
			msg += "</div><div class='embed-content-item'>2. ";
			int total0 = 0, count2 = 0, count3 = 0;
			for(Object[] it1 : prs){
				int t1 = ((Number)it1[0]).intValue(), 
						t2 = ((Number)it1[1]).intValue();
				if(t1 == 1){
					count3 += t2;
				}else if(t1 == 0){
					count2 += t2;
				}else{
					continue;
				}
				total0 += t2;
			}
			if(total0 == 0){
				//必须先填写主体村
				msg += "您未录入非主体村";
				//cansubmit = false;
			}else{
				if(count2 == total0){
					cansubmit = false;
					msg += "有" + total0 + "个非主体村全<font color='red'>未确认</font>过！";
				}else if(count3 == total0){
					msg += "有" + total0 + "个非主体村全部<font color='red'>已经确认</font>过！继续添加";
				}else{
					cansubmit = false;
					msg += "有" + total0 + "个非主体村，其中<font color='red'>未确认</font>有" + count2 + "个！";
				}
				msg += __button("2", "1");
			}
			List<Object[]> ias = modelAreaService.iarea(aid);
			msg += "</div><div class='embed-content-item'>3. ";
			int total1 = 0, count4 = 0, count5 = 0;
			for(Object[] it1 : ias){
				int t1 = ((Number)it1[0]).intValue(), 
						t2 = ((Number)it1[1]).intValue();
				if(t1 == 1){
					count5 += t2;
				}else if(t1 == 0){
					count4 += t2;
				}else{
					continue;
				}
				total1 += t2;
			}
			if(total1 == 0){
				cansubmit = false;
				//必须先填写主体村
				msg += "必须先填写产业发展";
			}else{
				if(count4 == total1){
					cansubmit = false;
					msg += "有" + total1 + "个产业发展全<font color='red'>未确认</font>过！";
				}else if(count5 == total1){
					msg += "有" + total1 + "个产业发展全部<font color='red'>已经确认</font>过！继续添加";
				}else{
					cansubmit = false;
					msg += "有" + total1 + "个产业发展，其中<font color='red'>未确认</font>有" + count4 + "个！";
				}
			}
			msg += __button("3", null) + "</div>";
			
			if(cansubmit){
				ii = moduleManager.find("CL_SYSTEM", "1", "6");
				msg += "<div class='embed-content-item'>您可以提交台账了，请进入[：<a href='"
					+ ii.getHref() + "' title='进入"
					+ ii.getName() + "' style='color:blue;'>"
					+ ii.getName() + "</a>]操作！</div>";
			}
		}
		return msg;
	}
	
	private boolean check(Object[] it){
		return ((Number)it[2]).intValue() != ApplicantStatus.DRAFT;
	}
}
