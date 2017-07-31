package cn.bonoon.controllers.village.uncommitted;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.controllers.village.AbstractVillageApplicantController;
import cn.bonoon.controllers.village.VillageApplicantDetail;
import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;

//未提交的申报，可以进行修改和删除操作
@Controller
@RequestMapping("s/cl/village/uncommitted/{year}")
public class UncommittedVillageApplicantController extends AbstractVillageApplicantController{

//	private AccountService accountService;
//	private PlaceService placeService;
//	private RoleService roleService;
	@Autowired
	public UncommittedVillageApplicantController(VillageApplicantService service/*,RoleService roleService,AccountService accountService, PlaceService placeService*/) {
		super(service);
//		this.placeService = placeService;
//		this.accountService = accountService;
//		this.roleService = roleService;
		
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
//		request.getParameterMap().put("year", year+"");
	}

//	private void bulidAccount() {
//		List<PlaceEntity> counties = placeService.byLevel(4);
//		RoleEntity countRole = roleService.get(1L);
//		for(PlaceEntity  place: counties){
//			AccountEntity account = new AccountEntity();
//			account.setLoginName(place.getCode().substring(0, 6));
//			account.setLoginPwd("1D916F4887D370BF1089880BDE586D13211769BE1E9A1615");
//			account.setStatus(1);
//			account.setExtStatus(0);
//			account.setFlag(FlagType.ADMIN);
//			account.setExtFlag(0);
//			account.setForcedChangePwd(false);
//			account.setHasLogon(false);			
//			account.setName(place.getParent().getName()+place.getName());
//			account.setCreateAt(new Date());
//			account.setDeleted(false);
//			account.setOwnerId(place.getId());
//			accountService.save(account);
//			roleService.saveAssignRole(getUser(), account.getId(), countRole.getId());
//		}
//		
//		List<PlaceEntity> cities = placeService.byLevel(3);
//		RoleEntity cityRole = roleService.get(2L);
//		for(PlaceEntity  place: cities){
//			AccountEntity account = new AccountEntity();
//			account.setLoginName(place.getCode().substring(0, 4));
//			account.setLoginPwd("1D916F4887D370BF1089880BDE586D13211769BE1E9A1615");
//			account.setStatus(1);
//			account.setExtStatus(0);
//			account.setFlag(FlagType.ADMIN);
//			account.setExtFlag(0);
//			account.setForcedChangePwd(false);
//			account.setHasLogon(false);			
//			account.setName(place.getName());
//			account.setCreateAt(new Date());
//			account.setDeleted(false);
//			account.setOwnerId(place.getId());
//			accountService.save(account);
//			roleService.saveAssignRole(getUser(), account.getId(), cityRole.getId());
//		}
//	}

	@GridStandardDefinition(
			insertClass = VillageApplicantEditor.class, 
			updateClass = VillageApplicantEditor.class, 
			detailClass = VillageApplicantDetail.class)
	@QueryExpression("x.status=0 and x.year={MODEL year -I}")
	@Override
	protected VillageApplicantService initLayoutGrid(LayoutGridRegister register) throws Exception {		
		return service;
	}
	
}
