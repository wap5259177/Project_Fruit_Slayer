package cn.bonoon.controllers.survey;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.kernel.VisibleScope;
import cn.bonoon.kernel.annotations.QueryExpression;
import cn.bonoon.kernel.menus.ItemInfo;
import cn.bonoon.kernel.web.ButtonEventType;
import cn.bonoon.kernel.web.ButtonRefreshType;
import cn.bonoon.kernel.web.annotations.grid.GridStandardDefinition;
import cn.bonoon.kernel.web.controllers.AbstractGridController;
import cn.bonoon.kernel.web.models.DialogModel;
import cn.bonoon.kernel.web.models.JsonResult;

@Controller
@RequestMapping("s/ml/ss/report")
public class CitySurverReportController extends
		AbstractGridController<SurveySummaryCityEntity, CitySurveyItem> {
	/**
	 * 实现了判断本年度某个市新农村建设摸底调查汇总表记录和上一年度的比较
	 * 
	 * @param surveySummaryCityService
	 *            该参数是一个摸底排查服务器对象这里用来获取某个年度的市的表，所有记录
	 * @param nowYearID
	 *            用来获取本年度某个市的表以便和上一年度的表比较
	 * @param countyMSG
	 *            这个参数不为空时表示前端填报县调查表的填表保存按钮时调用该参数是表示某个市表的一个记录市(区，县)必须和上一年度的该市的市(
	 *            区，县)指标比较，null时表示本方法是在前端执行提交并完成填报时调用，提交并完成填报时要比较本年度当前登录的市的表和上年度
	 *            比较
	 * @return
	 * @throws Exception
	 */
	public static String compareLastYearAndThisYear(
			SurveySummaryCityService surveySummaryCityService, 
			long nowYearID,
			SurveySummaryCountyEntity countyMSG) throws Exception {

		StringBuffer sb = new StringBuffer();

		List<SurveySummaryCountyEntity> sscs = surveySummaryCityService.countyReports(nowYearID);

		if (countyMSG == null) {
			boolean b = false;
//			log.info("执行了提交并完成填报按钮");

			if (!sscs.isEmpty()) {
				SurveySummaryCountyEntity ssc_0 = sscs.get(0);
				SurveySummaryCityEntity ssCity = ssc_0.getCity();
				int lastAnnual = ssCity.getProvince().getAnnual() - 1;
				
				List<SurveySummaryCountyEntity> sscs2 = surveySummaryCityService.getLastYearSurveySummaryCityEntityList(ssCity.getCityName(), lastAnnual);
//				System.out.println("上一年度的记录数：" + sscs2.size());
				if (!sscs2.isEmpty()) {

					for (SurveySummaryCountyEntity ssc : sscs) {
						for (SurveySummaryCountyEntity ssc2 : sscs2) {
							if (ssc.getCounty().getFullName()
									.equals(ssc2.getCounty().getFullName())) {
								if (ssc.getCleaningTeam() < ssc2
										.getCleaningTeam()
										|| ssc.getHardBottom() < ssc2
												.getHardBottom()
										|| ssc.getLivestockConcentratedCaptive() < ssc2
												.getLivestockConcentratedCaptive()

										|| ssc.getRainSewageDiversion() < ssc2
												.getRainSewageDiversion()
										|| ssc.getSewageTreatment() < ssc2
												.getSewageTreatment()
										|| ssc.getSpcvgb() < ssc2.getSpcvgb()
										|| ssc.getTapWater() < ssc2
												.getTapWater()
										|| ssc.getUnifiedStyle() < ssc2
												.getUnifiedStyle()
										|| ssc.getVillagePlanning() < ssc2
												.getVillagePlanning()
										|| ssc.getVillagerCouncil() < ssc2
												.getVillagerCouncil()
										|| ssc.getVillageRenovation() < ssc2
												.getVillageRenovation()) {
									if (!b) {
										sb.append("20户以上自然村(个)之后的信息指标填写不能小于上一年度的信息指标!!<br>"
												+ "本年度"
												+ ssc.getCity().getProvince()
														.getAnnual()
												+ "  填写错误的市(区,县)如下:<br>");
										b = true;
									}
									sb.append(ssc.getCounty().getFullName() + "<br>");

								}

							}
						}
					}
				} else {
					// 上一年度没有的话可能是年度最小的一年或者是上一年度不是-1
					List<Integer> yearlist = surveySummaryCityService
							.getAllAnnual(sscs.get(0).getCity().getCityName());
					System.out.println("总年度数量:" + yearlist.size());
					for (int i = 0; i < yearlist.size(); i++) {
						System.out.println("第" + i + "年:" + yearlist.get(i));
					}
					long nowAnnual = sscs.get(0).getCity().getProvince()
							.getAnnual();
					// 如果是最小的年度那就是刚创建就没得比上一年度的了 直接给填写
					if (yearlist.get(0) == nowAnnual) {
						System.out.println("检测到本年度是最小年度:" + nowAnnual);
						return null;
					} else {
						for (int i = 0; i < yearlist.size(); i++) {
							if ((long) sscs.get(0).getCity().getProvince()
									.getAnnual() == yearlist.get(i)) {
								System.out
										.println("检测到本年度不是最小年度,上一年度不是今年度-1,现在查询到上年度是:"
												+ yearlist.get(i - 1));
								List<SurveySummaryCountyEntity> sscs3 = surveySummaryCityService
										.getLastYearSurveySummaryCityEntityList(
												sscs.get(0).getCity()
														.getCityName(),
												yearlist.get(i - 1));

								System.out.println("上一年度不是今年度-1时记录的大小："
										+ sscs3.size() + "上一年度的该城市某个市区县的自然村数:"
										+ sscs3.get(0).getCounty().getName()
										+ sscs3.get(0).getNaturalVillage());
								for (SurveySummaryCountyEntity ssc : sscs) {
									for (SurveySummaryCountyEntity ssc2 : sscs3) {
										if (ssc.getCounty()
												.getFullName()
												.equals(ssc2.getCounty()
														.getFullName())) {

											if (ssc.getCleaningTeam() < ssc2
													.getCleaningTeam()
													|| ssc.getHardBottom() < ssc2
															.getHardBottom()
													|| ssc.getLivestockConcentratedCaptive() < ssc2
															.getLivestockConcentratedCaptive()

													|| ssc.getRainSewageDiversion() < ssc2
															.getRainSewageDiversion()
													|| ssc.getSewageTreatment() < ssc2
															.getSewageTreatment()
													|| ssc.getSpcvgb() < ssc2
															.getSpcvgb()
													|| ssc.getTapWater() < ssc2
															.getTapWater()
													|| ssc.getUnifiedStyle() < ssc2
															.getUnifiedStyle()
													|| ssc.getVillagePlanning() < ssc2
															.getVillagePlanning()
													|| ssc.getVillagerCouncil() < ssc2
															.getVillagerCouncil()
													|| ssc.getVillageRenovation() < ssc2
															.getVillageRenovation()) {
												if (!b) {
													sb.append("20户以上自然村(个)之后的信息指标填写不能小于上一年度"
															+ ssc2.getCity()
																	.getProvince()
																	.getAnnual()
															+ "年度的信息指标!!!<br>"
															+ "本年度    "
															+ ssc.getCity()
																	.getProvince()
																	.getAnnual()
															+ "填写错误的市(区,县)如下:<br>");
													b = true;
												}
												sb.append(ssc.getCounty()
														.getFullName() + "<br>");

											}
										}
									}
								}

							}

						}

					}
				}
			} else {
				throw new Exception("本年度的记录不存在!");
			}
			if (sb.toString().length() > 0) {
				return sb.toString();
			} else {
				return null;
			}

		} else {
			System.out.println("执行了填写某个市（区，县）按钮");
			List<SurveySummaryCountyEntity> sscs2 = surveySummaryCityService
					.getLastYearSurveySummaryCityEntityList(sscs.get(0)
							.getCity().getCityName(), sscs.get(0).getCity()
							.getProvince().getAnnual() - 1);
			if (sscs2.size() > 0) {
				for (SurveySummaryCountyEntity ssc2 : sscs2) {
					if (countyMSG.getCounty().getFullName()
							.equals(ssc2.getCounty().getFullName())) {
						if (countyMSG.getCleaningTeam() < ssc2
								.getCleaningTeam()
								|| countyMSG.getHardBottom() < ssc2
										.getHardBottom()
								|| countyMSG.getLivestockConcentratedCaptive() < ssc2
										.getLivestockConcentratedCaptive()

								|| countyMSG.getRainSewageDiversion() < ssc2
										.getRainSewageDiversion()
								|| countyMSG.getSewageTreatment() < ssc2
										.getSewageTreatment()
								|| countyMSG.getSpcvgb() < ssc2.getSpcvgb()
								|| countyMSG.getTapWater() < ssc2.getTapWater()
								|| countyMSG.getUnifiedStyle() < ssc2
										.getUnifiedStyle()
								|| countyMSG.getVillagePlanning() < ssc2
										.getVillagePlanning()
								|| countyMSG.getVillagerCouncil() < ssc2
										.getVillagerCouncil()
								|| countyMSG.getVillageRenovation() < ssc2
										.getVillageRenovation()) {
							sb.append("本年度:"
									+ ssc2.getCity().getProvince().getAnnual()
									+ countyMSG.getCounty().getFullName()
									+ "20户以上自然村之后的信息填写不能小于上一年度的信息指标!\n错误指标如下:\n");

							if (countyMSG.getVillagePlanning() < ssc2
									.getVillagePlanning()) {
								sb.append("已完成村庄规划的自然村(条)\n");
							}
							if (countyMSG.getUnifiedStyle() < ssc2
									.getUnifiedStyle()) {
								sb.append("外立面统一装饰风格风貌的自然村(条)\n");
							}
							if (countyMSG.getHardBottom() < ssc2
									.getHardBottom()) {
								sb.append("已实现村巷道硬底化的自然村(条)\n");
							}
							if (countyMSG.getTapWater() < ssc2.getTapWater()) {
								sb.append("已实现村村通自来水的自然村（条）\n");
							}
							if (countyMSG.getSpcvgb() < ssc2.getSpcvgb()) {
								sb.append("建有小公园、文化活动场所或绿化带的自然村（条）\n");
							}
							if (countyMSG.getVillageRenovation() < ssc2
									.getVillageRenovation()) {
								sb.append("已完成村容村貌整治的自然村（条）\n");
							}

							if (countyMSG.getCleaningTeam() < ssc2
									.getCleaningTeam()) {
								sb.append("建有卫生保洁队伍的自然村(条)\n");
							}
							if (countyMSG.getRainSewageDiversion() < ssc2
									.getRainSewageDiversion()) {
								sb.append("已实行雨污分流的自然村 \n");
							}
							if (countyMSG.getSewageTreatment() < ssc2
									.getSewageTreatment()) {
								sb.append("建有人工湿地、厌氧池、沼气池等处理生活污水的自然村（条）\n");
							}
							if (countyMSG.getLivestockConcentratedCaptive() < ssc2
									.getLivestockConcentratedCaptive()) {
								sb.append("实行畜禽集中圈养、人畜分离的自然村（条）\n");
							}

							if (countyMSG.getVillagerCouncil() < ssc2
									.getVillagerCouncil()) {
								sb.append("健全村规民约、章程及村民理事会的自然村（条）\n");
							}

							return sb.toString();

						} else {
							return null;
						}
					}

				}

			} else {
				System.out.println("查询某个市（区，县）记录-1上一年度没有记录");
				// 上一年度没有的话可能是年度最小的一年或者是上一年度不是-1
				List<Integer> yearlist = surveySummaryCityService
						.getAllAnnual(sscs.get(0).getCity().getCityName());
				System.out.println("年度有:" + yearlist.size());
				for (int i = 0; i < yearlist.size(); i++) {
					System.out.println("第" + i + "年:" + yearlist.get(i));
				}
				// 如果是最小的年度那就是刚创建就没得比上一年度的了 直接给填写
				if (yearlist.get(0) == sscs.get(0).getCity().getProvince()
						.getAnnual()) {
					System.out.println("本年度"
							+ sscs.get(0).getCity().getProvince().getAnnual()
							+ "是最小年度，没有上一年度可判断");
					return null;
				} else {
					for (int i = 0; i < yearlist.size(); i++) {
						if (sscs.get(0).getCity().getProvince().getAnnual() == yearlist
								.get(i)) {
							System.out.println("检测到本年度不是最小年度,现在查询到上年度是:"
									+ yearlist.get(i - 1));
							long getLastAnnual = yearlist.get(i - 1);
							int getLastAnnual2 = (int) getLastAnnual;
							System.out.println("getLastAnnual2:"
									+ getLastAnnual2);
							List<SurveySummaryCountyEntity> sscs3 = surveySummaryCityService
									.getLastYearSurveySummaryCityEntityList(
											sscs.get(0).getCity().getCityName(),
											getLastAnnual2);

							System.out.println("最近的上年度的记录数是：" + sscs3.size());
							for (SurveySummaryCountyEntity ssc2 : sscs3) {
								if (countyMSG.getCounty().getFullName()
										.equals(ssc2.getCounty().getFullName())) {
									if (countyMSG.getCleaningTeam() < ssc2
											.getCleaningTeam()
											|| countyMSG.getHardBottom() < ssc2
													.getHardBottom()
											|| countyMSG
													.getLivestockConcentratedCaptive() < ssc2
													.getLivestockConcentratedCaptive()

											|| countyMSG
													.getRainSewageDiversion() < ssc2
													.getRainSewageDiversion()
											|| countyMSG.getSewageTreatment() < ssc2
													.getSewageTreatment()
											|| countyMSG.getSpcvgb() < ssc2
													.getSpcvgb()
											|| countyMSG.getTapWater() < ssc2
													.getTapWater()
											|| countyMSG.getUnifiedStyle() < ssc2
													.getUnifiedStyle()
											|| countyMSG.getVillagePlanning() < ssc2
													.getVillagePlanning()
											|| countyMSG.getVillagerCouncil() < ssc2
													.getVillagerCouncil()
											|| countyMSG.getVillageRenovation() < ssc2
													.getVillageRenovation()) {
										sb.append("本年度:"
												+ ssc2.getCity().getProvince()
														.getAnnual()
												+ ""
												+ countyMSG.getCounty()
														.getFullName()
												+ "20户以上自然村之后的信息填写不能小于上一年度的信息指标!\n填写错误的指标有:\n");

										if (countyMSG.getVillagePlanning() < ssc2
												.getVillagePlanning()) {
											sb.append("已完成村庄规划的自然村（条）\n");
										}
										if (countyMSG.getUnifiedStyle() < ssc2
												.getUnifiedStyle()) {
											sb.append("外立面统一装饰风格风貌的自然村（条）\n");
										}
										if (countyMSG.getHardBottom() < ssc2
												.getHardBottom()) {
											sb.append("已实现村巷道硬底化的自然村(条)\n");
										}
										if (countyMSG.getTapWater() < ssc2
												.getTapWater()) {
											sb.append("已实现村村通自来水的自然村（条）\n");
										}
										if (countyMSG.getSpcvgb() < ssc2
												.getSpcvgb()) {
											sb.append("建有小公园、文化活动场所或绿化带的自然村（条）\n");
										}
										if (countyMSG.getVillageRenovation() < ssc2
												.getVillageRenovation()) {
											sb.append("已完成村容村貌整治的自然村（条）\n");
										}
										
										if (countyMSG.getCleaningTeam() < ssc2
												.getCleaningTeam()) {
											sb.append("建有卫生保洁队伍的自然村(条)\n");
										}
										if (countyMSG.getRainSewageDiversion() < ssc2
												.getRainSewageDiversion()) {
											sb.append("已实行雨污分流的自然村\n");
										}
										if (countyMSG.getSewageTreatment() < ssc2
												.getSewageTreatment()) {
											sb.append("建有人工湿地、厌氧池、沼气池等处理生活污水的自然村（条）\n");
										}
										if (countyMSG
												.getLivestockConcentratedCaptive() < ssc2
												.getLivestockConcentratedCaptive()) {
											sb.append("实行畜禽集中圈养、人畜分离的自然村（条）\n");
										}

										if (countyMSG.getVillagerCouncil() < ssc2
												.getVillagerCouncil()) {
											sb.append("健全村规民约、章程及村民理事会的自然村（条）\n");
										}

										return sb.toString();

									} else {
										return null;
									}
								}
							}
						}

					}

				}

			}
			return null;
		}

	}

	private SurveySummaryCityService surveySummaryCityService;

	@Autowired
	public CitySurverReportController(
			SurveySummaryCityService surveySummaryCityService) {
		super(surveySummaryCityService);
		this.surveySummaryCityService = surveySummaryCityService;
	}

	@Override
	@GridStandardDefinition(deleteOperation = false,// 去除删除按钮
	autoOperation = false)
	// @QueryExpression("x.unit.id={USER owner} and x.province.startAt<={USER now} and x.province.endAt>={USER now} and x.status<3")
	@QueryExpression("x.unit.id={USER owner}  ")
	protected SurveySummaryCityService initLayoutGrid(
			LayoutGridRegister register) throws Exception {
		register.button("开始填报", "index.start", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(0);
		register.button("填报县调查表", "index.report?v=true", ButtonEventType.DIALOG)
				.status(2);
		register.button("提交并完成填报", "index.finish", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(2);
		register.button("刷新", "index.refresh", ButtonEventType.GET,
				ButtonRefreshType.FINISH).status(2);

		register.button("查看", "index.report", ButtonEventType.DIALOG).status(1);

		register.button("打印", "index.print", ButtonEventType.JUMP).status(1, 2)
				.ordinal(50);

		return surveySummaryCityService;
	}

	@Override
	protected VisibleScope getScope(ItemInfo item) {
		// 1
		return VisibleScope.GLOBAL;
	}

	// 打印20160413
	@RequestMapping("!{key}/index.print")
	public String print(HttpServletRequest request, Long id, String gridid,
			Model model) {
		SurveySummaryCityEntity sscity = surveySummaryCityService.get(id);
		model.addAttribute("deadline",
				sdf.format(sscity.getProvince().getDeadline()));
		model.addAttribute(
				"title",
				"广东省<div style='font-size:20px;display:inline-block;width:100px;height:22px;line-height:22px;border-bottom: solid 1px #000000;'>"
						+ sscity.getCityName() + "</div>新农村建设摸底调查汇总表");
		List<SurveySummaryCountyEntity> sscs = surveySummaryCityService
				.countyReports(id);
		for (SurveySummaryCountyEntity ss : sscs) {
			sscity.sum(ss);
		}
		model.addAttribute("sscs", sscs);
		model.addAttribute("sscity", sscity);
		return "survey/summary-city-view";
	}

	// 20160330
	@ResponseBody
	@RequestMapping(value = "!{key}/index.refresh", method = GET)
	public JsonResult refresh(Long id) {
		try {
			surveySummaryCityService.refresh(getUser(), id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.start", method = GET)
	public JsonResult toReport(Long id) {
		try {
			surveySummaryCityService.toReport(getUser(), id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	@ResponseBody
	@RequestMapping(value = "!{key}/index.finish", method = GET)
	public JsonResult toFinish(Long id) {
		try {

			String result = compareLastYearAndThisYear(
					surveySummaryCityService, id, null);

			if (result != null)
				throw new Exception(result);
			surveySummaryCityService.toFinish(id);
			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

	@RequestMapping("!{key}/index.report")
	public ModelAndView report(HttpServletRequest request, Long id,
			String gridid, String v) {
		DialogModel model = new DialogModel("c_s_" + id, request);
		model.addForm(id);
		SurveySummaryCityEntity ssc = surveySummaryCityService.get(id);
		model.addObject("title", "广东省" + ssc.getCityName() + "新农村建设摸底调查汇总表");
		model.addObject("deadline", sdf.format(ssc.getProvince().getDeadline()));
		model.addObject("gridid", gridid);
		if ("true".equalsIgnoreCase(v)) {
			model.addObject("reportable", true);
		} else {
			model.addObject("reportable", false);
		}
		return model.execute("survey/report");
	}

	@ResponseBody
	@RequestMapping("!{key}/{id}!data.report")
	public List<SurveyItem> items(@PathVariable("id") Long id, String v) {
		// sscs表示摸底排查汇总表的每一个记录的集合
		List<SurveySummaryCountyEntity> sscs = surveySummaryCityService
				.countyReports(id);

//		String lookOrSubmit = "";
		saveSurveySummaryCountyID = id;
//		if (!"true".equalsIgnoreCase(v)) {
//			lookOrSubmit = "查看";
//		} else {
//			lookOrSubmit = "填报县调查表";
//		}
//		System.out.println("saveSurveySummaryCountyID在点击" + lookOrSubmit
//				+ "时设置为:" + saveSurveySummaryCountyID);
//		System.out
//				.println("--------------------------------------------------: "
//						+ id);

		List<SurveyItem> sis = new ArrayList<>();
		// v="true"表示状态:完成
		if (!"true".equalsIgnoreCase(v)) {

			// 获取该市每个字段的统计
			SurveySummaryCityEntity ssc = surveySummaryCityService.get(id);
			sis.add(new SurveyItem(ssc));

		}

		for (SurveySummaryCountyEntity ssc : sscs) {

			sis.add(new SurveyItem(ssc));

		}

		return sis;
	}

	@RequestMapping("!{key}/county.report")
	public ModelAndView county(HttpServletRequest request, Long id) {
		DialogModel model = new DialogModel("c_d_" + id, request);
		SurveySummaryCountyEntity ssc = surveySummaryCityService.county(id);
		model.addForm(ssc);
		model.addObject("title", "县调查表");
		return model.execute("survey/report-county");
	}

	private long saveSurveySummaryCountyID;

	String[] itemsName = {"已完成村庄规划的自然村", "外立面统一装饰风格风貌的自然村", "已实现村巷道硬底化的自然村", "已实现村村通自来水的自然村", "建有小公园、文化活动场所或绿化带的自然村", "已完成村容村貌整治的自然村", "建有卫生保洁队伍的自然村", "已实行雨污分流的自然村", "建有人工湿地、厌氧池、沼气池等处理生活污水的自然村", "实行畜禽集中圈养、人畜分离的自然村", "健全村规民约、章程及村民理事会的自然村"};

	@ResponseBody
	@RequestMapping(value = "!{key}/county.submit", method = GET)
	public JsonResult submit(Long id, 
			int agriculturalHousehold,    //第1项
			int agriculturalPopulation, 
			int villageCommittee,
			int naturalVillage,           //这里是大于20户以上的自然村，下面所有的完成的自然村，都不能大于这个值
			int villagePlanning,
			int unifiedStyle,
			int hardBottom, 
			int tapWater, 
			int spcvgb, 
			int villageRenovation,
			int cleaningTeam, 
			int rainSewageDiversion, 
			int sewageTreatment,
			int livestockConcentratedCaptive, 
			int villagerCouncil) {
		try {
			if (agriculturalHousehold > agriculturalPopulation) {
				throw new Exception("农业不能大于农业人口数" + saveSurveySummaryCountyID);
			}

			SurveySummaryCountyEntity ssc = surveySummaryCityService.county(id);

			if (null == ssc) throw new Exception("县数据不存在，请刷新后再试！");
			
			/*
			 * 这里需要判断所填写的完成各项指标的自然村数必须小于第4项中指定的自然村数
			 * 
			 */
			
			int[] needCheck = {
					villagePlanning,
					unifiedStyle,
					hardBottom, 
					tapWater, 
					spcvgb, 
					villageRenovation,
					cleaningTeam, 
					rainSewageDiversion, 
					sewageTreatment,
					livestockConcentratedCaptive, 
					villagerCouncil
			};
			
			StringBuilder errorMsg = new StringBuilder();
			int j = 1;
			for(int i = 0; i < needCheck.length; i++){
				if(needCheck[i] > naturalVillage){
					errorMsg.append(j++).append(". ");
					errorMsg.append(itemsName[i]).append("(").append(needCheck[i]).append("个) 大于 20户以上自然村数(").append(naturalVillage).append("个);\n");
				}
			}
			
			if(errorMsg.length() > 0){
				errorMsg.append("\n请检查以上异常项！");
				throw new Exception(errorMsg.toString());
			}

//			System.out.println("填报保存时    saveSurveySummaryCountyID:"
//					+ saveSurveySummaryCountyID);

			ssc.setAgriculturalHousehold(agriculturalHousehold);
			ssc.setAgriculturalPopulation(agriculturalPopulation);
			ssc.setCleaningTeam(cleaningTeam);
			ssc.setHardBottom(hardBottom);
			ssc.setLivestockConcentratedCaptive(livestockConcentratedCaptive);

			ssc.setNaturalVillage(naturalVillage);
			ssc.setRainSewageDiversion(rainSewageDiversion);
			ssc.setSewageTreatment(sewageTreatment);
			ssc.setSpcvgb(spcvgb);
			ssc.setVillageCommittee(villageCommittee);

			ssc.setVillagePlanning(villagePlanning);
			ssc.setVillagerCouncil(villagerCouncil);
			ssc.setVillageRenovation(villageRenovation);
			ssc.setTapWater(tapWater);
			ssc.setUnifiedStyle(unifiedStyle);

			String error = compareLastYearAndThisYear(surveySummaryCityService, saveSurveySummaryCountyID, ssc);
			if (error != null) throw new Exception(error);
			surveySummaryCityService.save(ssc);

			return JsonResult.result();
		} catch (Exception ex) {
			return JsonResult.error(ex);
		}
	}
}
