package cn.bonoon.controllers.file;

import cn.bonoon.kernel.annotations.OptionArray;
import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties(value = { 
		@ResetProperty(value = "name", name = "名称"),
		@ResetProperty(value = "remark", name = "备注"),
		@ResetProperty(value = "createAt", name = "创建时间"),
		@ResetProperty(value = "creatorName", name = "创建人"),
		@ResetProperty(value = "updateAt", name = "修改时间"),
		@ResetProperty(value = "updaterName", name = "修改人"),
		@ResetProperty(value = "length", name = "大小"),
		@ResetProperty(value = "status", name = "状态", options = @OptionArray(value = {"禁止修改", "删除", "正常"}, offset = -1)),
		@ResetProperty(value = "type", name = "类型", options = @OptionArray(value = {"文档", "图片", "音频", "视频", "其它"})),
		@ResetProperty(value = "ext", name = "扩展名"),
		@ResetProperty(value = "extendedAttributes", name = "扩展属性"),
		@ResetProperty(value = "version", name = "版本"),
		@ResetProperty(value = "level", name = "级别"),
		@ResetProperty(value = "shared", name = "分享", options = @OptionArray({ "否", "是" }))
	})

public interface FileDefine {

}
