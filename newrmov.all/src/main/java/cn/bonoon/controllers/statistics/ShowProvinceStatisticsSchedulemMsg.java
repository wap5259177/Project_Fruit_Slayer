package cn.bonoon.controllers.statistics;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import cn.bonoon.entities.ModelAreaQuarterAdministrationRural;
import cn.bonoon.entities.ModelAreaQuarterItem;

public class ShowProvinceStatisticsSchedulemMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1115334018725296356L;
	private boolean disabled;
	private Long id;
	private Long qId;
	private boolean isLock;
	private int status;
	private int ordinalModel;
	private ShowStatisticsSchedulemMsg sssm = new ShowStatisticsSchedulemMsg();
	private Set<ShowAdRuralStatisticsSchedulemMsg> sarssSet = new TreeSet<ShowAdRuralStatisticsSchedulemMsg>(
			new Comparator<ShowAdRuralStatisticsSchedulemMsg>() {
				@Override
				public int compare(ShowAdRuralStatisticsSchedulemMsg o1,
						ShowAdRuralStatisticsSchedulemMsg o2) {
					if (o2.getId() > o1.getId()) {
						return 1;
					} else if (o2.getId() < o1.getId()) {
						return -1;
					}
					return 0;
				}
			});

	public ShowProvinceStatisticsSchedulemMsg() {

	}

	public void setMsg(ModelAreaQuarterItem maqi) {
		disabled = maqi.isDisabled();
		id = maqi.getId();
		qId = maqi.getBatch().getQuarter().getId();
		isLock = maqi.isLock();
		status = maqi.getStatus();
		ordinalModel=maqi.getModelArea().getOrdinalModel();
		sssm.setVar1(maqi.getModelArea().getCounty());
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
		System.out.println(maqi.getModelArea().getName());
		for (ModelAreaQuarterAdministrationRural maqar : maqi.getAdminRurals()) {
			ShowAdRuralStatisticsSchedulemMsg sarssm = new ShowAdRuralStatisticsSchedulemMsg();
			sarssm.setMsg(maqar);
			sarssSet.add(sarssm);
		}
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getqId() {
		return qId;
	}

	public void setqId(Long qId) {
		this.qId = qId;
	}

	public boolean getIsLock() {
		return isLock;
	}

	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ShowStatisticsSchedulemMsg getSssm() {
		return sssm;
	}

	public void setSssm(ShowStatisticsSchedulemMsg sssm) {
		this.sssm = sssm;
	}

	public Set<ShowAdRuralStatisticsSchedulemMsg> getSarssSet() {
		return sarssSet;
	}

	public void setSarssSet(Set<ShowAdRuralStatisticsSchedulemMsg> sarssSet) {
		this.sarssSet = sarssSet;
	}

	public int getOrdinalModel() {
		return ordinalModel;
	}

	public void setOrdinalModel(int ordinalModel) {
		this.ordinalModel = ordinalModel;
	}


	
}
