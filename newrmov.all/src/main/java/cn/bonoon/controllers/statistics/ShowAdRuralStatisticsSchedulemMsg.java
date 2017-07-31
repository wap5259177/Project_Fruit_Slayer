package cn.bonoon.controllers.statistics;

import java.util.ArrayList;
import java.util.List;

import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterNaturalRural;

public class ShowAdRuralStatisticsSchedulemMsg {
	private Long id;
	private ShowStatisticsSchedulemMsg sssm = new ShowStatisticsSchedulemMsg();
	private List<ShowNewRuralStatisticsSchedulemMsg> snrssmList = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShowStatisticsSchedulemMsg getSssm() {
		return sssm;
	}

	public void setSssm(ShowStatisticsSchedulemMsg sssm) {
		this.sssm = sssm;
	}

	public List<ShowNewRuralStatisticsSchedulemMsg> getSnrssmList() {
		return snrssmList;
	}

	public void setSnrssmList(
			List<ShowNewRuralStatisticsSchedulemMsg> snrssmList) {
		this.snrssmList = snrssmList;
	}

	public void setMsg(ModelAreaQuarterAdministrationRural maqi) {
		id = maqi.getId();
		sssm.setVar1(maqi.getAdminRural().getName());
		sssm.setVar2(maqi.getArCount());
		sssm.setVar3(maqi.getNrCount());
		sssm.setVar4(maqi.getHouseholdCount());
		sssm.setVar5(maqi.getPopulationCount());
		sssm.setVar6(maqi.getArFinishPlan());
		sssm.setVar7(maqi.getNeedFinish().getNeedFinish1());
		sssm.setVar8(maqi.getNeedFinish().getNeedFinish2());
		sssm.setVar9(maqi.getNeedFinish().getNeedFinish3());
		sssm.setVar10(maqi.getNeedFinish().getNeedFinish4());
		sssm.setVar11(maqi.getNeedFinish().getNeedFinish5());
		sssm.setVar12(maqi.getNeedFinish().getNeedFinish6());
		sssm.setVar13(maqi.getNeedFinish().getNeedFinish7());
		sssm.setVar14(maqi.getNeedFinish().getNeedFinish8());
		sssm.setVar15(maqi.getNeedFinish().getNeedFinish9());
		sssm.setVar16(maqi.getStartProject());
		sssm.setVar17(maqi.getFinishProject());
		sssm.setVar18(maqi.getInvestment().getTotalFunds());
		sssm.setVar19(maqi.getInvestment().getStateFunds());
		sssm.setVar20(maqi.getInvestment().getSpecialFunds());
		sssm.setVar21(maqi.getInvestment().getProvinceFunds());
		sssm.setVar22(maqi.getInvestment().getCityFunds());
		sssm.setVar23(maqi.getInvestment().getCountyFunds());
		sssm.setVar24(maqi.getInvestment().getSocialFunds());
		sssm.setVar25(maqi.getInvestment().getMassFunds());
		sssm.setVar26(maqi.getInvestment().getOtherFunds());

		for (ModelAreaQuarterNaturalRural maqnr : maqi.getNaturaRurals()) {
			ShowNewRuralStatisticsSchedulemMsg snrssm = new ShowNewRuralStatisticsSchedulemMsg();
			snrssm.setMsg(maqnr);
			snrssmList.add(snrssm);
		}
	}
}
