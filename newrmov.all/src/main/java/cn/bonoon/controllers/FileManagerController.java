package cn.bonoon.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.bonoon.core.DirectoryService;
import cn.bonoon.core.FileService;
import cn.bonoon.core.FileType;
import cn.bonoon.entities.DirectoryEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.io.DirectoryStrategy;
import cn.bonoon.kernel.io.FileInfo;
import cn.bonoon.kernel.io.FileManager;
import cn.bonoon.kernel.io.FilenameStrategy;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.controllers.AbstractController;
import cn.bonoon.kernel.web.models.JsonResult;

/**
 * 文件上传
 * 
 * @author ocean~
 */
@Controller
@RequestMapping(value = "files")
public class FileManagerController extends AbstractController {
	@Autowired
	private FileService fileService;
	@Autowired
	private DirectoryService directoryService;

	//TODO ===上传文件===
	@Autowired
	private FileManager fileManager;
	
	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public JsonResult upload(HttpServletRequest request, HttpServletResponse response, Long id, String name,
			String extendedAttributes, Integer status, String remark, Long directory_id, FileType type, Date issueAt) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			if (null == fileMap || fileMap.size() == 0) {
				if (null != id && id > 0) {
					FileEntity fe = fileService.get(id);
					if(null != fe){
						fe.setName(name);
						fe.setExtendedAttributes(extendedAttributes);
						fe.setStatus(status);
						fe.setRemark(remark);
						fe.setType(type);
						fe.setIssueAt(issueAt);
						fileService.update(fe);
						//更新下信息，没有重新上传文档，不需要创建历史版本
						return JsonResult.result();
					}
				}
				throw new Exception("请选择需要上传的文件！");
			}
			// 文件保存路径 ctxPat本地路徑
			String mapPath = "/documents/report";
			LogonUser user = getUser();
			Date now = new Date();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				// 上传文件
				MultipartFile mf = entity.getValue();
				FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.YEAR_MONTH, FilenameStrategy.MD5, mf);

				name = StringHelper.isNotEmpty(name) ? name : fi.getOriginalFilename();
				if (null != id && id > 0) { // update
					fileService.updateFileVersion(user, id, name, extendedAttributes, 
							status, remark, type, issueAt, 
							fi.getFilesuffix(), fi.getSize(), fi.getRelativePath());
				} else { // insert
					FileEntity _file = new FileEntity();
					_file.setCreateAt(now);
					_file.setCreatorId(user.getId());
					_file.setCreatorName(user.getUsername());
					_file.setName(name);
					_file.setOwnerId(user.getOwnerId());
					_file.setExt(fi.getFilesuffix());
					_file.setExtendedAttributes(extendedAttributes);
					_file.setStatus(status);
					_file.setVersion(1);
					_file.setRemark(remark);
					_file.setType(type);
					_file.setIssueAt(issueAt);
					_file.setLength(mf.getSize());
					_file.setMapPath(fi.getRelativePath());
					List<DirectoryEntity> directory = new ArrayList<DirectoryEntity>();
					directory.add(directoryService.get(directory_id));
					_file.setDirectory(directory);
					fileService.save(_file);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return JsonResult.result(e);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}

	@RequestMapping("download")
	public void download(final HttpServletResponse response, final HttpServletRequest request, Model model, Long id, String mapPath, String name, String ext) {
		try {
			String _mapPath, _name, _ext;
			if (null != id && id > 0) {
				FileEntity file = fileService.get(id);
				_mapPath = file.getMapPath();
				_name = file.getName();// + "." + file.getExt();
				_ext = file.getExt();
			} else {
				_mapPath = mapPath;
				_name = name;
				_ext = ext;
			}
			fileManager.loadFile(request, response, _mapPath, _name, _ext);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@RequestMapping("detail")
	public void detail(final HttpServletResponse response, final HttpServletRequest request, Model model, Long id, String mapPath, String name, String ext) {
		try {
			String _mapPath, _name, _ext;
			if (null != id && id > 0) {
				FileEntity file = fileService.get(id);
				_mapPath = file.getMapPath();
				_name = file.getName();// + "." + file.getExt();
				_ext = file.getExt();
			} else {
				_mapPath = mapPath;
				_name = name;
				_ext = ext;
			}
			fileManager.openFile(request, response, _mapPath, _name, _ext);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// 不删除真实文件，数据库记录作逻辑删除，不使用此函数
	@ResponseBody
	@RequestMapping("delete")
	public JsonResult delete(final HttpServletRequest request, String ids, Long node_id) {
		try {
			if (StringHelper.isNotEmpty(ids)) {
				Long[] _ids = StringHelper.toLongArray(ids);
				for (Long id : _ids) {
					FileEntity fe = fileService.get(id);
					if (null != fe) {
						String fileFullPath = request.getSession().getServletContext().getRealPath(fe.getMapPath());
						File file = new File(fileFullPath);
						if (file.isFile() && file.exists()) {
							file.delete();
						}
						fileService.delete(fe);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}

}
