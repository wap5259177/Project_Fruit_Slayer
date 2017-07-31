package cn.bonoon.controllers.file.require;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.web.controllers.AbstractController;


/**
 * 以套树数据表格的形式显示
 * @author ocean~
 */
@Controller
@RequestMapping("/s/report")
public class RequireReportTreeItemController extends AbstractController {

	@Autowired
	private RequireReportService requireReportService;
	@Autowired
	private RequireReportItemService requireReportItemService;

	@ResponseBody
	@RequestMapping(value = "/item/tree", method = { RequestMethod.GET, RequestMethod.POST })
	public List<RequireReportTreeItem> item_tree(Long report_id) {
		List<RequireReportTreeItem> tree = new ArrayList<RequireReportTreeItem>();
		List<RequireReportItemEntity> items = requireReportService.findItem(report_id);
		if (null != items && items.size() != 0) {
			for (RequireReportItemEntity item : items) {
				Long _id = item.getId();
				RequireReportTreeItem node = new RequireReportTreeItem(_id, item.getUnit().getPlace().getName());
				List<RequireReportDocumentEntity> docs = requireReportItemService.get_by_item(_id);
				if (null != docs && docs.size() != 0) {
					List<RequireReportTreeItem> children = new ArrayList<RequireReportTreeItem>();
					for (RequireReportDocumentEntity doc : docs) {
						children.add(new RequireReportTreeItem(doc));
					}
					node.setChildren(children);
				}
				tree.add(node);
			}
		}
		return tree;
	}
}
