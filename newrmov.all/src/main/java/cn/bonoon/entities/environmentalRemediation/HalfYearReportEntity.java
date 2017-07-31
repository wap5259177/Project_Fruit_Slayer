package cn.bonoon.entities.environmentalRemediation;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.bonoon.entities.BaseRuralEntity;
import cn.bonoon.kernel.support.entities.AbstractEntity;
/**
 * 
 *  镇级填报其所有自然村半年度报表
 * @author wsf
 * @creation 2017-1-16 
 *
 */
@Entity
@Table(name = "t_halfyearreport")
public class HalfYearReportEntity  extends AbstractEntity{
	//每个半年度报表对应一个自然村
	@Column(name="R_Rural")
	private BaseRuralEntity rural;
	
	//指代是上半年还是下半年填写的 0上半年度报表,1下半年度报表
	@Column(name = "C_HALFYEARREPORTBATCH")
		private Integer halfYearReportBatch;
		
	// 记录镇提交完成上报报表时间
	@Column(name = "C_REPORTTIME")
		private Date reportTime;
	
	//报表状态 
	/**
	 * 0未开始 1提交到县 2镇级正在填报,暂存状态
	 */
	@Column(name="BATCH")
	private Integer batch;
//	一、自然村基本情况	
		
		//		所在行政村是否省相对贫困村   □是    □否;
		@Column(name = "C_POORVILLAGE")
		private Boolean poorVillage;

		//		总面积            平方公里，
		@Column(name = "C_RURALAREA")
		private Double ruralArea;
		
		//		耕地面积            亩；
		@Column(name = "C_ARABLELAND")
		private Double arableLand;
		
		//		总户数        户，
		@Column(name = "C_HOUSEHOLD")
		private Integer houseHold;
		
		//		总人口数         人；
		@Column(name = "C_POPULATION")
		private Integer population;

		//		是否成立了村民理事会：□是  □否 ；
		@Column(name = "C_COUNCIL")
		private Boolean  council;
		
		//		是否完成村庄人居环境综合整治  □是   □否；时间         年         月。	
		@Column(name = "C_HYFIXFINISHTIME")
		private Date hyFixFinishTime;
		
//		二、规划编制情况	
//		"是否编制村庄规划： □是   □否；
//		@Column(name = "C_PREPARATIONVILLAGEPLAN")
//		private Boolean preparationVillagePlan;
		
//		（□村庄规划  □村庄整治规划  □历史文化名村规划  □其他            ）"

		@Column(name = "C_VILLAGEPLAN")
		private Boolean villagePlan;
		
		@Column(name = "C_VILLAGERENOVATIONPLAN")
		private Boolean villageRenovationPlan;

		@Column(name = "C_PLANNFAMOUSHISTORICALVILLAGES")
		private Boolean plannFamousHistoricalVillages;
		
		@Column(name = "C_PREPARATIONVILLAGEPLANOTHER")
		private Boolean preparationVillagePlanOther;

//	三、整治情况	
		//		是否完成清四旧： □是   □否；
		@Column(name = "C_CLEARFOUROLD")
		private Boolean clearFourOld;
		
		//		是否完成清五乱： □是   □否；
		@Column(name = "C_CLEARFIVECHAOS")
		private Boolean clearFiveChaos;
		
		//		是否完成清六边： □是   □否；
		@Column(name = "C_CLEARSIXSIDES")
		private Boolean clearSixSides;
		
		//		已拆除“四旧”      处。累计清理垃圾     吨；
		@Column(name = "C_CLEARFOUROLDSIZE")
		private Integer clearFourOldSize;
		
