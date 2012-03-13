USE [tjetms-bes]
GO
--********************************************************************
--����ʱ����ͳ��
--********************************************************************
DROP procedure SP_ETMS_TIME_REP_MON	
GO

create procedure SP_ETMS_TIME_REP_MON	
@ch_mon		varchar(6),
@ch_db_msg	nvarchar(60)	output
as
declare	
@mon_start_time datetime,
@mon_end_time datetime

BEGIN
  select  @mon_start_time = convert(datetime,@ch_mon+'01',112)
  select  @mon_end_time = dateadd(month,1,convert(datetime,@ch_mon+'01',112))
	begin transaction

--����ʱ����ͳ�� 

	--�ֹ�¼��ļ�¼����뵽������²���ͳ���У�����ֻ��ɾ���Զ����ɵ�����
	delete from tb_etms_rept_op_time
	where rept_time = @ch_mon

	if @@error <> 0
	begin
		select @ch_db_msg = 'error'
		rollback transaction
		raiserror('There was an error when deleting',16,1)
		return
	end
--��������ʱ��
	insert	into tb_etms_rept_op_time(op_id,rept_time,trans_type,trans_mode,broad_time,upd_date,emp_name)
	select	b.id,@ch_mon,b.trans_type,b.trans_mode,datediff(minute,@mon_start_time,@mon_end_time),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date <= @mon_start_time
	  and isnull(a.end_date,convert(datetime,'29991231',112)) >= @mon_end_time
  union all  --�������н�����
	select	b.id,@ch_mon,b.trans_type,b.trans_mode,datediff(minute,@mon_start_time,a.end_date),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.end_date > @mon_start_time
	  and a.end_date < @mon_end_time
	  and a.start_date <= @mon_start_time
  union all --�������п�ʼ��
	select	b.id,@ch_mon,b.trans_type,b.trans_mode,datediff(minute,a.start_date,@mon_end_time),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date > @mon_start_time
	  and a.start_date < @mon_end_time
	  and isnull(a.end_date,convert(datetime,'29991231',112)) >= @mon_end_time
  union all --�������п�ʼ��������
	select	b.id,@ch_mon,b.trans_type,b.trans_mode,datediff(minute,a.start_date,a.end_date),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date > @mon_start_time
	  and a.end_date < @mon_end_time
	
	if @@error <> 0
	begin
		select @ch_db_msg = 'error'
		rollback transaction
		raiserror('There was an error when insert',16,1)
		return
	end

	select @ch_db_msg = 'Sucess'

	commit transaction

	return

END
GO

--********************************************************************
--��Ҫ��֤�ڴ���ʱ��ͳ��
--********************************************************************
DROP procedure SP_ETMS_TIME_REP_IMPT
GO

create procedure SP_ETMS_TIME_REP_IMPT	
@impt_seq_no		bigint,
@ch_db_msg	nvarchar(60)	output
as
declare	
@mon_start_time datetime,
@mon_end_time datetime

BEGIN

	select @mon_start_time = start_date,@mon_end_time = end_date
	  from tb_etms_rept_impt_period
	 where id = @impt_seq_no
	 
	begin transaction

--����ʱ����ͳ�� 

	delete from tb_etms_rept_op_time
	where rept_time = @impt_seq_no

	if @@error <> 0
	begin
		select @ch_db_msg = 'error'
		rollback transaction
		raiserror('There was an error when deleting',16,1)
		return
	end
