package cn.bonoon.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FVMAReportService;
import cn.bonoon.core.felicity.RuralInfo;
import cn.bonoon.entities.felicityVillage.FVCoreRuralEntity;
import cn.bonoon.entities.felicityVillage.FVModelAreaReportEntity;
import cn.bonoon.entities.felicityVillage.FVPeripheralRuralEntity;
import cn.bonoon.kernel.support.services.AbstractService;

@Service
@Transactional(readOnly = true)
public class FVMAReportServiceImpl extends AbstractService<FVModelAreaReportEntity> implements FVMAReportService{

	@Override
	public List<RuralInfo> checkRuralMakeList(Long id) {
		List<RuralInfo> rinfos = new ArrayList<>();
		String  ql = "select x from FVCoreRuralEntity x where x.modelArea.id=? and x.deleted=false  order by x.id desc";
		FVModelAreaReportEntity ma = __get(id);
		//添加小计行
		rinfos.add(new RuralInfo(ma));
		List<FVCoreRuralEntity> crs = __list(FVCoreRuralEntity.class, ql,id);
		for(FVCoreRuralEntity cr:crs){
			rinfos.add(new RuralInfo(cr));
			List<FVPeripheralRuralEntity> prs = cr.getFvprs();
			for(FVPeripheralRuralEntity pr:prs){
				if(!pr.isDeleted()){
					rinfos.add(new RuralInfo(pr));
				}
			}
		}
		return rinfos;
	}

	

}
