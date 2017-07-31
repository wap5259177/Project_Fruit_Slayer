package cn.bonoon.controllers.showstatistics;

import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import cn.bonoon.controllers.BatchHelper;
import cn.bonoon.core.plugins.LoginService;
import cn.bonoon.entities.plugins.AccountEntity;
import cn.bonoon.entities.plugins.FlagType;
import cn.bonoon.kernel.events.PanelEvent;

public class CountUtil {
	//
	//
	// public static Object batchSelect(BatchNameSortEntity
	// bnse,List<BatchNameSortEntity> bnseList){
	// StringBuilder html = new StringBuilder();
	// html.append("<div style='float:left'><select id='sel-batch'>");
	// for(BatchNameSortEntity bnseItem :bnseList){
	// html.append("<option value="+bnseItem.getId()+" ");
	// if(bnse.getId().equals(bnseItem.getId())){
	// html.append("selected='selected' ");
	// }
	// html.append(">"+bnseItem.getBatchName()+" </option>");
	//
	// }
	//
	// html.append("</select></div>");
	// html.append("<div style='float:left'><a id='btn_load' href='javascipt:void(null);' style='padding-left:10px;' onclick=\"this.href='?batch='+jQuery('#sel-batch').val()\">统计</a></div>");
	// return html;
	// }
	//
	// public static BatchNameSortEntity getBatch( PanelEvent event
	// ,BatchNameSortService batchNameSortServiceImpl){
	// String batchS= event.getModel().getParameter("batch");
	//
	// if(batchS==null){
	// return
	// batchNameSortServiceImpl.getBatchNameBySort(batchNameSortServiceImpl.getMinBatchSort());
	// }else{
	// return batchNameSortServiceImpl.get(Long.parseLong(batchS));
	// }
	//
	//
	// }
	// public static void setShowWhat(PanelEvent event,Object
	// items,BatchNameSortEntity bnse,BatchNameSortService
	// batchNameSortServiceImpl,String vmPath){
	// event.getModel().addObject("content",
	// items).addObject("select",CountUtil.batchSelect(bnse,batchNameSortServiceImpl.batchsSort()))
	// ;
	// event.setVmPath(vmPath);
	// }
	//

	public static void setShowWhat(PanelEvent event,
			Collection<Object[]> items, String batch, String vmPath) {
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (Object[] o : items) {

			index++;
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(index);
			sb.append("</td>");
			for (Object o2 : o) {
				sb.append("<td>");
				if (o2 != null) {
					if(o2.getClass().equals(Double.class)){
						DecimalFormat    df   = new DecimalFormat("######0.00"); 
						o2=	df.format(o2);					
					}
					sb.append(String.valueOf(o2));
				} else {
					sb.append("");
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		String batchIntS = String.valueOf(BatchHelper.indexOf(batch));
		event.getModel().addObject("content", sb.toString())
				.addObject("select", BatchHelper.batchSelect(batchIntS));
		event.setVmPath(vmPath);
	}

	public static boolean empowerVerification(String un, String up,
			LoginService loginService, PasswordEncoder passwordEncoder) {

		AccountEntity user = loginService.loadByLoginName(un);
		if (user == null
				|| user.getFlag() != FlagType.SUPER
				|| !passwordEncoder.isPasswordValid(user.getLoginPwd(), up,
						null)) {
			return false;
		}
		return true;
	}
}
