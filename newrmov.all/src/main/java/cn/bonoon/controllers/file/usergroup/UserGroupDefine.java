package cn.bonoon.controllers.file.usergroup;

import cn.bonoon.kernel.annotations.ResetProperties;
import cn.bonoon.kernel.annotations.ResetProperty;

@ResetProperties({
	@ResetProperty(value = "name", name = "用户组名称"),
	@ResetProperty(value = "createAt", name = "创建时间"),
	@ResetProperty(value = "remark", name = "备注")
})
public interface UserGroupDefine {

}
