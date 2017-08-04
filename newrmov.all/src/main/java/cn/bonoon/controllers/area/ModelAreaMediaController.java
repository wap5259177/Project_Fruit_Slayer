package cn.bonoon.controllers.area;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.management.FileItem;
import cn.bonoon.core.BaseRuralService;
import cn.bonoon.core.FileService;
import cn.bonoon.core.FileType;
import cn.bonoon.core.ModelAreaService;
import cn.bonoon.core.ProjectService;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.ModelAreaEntity;
import cn.bonoon.entities.ProjectEntity;
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
 * 核心村、项目（项目基本情况、进行中、结束）图片管理
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("s/ma/media")
public class ModelAreaMediaController extends AbstractController {

	// @Autowired
	// private NewRuralService newRuralService;
	// @Autowired
	// private PeripheraRuralService peripheraRuralService;
	@Autowired
	private FileService fileService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private BaseRuralService ruralService;
	@Autowired
	private ModelAreaService modelAreaService;

	/**
	 * 以流的形式输出，用于不在web-inf下的图片
	 */
	// flag 0:核心村 1:项目基本情况 2:进行中 3:结束
	@ResponseBody
	@RequestMapping("{id}/{img}/{type}-imgIO.do")
	public void imgIO(HttpServletResponse response,@PathVariable int img,@PathVariable Long id,@PathVariable int type){
		response.setHeader("Content-Type","image/jped");//设置响应的媒体类型，这样浏览器会识别出响应的是图片
		byte[] b=null;
		if(type==2){
			FileEntity fe= modelAreaService.get(id).getModelAreaImg();
//			for(FileEntity var: modelAreaService.get(id).getDirectory().getFile()){
//				System.out.println(var.getId());
//			}
			
			System.out.println(this.getClass().getResource("/").getPath());
			String[] filePathArray=this.getClass().getResource("/").getPath().split("/");
			StringBuffer sb=new StringBuffer();
			for(String var:filePathArray){
				if("target".equals(var))
					break;
					sb.append(var+"/");
			}
			sb.append("src/main/webapp");
			sb.append(fe.getMapPath());
			File f=new File(sb.toString());
			System.out.println(f.getPath());
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
				if(img==0){

				 BufferedImage bi = ImageIO.read(fis);
				  int newWidth = 1624;
		          int newHeight =787;
				 BufferedImage image = new BufferedImage(newWidth, newHeight,
		                    BufferedImage.TYPE_INT_BGR);
		            Graphics graphics = image.createGraphics();
		            graphics.drawImage(bi, 0, 0, newWidth, newHeight, null);
		            ImageIO.write(image,"jpg",response.getOutputStream());  		
				}else{
					b=new byte[fis.available()];
					b=Base64.encode(b);
					fis.read(b);
					response.getOutputStream().write(b);
				}
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
			}
		
		}
		
		
	}
	
	// flag 0:核心村 1:项目基本情况 2:进行中 3:结束
	@RequestMapping("{type}-manager.image")
	public ModelAndView imanager(HttpServletRequest request, Long id,
			String gridid, @PathVariable("type") int type) {
		DialogModel model = new DialogModel("m_m_" + id, request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		// model.addObject("id", id);
		model.addObject("title", "图片管理");
		model.addObject("type", type);
		model.addObject("code", 0);
		System.out.println("id" + id);
		System.out.println("type" + type);
		System.out.println("gridid" + gridid);
		return model.execute("applicant/media-manager");
	}

	@RequestMapping("{type}-manager.vedio")
	public ModelAndView vmanager(HttpServletRequest request, Long id,
			String gridid, @PathVariable("type") int type) {
		DialogModel model = new DialogModel("m_m_" + id, request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		// model.addObject("id", id);
		model.addObject("title", "视频管理");

		model.addObject("type", type);
		model.addObject("code", 1);
		return model.execute("applicant/media-manager");
	}

	@RequestMapping("{id}-{type}-{code}-file.add")
	public ModelAndView upload_add(HttpServletRequest request,
			String buildType, @PathVariable("id") Long id,
			@PathVariable("type") int type, @PathVariable("code") int code,
			String gridid) {

		DialogModel model = new DialogModel("e_m_" + id, request);
		model.addForm(id);
		model.addObject("code", code);
		model.addObject("type", type);
		model.addObject("gridid", gridid);
		model.addObject("buildType", buildType);
		return model.execute("applicant/media-editor");
	}

	// 加载图片列表显示
	@ResponseBody
	@RequestMapping("{id}-{type}-{code}-file.search")
	public List<FileItem> search(HttpServletRequest request,
			@PathVariable("id") Long id, @PathVariable("type") int type,
			@PathVariable("code") int code, String buildType) {
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			// DirectoryEntity de = _get_directory(id, build_type, flag);
			// if(null != de){
			List<FileEntity> files = null;// de.getFile();
			if (type == 1) {
				files = projectService.medias(id, code, buildType);
			} else if (type == 2) {
				files = new ArrayList<FileEntity>();
				if(modelAreaService.get(id).getModelAreaImg()!=null)
				files.add(modelAreaService.get(id).getModelAreaImg());

			} else if (type == 0) {
				files = ruralService.medias(id, code, buildType);
			}
			// if (null != files && files.size() != 0) {
			for (FileEntity file : files) {
				if (!file.isDeleted())
					items.add(new FileItem(file));
			}
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	// build_type 33：建设前，34：建设中，35，建设后
	// private DirectoryEntity _get_directory(Long id, Long build_type, Long
	// flag) {
	// DirectoryEntity directory = null;
	// if (flag == 0) {
	// directory = newRuralService.dir_building(id, build_type.intValue());
	// } else if (flag == 1 || flag == 2 || flag == 3) {
	// directory = projectService.dir_building(id, build_type.intValue());
	// }else if(flag == 4){
	// directory = peripheraRuralService.dir_building(id,
	// build_type.intValue());
	// }
	//
	// return directory;
	// }
	//
	@RequestMapping(value = "{id}!file.detail", method = GET)
	public ModelAndView detail(HttpServletRequest request,
			@PathVariable("id") Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}

	@ResponseBody
	@RequestMapping(value = "file.delete", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonResult file_delete(HttpServletRequest request, String ids) {
		try {
			fileService.delete(getUser(), StringHelper.toLongArray(ids));
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}

		return JsonResult.result();
	}

	// TODO ===上传文件===
	@Autowired
	private FileManager fileManager;

	@ResponseBody
	@RequestMapping(value = "{id}-{type}-{code}-file.save", method = RequestMethod.POST)
	public JsonResult save(HttpServletRequest request,
			HttpServletResponse response, String buildType,
			@PathVariable("id") Long id,
			@PathVariable("type") int type,
			@PathVariable("code") int code,
			MultipartFile mediaFile,
			// Long id,
			// Long build_type,
			// Long flag,
			String rename, String extendedAttributes, String externalUrl,
			boolean externalLink,
			// Integer status,
			String remark,
			// FileType type,
			Date issueAt) {
		try {
			// MultipartHttpServletRequest multipartRequest =
			// (MultipartHttpServletRequest) request;
			// Map<String, MultipartFile> fileMap =
			// multipartRequest.getFileMap();
			if (externalLink) {
				if (StringHelper.isEmpty(externalUrl)) {
					throw new Exception("请输入可访问的网址！");
				}
				if (StringHelper.isEmpty(rename)) {
					throw new Exception("请输入名称，以访问查看！");
				}
			} else {
				if (null == mediaFile || mediaFile.isEmpty()) {
					throw new Exception("请选择需要上传的文件！");
				}

			}
			LogonUser user = getUser();
			Date now = new Date();
			FileEntity _file = new FileEntity();
			_file.setCreateAt(now);
			_file.setCreatorId(user.getId());
			_file.setCreatorName(user.getUsername());
			_file.setOwnerId(user.getOwnerId());
			_file.setRemark(remark);
			_file.setIssueAt(issueAt);
			_file.setExtattr7(buildType);
			_file.setExtattr1(code);
			_file.setExternalLink(externalLink);
			_file.setExtendedAttributes(extendedAttributes);
			if (code == 1) {
				_file.setType(FileType.VIDEO);
			} else {
				// BufferedImage bi= ImageIO.read(mediaFile.getInputStream());
				// bi.
				// if (checkContentType(mediaFile.getOriginalFilename()) != 2) {
				// throw new Exception("上传得不是图片格式");
				//
				// }

				// String fileType = getFileType(mediaFile);
				// System.out.println("文件头测试文件后缀名:" + fileType);
				// if (fileType == null
				// || !(fileType.equals("jpg") || fileType.equals("png")
				// || fileType.equals("jpeg")
				// || fileType.equals("gif") || fileType
				// .equals("bmp"))) {
				// throw new Exception("上传得不是图片");
				// }
				_file.setType(FileType.IMAGE);
			}
			if (externalLink) {
				URL imageUrl = null;
				try {
					imageUrl = new URL(externalUrl);
				} catch (Exception e) {
					throw new Exception("无效的图片地址!");
				}
				if (!checkImage(imageUrl)) {
					throw new Exception("链接的地址不是图片!");
				}
			
				_file.setName(rename);
				_file.setMapPath(externalUrl);
				int p = externalUrl.lastIndexOf('.');
				if (p > 0) {
					_file.setExt(externalUrl.substring(p));
				}
				if (type == 1) {
					ProjectEntity pe = projectService.get(id);
					projectService.saveMedia(pe, _file);
				} else {
					BaseRuralEntity re = ruralService.get(id);
					ruralService.saveMedia(re, _file);
				}
			} else {
				if (code != 1) {
					boolean isImage = checkImage(mediaFile);
					if (!isImage) {
						throw new Exception("上传的本地文件不是图片!");

					}
				}
				if (type == 1) {
					ProjectEntity pe = projectService.get(id);
					String mapPath = "/modelarea/area_"
							+ pe.getModelArea().getId();
					mapPath += "/project/project_" + id + "/media";
					__set(_file, mediaFile, mapPath, rename);
					projectService.saveMedia(pe, _file);
				} else if (type == 2) {

					ModelAreaEntity ma = modelAreaService.get(id);				
					String mapPath = "/modelarea/area_" + ma.getId();
					mapPath += "/media";
					String oldFileName = ma.getName() + "规划图";
					__set(_file, mediaFile, mapPath, oldFileName);
					_file.setName(oldFileName);
					modelAreaService.saveMedia(ma, _file);
				} else if (type == 0) {
					BaseRuralEntity re = ruralService.get(id);
					System.out.println(re.getModelArea() == null);
					if (re.getModelArea() == null) {
						throw new Exception("没有该示范片,无法添加图片!");
					}
					String mapPath = "/modelarea/area_"
							+ re.getModelArea().getId();
					mapPath += "/rural/rural_" + id + "/media";
					__set(_file, mediaFile, mapPath, rename);
					ruralService.saveMedia(re, _file);
				}
			}
			// 文件保存路径 ctxPat本地路徑
			// DirectoryEntity dir = _get_directory(id, build_type, flag);

			// DirectoryEntity dir = null;
			// if (flag == 0) {
			// dir = newRuralService.dir_buildingOrCreate(getUser(), id,
			// build_type.intValue());
			// } else if (flag == 1 || flag == 2 || flag == 3) {
			// dir = projectService.dir_buildingOrCreate(getUser(), id,
			// build_type.intValue());
			// }else if(flag == 4){
			// dir = peripheraRuralService.dir_buildingOrCreate(getUser(),id,
			// build_type.intValue());
			// }

			// String mapPath = "/modelarea/area_";
			// if (flag == 0) {
			// mapPath += newRuralService.get(id).getModelArea().getId() +
			// "/rural/rural_" + id;
			// } else if (flag == 1 || flag == 2 || flag == 3) {
			// mapPath += projectService.get(id).getModelArea().getId() +
			// "/project/project_" + id;
			// }else if(flag == 4){
			// mapPath += peripheraRuralService.get(id).getModelArea().getId() +
			// "/peripheral/rural_" + id;
			// }
			// mapPath += "/media/" + (build_type == 33 ? "before" : build_type
			// == 34 ? "building" : "after") + "/";
			// String ctxPath =
			// request.getSession().getServletContext().getRealPath(mapPath) +
			// File.separator;
			// 创建文件夹
			// File file = new File(ctxPath);
			// if (!file.exists()) {
			// file.mkdirs();
			// // }
			// LogonUser user = getUser();
			// Date now = new Date();
			// for (Map.Entry<String, MultipartFile> entity :
			// fileMap.entrySet()) {
			// 上传文件
			// MultipartFile mf = entity.getValue();
			// FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE,
			// FilenameStrategy.MD5, mf);
			// String originalFileName = mf.getOriginalFilename(); // 包含后缀名
			// String fileName = originalFileName.substring(0,
			// originalFileName.lastIndexOf(".")); // 不包含后缀名
			// String fileExt =
			// originalFileName.substring(originalFileName.lastIndexOf(".") +
			// 1).toLowerCase();

			// name = StringHelper.isNotEmpty(name) ? name :
			// fi.getOriginalFilename();
			// 重命名文件，精确到毫秒
			// String newFileName = new
			// SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + "_" +
			// new Random().nextInt(1000) + "." + fileExt;
			// File uploadFile = new File(ctxPath + newFileName);

			// File uploadFile = new File(ctxPath + fileName);
			// FileCopyUtils.copy(mf.getBytes(), uploadFile);

			// _file.setExtendedAttributes(extendedAttributes);
			// List<DirectoryEntity> directory = new
			// ArrayList<DirectoryEntity>();
			// directory.add(dir);
			// _file.setDirectory(directory);
			// fileService.save(_file);
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}

	private void __set(FileEntity _file, MultipartFile mediaFile,
			String mapPath, String rename) throws IOException {
		FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.YEAR_MONTH,
				FilenameStrategy.MD5, mediaFile);
		rename = StringHelper.get(rename, fi.getOriginalFilename());
		_file.setExt(fi.getFilesuffix());
		_file.setLength(fi.getSize());
		_file.setMapPath(fi.getRelativePath());
		_file.setName(rename);
	}

	private boolean checkImage(MultipartFile mediaFile) {
		// BufferedImage i = ImageIO.read(this.image);
		if (mediaFile != null && mediaFile.getSize() > 0) {
			try {
				BufferedImage is2 = ImageIO.read(mediaFile.getInputStream());
				if (null == is2) {
					// System.out
					// .println(mediaFile.getOriginalFilename() + "不是图片");
					return false;
				}
				// System.out.println(mediaFile.getOriginalFilename() + "是图片");
				return true;
			} catch (Exception e) {
				// e.printStackTrace();
				// System.out.println("error");
				return false;
			}
		}
		return false;
	}

	private boolean checkImage(URL url) {
		try {
			BufferedImage is2 = ImageIO.read(url.openConnection()
					.getInputStream());
			if (null == is2) {
				return false;
			}
			return true;

		} catch (Exception e) {
		}
		return false;
	}

	// public static void checkImage(File file) {
	// // BufferedImage i = ImageIO.read(this.image);
	// //
	//
	// try {
	//
	// BufferedImage is2 = ImageIO.read(file);
	// if (null == is2) {
	// System.out.println(file.getName() + "不是图片");
	// return;
	// }
	//
	// System.out.println(file.getName() + "是图片");
	// } catch (Exception e) {
	// // e.printStackTrace();
	// System.out.println("error");
	// }
	//
	// }

	// public static void main(String[] args) throws Exception {
	// checkImage(new File(
	// "C:\\Users\\wuzhanggui\\Desktop\\wsf\\day20_uploadListener\\day20_uploadListener\\day20_uploadListener\\视频\\14踢人案例.avi"));
	// checkImage(new File("C:/Users/wuzhanggui/Desktop/aaa.gif"));
	// checkImage(new File("C:/Users/wuzhanggui/Desktop/aa.jpg"));
	// checkImage(new File("C:/Users/wuzhanggui/Desktop/a.gif"));
	//
	// }
}
