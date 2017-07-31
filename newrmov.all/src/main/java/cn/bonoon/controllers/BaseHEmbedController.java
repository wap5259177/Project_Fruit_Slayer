package cn.bonoon.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import cn.bonoon.core.LocalQuarterReportService;
import cn.bonoon.core.QuarterReportStatus;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.ModelAreaQuarterEntity;
import cn.bonoon.entities.ModelAreaQuarterItem;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.menus.ModuleManager;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractController;

public abstract class BaseHEmbedController extends AbstractController{

	@Autowired
	protected RequireReportItemService reportItemService;
	@Autowired
	protected ModuleManager moduleManager;
	@Autowired
	protected LocalQuarterReportService modelAreaQuarterItemService;
	
	protected abstract ItemInfo __menuReport();
	protected abstract ItemInfo __menuSupplement();
	protected abstract ItemInfo __menuQuarterReport();
	protected void __embedReport(LogonUser user, Model model){
		String rimsg = "";
		try{
			List<RequireReportItemEntity> items = reportItemService.get(user);//x.status=0  需要上报的
			List<RequireReportItemEntity> addit = reportItemService.getAdditional(user);//提交了,省级驳回,需要补报的 x.status=1
			List<RequireReportItemEntity> urges = reportItemService.getUrges(user);//x.urge>0  x.requireReport.status=0
			//boolean notitem = true;
			if(!urges.isEmpty()){
				ItemInfo ii = __menuReport();
				rimsg = "<div class='embed-content-title' style='color:red;'>省办催报[进入功能：<a href='"
						+ ii.getHref() + "' title='进入"
						+ ii.getName() + "' style='color:blue;'>"
						+ ii.getName() + "</a>]:</div>";
				int i;
				if(urges.size() > 1 ){
					i = 1;
				}else{
					i = 0;
				}
				for(RequireReportItemEntity rri : urges){
					RequireReportEntity rr = rri.getRequireReport();
					rimsg += "<div class='embed-content-item'> ";
					if(i > 0){
						rimsg += i++ + ". 《";
					}else{
						rimsg += "《";
					}
					rimsg += rr.getName() + "》请尽快上报文档";
					if(rri.getUrge() > 1){
						rimsg += "[已催报<b style='color:red;'>" + rri.getUrge() + "次</b>]";
					}
					rimsg += "</div>";
				}
				rimsg += "<div style='padding-top:10px;'></div>";
			}
			
			
			if(!items.isEmpty()){
				//notitem = false;
				ItemInfo ii = __menuReport();
				rimsg += "<div class='embed-content-title'>要求上报文档，如下：[进入功能：<a href='"
					+ ii.getHref() + "' title='进入"
					+ ii.getName() + "'>"
					+ ii.getName() + "</a>]</div>";
				int i;
				if(items.size() > 1){
					i = 1;
				}else{
					i = 0;
				}
				for(RequireReportItemEntity rri : items){
					RequireReportEntity rr = rri.getRequireReport();
					int dc = rri.getDocumentCount();
					rimsg += "<div class='embed-content-item'> ";
					if(i > 0){
						rimsg += i++ + ". 《";
					}else{
						rimsg += "《";
					}
					rimsg += rr.getName() + "》截止时间：" + StringHelper.date2String(rr.getEndReportedAt());
					if(dc > 0){
						rimsg += "；到目前<span style='color:blue;'>已经上传" + dc + "个</span>文件</div>";
					}else{
						rimsg += "；您未上传文档！</div>";
					}
				}
				rimsg += "<div style='padding-top:10px;'></div>";
			}
			
			if(!addit.isEmpty()){
				//notitem = false;
				ItemInfo ii = __menuSupplement();
				rimsg = "<div class='embed-content-title'><font color='red'>文档补报</font>，如下：[进入功能：<a href='"
						+ ii.getHref() + "' title='进入"
						+ ii.getName() + "'>"
						+ ii.getName() + "</a>]</div>";
				int i;
				if(addit.size() > 1){
					i = 1;
				}else{
					i = 0;
				}
				for(RequireReportItemEntity rri : addit){
					RequireReportEntity rr = rri.getRequireReport();
					int dc = rri.getDocumentCount();
					rimsg += "<div class='embed-content-item'> ";
					if(i > 0){
						rimsg += i++ + ". 《";
					}else{
						rimsg += "《";
					}
					rimsg += rr.getName() + "》<span style='color:blue;'>已经上报" + dc + "个</span>文档</div>";
				}
				rimsg += "<div style='padding-top:10px;'></div>";
			}
			
			
			if(rimsg.isEmpty()){
				rimsg = "<div class='embed-content-message'>当前没有要求上报文档！</div>";
			}				
			
		}catch(Exception ex){
			ex.printStackTrace();
			log.error(ex.getMessage());
			rimsg = "<div class='embed-content-message'>无法读取文档上报情况！</div>";
		}
		model.addAttribute("rimsg", rimsg);
		
		
		/*
		 * 催报:季度报表
		 */
		String reportMessage = "";
		String urgeMessage = "";
		try {
			ItemInfo info = __menuQuarterReport();
			if(info!=null){
				String title = "<font color='red'>季度报表</font>，如下：[进入功能：<a href='"+info.getHref()+"'>季度报表</a>]";
				model.addAttribute("quarterTitle", title);
			}
				
			List<ModelAreaQuarterItem> needReports = modelAreaQuarterItemService.getNeedReport(user);
			if(needReports == null || needReports.isEmpty()){
				reportMessage = "当前没有需要上报的季度报表";
			} else{
//				int hasReportCount = 0;
				int needReportCount = 0;
				int status;
				for(ModelAreaQuarterItem item : needReports){
					status = item.getStatus();
//					item.getStatus()//0:未开始  1:市级审核通过:完成  2:编辑状态 3:驳回  4:已经提交上去:待审核
					if(!item.isDisabled()){//禁止上报的状态就不需要上报了,需要上报的是非禁止状态下的item
						if(status==QuarterReportStatus.NOTSTART || 
								status== QuarterReportStatus.EDIT||
								status== QuarterReportStatus.REJECT){
							needReportCount++;
						}
					}
				}
				if(needReportCount>0){
					reportMessage+="有<font color='red'>"+needReportCount+"个季度</font>需要上报";
				}else{
					reportMessage = "当前没有需要上报的季度报表";
				}
			}
			
			List<ModelAreaQuarterItem> urges = modelAreaQuarterItemService.getUrges(user);
			if(!urges.isEmpty()){
				//String period = "";
				int i=1;
				int quarter ;
				for(ModelAreaQuarterItem item:urges){
					ModelAreaQuarterEntity q = item.getBatch().getQuarter();
					quarter = q.getPeriod();
					if(quarter < 0 || quarter >= quarters.length)quarter=0;
					urgeMessage += i+++".《" + q.getAnnual() + "年" + quarters[quarter]+"》[已催报<font color='red'>"+item.getUrge()+"次</font>]<br>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		model.addAttribute("reportMessage", reportMessage);
		model.addAttribute("urgeMessage", urgeMessage);
	}
	static final String[] quarters = {"第一季度","第二季度","第三季度","第四季度"};
}
