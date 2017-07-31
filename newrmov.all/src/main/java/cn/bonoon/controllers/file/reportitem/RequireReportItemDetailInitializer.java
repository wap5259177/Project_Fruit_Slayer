package cn.bonoon.controllers.file.reportitem;

import java.util.List;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class RequireReportItemDetailInitializer implements DialogFormInitializer<RequireReportItemEntity> {

	@Override
	public Object init(IService<RequireReportItemEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		RequireReportItemService requireReportItemService = (RequireReportItemService) service;
		List<RequireReportDocumentEntity> documents = requireReportItemService.get_by_item(id);
		if (null != documents && documents.size() != 0) {
			StringBuilder doc_item = new StringBuilder();
			for (RequireReportDocumentEntity document : documents) {
				FileEntity file = document.getDocument();
				doc_item.append("<tr><td>").append(file.getName()).append("</td><td>").append(file.getType().getName()).append("</td><td>");
				doc_item.append(StringHelper.date2String(document.getOperateAt()));
				doc_item.append("</td><td><a tagert='_blank' href=\"/files/download?id=").append(file.getId()).append("\">下载</a></td></tr>");
			}
			model.addObject("doc_item", doc_item);
		}
		return form;
	}

}
