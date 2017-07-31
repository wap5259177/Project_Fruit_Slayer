package cn.bonoon.controllers.area;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.controllers.file.FileHelper;
import cn.bonoon.controllers.file.management.FileItem;
import cn.bonoon.core.BaseRuralService;
import cn.bonoon.core.FileService;
import cn.bonoon.core.FileType;
import cn.bonoon.core.NewRuralService;
import cn.bonoon.entities.AdministrationCoreRuralEntity;
import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.NewRuralEntity;
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
 * 片区（核心村、非主体村、项目）文件上传
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("s/rural/annex")
public class RuralAnnexController extends AbstractController {

	@Autowired
	private FileService fileService;

	@Autowired
	private BaseRuralService ruralService;
	
	private final Map<String, String> values = new HashMap<>();
	public RuralAnnexController(){
		values.put("B27", "旧房整治改造规划");
		values.put("B28", "民居住宅设计标准图");
		values.put("B49", "总体规划");
		values.put("B50", "连线连片规划");
		values.put("B51", "村庄深度规划设计");
		values.put("C27", "旧房整治改造规划");
		values.put("C28", "民居住宅设计标准图");
		values.put("C36", "村庄环境整治规划");
	}
	// area_child：0核心村 1非主体村 2项目， id：核心村、非主体村或项目id， dir_flag：实体目录标识，如B27、B28、C27等
	@RequestMapping(value = "/{id}-{type}-{code}-upload.do", method = { POST, GET })
	public ModelAndView manager(HttpServletRequest request, 
			@PathVariable("id") Long id, 
			@PathVariable("type") int type, 
			@PathVariable("code") String code, 
			String readonly, long arid) {
		
		DialogModel model = new DialogModel("ra_" + id, request);
		model.addForm(id);
		model.addObject("editable", !"true".equalsIgnoreCase(readonly));
		model.addObject("dgid", "d_dg_" + id);
		__set(model, type, code, arid);
		return model.execute("applicant/rural-annex");
	}
	
	private void __set(DialogModel model, int type, String code, long arid){
		String title = type == 1 ? "主体村 | " : "非主体村 | ";
		title += values.get(code);
		model.addObject("title", title);
		model.addObject("code", code);
		model.addObject("type", type);
		model.addObject("arid", arid);
	}
	
	private void __set(DialogModel model,  String code, long arid){
		String title =  "主体村 | 行政村 | " ;
		title += values.get(code);
		model.addObject("title", title);
		model.addObject("code", code);
		model.addObject("arid", arid);
	}
//----------------------------行政村  上传文件-------------------------------------------------
	@RequestMapping(value = "/{id}-{code}-administration_upload.do", method = { POST, GET })
	public ModelAndView adminRuralManager(HttpServletRequest request, 
			@PathVariable("id") Long id, 
			@PathVariable("code") String code, 
			String readonly) {
		DialogModel model = new DialogModel("ra_" + id, request);
		model.addForm(id);
		model.addObject("editable", !"true".equalsIgnoreCase(readonly));
		model.addObject("dgid", "d_dg_" + id);
		__set(model, code, id);//关键代码,将arid  传到页面
//		return model.execute("applicant/rural2-annex");
		return model.execute("applicant/administrationrural-annex");
	}
	
