package cn.bonoon.controllers.file.require;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.RequireReportItemService;
import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.entities.RequireReportDocumentEntity;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.JsonResult;
import cn.bonoon.kernel.web.models.PanelModel;

/**
 * 添加上报记录（省级用户操作）
 * 
 * @author ocean~
 */
@Controller
@RequestMapping("/s/require/report_add")
public class RequireReportController extends AbstractGridController<RequireReportEntity, RequireReportItem> {
	private RequireReportService requireReportService;
	@Autowired
	private RequireReportItemService requireReportItemService;

	@Autowired
	protected RequireReportController(RequireReportService requireReportService) {
		super(requireReportService);
		this.requireReportService = requireReportService;
	}

	@Override
	@GridStandardDefinition(detailClass = RequireReportDetail.class, insertClass = RequireReportInsert.class, updateClass = RequireReportInsert.class, deleteOperation = true)
	@QueryExpression("x.deleted=0 and x.status=0") // 未结束（MySql）x.endReportedAt > curdate()
	protected RequireReportService initLayoutGrid(LayoutGridRegister register) throws Exception {
		register.button("发布/取消发布", "index.issue", ButtonEventType.GET, ButtonRefreshType.FINISH);
		register.button("归档", "index.finish", ButtonEventType.GET, ButtonRefreshType.FINISH).ordinal(22);
		return requireReportService;
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult finish(Long id){
		try{
			requireReportService.finish(id);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.issue", method = GET)
	public JsonResult issue(Long id){
		try{
			requireReportService.issue(getUser(), id);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}
	
	@ResponseBody
	@RequestMapping(value = "!{key}/index.urge", method = GET)//key=mid
	public JsonResult urge(Long[] cb_notup){
		try{
			if(null == cb_notup || cb_notup.length == 0){
				throw new RuntimeException("请至少选择一条件记录！");
			}
			requireReportService.urge(cb_notup);
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
		return JsonResult.result();
	}
	
	@ResponseBody
	@RequestMapping("!{key}/{id}!report.signin")
	public JsonResult sign(@PathVariable("id") Long id){
		try{
			requireReportItemService.sign(id,getUser());
			return JsonResult.result();
		}catch(Exception ex){
			return JsonResult.error(ex);
		}
	}
	
	
////	@ResponseBody
//	@RequestMapping("!{key}/report.tosend")
//	public String  sendback(Long id) {
//			requireReportItemService.sendback(id);
//			return "report/report_detail_tabs";
//	}
	
	@RequestMapping(value = "!{key}/index.zip", method = GET)
	public void zip(HttpServletResponse response, HttpServletRequest request, Long id, Long[] cb_file){
		response.setContentType("APPLICATION/OCTET-STREAM");  
		ZipOutputStream zos = null;
		try{
			String fileName = requireReportService.getName(id);
			
			response.setHeader("Content-Disposition","attachment; filename=" + new String(fileName.getBytes("GBK"), "iso8859-1") + ".zip");
			zos = new ZipOutputStream(response.getOutputStream());
			
			
			List<RequireReportDocumentEntity> docs = requireReportService.getDocuments(id, cb_file);
			ServletContext sc = request.getSession().getServletContext();
			
			 byte [] content=new byte[1024];
             int len;
             
			for(RequireReportDocumentEntity rrd : docs){
				String dir = rrd.getRequireReportItem().getUnit().getName();
				FileEntity file = rrd.getDocument();
				String _mapPath = file.getMapPath();
				String _name = file.getName() + "." + file.getExt();
				String fileFullPath = sc.getRealPath(_mapPath);
				File fwq_file = new File(fileFullPath);
				if (!fwq_file.isFile() || !fwq_file.exists()) {
					String absolutePath = fwq_file.getAbsolutePath();
					int index = absolutePath.lastIndexOf("\\");
					fileFullPath = absolutePath.substring(0, index + 1) + _name;
				}
				InputStream inputStream = new FileInputStream(fileFullPath);
				try{
					ZipEntry ze = new ZipEntry(dir + "/" + _name);
					zos.putNextEntry(ze);
					
					 while((len = inputStream.read(content))!=-1){
	                     zos.write(content,0,len);
	                     zos.flush();
	                 }
				}finally{
					inputStream.close();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
		}finally{
			if(null != zos){
				try{
					zos.setEncoding("gbk");
					zos.closeEntry();
					zos.close();	
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	protected void onLoad(PanelEvent event) {
		StringBuilder icl = new StringBuilder();
		PanelModel model = event.getModel();
		icl.append(model.toJs("/res/editor/kindeditor-min.js"));
		icl.append(model.toCss("/res/editor/themes/default/default.css"));
		icl.append(model.toJs("/res/editor/lang/zh_CN.js"));
		model.addIncludes(icl);
		super.onLoad(event);
	}
}
