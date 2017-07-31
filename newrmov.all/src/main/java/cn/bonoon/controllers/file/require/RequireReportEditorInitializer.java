package cn.bonoon.controllers.file.require;

import java.util.Collections;
import java.util.List;

import cn.bonoon.core.RequireReportService;
import cn.bonoon.entities.RequireReportEntity;
import cn.bonoon.entities.UnitEntity;
import cn.bonoon.kernel.events.DialogEvent;
import cn.bonoon.kernel.support.services.IService;
import cn.bonoon.kernel.web.handlers.DialogFormInitializer;
import cn.bonoon.kernel.web.models.DialogModel;

public class RequireReportEditorInitializer implements DialogFormInitializer<RequireReportEntity> {
	@Override
	public Object init(IService<RequireReportEntity> service, DialogModel model, DialogEvent event, Long id, Object form) {
		RequireReportService requireReportService = (RequireReportService) service;
		List<UnitEntity> cities = requireReportService.findCity();
		List<UnitEntity> counties = requireReportService.findCounty();
		List<Long> item_place_ids;
//		int count = 0;
		if (null == id || id < 0) { // insert
			item_place_ids = Collections.emptyList();
//			if (null != cities && cities.size() != 0) {
//				StringBuilder str_city = new StringBuilder();
//				for (UnitEntity unit : cities) {
//					str_city.append("<input type='checkbox' value='").append(unit.getId()).append("' name='unit' class='unit_city' />");
//					str_city.append(unit.getName()).append("&nbsp;");
////					if (++count % 8 == 0) {
////						str_city.append("<br/>");
////					}
//					str_city.append("<br/>");
//				}
//				model.addObject("city", str_city);
//			}
//			if (null != counties && counties.size() != 0) {
//				StringBuilder str_county = new StringBuilder();
////				count = 0;
//				for (UnitEntity unit : counties) {
//					str_county.append("<input type='checkbox' value='").append(unit.getId()).append("' name='unit' class='unit_county' />");
//					str_county.append(unit.getName()).append("&nbsp;");
////					if (++count % 8 == 0) {
//						str_county.append("<br/>");
////					}
//				}
//				model.addObject("county", str_county);
//			}
		} else { // update
			item_place_ids = requireReportService.findItemPlaceIds(id);
		}
		if (null != cities && cities.size() != 0) {
//			StringBuilder str_city = new StringBuilder();
//			for (UnitEntity unit : cities) {
//				Long uid = unit.getId();
//				str_city.append("<input type='checkbox' value='").append(uid).append("' name='unit' class='unit_city' id='u_").append(uid).append("' ");
//				str_city.append(item_place_ids.remove(uid) ? "checked='checked' />" : "/>");
//				str_city.append("<label for='u_").append(uid).append("'>").append(unit.getName()).append("</label><br/>");
//			}
			model.addObject("city", __read(cities, item_place_ids));
		}
		if (null != counties && counties.size() != 0) {
//			StringBuilder str_county = new StringBuilder();
//			for (UnitEntity unit : counties) {
//				Long uid = unit.getId();
//				str_county.append("<input type='checkbox' value='").append(unit.getId()).append("' name='unit' class='unit_county' ");
//				str_county.append(item_place_ids.remove(unit.getId()) ? "checked='checked' />" : "/>");
//				str_county.append(unit.getName()).append("&nbsp;");
//					str_county.append("<br/>");
//			}
			model.addObject("county", __read(counties, item_place_ids));
		}
		model.addObject("id", id);
		return form;
	}
	private StringBuilder __read(List<UnitEntity> units, List<Long> item_place_ids){
		StringBuilder str_unit = new StringBuilder();
		for (UnitEntity unit : units) {
			Long uid = unit.getId();
			str_unit.append("<input type='checkbox' value='").append(uid).append("' name='unit' class='unit_city' id='u_").append(uid).append("' ");
			str_unit.append(item_place_ids.remove(uid) ? "checked='checked' />" : "/>");
			str_unit.append("<label for='u_").append(uid).append("'>").append(unit.getName()).append("</label><br/>");
		}
		return str_unit;
	}
}
