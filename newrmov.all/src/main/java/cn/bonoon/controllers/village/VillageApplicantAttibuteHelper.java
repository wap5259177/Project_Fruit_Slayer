//package cn.bonoon.controllers.village;
//
//import java.text.DecimalFormat;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.springframework.web.servlet.ModelAndView;
//
//import cn.bonoon.entities.VillageEvaluatePointEntity;
//
//public class VillageApplicantAttibuteHelper {
//	private static final Map<String, Integer> codes;
//	static{
//		codes = new HashMap<>();
//		codes.put("A1", 3);
//		codes.put("A2", 3);
//		codes.put("A3", 3);
//		codes.put("A4", 3);
//		codes.put("A5", 3);
//		codes.put("A6", 3);
//		codes.put("A7", 3);
//		codes.put("A8", 3);
//		codes.put("A9", 3);
//		codes.put("A10", 3);
//		codes.put("A11", 3);
//		codes.put("A12", 3);
//		codes.put("A13", 3);
//		codes.put("A14", 3);
//		codes.put("A15", 3);
//		codes.put("A16", 3);
//		codes.put("A17", 3);
//		codes.put("A18", 3);
//		codes.put("A19", 3);
//		codes.put("B1", 3);
//		codes.put("B2", 3);
//		codes.put("B3", 3);
//		codes.put("C1", 3);
//		codes.put("C2", 3);
//		codes.put("C3", 3);
//		codes.put("C4", 3);
//		codes.put("C5", 3);
//		codes.put("D", 100);
//	}
//	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.##");
//	public static void view(ModelAndView model, List<VillageEvaluatePointEntity> eps){
//		Map<String, Integer> cs = new HashMap<>(codes);
//		if(null != eps){
//			for(VillageEvaluatePointEntity tep : eps){
//				String code = tep.getCode();
//				Integer val = cs.remove(code);
//				if(null == val){ continue; }
//				String ipt = "<td style='text-align:center;' valign='middle'>"
//						+ DECIMAL_FORMAT.format(tep.getPointSelf())+"</td><td style='text-align:center;' valign='middle'>"
//						+ DECIMAL_FORMAT.format(tep.getPointAudit())+"</td>";
//				model.addObject(code, ipt);
//			}
//		}
//		for(Entry<String, Integer> it : cs.entrySet()){
//			String code = it.getKey();
//			String ipt = "<td></td><td></td>";
//			model.addObject(code, ipt);
//		}
//	}
//	public static void edit(ModelAndView model, List<VillageEvaluatePointEntity> eps){
//		Map<String, Integer> cs = new HashMap<>(codes);
//		if(null != eps){
//			for(VillageEvaluatePointEntity tep : eps){
//				String code = tep.getCode();
//				Integer val = cs.remove(code);
//				if(null == val){ continue; }
//				String ipt = "<td valign='middle'><input type='hidden' name='codes' value='"
//						+ code+"'/><input type='hidden' name='"
//						+ code+"-value' value='"
//						+ val+"'/><input type='text' name='"
//						+ code+"-self' value='"
//						+ DECIMAL_FORMAT.format(tep.getPointSelf())+"'/></td><td valign='middle'><input type='text' name='"
//						+ code+"-audit' value='"
//						+ DECIMAL_FORMAT.format(tep.getPointAudit())+"'/></td>";
//				model.addObject(code, ipt);
//			}
//		}
//		for(Entry<String, Integer> it : cs.entrySet()){
//			String code = it.getKey();
//			Integer val = it.getValue();
//			String ipt = "<td><input type='hidden' name='codes' value='"
//					+ code+"'/><input type='hidden' name='"
//					+ code+"-value' value='"
//					+ val+"'/><input type='text' name='"
//					+ code+"-self'/></td><td><input type='text' name='"
//					+ code+"-audit'/></td>";
//			model.addObject(code, ipt);
//		}
//	}
//}
