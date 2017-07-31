//package cn.bonoon.controllers.constructionresults;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.POST;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
//import cn.bonoon.controllers.file.FileHelper;
//import cn.bonoon.controllers.file.management.FileItem;
//import cn.bonoon.core.BaseRuralService;
//import cn.bonoon.core.FileService;
//import cn.bonoon.core.FileType;
//import cn.bonoon.core.ModelAreaService;
//import cn.bonoon.entities.BaseRuralEntity;
//import cn.bonoon.entities.DirectoryEntity;
//import cn.bonoon.entities.FileEntity;
//import cn.bonoon.kernel.io.DirectoryStrategy;
//import cn.bonoon.kernel.io.FileInfo;
//import cn.bonoon.kernel.io.FileManager;
//import cn.bonoon.kernel.io.FilenameStrategy;
//import cn.bonoon.kernel.security.LogonUser;
//import cn.bonoon.kernel.util.StringHelper;
//import cn.bonoon.kernel.web.controllers.AbstractController;
//import cn.bonoon.kernel.web.models.DialogModel;
//import cn.bonoon.kernel.web.models.JsonResult;
//
//@Controller
//@RequestMapping("s/cl/vm")
//public class VideoManagementHandleController extends AbstractController{
//	@Autowired
//	private FileService fileService;
//	@Autowired
//	private BaseRuralService baseRuralService;
//	@Autowired
//	ModelAreaService modelAreaService;
//	
//	@RequestMapping(value = "/dialog", method = { POST, GET })
//	public ModelAndView get_upload(HttpServletRequest request, Long id, String gridid) {
//		DialogModel model = new DialogModel("vm_" + id, request);
//		model.addForm(id);
//		model.addObject("gridid", gridid);
//		model.addObject("id", id);
//		model.addObject("title", "视频管理");
//		return model.execute("applicant/constructionresults/vm");
//	}
//	
//	@RequestMapping(value = "!{key}/file.detail", method = POST)
//	public ModelAndView detail(HttpServletRequest request, final Long id) {
//		return FileHelper.detail(request, fileService, id);
//	}
//	
//	private DirectoryEntity _get_directory(Long id) {
//		BaseRuralEntity baseRural = baseRuralService.get(id);
//		
//		DirectoryEntity directory = baseRural.getDirectoryVideo();
//		
////		if (baseRural instanceof NewRuralEntity) {
////			newRural = (NewRuralEntity) baseRural;
////			directory = newRural.getDirectoryVideo();
////		} else if (baseRural instanceof PeripheralRuralEntity) {
////			PeripheralRuralEntity peripheralRural = (PeripheralRuralEntity) baseRural;
////			newRural = peripheralRural.getNewRural();
////			directory = newRural.getDirectoryVideo();
////		}
//		if (null == directory) {
//			
//		}
//		return directory;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value = "/file/search", method = { RequestMethod.GET, RequestMethod.POST })
//	public List<FileItem> search(HttpServletRequest request, Long id) {
//		try {
//			List<FileEntity> files = _get_directory(id).getFile();
//			List<FileItem> items = new ArrayList<FileItem>();
//			if (null != files && files.size() != 0) {
//				for (FileEntity file : files) {
//					if (!file.isDeleted())
//						items.add(new FileItem(file));
//				}
//			}
//			return items;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<FileItem>();
//		}
//	}
//
//
//	@ResponseBody
//	@RequestMapping(value = "/file/delete", method = { RequestMethod.GET, RequestMethod.POST })
//	public JsonResult file_delete(HttpServletRequest request, String ids) {
//		try {
//			fileService.delete(getUser(), StringHelper.toLongArray(ids));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return JsonResult.result(e);
//		}
//		return JsonResult.result();
//	}
//	
//	@RequestMapping(value = "/file/upload_add", method = { POST, GET })
//	public ModelAndView upload_add(HttpServletRequest request, String gridid, Long id) {
//		DialogModel model = new DialogModel(id + "upload_add", request);
//		model.addForm(id);
//		model.addObject("gridid", gridid);
//		model.addObject("id", id);
//		return model.execute("applicant/constructionresults/vm_upload");
//	}
//	
//	//TODO ===上传文件===
//	@Autowired
//	private FileManager fileManager;
//	
//	@ResponseBody
//	@RequestMapping(value = "/save_upload", method = RequestMethod.POST)
//	public JsonResult save(HttpServletRequest request, HttpServletResponse response, Long id, 
//			String name, String extendedAttributes, Integer status, String remark, FileType type, Date issueAt) {
//		try {
//			
//			BaseRuralEntity baseRural = baseRuralService.get(id);
//			DirectoryEntity dir = baseRural.getDirectoryVideo();
//			if(dir ==null){
//				dir = baseRuralService.createVideo(getUser(),baseRural);
//			}
//			
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//			
////			IRequireReportEditor rre = (IRequireReportEditor)event.getSource();
//////		if (null == fileMap || fileMap.size() == 0) {
////				throw new Exception("请选择需要上传的文件！");
////			}
//			// 文件保存路径 ctxPat本地路徑
//			String mapPath = "modelarea/area_";
//			
//			mapPath += baseRural.getModelArea().getId() + "/rural/rural_" + id + "/video/";
//			//TODO ===============================需要修改的地方
////			if (baseRural instanceof NewRuralEntity) {
////				NewRuralEntity newRural = (NewRuralEntity) baseRural;
////				dir = newRural.getDirectoryVideo();
////				mapPath += newRural.getModelArea().getId() + "/rual_" + id + "/video/";
////			} else if (baseRural instanceof PeripheralRuralEntity) {
////				PeripheralRuralEntity peripheralRural = (PeripheralRuralEntity) baseRural;
////				dir = peripheralRural.getNewRural().getDirectoryVideo();
////				
////				mapPath += peripheralRural.getNewRural().getModelArea().getId() + "/per_" + id + "/video/";
////			}
////			String ctxPath = request.getSession().getServletContext().getRealPath(mapPath) + File.separator;
////			// 创建文件夹
////			File file = new File(ctxPath);
////			if (!file.exists()) {
////				file.mkdirs();
////			}
//			LogonUser user = getUser();
//			Date now = new Date();
//			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//				// 上传文件
//				MultipartFile mf = entity.getValue();
//				
//				FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.YEAR_MONTH, FilenameStrategy.MD5, mf);
//				
////				String originalFileName = mf.getOriginalFilename(); // 包含后缀名
////				String fileName = originalFileName.substring(0, originalFileName.lastIndexOf(".")); // 不包含后缀名
////				String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
////
//			name = StringHelper.isNotEmpty(name) ? name : fi.getOriginalFilename();
////				// 重命名文件，精确到毫秒
////				String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
////				File uploadFile = new File(ctxPath + newFileName);
//
//				// File uploadFile = new File(ctxPath + fileName);
////				FileCopyUtils.copy(mf.getBytes(), uploadFile);
//				FileEntity _file = new FileEntity();
//				_file.setCreateAt(now);
//				_file.setCreatorId(user.getId());
//				_file.setCreatorName(user.getUsername());
//				_file.setOwnerId(user.getOwnerId());
//				_file.setExt(fi.getFilesuffix());
//				_file.setVersion(1);
//				_file.setLength(mf.getSize());
//				_file.setMapPath(fi.getRelativePath());
//				_file.setName(name);
//				_file.setExtendedAttributes(extendedAttributes);
//				_file.setStatus(1);
//				_file.setRemark(remark);
//				_file.setType(type);
//				_file.setIssueAt(issueAt);
//				List<DirectoryEntity> directory = new ArrayList<DirectoryEntity>();
//				directory.add(dir);
//				_file.setDirectory(directory);
//				fileService.save(_file);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return JsonResult.result(e);
//		}
//		return JsonResult.result();
//	}
//	
//}
