package cn.bonoon.controllers;

import java.util.List;

import cn.bonoon.entities.PictureEntity;
import cn.bonoon.kernel.web.models.DialogModel;

public class PictureHelper {
	
	public static void edit(Long id, DialogModel model, List<? extends PictureEntity<?>> pes, String burl){
		StringBuilder sbp = new StringBuilder("<ul>");
		if(null != pes && !pes.isEmpty()){
//			String burl = model.path("/s/tvmis/picture/village/");
			for(PictureEntity<?> pe : pes){
//				sbp.append("<li><a href='").append(model.path(pe.getPath()))
//				.append("' target='_blank'>").append(pe.getName())
//				.append("</a> - [<a href='").append(model.path(burl)).append(id).append('!').append(pe.getId())
//				.append("!delete.do' onclick='var tq=jQuery(this);jQuery.get(this.href, function(rd){if(__ajaxSuccess(rd)){tq.parent().remove();}});return false;'>删除图片</a>]</li>");
				
				sbp.append("<li>")
				.append(pe.getName())
				.append(" - [<a href='").append(model.path(burl)).append(id).append('!').append(pe.getId())
				.append("!delete.do' onclick='var tq=jQuery(this);jQuery.get(this.href, function(rd){if(__ajaxSuccess(rd)){tq.parent().remove();}});return false;'>删除图片</a>]</li>");
			}
		}
		sbp.append("</ul><input type='button' id='btn-add-picture' value='添加相片' /><p style='color:red;'>注意：提交审核前，请至少上传5张名镇名村建设成果的相片！</p>");
		
		model.addObject("pictures", sbp);
	}
	
	public static void view(DialogModel model, List<? extends PictureEntity<?>> pes) {
		if(null != pes && !pes.isEmpty()){
			StringBuilder sbp = new StringBuilder("<ul>");
			for(PictureEntity<?> pe : pes){
//				sbp.append("<li><a href='").append(model.path(pe.getPath())).append("' target='_blank'>").append(pe.getName()).append("</a></li>");
				sbp.append("<li>").append(pe.getName()).append("</li>");
			}
			model.addObject("pictures", sbp.append("</ul>"));
		}
	}
}