--��������֤��ʱ��
	insert	into tb_etms_rept_op_time(op_id,rept_time,trans_type,trans_mode,broad_time,upd_date,emp_name)
	select	b.id,@impt_seq_no,b.trans_type,b.trans_mode,datediff(minute,@mon_start_time,@mon_end_time),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date <= @mon_start_time
	  and isnull(a.end_date,convert(datetime,'29991231',112)) >= @mon_end_time
  union all  --���㱣֤���н�����
	select	b.id,@impt_seq_no,b.trans_type,b.trans_mode,datediff(minute,@mon_start_time,a.end_date),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.end_date > @mon_start_time
	  and a.end_date < @mon_end_time
	  and a.start_date <= @mon_start_time
  union all --���㱣֤���п�ʼ��
	select	b.id,@impt_seq_no,b.trans_type,b.trans_mode,datediff(minute,a.start_date,@mon_end_time),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date > @mon_start_time
	  and a.start_date < @mon_end_time
	  and isnull(a.end_date,convert(datetime,'29991231',112)) >= @mon_end_time
  union all --���㱣֤���п�ʼ��������
	select	b.id,@impt_seq_no,b.trans_type,b.trans_mode,datediff(minute,a.start_date,a.end_date),
	        getdate(),'�洢����'
	from tb_etms_base_schedule a,tb_etms_base_operation b
	where a.op_id = b.id
	  and a.start_date > @mon_start_time
	  and a.end_date < @mon_end_time
	
	if @@error <> 0
	begin
		select @ch_db_msg = 'error'
		rollback transaction
		raiserror('There was an error when insert',16,1)
		return
	end

	select @ch_db_msg = 'Sucess'
  commit transaction
	return

END
GO

--********************************************************************
--Ϊ̨վ�����±����嵥
--********************************************************************
drop procedure SP_ETMS_CREATE_REPT_DTL
go

create procedure SP_ETMS_CREATE_REPT_DTL
as
declare @ch_db_error nvarchar(30)  
BEGIN

  begin transaction

  delete from tb_etms_rept_dtl 
   where rept_time = convert(varchar(6),getdate(),112)


  if @@error <> 0
  begin
    select @ch_db_error = 'error'
    rollback transaction
    raiserror('There was an error',16,1)
    return
  end
  
  insert into tb_etms_rept_dtl(rept_id,rept_time,upd_date,emp_name)
  select rept_id,convert(varchar(6),getdate(),112),getdate(),'CREATE_REPT_DTL'
    from tb_etms_rept_def
   where data_source not in ('EXAM','IMPT')
     and if_using = 'Y'

  if @@error <> 0
  begin
    select @ch_db_error = 'error'
    rollback transaction
    raiserror('There was an error',16,1)
    return
  end

  select @ch_db_error = 'OK'
  commit transaction

  return
  
END
go

--********************************************************************
--Ϊ̨վ������֤�ڱ����嵥 �� ɾ����֤�ڱ����嵥
--********************************************************************
drop procedure SP_ETMS_CREATE_REPT_DTL_IMPT
go

create procedure SP_ETMS_CREATE_REPT_DTL_IMPT
@impt_period_seq  int,
@process_flag    char,   -- ���ӱ�֤��ΪA,ɾ����֤��ΪD
@ch_db_error    nvarchar(30)  output,
@ch_db_msg    nvarchar(60)  output
as  

BEGIN

  begin transaction

    delete from tb_etms_rept_dtl
     where rept_time = cast(@impt_period_seq as nvarchar(6))
       and rept_id in (select rept_id from tb_etms_rept_def where data_source = 'IMPT')
    if @@error <> 0
    begin
      select @ch_db_error = 'error'
      select @ch_db_msg = 'error'
      rollback transaction
      raiserror('There was an error',16,1)
      return
    end

  if(@process_flag = 'A')
  begin
    insert into tb_etms_rept_dtl(rept_id,rept_time,upd_date,emp_name)
    select rept_id,@impt_period_seq,getdate(),'CREATE_REPT_DTL_IMPT'
      from tb_etms_rept_def
     where data_source = 'IMPT'
       and if_using = 'Y'

    if @@error <> 0
    begin
      select @ch_db_error = 'error'
      select @ch_db_msg = 'error'
      rollback transaction
      raiserror('There was an error',16,1)
      return
    end
  end

  select @ch_db_error = 'OK'
  select @ch_db_msg = ''
  commit transaction

  return
  
END
go


--********************************************************************
--����ͳ�Ʊ�������
--********************************************************************
drop function f_etms_percentage
go

