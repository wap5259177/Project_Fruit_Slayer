package cn.bonoon.controllers.project.reportManager;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bonoon.core.ProjectService;
import cn.bonoon.core.project.MaProjectInfo;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;
/**
 * 省级项目【导出查询】
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/pl/ma/project/export")
public class ProjectExportController extends AbstractPanelController{

	@Autowired
	private ProjectService projectService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		//model.addObject("reports", reports);
		event.setVmPath("project/export/project-choose");
	}
	
	
	@RequestMapping("!{key}/loadContent.do")
	public String loadContent(Model model,String batch,String cityName,String maName,int status){
		model.addAttribute("layout", "layout-empty.vm");
		model.addAttribute("batch", batch);
		model.addAttribute("maName", maName);
		model.addAttribute("cityName", cityName);
		model.addAttribute("status", status);
		return "project/export/project-list-content";
	}
	
	@ResponseBody
	@RequestMapping("!{key}/search/project.json")
	public List<MaProjectInfo> projectJson(String batch,String cityName,String maName,int status){
		List<MaProjectInfo> infos = projectService.allProInfosBycondition(batch,cityName,maName,status);
		return infos;
	}
	
	//导出table
	@RequestMapping(value="!{key}/projects.excel",method = {POST,GET} )
	@ResponseBody
	public void exportTable(HttpServletRequest request,HttpServletResponse response,Model model,
			String batch,String cityName,String maName,int status){
		try {
//			batch = java.net.URLDecoder.decode(batch, "utf-8");
//			cityName = java.net.URLDecoder.decode(cityName, "utf-8");
//			maName = java.net.URLDecoder.decode(maName, "utf-8");
			projectService.exportMaPros(request,response,batch,cityName,maName,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
