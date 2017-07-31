package cn.bonoon.controllers.town;

import cn.bonoon.core.TownApplicantService;
import cn.bonoon.entities.TownApplicantEntity;
import cn.bonoon.kernel.web.controllers.AbstractGridController;

public abstract class AbstractTownApplicantController extends AbstractGridController<TownApplicantEntity, TownApplicantItem>{
	protected final TownApplicantService service;
	public AbstractTownApplicantController(TownApplicantService service) {
		super(service);
		this.service = service;
	}
}
