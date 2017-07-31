package cn.bonoon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bonoon.core.FundsStatisticsService;
import cn.bonoon.kernel.support.services.ServiceSupport;

@Service
@Transactional(readOnly = true)
public class FundsStatisticsServiceImpl extends ServiceSupport implements FundsStatisticsService{

}
