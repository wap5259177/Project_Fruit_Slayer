package cn.bonoon.controllers.file;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import cn.bonoon.core.FileService;
import cn.bonoon.entities.FileEntity;
import cn.bonoon.kernel.util.StringHelper;
import cn.bonoon.kernel.web.models.DialogModel;

public class FileHelper {
	private FileHelper(){}
	public final static FileHelper helper = new FileHelper();
	
	public ModelAndView edit(HttpServletRequest request, FileService fileService, Long id, Long directory_id) {
		DialogModel model = new DialogModel(request);
		if (null != id && id > 0) { // update
			FileEntity entity = fileService.get(id);
			if (null != entity) {
				if(entity.getStatus() == -1){
					model.addObject("cantEdit", true);
				}else{
					model.addObject("id", id);
					model.addObject("name", entity.getName());
					model.addObject("extendedAttributes", entity.getExtendedAttributes());
					model.addObject("status", entity.getStatus());
					model.addObject("type", entity.getType());
					model.addObject("issueAt", entity.getIssueAt());
					model.addObject("remark", entity.getRemark());
					model.addObject("version", entity.getVersion());
					model.addObject("path", entity.getMapPath());
				}
			}
		} else { // insert
			model.addObject("directory_id", directory_id);
			model.addObject("version", "1");
		}
		return model.execute("file/file-editor");
	}
	
	public ModelAndView detail(HttpServletRequest request, FileService fileService, Long id){
		DialogModel model = new DialogModel(request);
		if (null != id && id > 0) {
			FileEntity entity = fileService.get(id);
			if (null != entity) {
				model.addObject("id", id);
				model.addObject("name", entity.getName());
				model.addObject("extendedAttributes", entity.getExtendedAttributes());
				int status = entity.getStatus();
				model.addObject("status", status == 1 ? "正常" : status == 0 ? "删除" : status == -1 ? "禁止修改" : "");
				model.addObject("shared", entity.isShared() ? "是" : "否");
				model.addObject("remark", entity.getRemark());
				model.addObject("version", entity.getVersion());
				if(entity.isExternalLink()){
					model.addObject("length", " - ");
				}else{
					model.addObject("length", entity.getLength() / 1024);
				}
				model.addObject("ext", entity.getExt());
				model.addObject("externalLink", entity.isExternalLink());
				model.addObject("mapPath", entity.getMapPath());
				model.addObject("type", entity.getType().getName());
				model.addObject("creatorName", entity.getCreatorName());
				model.addObject("updaterName", entity.getUpdaterName());
				model.addObject("issueAt", StringHelper.date2String(entity.getIssueAt()));
				model.addObject("createAt", StringHelper.datetime2String(entity.getCreateAt()));
				model.addObject("updateAt", StringHelper.datetime2String(entity.getUpdateAt()));
			}
		}
		return model.execute("file/file-detail");
	}

	private final static int K = 1024, M = K * K, G = M * K;
	
	public String len(long len){
		if(len < K){
			return len + " B";
		}else if(len < M){
			return len / K + " KB";
		}else if(len < G){
			return len / M + " MB";
		}
		return len / G + " GB";
	}
	public String icon(String ext){
		if(null != ext){
			String icon = "/res/images/suffixes/";
			if(ext.startsWith(".")){
				icon += ext.substring(1).toLowerCase();
			}else{
				icon += ext.toLowerCase();
			}
			icon += ".png";
			return icon;
		}
		return "/res/images/file.png";
	}
	public boolean action(long act, int i){
		return (act & i) != 0;
	}
}
