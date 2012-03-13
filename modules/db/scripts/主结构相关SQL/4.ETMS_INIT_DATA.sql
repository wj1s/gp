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
*/--�������Ͷ���
insert into tb_etms_base_para(para_type,para_desc,define)
values('TRTP','��������','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(1,'TRTP','P','����ƽ̨',2,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(2,'TRTP','T','��ĿԴ����',3,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(3,'TRTP','S','��������',1,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--ҵ���䷽ʽ����
insert into tb_etms_base_para(para_type,para_desc,define)
values('TRMD','ҵ���䷽ʽ','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(4,'TRMD','TRAN','�⴫������',1,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(5,'TRMD','DAMI','����΢��',2,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(6,'TRMD','MIDF','��Ƶ����',3,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(77,'TRMD','ASI','ASI',3,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--�豸��������
insert into tb_etms_base_para(para_type,para_desc,define)
values('EQTP','�豸����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(7,'EQTP','000','HPA',null,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(8,'EQTP','001','UC',null,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(9,'EQTP','002','��˻�',null,'2010-10-1','����Ա');

insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(10,'EQTP','003','�л�����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--��������
insert into tb_etms_base_para(para_type,para_desc,define)
values('GPTP','��������','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(11,'GPTP','1','ֵ��',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(12,'GPTP','2','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(13,'GPTP','3','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(14,'GPTP','4','����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--���ű��
insert into tb_etms_base_para(para_type,para_desc,define)
values('DEPT','���ű��','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(15,'DEPT','40','���ǻ���',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(16,'DEPT','41','ƽ̨����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(17,'DEPT','01','̨�쵼',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(18,'DEPT','02','̨����',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(19,'DEPT','03','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(20,'DEPT','04','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(21,'DEPT','05','����',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(22,'DEPT','06','��������Ա�����',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(23,'DEPT','07','�������Ŀ�',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(24,'DEPT','08','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(25,'DEPT','09','���¿�',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(26,'DEPT','10','����֧/��֧��',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(27,'DEPT','11','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(28,'DEPT','12','ά�޿�/ά����',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(29,'DEPT','13','���߿�',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(30,'DEPT','14','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(31,'DEPT','15','���վ',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(32,'DEPT','16','������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(33,'DEPT','17','��Ϣ��С��',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(34,'DEPT','50','�ڴ�����',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(35,'DEPT','51','΢������',NULL,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(36,'DEPT','52','�������',NULL,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--��������
insert into tb_etms_base_para(para_type,para_desc,define)
values('DPTP','��������','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(37,'DPTP','MATA','ά�޲���',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(38,'DPTP','PSDP','����վ',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(39,'DPTP','SERV','��������',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(40,'DPTP','TKMG','��������',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(41,'DPTP','TKST','����֧��',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(42,'DPTP','SATE','���ǻ���',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(43,'DPTP','PLAT','ƽ̨����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--̨վ����
insert into tb_etms_base_para(para_type,para_desc,define)
values('STTP','̨վ����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(44,'STTP','CGST','�ۺ���̨վ',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--DTTP ̨վ����
insert into tb_etms_base_para(para_type,para_desc,define)
values('DTTP','̨վ����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(45,'DTTP','01','����ֱ��',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--WNTP �澯����
insert into tb_etms_base_para(para_type,para_desc,define)
values('WNTP','�澯����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(46,'WNTP','01','��ѹ�澯',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(47,'WNTP','02','�¶ȸ澯',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(48,'WNTP','03','ʪ�ȸ澯',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--PGTP ��Ŀ����
insert into tb_etms_base_para(para_type,para_desc,define)
values('PGTP','��Ŀ����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(49,'PGTP','TV','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(50,'PGTP','BD','�㲥',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(51,'PGTP','DT','����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--����/ɢ������

--�豸�ͺ�

--��������ABTP
insert into tb_etms_base_para(para_type,para_desc,define)
values('ABTP','��������','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(52,'ABTP','WEATH','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(53,'ABTP','DISTB','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(54,'ABTP','EQUIP','�豸��',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--�¹����ʶ���
insert into tb_etms_base_para(para_type,para_desc,define)
values('ADDU','�¹�����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(55,'ADDU','02','��Ϊ����','I','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(56,'ADDU','03','������','I','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(57,'ADDU','01','ά������','I','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(58,'ADDU','05','�����¹�','I','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(59,'ADDU','17','��·����','O','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(60,'ADDU','18','����ԭ��','O','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(61,'ADDU','12','���ԭ��','O','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(62,'ADDU','19','��ĿԴ�ж�','O','2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(63,'ADDU','16','����','O','2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--TUTP ���䵥λ����  
insert into tb_etms_base_para(para_type,para_desc,define)
values('TUTP','̨վ����','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(64,'TUTP','MBST','�в�����ӵ�Ƶ����̨',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(65,'TUTP','MSBC','�ж̲���������',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(66,'TUTP','MSBS','�̲����ж̲�����̨',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off


--��������̨վ���
insert into tb_etms_base_para(para_type,para_desc,define)
values('DBBD','��������̨վ','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(67,'DBBD','32','542',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(68,'DBBD','75','���е���վ',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(83,'DBBD','OT','����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--��������ԭ�����
insert into tb_etms_base_para(para_type,para_desc,define)
values('DBRN','��������ԭ��','N');

set identity_insert tb_etms_base_para_dtl on
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(69,'DBRN','DBWEAT','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(70,'DBRN','DBEQUP','�豸����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(71,'DBRN','DBPOWE','����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(72,'DBRN','DBPROG','��ĿԴ',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(73,'DBRN','DBOPDD','ҵ�����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(74,'DBRN','DBEXAM','�ƻ�����',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(75,'DBRN','DBDIST','������',null,'2010-10-1','����Ա');
insert into tb_etms_base_para_dtl(id,para_type,para_code,code_desc,sortby,upd_date,emp_name)
values(76,'DBRN','DBOTHE','����',null,'2010-10-1','����Ա');
set identity_insert tb_etms_base_para_dtl off

--����վ����
INSERT INTO tb_etms_base_STATION(ID,STATION_CODE,STATION_NAME,S_CODE,REGION_CODE,LONGITUDE,LATITUDE,STATION_ADDR,
ALTITUDE,ACREAGE,BUILD_DATE,START_DATE,RMKS,STATION_TYPE,STATION_BREV,GEO_INFOR,DISTRICT_TYPE,LOC
,TRANSMIT_UNIT_TYPE)
VALUES(1,'40','��������վ','BES','110100',null,null,null,
0.00,1000.00,'2006-4-30','2006-4-30',null,44,null,null,45,null,65);

--���ڶ���
set identity_insert tb_etms_base_tache on
insert into tb_etms_base_tache(id,ta_name,trans_type)
values(1,'��Դϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(2,'���븴��ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(3,'�ź����ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(4,'����ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(5,'CAϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(6,'�ڲ�����ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(7,'�ⲿ����ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(8,'���й���ϵͳ',1);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(9,'��ĿԴ����',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(10,'������·',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(11,'�źŴ���',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(12,'�����豸',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(13,'����ϵͳ',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(14,'�ڲ�����ϵͳ',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(15,'�ⲿ����ϵͳ',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(16,'���й���ϵͳ',2);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(17,'��ĿԴ����',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(18,'����',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(19,'�ϱ�Ƶ',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(20,'�߹���',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(21,'������',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(22,'����ϵͳ',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(23,'�ռ���·',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(24,'�ڲ�����ϵͳ',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(25,'�ⲿ����ϵͳ',3);

insert into tb_etms_base_tache(id,ta_name,trans_type)
values(26,'���й���ϵͳ',3);
set identity_insert tb_etms_base_tache off

--����ϵͳ����
set identity_insert tb_etms_base_techsystem on
insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(1,'C1','C1ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(2,'C2','C2ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(3,'C3','C3ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(4,'C4','C4ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(5,'C5','C5ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(6,'C6','C6ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(7,'C7','C7ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(8,'C8','C8ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(9,'C9','C9ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(10,'CA','CAϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(11,'CB','CBϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(12,'Ku1','Ku1ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(13,'Ku2','Ku2ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(14,'Ku3','Ku3ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(15,'Ku4','Ku4ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(16,'Ku5','Ku5ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(17,'Ku6','Ku6ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(18,'Ku7','Ku7ϵͳ',3);

insert into tb_etms_base_techsystem(id,tech_code,tech_name,trans_type)
values(19,'DBS','DBSϵͳ',3);
set identity_insert tb_etms_base_techsystem off

--������
insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MFAIL','�豸���й����±���','ACCD','�ڴ���','Y','�豸���й����±���.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MACCD','ͣ���¹��±���','ACCD','�ڴ���','Y','ͣ���¹��±���.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('MTRAN','��Ŀ�����±���','ACCD','�ڴ���','Y','��Ŀ�����±���.raq','cal_month');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('IFAIL','��֤���豸���й����±���','IMPT','�ڴ���','Y','��֤���豸���й��ϱ���.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('IACCD','��֤��ͣ���¹��±���','IMPT','�ڴ���','Y','��֤��ͣ���¹ʱ���.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('ITRAN','��֤�ڽ�Ŀ�����±���','IMPT','�ڴ���','Y','��֤�ڽ�Ŀ���䱨��.raq','impt_period_seq');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('DBBD','���������������','EXAM',null,'Y','���������������.raq','broad_by_flag,start_time,end_time,trans_type_db');

insert into tb_etms_rept_def(rept_id,rept_name,data_source,rept_dept,if_using,
       model_name,model_input)
values('CSQK','�������ͳ�Ʊ�','EXAM',null,'Y','�������ͳ�Ʊ�.raq','start_time,end_time,trans_type');

--��ɫ����
insert into tb_sec_role(id,role_name,ROLE_DESC)
values('1','WORKER','ֵ��Ա');
insert into tb_sec_role(id,role_name,role_desc)
values('10','TEKOFFICER','����');
insert into tb_sec_role(id,role_name,role_desc)
values('11','USER','��ͨ�û�');
insert into tb_sec_role(id,role_name,role_desc)
values('12','GUEST','�ÿ�');
insert into tb_sec_role(id,role_name,role_desc)
values('2','ANONYMOUS','�����û�');
insert into tb_sec_role(id,role_name,role_desc)
values('3','ADMIN','����Ա');
insert into tb_sec_role(id,role_name,role_desc)
values('4','WATCH','ֵ�೤');
insert into tb_sec_role(id,role_name,role_desc)
values('5','DIRECTOR','��������');
insert into tb_sec_role(id,role_name,role_desc)
values('7','OFFICER','��������');
insert into tb_sec_role(id,role_name,role_desc)
values('8','GOVERNOR','��λ����');

--���Ŷ���
set identity_insert TB_ETMS_BASE_DEPT on
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(1,'4017','��Ϣ���칫��','41',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(2,'4040','���ǻ���','42',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(3,'4041','ƽ̨����','43',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(4,'4015','��������','38',1,NULL);
insert into TB_ETMS_BASE_DEPT(id,dept_code,dept_name,dept_type,st_id,ad_id)
values(5,'4008','������','40',1,NULL);
set identity_insert TB_ETMS_BASE_DEPT off

--������ҵ�����͵Ķ�Ӧ��ϵ
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

--ְλ����
set identity_insert TB_ETMS_BASE_POST on
insert into tb_etms_base_post(id,post,post_name,sort)
values(1,'004A','����',5);
insert into tb_etms_base_post(id,post,post_name,sort)
values(2,'004B','������',6);
insert into tb_etms_base_post(id,post,post_name,sort)
values(3,'083Q','�ܹ���ʦ',3);
insert into tb_etms_base_post(id,post,post_name,sort)
values(4,'083R','���ܹ���ʦ',4);
insert into tb_etms_base_post(id,post,post_name,sort)
values(5,'133Q','���',80);
insert into tb_etms_base_post(id,post,post_name,sort)
values(6,'220A','�Ƴ�',20);
insert into tb_etms_base_post(id,post,post_name,sort)
values(7,'220B','���Ƴ�',21);
insert into tb_etms_base_post(id,post,post_name,sort)
values(8,'221S','���ο�Ա',22);
insert into tb_etms_base_post(id,post,post_name,sort)
values(9,'221T','�����ο�Ա',23);
insert into tb_etms_base_post(id,post,post_name,sort)
values(10,'404A','̨��',30);
insert into tb_etms_base_post(id,post,post_name,sort)
values(11,'404B','��̨��',31);
insert into tb_etms_base_post(id,post,post_name,sort)
values(12,'438A','վ��',1);
insert into tb_etms_base_post(id,post,post_name,sort)
values(13,'438B','��վ��',2);
insert into tb_etms_base_post(id,post,post_name,sort)
values(14,'819A','�೤',7);
insert into tb_etms_base_post(id,post,post_name,sort)
values(15,'819B','���೤',8);
insert into tb_etms_base_post(id,post,post_name,sort)
values(16,'819C','ֵ��Ա',10);
insert into tb_etms_base_post(id,post,post_name,sort)
values(17,'819D','����Ա',12);
insert into tb_etms_base_post(id,post,post_name,sort)
values(18,'819E','ά��Ա',13);
insert into tb_etms_base_post(id,post,post_name,sort)
values(19,'9988','����',98);
insert into tb_etms_base_post(id,post,post_name,sort)
values(20,'9999','����',99);
set identity_insert TB_ETMS_BASE_POST off

--����Ա����
set identity_insert TB_ETMS_BASE_PERSON on
insert into TB_ETMS_BASE_PERSON(id,dp_id,emp_code,login_name,login_password,emp_name,post,is_enable)
values(1,2,'0','admin','admin','����Ա',1,1);
set identity_insert TB_ETMS_BASE_PERSON off

--����ԱȨ��
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

--����Ա��ɫ
insert into tb_sec_pri_ass(role_id,emp_id)
values(3,1);

--��ʼ����������
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('ʮ�ռ�', '1', 1, 10, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('�¼�', '1', 1, 30, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('����', '1', 1, 90, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('�����', '1', 1, 180, NULL, NULL)
INSERT INTO [dbo].[TB_ETMS_REPAIR_PERIOD]([NAME], [TYPE], [PREVIOUS_VALUE], [VALUE], [START_DAY], [END_DAY])
VALUES('���', '1', 1, 360, NULL, NULL)



--���Ƕ���
set identity_insert TB_ETMS_BASE_SATELLITE on
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(1,'����6A','92.2��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(2,'����6B','115��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(3,'��̫V��','138��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(4,'��̫6��','134��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(5,'����10��','125��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(6,'����9��','92.2��E',getdate(),'�˹�����');
insert into TB_ETMS_BASE_SATELLITE(ID,STL_NAME,STL_ORBIT,UPD_DATE,EMP_NAME)
values(7,'����3S','105.5��E',getdate(),'�˹�����');
set identity_insert TB_ETMS_BASE_SATELLITE off

--ת��������
set identity_insert TB_ETMS_BASE_TRANSFER on
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(1,2,'S1ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(2,2,'S2ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(3,2,'S3ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(4,2,'S4ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(5,2,'S5ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(6,2,'S6ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(7,2,'S7ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(8,2,'S8ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(9,2,'S9ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(10,2,'S10ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(11,2,'S11ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(12,2,'S12ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(13,2,'S13ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(14,2,'S14ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(15,2,'S15ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(16,2,'S16ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(17,2,'S17ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(18,2,'S18ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(19,2,'S19ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(20,2,'S20ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(21,2,'S21ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(22,2,'S22ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(23,2,'S23ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(24,2,'S24ת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(25,3,'5Bת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(26,4,'K1ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(27,4,'K4ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(28,4,'K5ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(29,4,'K7ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(30,4,'K8ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(31,4,'K9ת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(32,4,'K11ת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(33,1,'1Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(34,1,'2Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(35,1,'3Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(36,1,'4Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(37,1,'5Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(38,1,'6Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(39,1,'7Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(40,1,'8Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(41,1,'9Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(42,1,'10Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(43,1,'11Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(44,1,'12Aת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(45,6,'3Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(46,6,'4Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(47,6,'5Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(48,6,'6Aת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(49,6,'6Bת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(50,6,'7Bת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(51,6,'8Bת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(52,6,'9Bת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(53,6,'10Bת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(54,5,'9Cת����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(55,5,'21Cת����',getdate(),'�˹�����');

insert into TB_ETMS_BASE_TRANSFER(id,stl_id,sk_name,upd_date,emp_name)
values(56,7,'K7Hת����',getdate(),'�˹�����');

set identity_insert TB_ETMS_BASE_TRANSFER off

--·�ɶ���
set identity_insert TB_ETMS_BASE_ROUTE on
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(1,'������','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(2,'�й���������̨','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(3,'��������̨','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(4,'�������̨','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(5,'�й�Ӱ�����ǹ�˾','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(6,'�й�����','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(7,'�ڴ�����','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(8,'������Է','��������վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(9,'��ӰƵ��','��������վ',getdate(),'�˹�����');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(10,'������','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(11,'�й���������̨','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(12,'�������̨','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(13,'�й�Ӱ�����ǹ�˾','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(14,'�й�����','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(15,'�ڴ�����','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(16,'������Է','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(17,'��ӰƵ��','542����վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(18,'��Ӱ���ֽ�Ŀ��������','542����վ',getdate(),'�˹�����');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(19,'������','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(20,'��������̨','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(21,'�������̨','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(22,'�й�Ӱ�����ǹ�˾','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(23,'�й�����','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(24,'������Է','���ͺ��ص���վ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(25,'��������վ','���ͺ��ص���վ',getdate(),'�˹�����');

insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(26,'����̨','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(27,'��������̨','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(28,'�������̨','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(29,'�й�Ӱ�����ǹ�˾','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(30,'�й�����','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(31,'������Է','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(32,'�й���������̨','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(33,'�ڴ�����','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(34,'��ӰƵ��','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(35,'��������վ','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(36,'�軪����','������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_ROUTE(id,from_pl,to_pl,upd_date,emp_name)
values(37,'CMMB','������',getdate(),'�˹�����');
set identity_insert TB_ETMS_BASE_ROUTE off

--��Ŀ������
set identity_insert TB_ETMS_BASE_CAWAVE on
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(1,'CCTV-3/5/6/8/����/�ٶ�/��¼',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(2,'CCTV-1/2/7/10/11/12/����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(3,'��������ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(4,'�������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(5,'��Ӱ����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(6,'�л���ʳ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(7,'�㶫/����/����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(8,'����/���ϸ���',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(9,'����������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(10,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(11,'��������/����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(12,'CETV-1',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(13,'������ýS11����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(14,'������ýS14����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(15,'������ýS16����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(16,'������ýS18����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(17,'������ýS20����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(18,'������ýS22����Ƶ��',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(19,'C����100·�㲥',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(20,'K4����̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(21,'K8����̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(22,'K5����ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(23,'K7����ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(24,'K9����ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(25,'KU����100·�㲥',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(26,'K11����ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(27,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(28,'�㶫/�Ϸ�/��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(29,'����/�ӱ�����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(30,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(31,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(32,'CMMB�ƶ��㲥����',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(33,'���ͨ4A��Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(34,'5A��������Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(35,'6A��������Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(36,'6B��������Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(37,'7B��������Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(38,'8B��������Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(39,'���ͨ9B��Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(40,'���ͨ10B��Ŀ',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(41,'ȫ��EPGͬ���ź�',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(42,'���ֵ�Ӱ������ӳ����ƽ̨',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(43,'����������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(44,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(45,'�½���������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(47,'�½����½��߳�ȥ',getdate(),'�˹�����');
--��Ϊ���Ǵ���ҵ�����вżӵ� ���ڴ��ṩ��û�У�����������������������id =10�ģ�id=46�ĸĸ�����������
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(46,'��������',getdate(),'�˹�����');
insert into TB_ETMS_BASE_CAWAVE(id,cawave_name,upd_date,emp_name)
values(48,'���ͨ3A��Ŀ',getdate(),'�˹�����');
set identity_insert TB_ETMS_BASE_CAWAVE off

--��Ŀ����   Ŀǰ��֪����Ŀ���룬��Ŀ�������ý�ĿID
set identity_insert TB_ETMS_BASE_PROGRAM on
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(1,1,'CCTV1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(2,2,'BTV-1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(3,3,'CCTV-10',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(4,4,'CCTV-12',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(5,5,'CCTV-2',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(6,6,'CCTV-7',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(7,7,'CCTV-�ٶ�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(8,8,'CCTV-����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(9,9,'CETV-1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(10,10,'TJTV-1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(11,11,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(12,12,'�����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(13,13,'��������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(14,14,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(15,15,'�㶫����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(16,16,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(17,17,'��������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(18,18,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(19,19,'����1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(20,20,'����10',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(21,21,'����11',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(22,22,'����12',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(23,23,'����13',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(24,24,'����14',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(25,25,'����15',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(26,26,'����16',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(27,27,'����17',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(28,28,'����18',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(29,29,'����19',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(30,30,'����2',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(31,31,'����20',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(32,32,'����21',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(33,33,'����22',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(34,34,'����23',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(35,35,'����24',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(36,36,'����25',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(37,37,'����26',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(38,38,'����27',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(39,39,'����28',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(40,40,'����29',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(41,41,'����3',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(42,42,'����30',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(43,43,'����31',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(44,44,'����32',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(45,45,'����33',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(46,46,'����34',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(47,47,'����35',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(48,48,'����36',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(49,49,'����37',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(50,50,'����38',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(51,51,'����39',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(52,52,'����4',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(53,53,'����40',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(54,54,'����41',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(55,55,'����42',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(56,56,'����43',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(57,57,'����44',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(58,58,'����45',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(59,59,'����46',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(60,60,'����47',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(61,61,'����48��Ԥ����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(62,62,'����49��Ԥ����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(63,63,'����5',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(64,64,'����50',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(65,65,'����6',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(66,66,'����7',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(67,67,'����8',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(68,68,'����9',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(69,69,'����㲥',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(70,70,'�ӱ�1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(71,71,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(72,72,'������1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(73,73,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(74,74,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(75,75,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(76,76,'���ֳ���',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(77,77,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(78,78,'����֮��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(79,79,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(80,80,'��������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(81,81,'�ɿ���㲥',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(82,82,'����֮��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(83,83,'���ɺ���',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(84,84,'��������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(85,85,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(86,86,'�ຣ1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(87,87,'�ຣ����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(88,88,'ɽ��1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(89,89,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(90,90,'����ũ��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(91,91,'��������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(92,92,'�Ĵ�1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(93,93,'�Ĵ����Ͳ���',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(94,94,'ά�ﾭ������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(95,95,'ά������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(96,96,'ά���ۺ�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(97,97,'ά������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(98,98,'���ز���',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(99,99,'���غ���',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(100,100,'�½�1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(101,101,'�½�����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(102,102,'�½�����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(103,103,'�½���������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(104,104,'�½���������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(105,105,'�½��ٶ�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(106,106,'�½�ά��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(107,107,'����֮��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(108,108,'Ԥ��1',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(109,109,'Ԥ��2',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(110,110,'Ԥ��3',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(111,111,'Ԥ��4',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(112,112,'Ԥ��5',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(113,113,'Ԥ��6',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(114,114,'Ԥ��7',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(115,115,'Ԥ��8',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(116,116,'Ԥ��9',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(117,117,'Ԥ��10',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(118,118,'Ԥ��11',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(119,119,'Ԥ��12',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(120,120,'����1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(121,121,'�㽭1��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(122,122,'�а�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(123,123,'�ж�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(124,124,'�й�֮��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(125,125,'�о�',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(126,126,'����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(127,127,'������',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(128,128,'����˫',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(129,129,'����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(130,130,'��ʮ',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(132,132,'��ʮ��',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(131,131,'��ʮһ',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(133,133,'����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(134,134,'����',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(135,135,'��һ',49,getdate(),'�˹�����');
insert into TB_ETMS_BASE_PROGRAM(id,program_code,program_name,program_type,upd_date,emp_name)
values(136,136,'����1��',49,getdate(),'�˹�����');

set identity_insert TB_ETMS_BASE_PROGRAM off

--��Ŀ����ƽ̨ҵ��
set identity_insert TB_ETMS_BASE_OPERATION on
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(1,'�㶫����','P',1,7,15,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(2,'��������','P',1,7,17,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(3,'��������','P',1,7,91,'2011-01-01',getdate(),'�˹�����');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(4,'��������','P',1,40,13,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(5,'����1��','P',1,40,14,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(6,'����1��','P',1,40,16,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(7,'����1��','P',1,40,18,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(8,'����1��','P',1,40,71,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(9,'����1��','P',1,40,73,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(10,'����1��','P',1,40,74,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(11,'����1��','P',1,40,77,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(12,'��������','P',1,40,80,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(13,'����1��','P',1,40,85,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(14,'�ຣ1��','P',1,40,86,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(15,'�ຣ����','P',1,40,87,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(16,'�Ĵ�1��','P',1,40,92,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(17,'�½���������','P',1,40,103,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(18,'�½��ٶ�','P',1,40,105,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(19,'����1��','P',1,40,136,'2011-01-01',getdate(),'�˹�����');


insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(20,'BTV-1','P',1,33,2,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(21,'CCTV1','P',1,33,1,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(22,'CCTV-10','P',1,33,3,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(23,'CCTV-12','P',1,33,4,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(24,'CCTV-2','P',1,33,5,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(25,'CCTV-7','P',1,33,6,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(26,'CCTV-�ٶ�','P',1,33,7,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(27,'CCTV-����','P',1,33,8,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(28,'CETV-1','P',1,33,9,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(29,'TJTV-1','P',1,33,10,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(30,'����㲥','P',1,33,69,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(31,'����֮��','P',1,33,78,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(32,'�ɿ���㲥','P',1,33,81,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(33,'����֮��','P',1,33,82,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(34,'����1��','P',1,33,89,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(35,'�Ĵ����Ͳ���','P',1,33,93,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(36,'ά������','P',1,33,95,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(37,'ά���ۺ�','P',1,33,96,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(38,'���ز���','P',1,33,98,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(39,'���غ���','P',1,33,99,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(40,'�½�����','P',1,33,101,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(41,'�½���������','P',1,33,104,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(42,'����֮��','P',1,33,107,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(43,'����1��','P',1,33,120,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(44,'�й�֮��','P',1,33,124,'2011-01-01',getdate(),'�˹�����');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(45,'����1��','P',1,39,11,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(46,'�ӱ�1��','P',1,39,70,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(47,'������1��','P',1,39,72,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(48,'����1��','P',1,39,75,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(49,'���ֳ���','P',1,39,76,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(50,'����1��','P',1,39,79,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(51,'���ɺ���','P',1,39,83,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(52,'��������','P',1,39,84,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(53,'ɽ��1��','P',1,39,88,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(54,'����ũ��','P',1,39,90,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(55,'ά�ﾭ������','P',1,39,94,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(56,'ά������','P',1,39,97,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(57,'�½�1��','P',1,39,100,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(58,'�½�����','P',1,39,102,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(59,'�½�ά��','P',1,39,106,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(60,'�㽭1��','P',1,39,121,'2011-01-01',getdate(),'�˹�����');

insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(61,'�����','P',1,19,12,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(62,'����1','P',1,19,19,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(63,'����10','P',1,19,20,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(64,'����11','P',1,19,21,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(65,'����12','P',1,19,22,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(66,'����13','P',1,19,23,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(67,'����14','P',1,19,24,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(68,'����15','P',1,19,25,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(69,'����16','P',1,19,26,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(70,'����17','P',1,19,27,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(71,'����18','P',1,19,28,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(72,'����19','P',1,19,29,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(73,'����2','P',1,19,30,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(74,'����20','P',1,19,31,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(75,'����21','P',1,19,32,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(76,'����22','P',1,19,33,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(77,'����23','P',1,19,34,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(78,'����24','P',1,19,35,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(79,'����25','P',1,19,36,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(80,'����26','P',1,19,37,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(81,'����27','P',1,19,38,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(82,'����28','P',1,19,39,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(83,'����29','P',1,19,40,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(84,'����3','P',1,19,41,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(85,'����30','P',1,19,42,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(86,'����31','P',1,19,43,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(87,'����32','P',1,19,44,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(88,'����33','P',1,19,45,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(89,'����34','P',1,19,46,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(90,'����35','P',1,19,47,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(91,'����36','P',1,19,48,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(92,'����37','P',1,19,49,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(93,'����38','P',1,19,50,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(94,'����39','P',1,19,51,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(95,'����4','P',1,19,52,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(96,'����40','P',1,19,53,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(97,'����41','P',1,19,54,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(98,'����42','P',1,19,55,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(99,'����43','P',1,19,56,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(100,'����44','P',1,19,57,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(101,'����45','P',1,19,58,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(102,'����46','P',1,19,59,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(103,'����47','P',1,19,60,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(104,'����48��Ԥ����','P',1,19,61,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(105,'����49��Ԥ����','P',1,19,62,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(106,'����5','P',1,19,63,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(107,'����50','P',1,19,64,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(108,'����6','P',1,19,65,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(109,'����7','P',1,19,66,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(110,'����8','P',1,19,67,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(111,'����9','P',1,19,68,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(112,'Ԥ��','P',1,19,108,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(113,'Ԥ��','P',1,19,109,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(114,'Ԥ��','P',1,19,110,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(115,'Ԥ��','P',1,19,111,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(116,'Ԥ��','P',1,19,112,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(117,'Ԥ��','P',1,19,113,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(118,'�а�','P',1,19,122,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(119,'�ж�','P',1,19,123,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(120,'�о�','P',1,19,125,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(121,'����','P',1,19,126,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(122,'������','P',1,19,127,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(123,'����˫','P',1,19,128,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(124,'����','P',1,19,129,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(125,'��ʮ','P',1,19,130,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(126,'��ʮ��','P',1,19,131,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(127,'��ʮһ','P',1,19,132,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(128,'����','P',1,19,133,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(129,'����','P',1,19,134,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(130,'��һ','P',1,19,135,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(131,'�����','P',1,25,12,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(132,'����1','P',1,25,19,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(133,'����10','P',1,25,20,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(134,'����11','P',1,25,21,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(135,'����12','P',1,25,22,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(136,'����13','P',1,25,23,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(137,'����14','P',1,25,24,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(138,'����15','P',1,25,25,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(139,'����16','P',1,25,26,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(140,'����17','P',1,25,27,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(141,'����18','P',1,25,28,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(142,'����19','P',1,25,29,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(143,'����2','P',1,25,30,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(144,'����20','P',1,25,31,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(145,'����21','P',1,25,32,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(146,'����22','P',1,25,33,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(147,'����23','P',1,25,34,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(148,'����24','P',1,25,35,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(149,'����25','P',1,25,36,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(150,'����26','P',1,25,37,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(151,'����27','P',1,25,38,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(152,'����28','P',1,25,39,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(153,'����29','P',1,25,40,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(154,'����3','P',1,25,41,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(155,'����30','P',1,25,42,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(156,'����31','P',1,25,43,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(157,'����32','P',1,25,44,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(158,'����33','P',1,25,45,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(159,'����34','P',1,25,46,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(160,'����35','P',1,25,47,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(161,'����36','P',1,25,48,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(162,'����37','P',1,25,49,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(163,'����38','P',1,25,50,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(164,'����39','P',1,25,51,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(165,'����4','P',1,25,52,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(166,'����40','P',1,25,53,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(167,'����41','P',1,25,54,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(168,'����42','P',1,25,55,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(169,'����43','P',1,25,56,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(170,'����44','P',1,25,57,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(171,'����45','P',1,25,58,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(172,'����46','P',1,25,59,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(173,'����47','P',1,25,60,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(174,'����48��Ԥ����','P',1,25,61,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(175,'����49��Ԥ����','P',1,25,62,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(176,'����5','P',1,25,63,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(177,'����50','P',1,25,64,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(178,'����6','P',1,25,65,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(179,'����7','P',1,25,66,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(180,'����8','P',1,25,67,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(181,'����9','P',1,25,68,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(182,'Ԥ��','P',1,25,114,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(183,'Ԥ��','P',1,25,115,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(184,'Ԥ��','P',1,25,116,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(185,'Ԥ��','P',1,25,117,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(186,'Ԥ��','P',1,25,118,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(187,'Ԥ��','P',1,25,119,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(188,'�а�','P',1,25,122,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(189,'�ж�','P',1,25,123,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(190,'�о�','P',1,25,125,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(191,'����','P',1,25,126,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(192,'������','P',1,25,127,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(193,'����˫','P',1,25,128,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(194,'����','P',1,25,129,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(195,'��ʮ','P',1,25,130,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(196,'��ʮ��','P',1,25,131,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(197,'��ʮһ','P',1,25,132,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(198,'����','P',1,25,133,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(199,'����','P',1,25,134,'2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_p_id,program_id,
start_date,upd_date,emp_name)
values(200,'��һ','P',1,25,135,'2011-01-01',getdate(),'�˹�����');

set identity_insert TB_ETMS_BASE_OPERATION off

--���Ǵ���ҵ��
set identity_insert TB_ETMS_BASE_OPERATION on
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(201,'����/���ϸ���','S',3,8,34,3760,30.6,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(202,'�㶫�Ϸ���������','S',3,28,36,3845,17.778,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(203,'��������','S',3,27,37,3884,5.72,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(204,'CMMB�ƶ��㲥����','S',3,32,37,3866,5.425,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(205,'�����ӱ�����','S',3,29,38,3909,8.934,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(206,'��������','S',3,30,38,3922,7.25,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(207,'��������','S',3,31,38,3933,6.59,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(208,'����������','S',3,9,39,3951,13.4,'7/8','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(209,'����������','S',3,43,37,3893,6.88,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(210,'��������','S',3,44,40,3989,9.07,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(211,'�½���������','S',3,45,40,3999,4.42,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(212,'��������','S',3,10,40,4006,4.42,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(213,'�½����½��߳�ȥ','S',3,47,43,4120,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(214,'CCT-V1/2/7/10/11/����','S',3,2,42,4080,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(215,'CCTV-3/5/6/8/����/�ٶ�/��¼','S',3,1,44,4160,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(216,'�������','S',3,4,1,3730,10.72,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(217,'��������','S',3,46,1,3706,4.42,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(218,'��Ӱ����','S',3,5,2,3740,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(219,'�л���ʳ','S',3,6,4,3780,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(220,'CCTV-1/2/7/10/11/����','S',3,2,7,3840,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(221,'CCTV-3/5/6/8/����/�ٶ�/��¼','S',3,1,9,3880,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(222,'�������ӡ�����','S',3,11,12,3951,9.52,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(223,'CETV1','S',3,12,15,4000,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(224,'������ýS14����Ƶ��','S',3,14,14,3980,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(225,'������ýS16����Ƶ��','S',3,15,16,4020,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(226,'������ýS18����Ƶ��','S',3,16,18,4060,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(227,'������ýS20����Ƶ��','S',3,17,20,4100,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(228,'������ýS22����Ƶ��','S',3,18,22,4140,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(229,'C100·�㲥','S',3,19,24,4175,18,'1/2','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(230,'�㶫������������','S',3,7,13,3950,11.407,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(231,'������ýS11����Ƶ��','S',3,13,11,3920,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(232,'��������','S',3,10,13,3961,3.572,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(233,'ȫ��EPGͬ���ź�','S',3,41,5,3816.1,3.167,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(234,'��������ƽ̨','S',3,3,25,12537.5,41.25,'1/2','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(235,'K4����̨','S',3,20,27,12395,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(236,'K8����̨','S',3,21,30,12550,19.11,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(237,'K5����ƽ̨','S',3,22,28,12435,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(238,'K7����ƽ̨','S',3,23,29,12515,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(239,'K9����ƽ̨','S',3,24,31,12595,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(240,'KU����100·�㲥','S',3,25,26,12269.5,18,'1/2','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(241,'K11����ƽ̨','S',3,26,32,12675,27.5,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(242,'���ͨ3A','S',3,48,45,11840,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(243,'���ͨ4A','S',3,33,46,11880,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(244,'���ͨ5A','S',3,34,47,11920,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(245,'���ͨ6A','S',3,35,48,11960,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(246,'���ͨ6B','S',3,36,49,11980,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(247,'���ͨ7B','S',3,37,50,12020,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(248,'���ͨ8B','S',3,38,51,12060,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(249,'���ͨ9B','S',3,39,52,12100,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(250,'���ͨ10B','S',3,40,53,12140,28.8,'3/4','2011-01-01',getdate(),'�˹�����');
insert into TB_ETMS_BASE_OPERATION(id,op_name,operation_type,trans_type,cawave_s_id,sk_id,
cawave_frq,symble_rate,channel_rate,start_date,upd_date,emp_name)
values(251,'���ֵ�Ӱ������ӳ����ƽ̨','S',3,42,56,12631,26.5,'3/4','2011-01-01',getdate(),'�˹�����');

set identity_insert TB_ETMS_BASE_OPERATION off