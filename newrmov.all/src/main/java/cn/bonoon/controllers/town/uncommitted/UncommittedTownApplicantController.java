package cn.bonoon.controllers.town.uncommitted;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.town.AbstractTownApplicantController;
import cn.bonoon.controllers.town.TownApplicantDetail;
import cn.bonoon.core.TownApplicantService;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;

//未提交的申报，可以进行修改和删除操作
@Controller
@RequestMapping("s/cl/town/uncommitted/{year}")
public class UncommittedTownApplicantController extends AbstractTownApplicantController{

	@Autowired
	public UncommittedTownApplicantController(TownApplicantService service) {
		super(service);
	}
	
	private int year;
	
	@ModelAttribute("year")
	public int year(@PathVariable("year") int year){
		this.year = year;
		return year;
	}
	
	@Override
	protected void initRequest(HttpServletRequest request) {
		request.setAttribute("year", year);
	}

	@GridStandardDefinition(
			insertClass = TownApplicantEditor.class, 
			updateClass = TownApplicantEditor.class, 
			detailClass = TownApplicantDetail.class)
	@QueryExpression("x.status=0 and x.year={MODEL year -I}")
	@Override
	protected TownApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {
		return service;
	}
}
