delete from tb_sec_dept_per
GO
delete from tb_sec_pri_ass
GO
delete from tb_sec_role
GO
delete from tb_etms_base_person
GO
delete from tb_etms_base_dept
GO
delete from tb_etms_base_station
GO
delete from tb_etms_base_para_dtl
GO
delete from TB_ETMS_BASE_DP_TRTP
GO
--标识种子置0--
DBCC CHECKIDENT (tb_etms_base_para_dtl , RESEED , 1) 
GO
DBCC CHECKIDENT (tb_etms_base_station , RESEED , 1) 
GO
DBCC CHECKIDENT (tb_etms_base_dept , RESEED , 1)
GO
DBCC CHECKIDENT (tb_etms_base_person , RESEED , 1)
GO
DBCC CHECKIDENT (tb_sec_role , RESEED , 1)
GO
DBCC CHECKIDENT (tb_sec_dept_per , RESEED , 1)
GO



--参数
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('TRTP', '集成平台', '管理员', 'P', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('TRTP', '节目源传输', '管理员', 'T', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('TRTP', '卫星上行', '管理员', 'S', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('DPTP', '平台机房', '管理员', 'P', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('GPTP', '值班', '管理员', 'zb', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('GPTP', '检修班 ', '管理员', 'j', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('STTP', '地球站', '管理员', 'TFCT', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('TUTP', '地球站', '管理员', 'abcc', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('DTTP', '节传处', '管理员', 'ccd', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('EQTP', '变频器', '管理员', '01', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('EQTP', '仪器仪表', '管理员', '02', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('PGTP', '对内节目', '管理员', '01', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('ABTP', '故障类型1', '管理员', '01', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('ADDU', '人为责任', '管理员', '01', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('WNTP', '卫星告警', '管理员', '01', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('ADDU', '外电', '管理员', '02', NULL, NULL)
GO
INSERT INTO [dbo].[tb_etms_base_para_dtl]([para_type], [code_desc], [emp_name], [para_code], [sortby], [upd_date])
VALUES('TRMD', '传输网', '管理员', '01', NULL, NULL)
GO

--台站信息
INSERT INTO [dbo].[tb_etms_base_station]([s_code], [acreage], [station_addr], [altitude], [station_brev], [build_date], [station_code], [geo_infor], [latitude], [loc], [longitude], [station_name], [region_code], [rmks], [start_date], [district_type], [station_type], [transmit_unit_type])
VALUES('BES', 1000, NULL, 0, NULL, '20101010 00:00:00.0', '40', NULL, NULL, NULL, NULL, '北京地球站', '110100', NULL, '20101010 00:00:00.0', 9, 7, 8)
GO

--部门信息
INSERT INTO [dbo].[tb_etms_base_dept]([dept_code], [dept_name], [dept_type], [st_id])
VALUES('1102', '卫星机房', 4, 1)
GO

INSERT INTO [dbo].[TB_ETMS_BASE_DP_TRTP]([TRANS_TYPE], [DP_ID])
VALUES(3, 1)
GO

--人员信息
INSERT INTO [dbo].[tb_etms_base_person]([ad_id], [emp_code], [dorm], [email], [is_enable], [idty_card], [login_name], [login_password], [mobile], [emp_name], [office_password], [office_tel], [sex], [dp_id], [group_id], [post])
VALUES(NULL, 'BT001', NULL, NULL, '1', NULL, 'admin', 'admin', NULL, '管理员', NULL, NULL, NULL, 1, NULL, NULL)
GO
--角色信息
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('匿名用户', 'ANONYMOUS')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('普通用户', 'USER')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('值班长', 'WATCH')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('机房主任', 'DIRECTOR')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('技办', 'TEKOFFICER')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('技术主管', 'OFFICER')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('单位主管', 'GOVERNOR')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('管理员', 'ADMIN')
GO
INSERT INTO [dbo].[tb_sec_role]([role_desc], [role_name])
VALUES('访客', 'GUEST')
GO
--人员的角色信息
INSERT INTO [dbo].[tb_sec_pri_ass]([emp_id], [role_id])
VALUES(1, 3)
GO
INSERT INTO [dbo].[tb_sec_pri_ass]([emp_id], [role_id])
VALUES(1, 4)
GO
INSERT INTO [dbo].[tb_sec_pri_ass]([emp_id], [role_id])
VALUES(1, 5)
GO
INSERT INTO [dbo].[tb_sec_pri_ass]([emp_id], [role_id])
VALUES(1, 6)
GO
INSERT INTO [dbo].[tb_sec_pri_ass]([emp_id], [role_id])
VALUES(1, 7)
GO
--人员的部门权限信息
INSERT INTO [dbo].[tb_sec_dept_per]([fun_module], [dp_id], [emp_id])
VALUES('accd', 1, 1)
GO
INSERT INTO [dbo].[tb_sec_dept_per]([fun_module], [dp_id], [emp_id])
VALUES('duty', 1, 1)
GO

--设备
INSERT INTO [dbo].[tb_etms_base_tache]([ta_name], [trans_type])
VALUES('环节1', 3)
GO
INSERT INTO [dbo].[tb_etms_base_techsystem]([tech_code], [tech_name], [trans_type])
VALUES('01', '系统1', 3)
GO
INSERT INTO [dbo].[tb_etms_base_channel]([channel_name], [system_id])
VALUES('通路1', 1)
GO
INSERT INTO [dbo].[tb_etms_base_equip]([is_equip], [equip_code], [deal_time], [emp_name], [equip_flag], [equip_name], [upd_date], [dp_id], [equip_type], [ta_id], [system_id])
VALUES('E', '', '18991231 00:00:00.0', '王健', 1, '上变频器', '18991231 00:00:00.0', 1, 10, 1, 1)
GO
INSERT INTO [dbo].[tb_etms_base_channel_equip_ref]([equip_id], [channel_id])
VALUES(1, 1)
GO






