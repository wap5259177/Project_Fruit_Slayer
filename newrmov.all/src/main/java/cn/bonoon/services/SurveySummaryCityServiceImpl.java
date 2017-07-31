package cn.bonoon.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.SurveySummaryCityService;
import cn.bonoon.entities.SurveySummaryCityEntity;
import cn.bonoon.entities.SurveySummaryCountyEntity;
import cn.bonoon.entities.SurveySummaryProvinceEntity;
import cn.bonoon.entities.plugins.PlaceEntity;
import cn.bonoon.kernel.security.LogonUser;
import cn.bonoon.kernel.support.IOperator;
import cn.bonoon.kernel.support.services.AbstractSearchService;

/**
 * <pre>
 * 有两个市是没有区的，这个要如何处理、包括：
 * 中山：4420
 * 东莞:4419
 * </pre>
 * 
 * @author jackson
 * 
 */
@Service
@Transactional(readOnly = true)
public class SurveySummaryCityServiceImpl extends
		AbstractSearchService<SurveySummaryCityEntity> implements
		SurveySummaryCityService {

	@Override
	@Transactional
	public void toReport(IOperator opt, Long id) {
		SurveySummaryCityEntity ssc = __get(id);
		if (null == ssc || ssc.getStatus() != 0) {
			throw new RuntimeException("数据出错，请联系管理人员！");
		}
		PlaceEntity city = ssc.getUnit().getPlace();
		int i = 0;
		String ql = "select x from SurveySummaryCountyEntity x where x.city.id=?";
		List<SurveySummaryCountyEntity> ssces = __list(
				SurveySummaryCountyEntity.class, ql, id);
		for (PlaceEntity county : city.getChildren()) {
			if (county.isDeleted() || county.isDisabled())
				continue;
			SurveySummaryCountyEntity ss = null;
			for (SurveySummaryCountyEntity s : ssces) {
				if (s.getCounty().getId().equals(county.getId())) {
					ss = s;
					break;
				}
			}
			if (null == ss) {
				ss = new SurveySummaryCountyEntity();
				ss.setCity(ssc);
				ss.setCounty(county);
				entityManager.persist(ss);
			} else {
				ssces.remove(ss);
			}
			i++;
		}
		// 删除没有用的
		for (SurveySummaryCountyEntity s : ssces) {
			entityManager.remove(s);
		}
		ssc.setCountyCount(i);
		ssc.setStatus(2);
		ssc.setReportAt(new Date());
		ssc.setReporterId(opt.getId());
		ssc.setReporterName(opt.getUsername());
		entityManager.merge(ssc);

		// 下面是重新统计正在进行中的有多少个
		__refresh(ssc);
		// SurveySummaryProvinceEntity ssp = ssc.getProvince();
		// ql =
		// "select count(x) from SurveySummaryCityEntity x where x.province.id=? and x.status=2";
		// ssp.setStartReport(__first(Number.class, ql,
		// ssp.getId()).intValue());
		// entityManager.merge(ssp);
	}

	// 刷新
	@Override
	@Transactional
	public void refresh(IOperator opt, Long id) {
		SurveySummaryCityEntity ssc = __get(id);
		Long cityid = ssc.getCityId();

		String hql = "select x.county.id from SurveySummaryCountyEntity x where x.city.cityId=? and x.city.id=?";// id
		List<Long> placeids = __list(Long.class, hql, cityid, id);

		String sql = "select x from PlaceEntity x where x.parent.id=?";
		List<PlaceEntity> countys = __list(PlaceEntity.class, sql, cityid);
		int i = 0;
		for (PlaceEntity pe : countys) {
			//
			if (!placeids.contains(pe.getId())) {
				SurveySummaryCountyEntity ns = new SurveySummaryCountyEntity();
				ns.setCounty(pe);

				ns.setCity(ssc);
				entityManager.persist(ns);
			}
			i++;
			ssc.setCountyCount(i);
			entityManager.persist(ssc);
		}
		// PlaceEntity city = ssc.getUnit().getPlace();
		// int i = 0;
		// String ql =
		// "select x from SurveySummaryCountyEntity x where x.city.id=?";
		// List<SurveySummaryCountyEntity> ssces =
		// __list(SurveySummaryCountyEntity.class, ql, id);
		__refresh(ssc);
	}

	private void __refresh(SurveySummaryCityEntity ssc) {
		// 重新统计一下
		entityManager.merge(ssc);
		String ql = "select count(x) from SurveySummaryCityEntity x where x.province.id=? and x.status=?";
		SurveySummaryProvinceEntity province = ssc.getProvince();
		System.out.println(province.getCreateAt());
		int sr = __first(Number.class, ql, province.getId(), 2).intValue();
		System.out.println("sr" + sr);
		int fr = __first(Number.class, ql, province.getId(), 1).intValue();
		System.out.println("fr" + fr);
		province.setStartReport(sr);
		province.setFinishReport(fr);
		entityManager.merge(province);
	}

	@Override
	@Transactional
	public void toFinish(Long id) {
		// ,getUser()

		// 查出上一年度的
		// x.unit.id={USER owner} \
		SurveySummaryCityEntity ssc = __get(id);

		if (null == ssc || ssc.getStatus() != 2) {
			throw new RuntimeException("数据出错，请联系管理人员！");
		}
		// 把所有县的累加上来
		String ql = "select x from SurveySummaryCountyEntity x where x.city.id=?";
		// String error = "";
		ssc.clear();
		for (SurveySummaryCountyEntity ss : __list(
				SurveySummaryCountyEntity.class, ql, id)) {
			ssc.sum(ss);
			// if(ss.getAgriculturalHousehold() > 0 &&
			// ss.getAgriculturalPopulation() > 0){
			// }else{
			// //error += ","+ ss.getCounty().getName();
			// }
		}
		// if(!error.isEmpty()){
		// throw new RuntimeException("检查到区/县 [" + error.substring(1)
		// +"] 数据不完整！");
		// }
		ssc.setStatus(1);
		entityManager.merge(ssc);

		__refresh(ssc);

		// SurveySummaryProvinceEntity ssp = ssc.getProvince();
		// ssp.sum(ssc);
		// ssp.setStartReport(ssp.getStartReport() - 1);
		// ssp.setFinishReport(ssp.getFinishReport() + 1);
		// if(ssp.getFinishReport() >= ssp.getNeedReport()){
		// //完成
		// ssp.setStatus(1);
		// }
		// entityManager.merge(ssp);
	}

	// 某个市,省创建市的表
	@Override
	public List<SurveySummaryCountyEntity> countyReports(Long id) {
		String ql = "select x from SurveySummaryCountyEntity x where x.city.id=?";
		return __list(SurveySummaryCountyEntity.class, ql, id);
	}

	@Override
	public List<SurveySummaryCountyEntity> getLastYearSurveySummaryCityEntityList(
			String cityName, int lastAnnual) {
		String preQL = "select x from SurveySummaryCountyEntity x where x.city.cityName=? and x.city.province.annual=?";
		// List<SurveySummaryCountyEntity>
		// List<SurveySummaryCityEntity> preSsc = __list(
		// SurveySummaryCityEntity.class, preQL, user.getId(), ssc
		// .getCity().getProvince().getAnnual() - 1);
		System.out.println("service getqueryAttabute cityName=" + cityName
				+ "\t year" + lastAnnual);
		List<SurveySummaryCountyEntity> preSsc = __list(
				SurveySummaryCountyEntity.class, preQL, cityName, lastAnnual);
		return preSsc;
	}

	@Override
	public SurveySummaryCountyEntity county(Long id) {
		return entityManager.find(SurveySummaryCountyEntity.class, id);
	}

	@Override
	@Transactional
	public void save(SurveySummaryCountyEntity ssc) {
		entityManager.merge(ssc);
	}

	@Override
	public List<SurveySummaryCityEntity> get(IOperator user) {
		Date current = new Date();
		String hql = "select x from SurveySummaryCityEntity x where x.status in(0,2) and x.unit.id=? and x.province.startAt<=? and x.province.endAt>=?";
		return __list(SurveySummaryCityEntity.class, hql, user.getOwnerId(),
				current, current);
	}

	@Override
	public List<SurveySummaryCityEntity> get(IOperator user, int count) {
		String ql = "select x from SurveySummaryCityEntity x where x.status=1 and x.unit.id=? order by x.province.annual desc";
		return __top(count, SurveySummaryCityEntity.class, ql,
				user.getOwnerId());
	}

	@Override
	public List<SurveySummaryCityEntity> getUrges(IOperator user) {
		String ql = "select x from SurveySummaryCityEntity x where x.unit.id=? and x.status in(0,2) and x.urge>0";
		return __list(SurveySummaryCityEntity.class, ql, user.getOwnerId());
	}

	@Override
	public SurveySummaryCityEntity getCity(Long id) {
		String ql = "select x from SurveySummaryCityEntity x where x.id=?";
		return __first(SurveySummaryCityEntity.class, ql, id);
	}

	@Override
	public List<Integer> getAllAnnual(String cityName) {
		String preQL = "select x.province.annual from SurveySummaryCityEntity x where x.cityName=?";
		List<Integer> list = __list(Integer.class, preQL, cityName);
		Collections.sort(list);
		return list;
	}
	// public List<SurveySummaryCityEntity> getAllAnnual(String cityName) {
	// String preQL =
	// "select x.province.annual from SurveySummaryCityEntity x where x.cityName=?";
	// return __list(SurveySummaryCityEntity.class, preQL);
	// }

}