		@Column(name = "C_ACCUMULATEDCLEANUPGARBAGE")
		private Double accumulatedCleanUpGarbage;
		
//		是否进行了环境提升：□是   □否；复绿面积       平方米；种树      棵；
		@Column(name = "C_GREENAREA")
		private Double greenArea;
		@Column(name = "C_PLANTTREESAMOUNT")
		private Double plantTreesAmount;
		
//	四、长效保洁机制建立情况	是否设有垃圾收集点：□是  □否；
		@Column(name = "C_GARBAGESTATION")
		private Boolean GarbageStation;
		
//		是否建立门前“三包”等长效保洁机制：□是  □否；
		@Column(name = "C_THREEGUARANTEES")
		private Boolean ThreeGuaranTees;
		
//		是否建立村保洁队伍：□是  □否（保洁员数量    人）；
		@Column(name = "C_CLEANTEAM")
		private Integer cleanTeam;
		
//	五、整治污水情况	是否完成卫生改厕： □是  □否；
		@Column(name = "C_CHANGEWC")
		private Boolean changeWC;
		
//		是否实行畜禽圈养: □是  □否；
		@Column(name = "C_ANIMALCAPTIVE")
		private Boolean animalCaptive;
		
//		是否实施雨污分流： □是  □否；
		@Column(name = "C_RAINANDSEWAGEDIVERSION")
		private Boolean rainAndSewageDiversion;
		
//		"是否建有污水处理设施：□是  □否
		@Column(name = "C_BADWATER")
		private Boolean badWater;
		
//		（□厌氧池 □人工湿地 □沼气池 □其他              ）"
		@Column(name = "C_ANAEROBICTANK")
		private Boolean AnaerobicTank;
		@Column(name = "C_ARTIFICIALWETLAND")
		private Boolean ArtificialWetland;
		@Column(name = "C_BIOGASDIGESTERS")
		private Boolean BiogasDigesters;
		@Column(name = "C_SEWAGETREATMENTFACILITIESOTHER")
		private Boolean SewageTreatmentFacilitiesOther;
		
//	六、公共基础设施建设情况	是否完成通自来水：□是   □否；
		@Column(name = "C_TAPWATER")
		private Boolean tapWater;
		
//		是否完成村巷道硬底化：□是   □否；
		@Column(name = "C_HARDBOTTOM")
		private Boolean hardBottom;
//		是否建有小公园、小广场： □是   □否；
		@Column(name = "C_PARKORSQUARE")
		private Boolean ParkOrSquare;
//		@Column(name = "C_SQUARE")
//		private Boolean Square;
		
		
		
