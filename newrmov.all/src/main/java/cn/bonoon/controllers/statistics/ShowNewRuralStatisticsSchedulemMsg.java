package cn.bonoon.controllers.statistics;

import cn.bonoon.entities.ModelAreaQuarterNaturalRural;

public class ShowNewRuralStatisticsSchedulemMsg {
	private Long id;
	private ShowStatisticsSchedulemMsg sssm = new ShowStatisticsSchedulemMsg();

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

	public void setMsg(ModelAreaQuarterNaturalRural maqi) {
		id = maqi.getId();
		sssm.setVar1(maqi.getNewRural().getNaturalVillage());
		sssm.setVar2(maqi.getNrCount());
		sssm.setVar3(maqi.getHouseholdCount());
		sssm.setVar4(maqi.getPopulationCount());
		sssm.setVar5(maqi.getNeedFinish().getNeedFinish1());
		sssm.setVar6(maqi.getNeedFinish().getNeedFinish2());
		sssm.setVar7(maqi.getNeedFinish().getNeedFinish3());
		sssm.setVar8(maqi.getNeedFinish().getNeedFinish4());
		sssm.setVar9(maqi.getNeedFinish().getNeedFinish5());
		sssm.setVar10(maqi.getNeedFinish().getNeedFinish6());
		sssm.setVar11(maqi.getNeedFinish().getNeedFinish7());
		sssm.setVar12(maqi.getNeedFinish().getNeedFinish8());
		sssm.setVar13(maqi.getNeedFinish().getNeedFinish9());
		sssm.setVar14(maqi.getStartProject());
		sssm.setVar15(maqi.getFinishProject());
		sssm.setVar18(maqi.getInvestment().getTotalFunds());
		sssm.setVar19(maqi.getInvestment().getStateFunds());
		sssm.setVar20(maqi.getInvestment().getSpecialFunds());
		sssm.setVar21(maqi.getInvestment().getProvinceFunds());
		sssm.setVar22(maqi.getInvestment().getCityFunds());
		sssm.setVar23(maqi.getInvestment().getCountyFunds());
		sssm.setVar24(maqi.getInvestment().getSocialFunds());
		sssm.setVar25(maqi.getInvestment().getMassFunds());
		sssm.setVar26(maqi.getInvestment().getOtherFunds());
	}

}
