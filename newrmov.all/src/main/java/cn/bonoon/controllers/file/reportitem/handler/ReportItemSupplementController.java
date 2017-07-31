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
 * 补录文档相关处理
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/document/supplement")
public class ReportItemSupplementController extends AbstractController {

	@Autowired
	private RequireReportItemService requireReportItemService;
//	@Autowired
//	private FileService fileService;
//	@Autowired
//	private DirectoryService directoryService;

	// 文件管理->补报文档->补报->dg show
	@RequestMapping(value = "!{mid}/supplement.do", method = { POST, GET })
	public ModelAndView get_supplement(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "supplement", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "补报文档");
		return model.execute("report/report_upload");
	}

	// 文件管理->补报文档->补报->datadrid search
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

	// 文件管理->补报文档->补报->查看
	@RequestMapping(value = "!{mid}/document/detial", method = { POST, GET })
	public ModelAndView document_detial(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "detial", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("doc", new RequireReportDocumentDetail(requireReportItemService.get_document(id)));
		return model.execute("report/document_detial");
	}

	// 文件管理->补报文档->补报->删除
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

	// 文件管理->补报文档->补报->直接添加->dg show
	@RequestMapping(value = "!{mid}/document/upload_add", method = { POST, GET })
	public ModelAndView upload_add(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "upload_add", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "补报文档");
		return model.execute("report/report_upload_add");
	}

	// 文件管理->补报文档->补报->直接添加->保存
	//TODO ===上传文件===
	@Autowired
	private FileManager fileManager;
	@ResponseBody
	@RequestMapping(value = "!{mid}/document/save_upload", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonResult save_upload(@PathVariable("mid") String mid, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Long item_id, 
			String name, 
			String extendedAttributes, 
			//Integer status, 
			String remark//, 
			//Long directory_id, 
			//FileType type, 
			//Date issueAt
		) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			if (null == fileMap || fileMap.size() == 0) {
				throw new Exception("请选择需要上传的文件！");
			}
			RequireReportItemEntity item = requireReportItemService.get(item_id);
			String real_path = RequireReportService.ROOT_PATH + item.getRequireReport().getId();
			// 文件保存路径 ctxPat本地路徑
			//String mapPath = "/uploadFiles";
			//mapPath += "/" + real_path + "/";
			//String ctxPath = request.getSession().getServletContext().getRealPath(mapPath) + File.separator;
			// 创建文件夹
//			File file = new File(ctxPath);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
			LogonUser user = getUser();
//			Date now = new Date();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件
				MultipartFile mf = entity.getValue();
				FileInfo fi = fileManager.save(real_path, DirectoryStrategy.YEAR_MONTH, FilenameStrategy.MD5, mf);
//				String originalFileName = mf.getOriginalFilename(); // 包含后缀名
//				String fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")); // 不包含后缀名
//				String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
//				 重命名文件
//				String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
//				File uploadFile = new File(ctxPath + newFileName);
				
//				File uploadFile = new File(ctxPath + fileName);
//				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				
				name = StringHelper.isNotEmpty(name) ? name : fi.getOriginalFilename();
				
				FileEntity _file = new FileEntity();
//				_file.setCreateAt(now);
//				_file.setCreatorId(user.getId());
//				_file.setCreatorName(user.getUsername());
				_file.setName(name);
//				_file.setOwnerId(user.getOwnerId());
				_file.setExt(fi.getFilesuffix());
				_file.setLength(mf.getSize());
				_file.setMapPath(fi.getRelativePath());
				_file.setExtendedAttributes(extendedAttributes);
//				_file.setStatus(status);
//				_file.setVersion(1);
				_file.setRemark(remark);
//				_file.setType(type);
//				_file.setIssueAt(issueAt);
//				List<DirectoryEntity> directory = new ArrayList<DirectoryEntity>();
//				directory.add(directoryService.get(directory_id));
//				_file.setDirectory(directory);
				requireReportItemService.save_upload(user, item_id, _file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}

	// 文件管理->补报文档->补报->选择添加->dg show
	@RequestMapping(value = "!{mid}/document/option_add", method = { POST, GET })
	public ModelAndView option_add(@PathVariable("mid") String mid, HttpServletRequest request, Long id, String gridid) {
		DialogModel model = new DialogModel(mid + "option_add", request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		model.addObject("id", id);
		model.addObject("title", "补报文档");
		return model.execute("report/report_option_add");
	}

	// 文件管理->补报文档->补报->选择添加->保存
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

}