create function f_etms_percentage 
(@start_time		datetime,   --��¼�Ŀ�ʼʱ��
@end_time		datetime,     --��¼�Ľ���ʱ��
@cal_start  datetime,     --ͳ�ƵĿ�ʼʱ��
@cal_end    datetime)      --ͳ�ƵĿ�ʼʱ��
returns numeric(5,4)
BEGIN
  declare @percentage numeric(5,4)
  
  select @percentage = 0
  
	if @start_time <= @cal_start and @cal_end <= @end_time
	begin 
      select @percentage= datediff(second,@cal_start,@cal_end)*1.0/datediff(second,@start_time,@end_time)
	end
	
	if @cal_start <= @start_time and @end_time <= @cal_end
	begin 
      select @percentage= 1.0
	end
	
	if (@cal_start between @start_time and @end_time) and @end_time < @cal_end
	begin
	   select @percentage= datediff(second,@cal_start,@end_time)*1.0/datediff(second,@start_time,@end_time)
  end
  
	if (@cal_end between @start_time and @end_time) and @cal_start < @start_time
	begin
	  select @percentage=  datediff(second,@start_time,@cal_end)*1.0/datediff(second,@start_time,@end_time)
  end
  
  return @percentage
END
GO


drop procedure restoreEndedProcInstanceToTaskNode_proc
go
create procedure restoreEndedProcInstanceToTaskNode_proc
	    @processInstanceId numeric,
	    @backToTaskNode nvarchar(200) -- Ҫ���˵�������������(TaskNode������, ����task)
as

declare @hasError int, @tmpSQL nvarchar(1500)
set @hasError=0
set @tmpSQL = ' declare @pd numeric, 
			@taskNodeId numeric, 
			@taskId numeric

		--print @processInstanceId
		--print @backToTaskNode
		select @pd=pi.PROCESSDEFINITION_ from JBPM_PROCESSINSTANCE pi where pi.ID_=@processInstanceId
		--print @pd
		select @taskNodeId=n.ID_ from JBPM_NODE n where n.PROCESSDEFINITION_=@pd and n.NAME_=@backToTaskNode
		--print @taskNodeId
		select @taskId=t.ID_ from JBPM_TASK t where t.PROCESSDEFINITION_=@pd and t.TASKNODE_=@taskNodeId
		--print @taskId

		BEGIN TRANSACTION
		-- ��������ʵ��
		update JBPM_PROCESSINSTANCE set END_=null where ID_=@processInstanceId
		if @@error=0 
		BEGIN
			-- ��������
			update JBPM_TASKINSTANCE set END_=null, ISOPEN_=1, ISSIGNALLING_=1 where PROCINST_=@processInstanceId and TASK_=@taskId and create_ = (select max(create_) from JBPM_TASKINSTANCE where PROCINST_=@processInstanceId and TASK_=@taskId)
			if @@error=0 
			BEGIN
				-- ��������
				update JBPM_TOKEN set NODE_=@taskNodeId, END_=null where PROCESSINSTANCE_=@processInstanceId
				if @@error=0 
				BEGIN
					COMMIT TRAN
				END
				ELSE
				BEGIN
					set @hasError=1
					ROLLBACK TRAN
				END
			END
			ELSE
			BEGIN
				set @hasError=1
				ROLLBACK TRAN
			END
		END
		ELSE
		BEGIN
			set @hasError=1
			rollback tran
			
		END
		'
--print @tmpSQL

exec sp_executesql @tmpSQL, N'@processInstanceId numeric, @backToTaskNode nvarchar(200), @hasError int output', @processInstanceId, @backToTaskNode, @hasError output

if @hasError<>0
begin
	RAISERROR('�ָ�����ʵ��:%d, ��״̬:%s ʧ��.', 16, 1 , @processInstanceId, @backToTaskNode)
	return 1
end
else
	return 0
GO
--********************************************************************
--�����Ű�ʱ�亯��
--********************************************************************
drop function f_etms_dutyStaffStr
GO
CREATE   function  f_etms_dutyStaffStr (@ID   bigint)
  returns   varchar(8000)
  AS
  begin
      declare   @ret   varchar(8000)
      set   @ret   =   ''
      select   @ret   =   @ret+'��'+rtrim(EMP_NAME)   from   TB_ETMS_DUTY_STAFF_ON_DUTY   where   (DU_M_ID =@ID or DU_W_ID=@ID)
      set   @ret   =   case   when   len(@ret)>0   then   stuff(@ret,1,1,'')   else   @ret   end
      return   @ret
  end