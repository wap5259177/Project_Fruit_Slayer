package cn.bonoon.controllers.city;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.bonoon.core.ProjectService;
import cn.bonoon.kernel.events.PanelEvent;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.web.controllers.AbstractPanelController;

/**
 * 项目进度跟踪
 * @author Administrator
 *
 */
@Controller
@RequestMapping("s/ml/ppt")
public class ProjectProgressTrackController extends AbstractPanelController {

	@Autowired
	private ProjectService projectService;
	@Override
	protected void onLoad(PanelEvent event) throws Exception {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//当前年度
		int month = cal.get(Calendar.MONTH);//当前月度
		LogonUser user = getUser();
		Long owner = user.getOwnerId();
		//片区名、月份、总数、已提交数
		List<Object[]> items = projectService.getStatus(owner, year);
		StringBuilder html = new StringBuilder();
		Map<Object, Object[][]> map = new HashMap<>();
		for(Object[] it : items){
			Object[][] ent = map.get(it[0]);
			if(null == ent){
				ent = new Object[12][];
				map.put(it[0], ent);
			}
			int ord = ((Number)it[1]).intValue();
			ent[ord] = it;
		}
		for(Map.Entry<Object, Object[][]> ents : map.entrySet()){
			html.append("<tr><td>").append(ents.getKey()).append("</td>");
			Object[][] ent = ents.getValue();
			for(int monthOrdinal = 0; monthOrdinal < 12; monthOrdinal++){
				html.append("<td");
				if(month == monthOrdinal){
					html.append(" style='background-color:#99CCFF;'");
				}
				html.append(">");
				Object[] ml = ent[monthOrdinal];
				if(ml == null || ((Number)ml[2]).intValue() == 0 || ml[3] == null){
					html.append("未填报");
				}else if(((Number)ml[3]).intValue() > 0){
					html.append("已提交");
				}else{
					html.append("未提交");
				}
				html.append("</td>");
			}
			html.append("</tr>");
		}
		event.getModel().addObject("content", html).addObject("year", year);
		event.setVmPath("project/progress-track");
	}

}
