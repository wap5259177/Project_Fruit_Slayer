-- 用于更新对年度或季度查询和排序的字段
update t_maquarter set C_ORDINAL=C_ANNUAL*100+C_PERIOD;

update t_modelarea set C_ZSJ=0;