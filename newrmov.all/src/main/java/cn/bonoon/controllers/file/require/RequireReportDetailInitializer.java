package cn.bonoon.controllers.file.require;

import java.util.List;

import cn.bonoon.core.RequireDetailStatus;
import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class RequireReportDetailInitializer implements DialogFormInitializer<RequireReportEntity> {
	@Override
	public Object init(IService<RequireReportEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		//model.addObject("id", id);
		RequireReportService rrs = (RequireReportService)service;
		List<RequireReportItemEntity> items = rrs.findItem(id);
		StringBuilder finish = new StringBuilder(), notup = new StringBuilder();
		int fincou = 0, notcou = 0;
		boolean showcb = ((RequireReportDetail)form).getStatusValue() == 0;
		for(RequireReportItemEntity it : items){
			Long iid = it.getId();
			if(it.getStatus()==RequireDetailStatus.FINISH || it.getStatus()==RequireDetailStatus.NOTFING){
				//已经上传访求
				fincou++;
				if(it.getStatus()==RequireDetailStatus.NOTFING){
					finish.append("<tr><td style='padding-left:20px;'>").append(fincou).append(". ").append(it.getUnit().getName()+"<font color='red' style='font-size:12px;'>(未查收)</font>")
					.append("<a href='javascript:'   onclick='change(this, "+it.getId()+");'  style='padding-left:20px;'>查收</a> " )
					.append("</td><td>").append(it.getDocumentCount())
					.append("个文档</td></tr>");
				}else{
					finish.append("<tr><td style='padding-left:20px;'>").append(fincou).append(". ").append(it.getUnit().getName()+"<font color='blue' style='font-size:12px;'>(已查收)</font>")
					.append("</td><td>").append(it.getDocumentCount())
					.append("个文档</td></tr>");
				}
				//加载文档
				List<FileEntity> files = rrs.itemFiles(iid);
				for(FileEntity fe : files){
					Long fid = fe.getId();
					finish.append("<tr><td style='padding-left:50px;'><input type='checkbox' value='").append(fid)
						.append("' name='cb_file' id='cff_").append(fid)
						.append("' class='cb_selector'/><label for='cff_").append(fid)
						.append("'>").append(fe.getName())
						.append("</label></td><td><a href='/files/download?id=").append(fid)
						.append("' target='_blank'>下载</a></td></tr>");
				}
			}else{
				//还没开始，可以进行催报
				notcou++;
				notup.append("<tr><td style='padding-left:20px;'>");
				if(showcb){
					notup.append("<input type='checkbox' value='").append(iid)
					.append("' name='cb_notup' id='cn_").append(iid)
					.append("' class='cb_selector'/><label for='cn_").append(iid)
					.append("'> ").append(notcou).append(". ").append(it.getUnit().getName())
					.append("</label>");
				}else{
					notup.append(notcou).append(". ").append(it.getUnit().getName());
				}
				if(it.getStatus()==RequireDetailStatus.NOTSIGN){
					notup.append("</td><td>未签收</td></tr>");
				}else{
					notup.append("</td><td>已签收-未提交</td></tr>");
				}
			}
		}
		if(notup.length() > 0){
			model.addObject("notup", "<tbody id='notup-content'>" + notup + "</tbody>").addObject("notcou", notcou);
		}
		if(finish.length() > 0){
			model.addObject("finish", "<tbody id='finish-content'>" + finish + "</tbody>").addObject("fincou", fincou);
		}
		return form;
	}

}
