package cn.bonoon.util;


import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class MonthAndQuarterUtil {
/**
 * 
 * @param nowAnnual
 * @param nowQuarter
 * @return 返回上个季度的年,月
 */
	public static int[] getLastQuarter(int nowAnnual,int nowQuarter){
		if(nowQuarter>3||nowQuarter<0||nowAnnual<0)
			return null;
	
		if(nowQuarter-1==-1){
			if(nowAnnual-1<0){
				return null;
			}
			return  new int[]{nowAnnual-1,3};
		}
		
		return new int[]{nowAnnual,nowQuarter-1};
		
	}
	
	/**
	 * 
	 * @return 一年中的所有个季度    key哪个季度(0,3) value该季度包含的月份(0,11) 
	 */
	public static Map<Integer,int[]> setMonthByQuarter(){
		Map<Integer,int[]> map=new TreeMap<>();
		int monthList[]=new int[12];
		for(int i=0;i<12;i++){
			monthList[i]=i;
		}
		int monthIndex=0;
		for(int i=0;i<4;i++){
			int[] monthByPeriod=new int[3];
			for(int i2=0;i2<monthByPeriod.length;i2++){
				monthByPeriod[i2]=monthList[monthIndex];
				 monthIndex++;
			}
			map.put(new Integer(i), monthByPeriod);
		}
		return map;
	}
	/**
	 * 拿到某个季度的最大月份
	 * @param jidu
	 * @return 
	 */
	public static int getQuarterMaxMonth(int jidu){
		Map<Integer,int[]> map =setMonthByQuarter();
		return map.get(jidu)[map.get(jidu).length-1];
	}
	/**
	 * 
	 * @param period 某个月份
	 * @return 返回key 为季度值 value 为该季度包含的月份
	 */
	public static Entry<Integer, int[]> getQuarter(int period){
		Map<Integer,int[]> map=setMonthByQuarter();
		for(Entry<Integer, int[]> set :map.entrySet()){
			System.out.println(set.getKey());
			System.out.println("value:");
			for(int i:set.getValue()){
				System.out.println(i);
				if(i==period){
				return set;
				}
			}
		}
		return null;
	}
	
	/**
	 * 提交了某个月的项目月度报表后
	 * 对该月对应所在季度报表以及该季度报表往后的季度报表
	 * 进行重新统计资金 (加上到该季度之前所有提交的项目月报里的主体村项目月报资金)
	 * @param period 某月
	 * @param annual 某年
	 * @param ownerId 
	 * @param mid 片区id
	 */
//	private void flashQuarterReport(ProjectReportEntity projectReport){
//	
//		Entry<Integer, int[]> set=	getQuarter(projectReport.getPeriod());
//		if(set!=null){
//			String ql = "select x from ModelAreaQuarterItem x where x.modelArea.ownerId=? and x.batch.quarter.annual*100+x.batch.quarter.period>=? order by x.batch.quarter.annual asc,x.batch.quarter.period asc";
//			List<ModelAreaQuarterItem> maqiList= __list(ModelAreaQuarterItem.class, ql, projectReport.getModelArea().getOwnerId(),projectReport.getAnnual()*100+set.getKey());
//			if(maqiList!=null){
//				for(ModelAreaQuarterItem maqi: maqiList ){
//					maqi.getInvestment().clear();
//					int maxMonthByQuarter=getQuarterMaxMonth(maqi.getBatch().getQuarter().getPeriod());
//					ql="select x from ProjectReportItem x where x.report.modelArea.id=? and x.report.status=1 and x.report.annual*100+x.report.period<=?";
//					List<ProjectReportItem> projectReportItemList=	__list(ProjectReportItem.class, ql,projectReport.getModelArea().getId(),maqi.getBatch().getQuarter().getAnnual()*100+maxMonthByQuarter);
//				
//					for(ProjectReportItem proRI: projectReportItemList){
//						if(proRI.getProject().getRural() instanceof NewRuralEntity){
//							NewRuralEntity newRural=(NewRuralEntity)proRI.getProject().getRural();
//							if(newRural.getAdminRural()!=null){
//								maqi.getInvestment().sum(proRI.getInvestment());
//								System.out.println("季报的资金加上了自然村为:"+newRural.getNaturalVillage());
//							}else{
//								System.out.println(newRural.getName()+"行政村为空null");
//							}
//						}else{
//							
//							System.out.println("非主体村的项目"+proRI.getProject().getRural().getNaturalVillage()+proRI.getProject());
//						}
//					}
//					entityManager.merge(maqi);
//				}
//			}
//		}
//	}
	

}
