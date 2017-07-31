package cn.bonoon.controllers.village;

import cn.bonoon.core.VillageApplicantService;
import cn.bonoon.entities.VillageApplicantEntity;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

public abstract class AbstractVillageApplicantController extends AbstractGridController<VillageApplicantEntity, VillageApplicantItem>{
	protected final VillageApplicantService service;
	public AbstractVillageApplicantController(VillageApplicantService service) {
		super(service);
		this.service = service;
	}

}
