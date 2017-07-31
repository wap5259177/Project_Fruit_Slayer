package cn.bonoon.controllers.file.reportitem.handler;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.FileService;
import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportItemEntity;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 上报文档相关处理
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/report")
public class ReportItemHandlerController extends AbstractController {
	
	@Autowired
	private RequireReportItemService requireReportItemService;
	@Autowired
	private FileService fileService;
//	@Autowired
//	private DirectoryService directoryService;
	@Autowired
	private FileManager fileManager;
	
	// 文件管理->上报文档->上报->dg show
	@RequestMapping(value = "!{mid}/upload.do", method = { POST, GET })
	public ModelAndView get_upload(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "上报文档");
		return model.execute("report/report_upload");
	}
	
	// 文件管理->上报文档->上报->datadrid search
	@ResponseBody
	@RequestMapping(value = "!{mid}/document/search", method = { RequestMethod.GET, RequestMethod.POST })
	public List<RequireReportDocumentItem> search(HttpServletRequest request, Long id) {
		try {
			List<RequireReportDocumentEntity> documents = requireReportItemService.get_by_item(id);
			List<RequireReportDocumentItem> items = new ArrayList<RequireReportDocumentItem>();
			if (null != documents && documents.size() != 0) {
				for (RequireReportDocumentEntity document : documents) {
					items.add(new RequireReportDocumentItem(document));
				}
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<RequireReportDocumentItem>();
		}
	}
	
	// 文件管理->上报文档->上报->查看
	@RequestMapping(value = "!{mid}/document/detial", method = { POST, GET })
	public ModelAndView document_detial(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "detial", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("doc", new RequireReportDocumentDetail(requireReportItemService.get_document(id)));
		return model.execute("report/document_detial");
	}
	
	// 文件管理->上报文档->上报->删除
	@ResponseBody
	@RequestMapping(value = "!{mid}/document/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult file_delete(@PathVariable("mid") String mid, HttpServletRequest request, String ids) {
		try {
			requireReportItemService.delete(getUser(), StringHelper.toLongArray(ids));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.result();
	}
	
	// 文件管理->上报文档->上报->直接添加->dg show
	@RequestMapping(value = "!{mid}/document/upload_add", method = { POST, GET })
	public ModelAndView upload_add(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "upload_add", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "上报文档");
		return model.execute("report/report_upload_add");
	}
	
	// 文件管理->上报文档->上报->直接添加->保存
	@ResponseBody
	@RequestMapping(value = "!{mid}/document/save_upload", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult save_upload(@PathVariable("mid") String mid, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Long item_id, 
			String name, 
			String extendedAttributes, 
			String remark
		) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			if (null == fileMap || fileMap.size() == 0) {
				throw new Exception("请选择需要上传的文件！");
			}
			RequireReportItemEntity item = requireReportItemService.get(item_id);
			String real_path = RequireReportService.ROOT_PATH + item.getRequireReport().getId();
			
			LogonUser user = getUser();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件
				MultipartFile mf = entity.getValue();
				if(mf.isEmpty()) continue;
				
				FileInfo fi = fileManager.save(real_path, DirectoryStrategy.YEAR_MONTH, FilenameStrategy.MD5, mf);
				FileEntity _file = new FileEntity();
				_file.setName(StringHelper.isNotEmpty(name) ? name : mf.getOriginalFilename());
				_file.setExt(fi.getFilesuffix());
				_file.setLength(mf.getSize());
				_file.setMapPath(fi.getRelativePath());
				_file.setExtendedAttributes(extendedAttributes);
				_file.setRemark(remark);
				requireReportItemService.save_upload(user, item_id, _file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}
	
	// 文件管理->上报文档->上报->选择添加->dg show
	@RequestMapping(value = "!{mid}/document/option_add", method = { POST, GET })
	public ModelAndView option_add(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "option_add", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "上报文档");
		return model.execute("report/report_option_add");
	}
	
	// 文件管理->上报文档->上报->选择添加->保存
	@ResponseBody
	@RequestMapping(value = "!{mid}/document/save_option", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult save_option(@PathVariable("mid") String mid, HttpServletRequest request, Long item_id, String file_ids) {
		try {
			requireReportItemService.save_from_file(getUser(), item_id, StringHelper.toLongArray(file_ids));
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}
	
	// 文件管理->上报文档->上报->选择添加->查找并显示目录下的文件
	@ResponseBody
	@RequestMapping(value = "files/find", method = { POST, GET })
	public JsonResult find_files(Long dir_id, String added_fids) {
		List<FileEntity> files = fileService.getFile(dir_id);
		List<String> _added_fids = StringHelper.toList(added_fids);
		StringBuilder dir_file = new StringBuilder("<table style='border:0;width:100%;' id='tb_dir_file'>");
		if (null != files && files.size() != 0) {
			for (FileEntity file : files) {
				Long fid = file.getId();
				dir_file.append("<tr><td style='border:0;text-align:left;vertical-align:middle;padding:3px;cursor:pointer;' class='file_list");
				if (null != _added_fids && _added_fids.size() != 0 && _added_fids.contains(fid.toString())) {
					dir_file.append(" active");
				}
				dir_file.append("' name='fid_").append(fid).append("'>").append(file.getName()).append("</td></tr>");
			}
		}
		dir_file.append("</table>");
		return JsonResult.result(dir_file);
	}
	
}