		public Integer getHalfYearReportBatch() {
			return halfYearReportBatch;
		}
		public BaseRuralEntity getRural() {
			return rural;
		}
		public void setRural(BaseRuralEntity rural) {
			this.rural = rural;
		}
		public Integer getBatch() {
			return batch;
		}
		public void setBatch(Integer batch) {
			this.batch = batch;
		}
		public void setHalfYearReportBatch(Integer halfYearReportBatch) {
			this.halfYearReportBatch = halfYearReportBatch;
		}
		public Date getReportTime() {
			return reportTime;
		}
		public void setReportTime(Date reportTime) {
			this.reportTime = reportTime;
		}
		public Boolean isPoorVillage() {
			return poorVillage;
		}
		public void setPoorVillage(Boolean poorVillage) {
			this.poorVillage = poorVillage;
		}
		public Double getRuralArea() {
			return ruralArea;
		}
		public void setRuralArea(Double ruralArea) {
			this.ruralArea = ruralArea;
		}
		public Double getArableLand() {
			return arableLand;
		}
		public void setArableLand(Double arableLand) {
			this.arableLand = arableLand;
		}
		public Integer getHouseHold() {
			return houseHold;
		}
		public void setHouseHold(Integer houseHold) {
			this.houseHold = houseHold;
		}
		public Integer getPopulation() {
			return population;
		}
		public void setPopulation(Integer population) {
			this.population = population;
		}
		public Boolean isCouncil() {
			return council;
		}
		public void setCouncil(Boolean council) {
			this.council = council;
		}
		public Date getHyFixFinishTime() {
			return hyFixFinishTime;
		}
		public void setHyFixFinishTime(Date hyFixFinishTime) {
			this.hyFixFinishTime = hyFixFinishTime;
		}
//		public Boolean isPreparationVillagePlan() {
//			return preparationVillagePlan;
//		}
//		public void setPreparationVillagePlan(Boolean preparationVillagePlan) {
//			this.preparationVillagePlan = preparationVillagePlan;
//		}
		public Boolean isVillagePlan() {
			return villagePlan;
		}
		public void setVillagePlan(Boolean villagePlan) {
			this.villagePlan = villagePlan;
		}
		public Boolean isVillageRenovationPlan() {
			return villageRenovationPlan;
		}
		public void setVillageRenovationPlan(Boolean villageRenovationPlan) {
			this.villageRenovationPlan = villageRenovationPlan;
		}
		public Boolean isPlannFamousHistoricalVillages() {
			return plannFamousHistoricalVillages;
		}
		public void setPlannFamousHistoricalVillages(
				Boolean plannFamousHistoricalVillages) {
			this.plannFamousHistoricalVillages = plannFamousHistoricalVillages;
		}
		public Boolean isPreparationVillagePlanOther() {
			return preparationVillagePlanOther;
		}
		public void setPreparationVillagePlanOther(Boolean preparationVillagePlanOther) {
			this.preparationVillagePlanOther = preparationVillagePlanOther;
		}
		public Boolean isClearFourOld() {
			return clearFourOld;
		}
		public void setClearFourOld(Boolean clearFourOld) {
			this.clearFourOld = clearFourOld;
		}
		public Boolean isClearFiveChaos() {
			return clearFiveChaos;
		}
		public void setClearFiveChaos(Boolean clearFiveChaos) {
			this.clearFiveChaos = clearFiveChaos;
		}
		public Boolean isClearSixSides() {
			return clearSixSides;
		}
		public void setClearSixSides(Boolean clearSixSides) {
			this.clearSixSides = clearSixSides;
		}
		public Integer getClearFourOldSize() {
			return clearFourOldSize;
		}
		public void setClearFourOldSize(Integer clearFourOldSize) {
			this.clearFourOldSize = clearFourOldSize;
		}
		public Double getAccumulatedCleanUpGarbage() {
			return accumulatedCleanUpGarbage;
		}
		public void setAccumulatedCleanUpGarbage(Double accumulatedCleanUpGarbage) {
			this.accumulatedCleanUpGarbage = accumulatedCleanUpGarbage;
		}
		public Double getGreenArea() {
			return greenArea;
		}
		public void setGreenArea(Double greenArea) {
			this.greenArea = greenArea;
		}
		public Double getPlantTreesAmount() {
			return plantTreesAmount;
		}
		public void setPlantTreesAmount(Double plantTreesAmount) {
			this.plantTreesAmount = plantTreesAmount;
		}
		public Boolean isGarbageStation() {
			return GarbageStation;
		}
		public void setGarbageStation(Boolean garbageStation) {
			GarbageStation = garbageStation;
		}
		public Boolean isThreeGuaranTees() {
			return ThreeGuaranTees;
		}
		public void setThreeGuaranTees(Boolean threeGuaranTees) {
			ThreeGuaranTees = threeGuaranTees;
		}
		public Integer getCleanTeam() {
			return cleanTeam;
		}
		public void setCleanTeam(Integer cleanTeam) {
			this.cleanTeam = cleanTeam;
		}
		public Boolean isChangeWC() {
			return changeWC;
		}
		public void setChangeWC(Boolean changeWC) {
			this.changeWC = changeWC;
		}
		public Boolean isAnimalCaptive() {
			return animalCaptive;
		}
		public void setAnimalCaptive(Boolean animalCaptive) {
			this.animalCaptive = animalCaptive;
		}
		public Boolean isRainAndSewageDiversion() {
			return rainAndSewageDiversion;
		}
		public void setRainAndSewageDiversion(Boolean rainAndSewageDiversion) {
			this.rainAndSewageDiversion = rainAndSewageDiversion;
		}
		public Boolean isBadWater() {
			return badWater;
		}
		public void setBadWater(Boolean badWater) {
			this.badWater = badWater;
		}
		public Boolean isAnaerobicTank() {
			return AnaerobicTank;
		}
		public void setAnaerobicTank(Boolean anaerobicTank) {
			AnaerobicTank = anaerobicTank;
		}
		public Boolean isArtificialWetland() {
			return ArtificialWetland;
		}
		public void setArtificialWetland(Boolean artificialWetland) {
			ArtificialWetland = artificialWetland;
		}
		public Boolean isBiogasDigesters() {
			return BiogasDigesters;
		}
		public void setBiogasDigesters(Boolean biogasDigesters) {
			BiogasDigesters = biogasDigesters;
		}
		public Boolean isSewageTreatmentFacilitiesOther() {
			return SewageTreatmentFacilitiesOther;
		}
		public void setSewageTreatmentFacilitiesOther(
				Boolean sewageTreatmentFacilitiesOther) {
			SewageTreatmentFacilitiesOther = sewageTreatmentFacilitiesOther;
		}
		public Boolean isTapWater() {
			return tapWater;
		}
		public void setTapWater(Boolean tapWater) {
			this.tapWater = tapWater;
		}
		public Boolean isHardBottom() {
			return hardBottom;
		}
		public void setHardBottom(Boolean hardBottom) {
			this.hardBottom = hardBottom;
		}
		public Boolean isParkOrSquare() {
			return ParkOrSquare;
		}
		public void setParkOrSquare(Boolean parkOrSquare) {
			ParkOrSquare = parkOrSquare;
		}
}