	//添加
	@RequestMapping(value = "/{id}-{code}-administration_file.add", method = { POST, GET })
	public ModelAndView adminRuralAdd(HttpServletRequest request, 
			@PathVariable("id") Long id, 
			@PathVariable("code") String code,
			String gridid) {
		DialogModel model = new DialogModel("fa_" + id, request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		__set(model,  code, id);//关键代码,将arid  传到页面
		
//		return model.execute("applicant/rural-annex-editor2");
		return model.execute("applicant/administrationrural-annex-editor");
	}
	
	
	
//-----------------------------------------------------------------------------------------	
	//自然村的文件上传
	@RequestMapping(value = "/{id}-{type}-{code}-file.add", method = { POST, GET })
	public ModelAndView add(HttpServletRequest request, 
			@PathVariable("id") Long id, 
			@PathVariable("type") int type, 
			@PathVariable("code") String code,
			String gridid, long arid) {
		DialogModel model = new DialogModel("fa_" + id, request);
		model.addForm(id);
		model.addObject("gridid", gridid);
		__set(model, type, code, arid);
		
		return model.execute("applicant/rural-annex-editor");
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}-{code}-file.search", method = { RequestMethod.GET, RequestMethod.POST })
	public List<FileItem> search(HttpServletRequest request, 
			@PathVariable("id") Long id, 
			@PathVariable("code") String code, long arid) {
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			List<FileEntity> files = ruralService.annexes(id, arid, code);//这里的实现是当arid >0时,从t_adminrural中查找文件
			if (null != files && files.size() != 0) {
				for (FileEntity file : files) {
					if(!file.isDeleted()){
						items.add(new FileItem(file));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	//文件查看
	@RequestMapping(value = "{id}!file.detail")
	public ModelAndView detail(HttpServletRequest request, @PathVariable("id") Long id) {
		return FileHelper.helper.detail(request, fileService, id);
	}

	@ResponseBody
	@RequestMapping(value = "/file.delete")
	public JsonResult delete(HttpServletRequest request, String ids) {
		try {
			fileService.delete(getUser(), StringHelper.toLongArray(ids));
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.result(e);
		}
		return JsonResult.result();
	}
	//TODO ===上传文件===
	@Autowired
	private FileManager fileManager;
	@ResponseBody
	@RequestMapping(value = "/{id}-{code}-file.upload", method = RequestMethod.POST)
	public JsonResult save(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("id") Long id, 
			@PathVariable("code") String code,
			MultipartFile annexFile,
			String rename, 
			String remark, 
			FileType type, 
			Date issueAt, long arid) {
		try {
			if (null == annexFile || annexFile.isEmpty()) {
				throw new Exception("请选择需要上传的文件！");
			}
			String mapPath = "/modelarea/area_";
			if(arid > 0){
				AdministrationCoreRuralEntity are = ruralService.getAdminRural(arid);
				mapPath += are.getModelArea().getId() + "/rural/administration_" + arid;
				FileEntity _file = __save(code, annexFile, rename, remark, type, issueAt, mapPath);
				ruralService.saveAnnex(are, _file);
			}else{
				BaseRuralEntity rural = ruralService.get(id);
				mapPath += rural.getModelArea().getId() + "/rural/rural_" + id;
				FileEntity _file = __save(code, annexFile, rename, remark, type, issueAt, mapPath);
				ruralService.saveAnnex(rural, _file);
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
	private FileEntity __save(String code, MultipartFile annexFile,
			String rename, String remark, FileType type, Date issueAt,
			String mapPath) throws IOException {
		LogonUser user = getUser();
		Date now = new Date();
		FileInfo fi = fileManager.save(mapPath, DirectoryStrategy.NONE, FilenameStrategy.MD5, annexFile);
		rename = StringHelper.get(rename, fi.getOriginalFilename());
		FileEntity _file = new FileEntity();
		_file.setCreateAt(now);
		_file.setCreatorId(user.getId());
		_file.setCreatorName(user.getUsername());
		_file.setName(rename);
		_file.setOwnerId(user.getOwnerId());
		_file.setExt(fi.getFilesuffix());
		_file.setRemark(remark);
		_file.setType(type);
		_file.setIssueAt(issueAt);
		_file.setLength(fi.getSize());
		_file.setMapPath(fi.getRelativePath());
		_file.setExtattr6(code);
		return _file;
	}
	
	@Autowired
	private NewRuralService newRuralService;
	
	@RequestMapping(value = "councilFile.download", method = GET)
	public void councilFile(
			final HttpServletResponse response, 
			final HttpServletRequest request, 
			final Long id){
		try{
			NewRuralEntity it = newRuralService.get(id);
			String path = it.getCouncilFilePath();
			String name = it.getCouncilFileName();
			
			fileManager.loadFile(request, response, path, name, null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
