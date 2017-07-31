package cn.bonoon.controllers;

import java.util.List;

import cn.bonoon.entities.AbstractProjectEntity;
import cn.bonoon.kernel.web.models.DialogModel;

public class ProjectHelper {
	public static void edit(DialogModel model, List<? extends AbstractProjectEntity<?>> pes){
		int projectCount = 1;
		if(null != pes && !pes.isEmpty()){
			StringBuilder sb = new StringBuilder();
			String removeImg = model.path("/r/images/remove.png");
			for(AbstractProjectEntity<?> pe : pes){
				sb.append("<tr><td>-</td><td><input type='text' name='projectName-")
				.append(projectCount).append("' value='")
				.append(pe.getName()).append("'/><input type='hidden' name='projects' value='")
				.append(projectCount).append("'/></td><td><input type='text' name='projectContent-")
				.append(projectCount).append("' value='")
				.append(pe.getContent()).append("'/></td><td class='dt-input-class'><input style='width:100px;' type='text' name='projectAt-")
				.append(projectCount).append("' value='")
				.append(pe.getExecuteTime()).append("'/></td><td><input type='text' class='num-input-class' name='projectBudget-")
				.append(projectCount).append("' value='")
				.append(pe.getBudget()).append("'/></td><td><input type='text' class='num-input-class' name='projectDone-")
				.append(projectCount).append("' value='")
				.append(pe.getDoneInvestment()).append("'/></td><td><input type='text' name='projectExecutor-")
				.append(projectCount).append("' value='")
				.append(pe.getExecutor()).append("'/></td><td><a href='#' onclick='jQuery(this).parent().parent().remove();return false;'><img src='")
				.append(removeImg).append("'/></a></td></tr>");
				projectCount++;
			}
			model.addObject("projectItems", sb);
		}
		model.addObject("projectCount", projectCount);
	}

	public static void view(DialogModel model, List<? extends AbstractProjectEntity<?>> pes) {
		int projectCount = 1;
		StringBuilder sb = new StringBuilder();
		if(null != pes && !pes.isEmpty()){
			for(AbstractProjectEntity<?> pe : pes){
				sb.append("<tr><td>")
					.append(projectCount++).append("</td><td>")
					.append(pe.getName()).append("</td><td>")
					.append(pe.getContent()).append("</td><td>")
					.append(pe.getExecuteTime()).append("</td><td>")
					.append(pe.getBudget()).append("</td><td>")
					.append(pe.getDoneInvestment()).append("</td><td>")
					.append(pe.getExecutor()).append("</td></tr>");
			}
		}
		for(; projectCount <= 5; projectCount++){
			sb.append("<tr><td>").append(projectCount).append("</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
		}
		model.addObject("projectItems", sb);
	}
}
