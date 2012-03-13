USE [tjetms-bes]
GO
--delete from tb_etms_base_para_dtl
--delete from tb_etms_base_para
/*
delete from tb_etms_base_schedule
delete from tb_etms_rept_broad_by_time
delete from tb_etms_rept_op_time
delete from tb_etms_accd_duty_time
delete from tb_etms_accd_abn_operation
delete from tb_etms_base_operation
delete from tb_etms_base_transfer
delete from tb_etms_base_satellite
delete from TB_ETMS_BASE_ROUTE
delete from TB_ETMS_BASE_CAWAVE_PG_REF
delete from TB_ETMS_BASE_CAWAVE
delete from TB_ETMS_BASE_PROGRAM
*/--传输类型定义
insert into tb_etms_base_para(para_type,para_desc,define)
values('TRTP','传输类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(1,'TRTP','P','集成平台',2,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(2,'TRTP','T','节目源传输',3,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(3,'TRTP','S','卫星上行',1,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--业务传输方式定义
insert into tb_etms_base_para(para_type,para_desc,define)
values('TRMD','业务传输方式','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(4,'TRMD','TRAN','光传输网络',1,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(5,'TRMD','DAMI','数字微波',2,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(6,'TRMD','MIDF','中频传输',3,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(77,'TRMD','ASI','ASI',3,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--设备备件类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('EQTP','设备类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(7,'EQTP','000','HPA',null,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(8,'EQTP','001','UC',null,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(9,'EQTP','002','光端机',null,'2010-10-1','管理员');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(10,'EQTP','003','切换开关',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--班组类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('GPTP','班组类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(11,'GPTP','1','值班',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(12,'GPTP','2','日勤',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(13,'GPTP','3','留守',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(14,'GPTP','4','检修',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--部门编号
insert into tb_etms_base_para(para_type,para_desc,define)
values('DEPT','部门编号','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(15,'DEPT','40','卫星机房',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(16,'DEPT','41','平台机房',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(17,'DEPT','01','台领导',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(18,'DEPT','02','台办室',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(19,'DEPT','03','保卫科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(20,'DEPT','04','党办室',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(21,'DEPT','05','工会',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(22,'DEPT','06','离退休人员管理科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(23,'DEPT','07','财务器材科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(24,'DEPT','08','技办室',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(25,'DEPT','09','人事科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(26,'DEPT','10','团总支/团支部',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(27,'DEPT','11','行政科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(28,'DEPT','12','维修科/维修室',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(29,'DEPT','13','天线科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(30,'DEPT','14','调度室',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(31,'DEPT','15','变电站',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(32,'DEPT','16','政工科',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(33,'DEPT','17','信息化小组',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(34,'DEPT','50','节传机房',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(35,'DEPT','51','微波机房',NULL,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(36,'DEPT','52','监控中心',NULL,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--部门类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('DPTP','部门类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(37,'DPTP','MATA','维修部门',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(38,'DPTP','PSDP','供电站',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(39,'DPTP','SERV','行政部门',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(40,'DPTP','TKMG','技术管理',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(41,'DPTP','TKST','技术支持',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(42,'DPTP','SATE','卫星机房',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(43,'DPTP','PLAT','平台机房',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--台站类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('STTP','台站类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(44,'STTP','CGST','综合性台站',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--DTTP 台站归属
insert into tb_etms_base_para(para_type,para_desc,define)
values('DTTP','台站归属','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(45,'DTTP','01','国内直属',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--WNTP 告警类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('WNTP','告警类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(46,'WNTP','01','电压告警',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(47,'WNTP','02','温度告警',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(48,'WNTP','03','湿度告警',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--PGTP 节目类型
insert into tb_etms_base_para(para_type,para_desc,define)
values('PGTP','节目类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(49,'PGTP','TV','电视',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(50,'PGTP','BD','广播',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(51,'PGTP','DT','数据',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--整机/散件代码

--设备型号

--故障类型ABTP
insert into tb_etms_base_para(para_type,para_desc,define)
values('ABTP','故障类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(52,'ABTP','WEATH','天气',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(53,'ABTP','DISTB','干扰',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(54,'ABTP','EQUIP','设备损坏',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--事故性质定义
insert into tb_etms_base_para(para_type,para_desc,define)
values('ADDU','事故性质','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(55,'ADDU','02','人为过错','I','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(56,'ADDU','03','处理不当','I','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(57,'ADDU','01','维修责任','I','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(58,'ADDU','05','技术事故','I','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(59,'ADDU','17','线路故障','O','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(60,'ADDU','18','天气原因','O','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(61,'ADDU','12','外电原因','O','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(62,'ADDU','19','节目源中断','O','2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(63,'ADDU','16','其他','O','2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--TUTP 发射单位类型  
insert into tb_etms_base_para(para_type,para_desc,define)
values('TUTP','台站类型','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(64,'TUTP','MBST','中波或电视调频发射台',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(65,'TUTP','MSBC','中短波发射中心',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(66,'TUTP','MSBS','短波或中短波发射台',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off


--代播被代台站编号
insert into tb_etms_base_para(para_type,para_desc,define)
values('DBBD','代播被代台站','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(67,'DBBD','32','542',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(68,'DBBD','75','呼市地球站',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(83,'DBBD','OT','其他',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--代播被代原因分类
insert into tb_etms_base_para(para_type,para_desc,define)
values('DBRN','代播被代原因','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(69,'DBRN','DBWEAT','天气',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(70,'DBRN','DBEQUP','设备故障',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(71,'DBRN','DBPOWE','电力',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(72,'DBRN','DBPROG','节目源',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(73,'DBRN','DBOPDD','业务调度',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(74,'DBRN','DBEXAM','计划检修',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(75,'DBRN','DBDIST','外界干扰',null,'2010-10-1','管理员');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(76,'DBRN','DBOTHE','其他',null,'2010-10-1','管理员');
set identity_insert tb_etms_base_para_dtl off

--地球站资料
INSERT INTO tb_etms_base_STATION(ID,STATION_CODE,STATION_NAME,S_CODE,REGION_CODE,LONGITUDE,LATITUDE,STATION_ADDR,
ALTITUDE,ACREAGE,BUILD_DATE,START_DATE,RMKS,STATION_TYPE,STATION_BREV,GEO_INFOR,DISTRICT_TYPE,LOC
,TRANSMIT_UNIT_TYPE)
VALUES(1,'40','北京地球站','BES','110100',null,null,null,
0.00,1000.00,'2006-4-30','2006-4-30',null,44,null,null,45,null,65);

--环节定义
set identity_insert tb_etms_base_tache on
insert into tb_etms_base_tache(id,ta_name,trans_type)
values(1,'信源系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(2,'编码复用系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(3,'信号输出系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(4,'网管系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(5,'CA系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(6,'内部电力系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(7,'外部电力系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(8,'集中供电系统',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(9,'节目源引接',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(10,'传输线路',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(11,'信号处理',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(12,'传输设备',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(13,'网管系统',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(14,'内部电力系统',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(15,'外部电力系统',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(16,'集中供电系统',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(17,'节目源引接',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(18,'调制',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(19,'上变频',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(20,'高功放',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(21,'天馈线',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(22,'控制系统',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(23,'空间链路',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(24,'内部电力系统',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(25,'外部电力系统',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(26,'集中供电系统',3);
set identity_insert tb_etms_base_tache off

--技术系统定义
set identity_insert tb_etms_base_techsystem on
insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(1,'C1','C1系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(2,'C2','C2系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(3,'C3','C3系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(4,'C4','C4系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(5,'C5','C5系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(6,'C6','C6系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(7,'C7','C7系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(8,'C8','C8系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(9,'C9','C9系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(10,'CA','CA系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(11,'CB','CB系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(12,'Ku1','Ku1系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(13,'Ku2','Ku2系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(14,'Ku3','Ku3系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(15,'Ku4','Ku4系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(16,'Ku5','Ku5系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(17,'Ku6','Ku6系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(18,'Ku7','Ku7系统',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(19,'DBS','DBS系统',3);
set identity_insert tb_etms_base_techsystem off

--报表定义
insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MFAIL','设备运行故障月报表','ACCD','节传处','Y','设备运行故障月报表.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MACCD','停传事故月报表','ACCD','节传处','Y','停传事故月报表.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MTRAN','节目传输月报表','ACCD','节传处','Y','节目传输月报表.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('IFAIL','保证期设备运行故障月报表','IMPT','节传处','Y','保证期设备运行故障报表.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('IACCD','保证期停传事故月报表','IMPT','节传处','Y','保证期停传事故报表.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('ITRAN','保证期节目传输月报表','IMPT','节传处','Y','保证期节目传输报表.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('DBBD','代播被代情况汇总','EXAM',null,'Y','代播被代情况汇总.raq','broad_by_flag,start_time,end_time,trans_type_db');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('CSQK','传输情况统计表','EXAM',null,'Y','传输情况统计表.raq','start_time,end_time,trans_type');

--角色定义
insert into tb_sec_role(id,role_name,ROLE_DESC)
values('1','WORKER','值班员');
insert into tb_sec_role(id,role_name,role_desc)
values('10','TEKOFFICER','技办');
insert into tb_sec_role(id,role_name,role_desc)
values('11','USER','普通用户');
insert into tb_sec_role(id,role_name,role_desc)
values('12','GUEST','访客');
insert into tb_sec_role(id,role_name,role_desc)
values('2','ANONYMOUS','匿名用户');
insert into tb_sec_role(id,role_name,role_desc)
values('3','ADMIN','管理员');
insert into tb_sec_role(id,role_name,role_desc)
values('4','WATCH','值班长');
insert into tb_sec_role(id,role_name,role_desc)
values('5','DIRECTOR','机房主任');
insert into tb_sec_role(id,role_name,role_desc)
values('7','OFFICER','技术主管');
insert into tb_sec_role(id,role_name,role_desc)
values('8','GOVERNOR','单位主管');

--部门定义
set identity_insert TB_ETMS_BASE_DEPT on
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(1,'4017','信息化办公室','41',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(2,'4040','卫星机房','42',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(3,'4041','平台机房','43',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(4,'4015','电力机房','38',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(5,'4008','技办室','40',1,NULL);
set identity_insert TB_ETMS_BASE_DEPT off

--部门与业务类型的对应关系
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(2, 3)
GO
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(3, 1)
GO
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(3, 2)
GO
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(5, 1)
GO
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(5, 2)
GO
INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([DP_ID], [TRANS_TYPE])
VALUES(5, 3)
GO

--职位定义
set identity_insert TB_ETMS_BASE_POST on
insert into tb_etms_base_post(id,post,post_name,sort)
values(1,'004A','主任',5);
insert into tb_etms_base_post(id,post,post_name,sort)
values(2,'004B','副主任',6);
insert into tb_etms_base_post(id,post,post_name,sort)
values(3,'083Q','总工程师',3);
insert into tb_etms_base_post(id,post,post_name,sort)
values(4,'083R','副总工程师',4);
insert into tb_etms_base_post(id,post,post_name,sort)
values(5,'133Q','会计',80);
insert into tb_etms_base_post(id,post,post_name,sort)
values(6,'220A','科长',20);
insert into tb_etms_base_post(id,post,post_name,sort)
values(7,'220B','副科长',21);
insert into tb_etms_base_post(id,post,post_name,sort)
values(8,'221S','主任科员',22);
insert into tb_etms_base_post(id,post,post_name,sort)
values(9,'221T','副主任科员',23);
insert into tb_etms_base_post(id,post,post_name,sort)
values(10,'404A','台长',30);
insert into tb_etms_base_post(id,post,post_name,sort)
values(11,'404B','副台长',31);
insert into tb_etms_base_post(id,post,post_name,sort)
values(12,'438A','站长',1);
insert into tb_etms_base_post(id,post,post_name,sort)
values(13,'438B','副站长',2);
insert into tb_etms_base_post(id,post,post_name,sort)
values(14,'819A','班长',7);
insert into tb_etms_base_post(id,post,post_name,sort)
values(15,'819B','副班长',8);
insert into tb_etms_base_post(id,post,post_name,sort)
values(16,'819C','值班员',10);
insert into tb_etms_base_post(id,post,post_name,sort)
values(17,'819D','检修员',12);
insert into tb_etms_base_post(id,post,post_name,sort)
values(18,'819E','维修员',13);
insert into tb_etms_base_post(id,post,post_name,sort)
values(19,'9988','其它',98);
insert into tb_etms_base_post(id,post,post_name,sort)
values(20,'9999','不详',99);
set identity_insert TB_ETMS_BASE_POST off

--管理员定义
set identity_insert TB_ETMS_BASE_PERSON on
insert into TB_ETMS_BASE_PERSON(id,dp_id,emp_code,login_name,login_password,emp_name,post,is_enable)
values(1,2,'0','admin','admin','管理员',1,1);
set identity_insert TB_ETMS_BASE_PERSON off

--管理员权限
set identity_insert TB_SEC_DEPT_PER on
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(1,1,2,'general');
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(2,1,2,'duty');
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(3,1,2,'accd');
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(4,1,2,'repair');
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(5,1,2,'report');
insert into TB_SEC_DEPT_PER(id,emp_id,dp_id,fun_module)
values(6,1,2,'baseinfo');
set identity_insert TB_SEC_DEPT_PER off

--管理员角色
insert into tb_sec_pri_ass(role_id,emp_id)
values(3,1);

--初始化检修周期
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('十日检', '1', 1, 10, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('月检', '1', 1, 30, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('季检', '1', 1, 90, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('半年检', '1', 1, 180, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('年检', '1', 1, 360, NULL, NULL)



--卫星定义
set identity_insert TB_ETMS_BASE_SATELLITE on
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(1,'中星6A','92.2°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(2,'中星6B','115°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(3,'亚太V号','138°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(4,'亚太6号','134°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(5,'范美10号','125°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(6,'中星9号','92.2°E',getdate(),'人工加载');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(7,'亚洲3S','105.5°E',getdate(),'人工加载');
set identity_insert TB_ETMS_BASE_SATELLITE off

--转发器定义
set identity_insert TB_ETMS_BASE_TRANSFER on
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(1,2,'S1转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(2,2,'S2转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(3,2,'S3转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(4,2,'S4转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(5,2,'S5转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(6,2,'S6转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(7,2,'S7转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(8,2,'S8转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(9,2,'S9转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(10,2,'S10转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(11,2,'S11转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(12,2,'S12转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(13,2,'S13转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(14,2,'S14转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(15,2,'S15转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(16,2,'S16转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(17,2,'S17转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(18,2,'S18转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(19,2,'S19转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(20,2,'S20转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(21,2,'S21转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(22,2,'S22转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(23,2,'S23转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(24,2,'S24转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(25,3,'5B转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(26,4,'K1转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(27,4,'K4转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(28,4,'K5转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(29,4,'K7转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(30,4,'K8转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(31,4,'K9转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(32,4,'K11转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(33,1,'1A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(34,1,'2A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(35,1,'3A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(36,1,'4A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(37,1,'5A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(38,1,'6A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(39,1,'7A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(40,1,'8A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(41,1,'9A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(42,1,'10A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(43,1,'11A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(44,1,'12A转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(45,6,'3A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(46,6,'4A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(47,6,'5A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(48,6,'6A转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(49,6,'6B转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(50,6,'7B转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(51,6,'8B转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(52,6,'9B转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(53,6,'10B转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(54,5,'9C转发器',getdate(),'人工加载');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(55,5,'21C转发器',getdate(),'人工加载');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(56,7,'K7H转发器',getdate(),'人工加载');

set identity_insert TB_ETMS_BASE_TRANSFER off

--路由定义
set identity_insert TB_ETMS_BASE_ROUTE on
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(1,'中央塔','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(2,'中国教育电视台','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(3,'北京电视台','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(4,'中央电视台','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(5,'中广影视卫星公司','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(6,'中国有线','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(7,'节传中心','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(8,'京都信苑','北京地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(9,'电影频道','北京地球站',getdate(),'人工加载');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(10,'中央塔','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(11,'中国教育电视台','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(12,'中央电视台','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(13,'中广影视卫星公司','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(14,'中国有线','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(15,'节传中心','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(16,'京都信苑','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(17,'电影频道','542地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(18,'电影数字节目管理中心','542地球站',getdate(),'人工加载');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(19,'中央塔','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(20,'北京电视台','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(21,'中央电视台','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(22,'中广影视卫星公司','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(23,'中国有线','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(24,'京都信苑','呼和浩特地球站',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(25,'北京地球站','呼和浩特地球站',getdate(),'人工加载');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(26,'中央台','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(27,'北京电视台','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(28,'中央电视台','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(29,'中广影视卫星公司','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(30,'中国有线','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(31,'京都信苑','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(32,'中国教育电视台','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(33,'节传中心','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(34,'电影频道','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(35,'北京地球站','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(36,'歌华有线','中央塔',getdate(),'人工加载');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(37,'CMMB','中央塔',getdate(),'人工加载');
set identity_insert TB_ETMS_BASE_ROUTE off

--节目流定义
set identity_insert TB_ETMS_BASE_CAWAVE on
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(1,'CCTV-3/5/6/8/新闻/少儿/纪录',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(2,'CCTV-1/2/7/10/11/12/音乐',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(3,'长城亚洲平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(4,'凤凰卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(5,'电影高清',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(6,'中华美食',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(7,'广东/广西/深圳',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(8,'北京/湖南高清',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(9,'黑龙江高清',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(10,'辽宁卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(11,'北京卫视/卡酷',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(12,'CETV-1',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(13,'中数传媒S11付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(14,'中数传媒S14付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(15,'中数传媒S16付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(16,'中数传媒S18付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(17,'中数传媒S20付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(18,'中数传媒S22付费频道',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(19,'C波段100路广播',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(20,'K4教育台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(21,'K8教育台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(22,'K5境外平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(23,'K7境外平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(24,'K9境外平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(25,'KU波段100路广播',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(26,'K11境外平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(27,'广西卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(28,'广东/南方/深圳卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(29,'吉林/延边卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(30,'云南卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(31,'海南卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(32,'CMMB移动广播电视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(33,'村村通4A节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(34,'5A加密流节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(35,'6A加密流节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(36,'6B加密流节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(37,'7B加密流节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(38,'8B加密流节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(39,'村村通9B节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(40,'村村通10B节目',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(41,'全国EPG同步信号',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(42,'数字电影流动放映服务平台',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(43,'黑龙江卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(44,'西藏卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(45,'新疆兵团卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(47,'新疆、新疆走出去',getdate(),'人工加载');
--因为卫星传输业务里有才加的 ，节传提供的没有，辽宁卫视有两个，保留了id =10的，id=46的改给福建卫视了
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(46,'福建卫视',getdate(),'人工加载');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(48,'村村通3A节目',getdate(),'人工加载');
set identity_insert TB_ETMS_BASE_CAWAVE off

--节目定义   目前不知道节目代码，节目代码暂用节目ID
set identity_insert TB_ETMS_BASE_PROGRAM on
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(1,1,'CCTV1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(2,2,'BTV-1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(3,3,'CCTV-10',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(4,4,'CCTV-12',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(5,5,'CCTV-2',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(6,6,'CCTV-7',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(7,7,'CCTV-少儿',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(8,8,'CCTV-新闻',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(9,9,'CETV-1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(10,10,'TJTV-1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(11,11,'安徽1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(12,12,'打击乐',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(13,13,'福建东南',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(14,14,'甘肃1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(15,15,'广东卫视',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(16,16,'广西1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(17,17,'广西卫视',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(18,18,'贵州1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(19,19,'国际1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(20,20,'国际10',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(21,21,'国际11',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(22,22,'国际12',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(23,23,'国际13',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(24,24,'国际14',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(25,25,'国际15',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(26,26,'国际16',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(27,27,'国际17',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(28,28,'国际18',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(29,29,'国际19',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(30,30,'国际2',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(31,31,'国际20',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(32,32,'国际21',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(33,33,'国际22',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(34,34,'国际23',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(35,35,'国际24',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(36,36,'国际25',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(37,37,'国际26',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(38,38,'国际27',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(39,39,'国际28',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(40,40,'国际29',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(41,41,'国际3',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(42,42,'国际30',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(43,43,'国际31',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(44,44,'国际32',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(45,45,'国际33',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(46,46,'国际34',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(47,47,'国际35',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(48,48,'国际36',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(49,49,'国际37',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(50,50,'国际38',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(51,51,'国际39',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(52,52,'国际4',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(53,53,'国际40',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(54,54,'国际41',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(55,55,'国际42',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(56,56,'国际43',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(57,57,'国际44',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(58,58,'国际45',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(59,59,'国际46',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(60,60,'国际47',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(61,61,'国际48（预留）',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(62,62,'国际49（预留）',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(63,63,'国际5',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(64,64,'国际50',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(65,65,'国际6',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(66,66,'国际7',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(67,67,'国际8',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(68,68,'国际9',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(69,69,'哈语广播',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(70,70,'河北1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(71,71,'河南1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(72,72,'黑龙江1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(73,73,'湖北1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(74,74,'湖南1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(75,75,'吉林1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(76,76,'吉林朝语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(77,77,'江西1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(78,78,'经济之声',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(79,79,'辽宁1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(80,80,'旅游卫视',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(81,81,'蒙柯语广播',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(82,82,'民族之声',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(83,83,'内蒙汉语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(84,84,'内蒙蒙语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(85,85,'宁夏1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(86,86,'青海1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(87,87,'青海藏语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(88,88,'山西1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(89,89,'陕西1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(90,90,'陕西农林',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(91,91,'深圳卫视',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(92,92,'四川1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(93,93,'四川康巴藏语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(94,94,'维语经济生活',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(95,95,'维语文艺',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(96,96,'维语综合',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(97,97,'维语综艺',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(98,98,'西藏藏语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(99,99,'西藏汉语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(100,100,'新疆1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(101,101,'新疆兵团',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(102,102,'新疆哈语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(103,103,'新疆哈语综艺',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(104,104,'新疆汉语新闻',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(105,105,'新疆少儿',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(106,106,'新疆维语',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(107,107,'音乐之声',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(108,108,'预留1',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(109,109,'预留2',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(110,110,'预留3',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(111,111,'预留4',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(112,112,'预留5',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(113,113,'预留6',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(114,114,'预留7',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(115,115,'预留8',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(116,116,'预留9',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(117,117,'预留10',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(118,118,'预留11',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(119,119,'预留12',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(120,120,'云南1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(121,121,'浙江1套',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(122,122,'中八',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(123,123,'中二',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(124,124,'中国之声',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(125,125,'中九',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(126,126,'中六',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(127,127,'中七普',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(128,128,'中七双',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(129,129,'中三',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(130,130,'中十',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(132,132,'中十二',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(131,131,'中十一',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(133,133,'中四',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(134,134,'中五',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(135,135,'中一',49,getdate(),'人工加载');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(136,136,'重庆1套',49,getdate(),'人工加载');

set identity_insert TB_ETMS_BASE_PROGRAM off

--节目集成平台业务
set identity_insert TB_ETMS_BASE_OPERATION on
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(1,'广东卫视','P',1,7,15,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(2,'广西卫视','P',1,7,17,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(3,'深圳卫视','P',1,7,91,'2011-01-01',getdate(),'人工加载');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(4,'福建东南','P',1,40,13,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(5,'甘肃1套','P',1,40,14,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(6,'广西1套','P',1,40,16,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(7,'贵州1套','P',1,40,18,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(8,'河南1套','P',1,40,71,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(9,'湖北1套','P',1,40,73,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(10,'湖南1套','P',1,40,74,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(11,'江西1套','P',1,40,77,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(12,'旅游卫视','P',1,40,80,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(13,'宁夏1套','P',1,40,85,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(14,'青海1套','P',1,40,86,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(15,'青海藏语','P',1,40,87,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(16,'四川1套','P',1,40,92,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(17,'新疆哈语综艺','P',1,40,103,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(18,'新疆少儿','P',1,40,105,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(19,'重庆1套','P',1,40,136,'2011-01-01',getdate(),'人工加载');


insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(20,'BTV-1','P',1,33,2,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(21,'CCTV1','P',1,33,1,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(22,'CCTV-10','P',1,33,3,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(23,'CCTV-12','P',1,33,4,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(24,'CCTV-2','P',1,33,5,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(25,'CCTV-7','P',1,33,6,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(26,'CCTV-少儿','P',1,33,7,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(27,'CCTV-新闻','P',1,33,8,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(28,'CETV-1','P',1,33,9,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(29,'TJTV-1','P',1,33,10,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(30,'哈语广播','P',1,33,69,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(31,'经济之声','P',1,33,78,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(32,'蒙柯语广播','P',1,33,81,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(33,'民族之声','P',1,33,82,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(34,'陕西1套','P',1,33,89,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(35,'四川康巴藏语','P',1,33,93,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(36,'维语文艺','P',1,33,95,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(37,'维语综合','P',1,33,96,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(38,'西藏藏语','P',1,33,98,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(39,'西藏汉语','P',1,33,99,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(40,'新疆兵团','P',1,33,101,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(41,'新疆汉语新闻','P',1,33,104,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(42,'音乐之声','P',1,33,107,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(43,'云南1套','P',1,33,120,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(44,'中国之声','P',1,33,124,'2011-01-01',getdate(),'人工加载');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(45,'安徽1套','P',1,39,11,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(46,'河北1套','P',1,39,70,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(47,'黑龙江1套','P',1,39,72,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(48,'吉林1套','P',1,39,75,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(49,'吉林朝语','P',1,39,76,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(50,'辽宁1套','P',1,39,79,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(51,'内蒙汉语','P',1,39,83,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(52,'内蒙蒙语','P',1,39,84,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(53,'山西1套','P',1,39,88,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(54,'陕西农林','P',1,39,90,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(55,'维语经济生活','P',1,39,94,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(56,'维语综艺','P',1,39,97,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(57,'新疆1套','P',1,39,100,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(58,'新疆哈语','P',1,39,102,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(59,'新疆维语','P',1,39,106,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(60,'浙江1套','P',1,39,121,'2011-01-01',getdate(),'人工加载');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(61,'打击乐','P',1,19,12,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(62,'国际1','P',1,19,19,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(63,'国际10','P',1,19,20,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(64,'国际11','P',1,19,21,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(65,'国际12','P',1,19,22,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(66,'国际13','P',1,19,23,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(67,'国际14','P',1,19,24,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(68,'国际15','P',1,19,25,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(69,'国际16','P',1,19,26,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(70,'国际17','P',1,19,27,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(71,'国际18','P',1,19,28,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(72,'国际19','P',1,19,29,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(73,'国际2','P',1,19,30,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(74,'国际20','P',1,19,31,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(75,'国际21','P',1,19,32,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(76,'国际22','P',1,19,33,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(77,'国际23','P',1,19,34,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(78,'国际24','P',1,19,35,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(79,'国际25','P',1,19,36,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(80,'国际26','P',1,19,37,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(81,'国际27','P',1,19,38,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(82,'国际28','P',1,19,39,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(83,'国际29','P',1,19,40,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(84,'国际3','P',1,19,41,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(85,'国际30','P',1,19,42,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(86,'国际31','P',1,19,43,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(87,'国际32','P',1,19,44,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(88,'国际33','P',1,19,45,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(89,'国际34','P',1,19,46,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(90,'国际35','P',1,19,47,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(91,'国际36','P',1,19,48,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(92,'国际37','P',1,19,49,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(93,'国际38','P',1,19,50,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(94,'国际39','P',1,19,51,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(95,'国际4','P',1,19,52,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(96,'国际40','P',1,19,53,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(97,'国际41','P',1,19,54,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(98,'国际42','P',1,19,55,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(99,'国际43','P',1,19,56,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(100,'国际44','P',1,19,57,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(101,'国际45','P',1,19,58,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(102,'国际46','P',1,19,59,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(103,'国际47','P',1,19,60,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(104,'国际48（预留）','P',1,19,61,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(105,'国际49（预留）','P',1,19,62,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(106,'国际5','P',1,19,63,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(107,'国际50','P',1,19,64,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(108,'国际6','P',1,19,65,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(109,'国际7','P',1,19,66,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(110,'国际8','P',1,19,67,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(111,'国际9','P',1,19,68,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(112,'预留','P',1,19,108,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(113,'预留','P',1,19,109,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(114,'预留','P',1,19,110,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(115,'预留','P',1,19,111,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(116,'预留','P',1,19,112,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(117,'预留','P',1,19,113,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(118,'中八','P',1,19,122,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(119,'中二','P',1,19,123,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(120,'中九','P',1,19,125,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(121,'中六','P',1,19,126,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(122,'中七普','P',1,19,127,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(123,'中七双','P',1,19,128,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(124,'中三','P',1,19,129,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(125,'中十','P',1,19,130,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(126,'中十二','P',1,19,131,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(127,'中十一','P',1,19,132,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(128,'中四','P',1,19,133,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(129,'中五','P',1,19,134,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(130,'中一','P',1,19,135,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(131,'打击乐','P',1,25,12,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(132,'国际1','P',1,25,19,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(133,'国际10','P',1,25,20,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(134,'国际11','P',1,25,21,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(135,'国际12','P',1,25,22,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(136,'国际13','P',1,25,23,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(137,'国际14','P',1,25,24,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(138,'国际15','P',1,25,25,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(139,'国际16','P',1,25,26,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(140,'国际17','P',1,25,27,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(141,'国际18','P',1,25,28,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(142,'国际19','P',1,25,29,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(143,'国际2','P',1,25,30,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(144,'国际20','P',1,25,31,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(145,'国际21','P',1,25,32,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(146,'国际22','P',1,25,33,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(147,'国际23','P',1,25,34,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(148,'国际24','P',1,25,35,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(149,'国际25','P',1,25,36,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(150,'国际26','P',1,25,37,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(151,'国际27','P',1,25,38,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(152,'国际28','P',1,25,39,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(153,'国际29','P',1,25,40,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(154,'国际3','P',1,25,41,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(155,'国际30','P',1,25,42,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(156,'国际31','P',1,25,43,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(157,'国际32','P',1,25,44,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(158,'国际33','P',1,25,45,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(159,'国际34','P',1,25,46,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(160,'国际35','P',1,25,47,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(161,'国际36','P',1,25,48,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(162,'国际37','P',1,25,49,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(163,'国际38','P',1,25,50,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(164,'国际39','P',1,25,51,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(165,'国际4','P',1,25,52,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(166,'国际40','P',1,25,53,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(167,'国际41','P',1,25,54,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(168,'国际42','P',1,25,55,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(169,'国际43','P',1,25,56,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(170,'国际44','P',1,25,57,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(171,'国际45','P',1,25,58,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(172,'国际46','P',1,25,59,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(173,'国际47','P',1,25,60,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(174,'国际48（预留）','P',1,25,61,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(175,'国际49（预留）','P',1,25,62,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(176,'国际5','P',1,25,63,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(177,'国际50','P',1,25,64,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(178,'国际6','P',1,25,65,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(179,'国际7','P',1,25,66,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(180,'国际8','P',1,25,67,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(181,'国际9','P',1,25,68,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(182,'预留','P',1,25,114,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(183,'预留','P',1,25,115,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(184,'预留','P',1,25,116,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(185,'预留','P',1,25,117,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(186,'预留','P',1,25,118,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(187,'预留','P',1,25,119,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(188,'中八','P',1,25,122,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(189,'中二','P',1,25,123,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(190,'中九','P',1,25,125,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(191,'中六','P',1,25,126,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(192,'中七普','P',1,25,127,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(193,'中七双','P',1,25,128,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(194,'中三','P',1,25,129,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(195,'中十','P',1,25,130,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(196,'中十二','P',1,25,131,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(197,'中十一','P',1,25,132,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(198,'中四','P',1,25,133,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(199,'中五','P',1,25,134,'2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(200,'中一','P',1,25,135,'2011-01-01',getdate(),'人工加载');

set identity_insert TB_ETMS_BASE_OPERATION off

--卫星传输业务
set identity_insert TB_ETMS_BASE_OPERATION on
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(201,'北京/湖南高清','S',3,8,34,3760,30.6,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(202,'广东南方深圳卫视','S',3,28,36,3845,17.778,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(203,'广西卫视','S',3,27,37,3884,5.72,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(204,'CMMB移动广播电视','S',3,32,37,3866,5.425,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(205,'吉林延边卫视','S',3,29,38,3909,8.934,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(206,'云南卫视','S',3,30,38,3922,7.25,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(207,'海南卫视','S',3,31,38,3933,6.59,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(208,'黑龙江高清','S',3,9,39,3951,13.4,'7/8','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(209,'黑龙江卫视','S',3,43,37,3893,6.88,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(210,'西藏卫视','S',3,44,40,3989,9.07,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(211,'新疆兵团卫视','S',3,45,40,3999,4.42,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(212,'辽宁卫视','S',3,10,40,4006,4.42,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(213,'新疆、新疆走出去','S',3,47,43,4120,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(214,'CCT-V1/2/7/10/11/音乐','S',3,2,42,4080,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(215,'CCTV-3/5/6/8/新闻/少儿/纪录','S',3,1,44,4160,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(216,'凤凰卫视','S',3,4,1,3730,10.72,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(217,'福建卫视','S',3,46,1,3706,4.42,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(218,'电影高清','S',3,5,2,3740,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(219,'中华美食','S',3,6,4,3780,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(220,'CCTV-1/2/7/10/11/音乐','S',3,2,7,3840,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(221,'CCTV-3/5/6/8/新闻/少儿/纪录','S',3,1,9,3880,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(222,'北京卫视、卡酷','S',3,11,12,3951,9.52,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(223,'CETV1','S',3,12,15,4000,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(224,'中数传媒S14付费频道','S',3,14,14,3980,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(225,'中数传媒S16付费频道','S',3,15,16,4020,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(226,'中数传媒S18付费频道','S',3,16,18,4060,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(227,'中数传媒S20付费频道','S',3,17,20,4100,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(228,'中数传媒S22付费频道','S',3,18,22,4140,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(229,'C100路广播','S',3,19,24,4175,18,'1/2','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(230,'广东、广西、深圳','S',3,7,13,3950,11.407,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(231,'中数传媒S11付费频道','S',3,13,11,3920,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(232,'辽宁卫视','S',3,10,13,3961,3.572,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(233,'全国EPG同步信号','S',3,41,5,3816.1,3.167,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(234,'长城亚洲平台','S',3,3,25,12537.5,41.25,'1/2','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(235,'K4教育台','S',3,20,27,12395,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(236,'K8教育台','S',3,21,30,12550,19.11,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(237,'K5境外平台','S',3,22,28,12435,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(238,'K7境外平台','S',3,23,29,12515,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(239,'K9境外平台','S',3,24,31,12595,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(240,'KU波段100路广播','S',3,25,26,12269.5,18,'1/2','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(241,'K11境外平台','S',3,26,32,12675,27.5,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(242,'村村通3A','S',3,48,45,11840,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(243,'村村通4A','S',3,33,46,11880,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(244,'村村通5A','S',3,34,47,11920,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(245,'村村通6A','S',3,35,48,11960,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(246,'村村通6B','S',3,36,49,11980,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(247,'村村通7B','S',3,37,50,12020,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(248,'村村通8B','S',3,38,51,12060,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(249,'村村通9B','S',3,39,52,12100,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(250,'村村通10B','S',3,40,53,12140,28.8,'3/4','2011-01-01',getdate(),'人工加载');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(251,'数字电影流动放映服务平台','S',3,42,56,12631,26.5,'3/4','2011-01-01',getdate(),'人工加载');

set identity_insert TB_ETMS_BASE_OPERATION off