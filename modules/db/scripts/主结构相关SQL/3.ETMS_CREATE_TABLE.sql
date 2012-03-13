USE [tjetms-bes]
GO
/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2011-3-8 9:57:41                             */
/*==============================================================*/


if exists (select 1
            from  sysindexes
           where  id    = object_id('VI_ETMS_BASE_OPERATION_P')
            and   name  = 'IND_ETMS_BASE_OPERATION_P'
            and   indid > 0
            and   indid < 255)
   drop index VI_ETMS_BASE_OPERATION_P.IND_ETMS_BASE_OPERATION_P
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VI_ETMS_BASE_OPERATION_P')
            and   type = 'V')
   drop view VI_ETMS_BASE_OPERATION_P
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VI_ETMS_BASE_OPERATION_S')
            and   name  = 'IND_ETMS_BASE_OPERATION_S'
            and   indid > 0
            and   indid < 255)
   drop index VI_ETMS_BASE_OPERATION_S.IND_ETMS_BASE_OPERATION_S
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VI_ETMS_BASE_OPERATION_S')
            and   type = 'V')
   drop view VI_ETMS_BASE_OPERATION_S
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('VI_ETMS_BASE_OPERATION_T')
            and   name  = 'IND_ETMS_BASE_OPERATION_T'
            and   indid > 0
            and   indid < 255)
   drop index VI_ETMS_BASE_OPERATION_T.IND_ETMS_BASE_OPERATION_T
go

if exists (select 1
            from  sysobjects
           where  id = object_id('VI_ETMS_BASE_OPERATION_T')
            and   type = 'V')
   drop view VI_ETMS_BASE_OPERATION_T
go

/*==============================================================*/
/* Table: TB_ETMS_ACCD_ABNORMAL                                 */
/*==============================================================*/
create table TB_ETMS_ACCD_ABNORMAL (
   ID                   bigint               identity,
   ABNORMAL_TYPE        char(1)              not null,
   ABN_ID               bigint               null,
   TRANS_TYPE           bigint               not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   EQUIP_B_ID           bigint               null,
   EQUIP_F_ID           bigint               null,
   ABN_TYPE             varchar(10)          not null,
   ABN_DESC             varchar(512)         not null,
   ABN_REASON           varchar(512)         not null,
   PROCESS_STEP         varchar(512)         not null,
   ACCD_CODE            varchar(20)          null,
   ACCD_DESC            varchar(2048)        null,
   ACCD_REASON          varchar(2048)        null,
   PREV_WAY             varchar(2048)        null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_ACCD_ABNORMAL
   add constraint PK_ETMS_ACCD_ABNORMAL primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_ACCD_ABN_EQUIP                                */
/*==============================================================*/
create table TB_ETMS_ACCD_ABN_EQUIP (
   ID                   bigint               identity,
   ABN_O_ID             bigint               not null,
   SORTBY               int                  not null,
   EQUIP_ID             bigint               not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   ABN_B_ID             bigint               null
)
go

alter table TB_ETMS_ACCD_ABN_EQUIP
   add constraint PK_ETMS_ACCD_ABN_EQUIP primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_ACCD_ABN_OPERATION                            */
/*==============================================================*/
create table TB_ETMS_ACCD_ABN_OPERATION (
   ID                   bigint               identity,
   ABN_ID               bigint               not null,
   SORTBY               int                  not null,
   OP_ID                bigint               not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   ACCD_FLAG            char(1)              not null default 'N'
)
go

alter table TB_ETMS_ACCD_ABN_OPERATION
   add constraint PK_EMS_ABNORMAL_OPERATION primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_ACCD_DUTY_TIME                                */
/*==============================================================*/
create table TB_ETMS_ACCD_DUTY_TIME (
   ID                   bigint               identity,
   SORTBY               int                  not null,
   ABN_OP_ID            bigint               not null,
   DUTY_CODE            bigint               not null,
   DUTY_TIME            int                  not null
)
go

alter table TB_ETMS_ACCD_DUTY_TIME
   add constraint PK_ETMS_ACCD_DUTY_TIME primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_CAWAVE                                   */
/*==============================================================*/
create table TB_ETMS_BASE_CAWAVE (
   ID                   bigint               identity,
   CAWAVE_NAME          varchar(64)          not null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_CAWAVE
   add constraint PK_ETMS_BASE_CAWAVE primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_CAWAVE                                  */
/*==============================================================*/
create unique index IND_ETMS_BASE_CAWAVE on TB_ETMS_BASE_CAWAVE (
CAWAVE_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_CAWAVE_PG_REF                            */
/*==============================================================*/
create table TB_ETMS_BASE_CAWAVE_PG_REF (
   ID                   bigint               identity,
   CAWAVE_ID            bigint               not null,
   PROGRAM_ID           bigint               not null,
   START_DATE           datetime             null,
   END_DATE             datetime             null
)
go

alter table TB_ETMS_BASE_CAWAVE_PG_REF
   add constraint PK_ETMS_BASE_CAWAVE_PG_REF primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_CAWAVE_PG_REF                           */
/*==============================================================*/
create unique index IND_ETMS_BASE_CAWAVE_PG_REF on TB_ETMS_BASE_CAWAVE_PG_REF (
CAWAVE_ID ASC,
PROGRAM_ID ASC,
START_DATE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_CHANNEL                                  */
/*==============================================================*/
create table TB_ETMS_BASE_CHANNEL (
   ID                   bigint               identity,
   CHANNEL_NAME         varchar(20)          not null,
   SYSTEM_ID            bigint               not null
)
go

alter table TB_ETMS_BASE_CHANNEL
   add constraint PK_ETMS_BASE_CHANNEL primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_CHANNEL                                 */
/*==============================================================*/
create unique index IND_ETMS_BASE_CHANNEL on TB_ETMS_BASE_CHANNEL (
CHANNEL_NAME ASC,
SYSTEM_ID ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_CHANNEL_EQUIP_REF                        */
/*==============================================================*/
create table TB_ETMS_BASE_CHANNEL_EQUIP_REF (
   CHANNEL_ID           bigint               not null,
   EQUIP_ID             bigint               not null
)
go

alter table TB_ETMS_BASE_CHANNEL_EQUIP_REF
   add constraint PK_ETMS_BASE_CHANNEL_EQUIP_REF primary key (CHANNEL_ID, EQUIP_ID)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_DEPT                                     */
/*==============================================================*/
create table TB_ETMS_BASE_DEPT (
   ID                   bigint               identity,
   DEPT_CODE            char(4)              not null,
   DEPT_NAME            varchar(100)         not null,
   DEPT_TYPE            bigint               not null,
   ST_ID                bigint               not null,
   AD_ID                varchar(50)          null
)
go

alter table TB_ETMS_BASE_DEPT
   add constraint PK_ETMS_BASE_DEPT primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_DEPT_NAME                               */
/*==============================================================*/
create unique index IND_ETMS_BASE_DEPT_NAME on TB_ETMS_BASE_DEPT (
DEPT_NAME ASC
)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_DEPT_CODE                               */
/*==============================================================*/
create unique index IND_ETMS_BASE_DEPT_CODE on TB_ETMS_BASE_DEPT (
DEPT_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_DP_TRTP                                  */
/*==============================================================*/
create table TB_ETMS_BASE_DP_TRTP (
   DP_ID                bigint               not null,
   TRANS_TYPE           bigint               not null
)
go

alter table TB_ETMS_BASE_DP_TRTP
   add constraint PK_ETMS_BASE_DP_TRTP primary key (DP_ID, TRANS_TYPE)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_DUTY                                     */
/*==============================================================*/
create table TB_ETMS_BASE_DUTY (
   DUTY_CODE            varchar(4)           not null,
   DUTY_TYPE            varchar(4)           not null,
   DUTY_NAME            varchar(64)          not null
)
go

alter table TB_ETMS_BASE_DUTY
   add constraint PK_ETMS_BASE_DUTY primary key (DUTY_CODE)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_EQUIP                                    */
/*==============================================================*/
create table TB_ETMS_BASE_EQUIP (
   ID                   bigint               identity,
   IS_EQUIP             varchar(1)           null default 'E',
   TA_ID                bigint               not null,
   DP_ID                bigint               null,
   EQUIP_FLAG           tinyint              null,
   EQUIP_CODE           varchar(24)          null,
   EQUIP_NAME           varchar(50)          not null,
   EQUIP_MODEL          bigint               not null,
   EQUIP_SN             varchar(20)          null,
   DEAL_TIME            datetime             null,
   RUN_TIME             datetime             null,
   DESIGN_LIFE          int                  null,
   POSITION             varchar(4)           null,
   EQUIP_STATUS         varchar(4)           null,
   ORI_EQUIP_ID         bigint               null,
   PA_EQUIP_ID          bigint               null,
   PRODUCT_ADDR         varchar(20)          null,
   MA_NAME              varchar(20)          null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint PK_ETMS_BASE_EQUIP primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_EQUIP_CODE                              */
/*==============================================================*/
create index IND_ETMS_BASE_EQUIP_CODE on TB_ETMS_BASE_EQUIP (
EQUIP_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_EQUIP_MODEL                              */
/*==============================================================*/
create table TB_ETMS_BASE_EQUIP_MODEL (
   ID                   bigint               identity,
   EQUIP_MODEL          varchar(3)           not null,
   MODEL_NAME           varchar(64)          null,
   EQUIP_TYPE           bigint               not null,
   DEMAND               int                  null,
   SEQ_NO               bigint               null
)
go

alter table TB_ETMS_BASE_EQUIP_MODEL
   add constraint PK_ETMS_BASE_EQUIP_MODEL primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_EQUIP_MODEL                             */
/*==============================================================*/
create index IND_ETMS_BASE_EQUIP_MODEL on TB_ETMS_BASE_EQUIP_MODEL (
EQUIP_MODEL ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_GROUP                                    */
/*==============================================================*/
create table TB_ETMS_BASE_GROUP (
   ID                   bigint               identity,
   DP_ID                bigint               not null,
   GROUP_NAME           varchar(64)          not null,
   GROUP_TYPE           bigint               not null
)
go

alter table TB_ETMS_BASE_GROUP
   add constraint PK_ETMS_BASE_GROUP primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_GROUP                                   */
/*==============================================================*/
create unique index IND_ETMS_BASE_GROUP on TB_ETMS_BASE_GROUP (
DP_ID ASC,
GROUP_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_OPERATION                                */
/*==============================================================*/
create table TB_ETMS_BASE_OPERATION (
   ID                   bigint               identity,
   OPERATION_TYPE       char(1)              not null,
   OP_NAME              varchar(64)          not null,
   TRANS_TYPE           bigint               not null,
   TRANS_MODE           bigint               null,
   CAWAVE_T_ID          bigint               null,
   CAWAVE_S_ID          bigint               null,
   CAWAVE_P_ID          bigint               null,
   PROGRAM_ID           bigint               null,
   ROUTE_ID             bigint               null,
   SK_ID                bigint               null,
   CAWAVE_FRQ           decimal(6,1)         null,
   SYMBLE_RATE          decimal(6,3)         null,
   START_DATE           datetime             not null,
   END_DATE             datetime             null,
   CHANNEL_RATE         varchar(10)         null,
   RMKS                 varchar(128)         null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint PK_ETMS_BASE_OPERATION primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_PARA                                     */
/*==============================================================*/
create table TB_ETMS_BASE_PARA (
   PARA_TYPE            VARCHAR(4)           not null,
   PARA_DESC            VARCHAR(40)          null,
   DEFINE               CHAR(1)              null
)
go

alter table TB_ETMS_BASE_PARA
   add constraint PK_ETMS_BASE_PARA primary key nonclustered (PARA_TYPE)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_PARA_DTL                                 */
/*==============================================================*/
create table TB_ETMS_BASE_PARA_DTL (
   ID                   bigint               identity,
   PARA_TYPE            VARCHAR(4)           not null,
   PARA_CODE            VARCHAR(6)           not null,
   CODE_DESC            VARCHAR(40)          not null,
   SORTBY               VARCHAR(4)           null,
   UPD_DATE             DATETIME             null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_PARA_DTL
   add constraint PK_ETMS_BASE_PARA_DTL primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_PARA_DTL                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_PARA_DTL on TB_ETMS_BASE_PARA_DTL (
PARA_TYPE ASC,
PARA_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_PERSON                                   */
/*==============================================================*/
create table TB_ETMS_BASE_PERSON (
   ID                   bigint               identity,
   GROUP_ID             bigint               null,
   DP_ID                bigint               null,
   EMP_CODE             varchar(10)          null,
   LOGIN_NAME           varchar(60)          null,
   LOGIN_PASSWORD       varchar(60)          null,
   EMP_NAME             varchar(20)          null,
   POST                 bigint               null,
   SEX                  tinyint              null,
   MOBILE               varchar(20)          null,
   OFFICE_TEL           varchar(40)          null,
   OFFICE_PASSWORD      varchar(40)          null,
   EMAIL                varchar(100)         null,
   DORM                 varchar(100)         null,
   IDTY_CARD            varchar(18)          null,
   AD_ID                VARCHAR(50)          null,
   IS_ENABLE            VARCHAR(1)           null default '1'
)
go

alter table TB_ETMS_BASE_PERSON
   add constraint PK_ETMS_BASE_PERSON primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_PERSON_LOGIN                            */
/*==============================================================*/
create index IND_ETMS_BASE_PERSON_LOGIN on TB_ETMS_BASE_PERSON (
LOGIN_NAME ASC
)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_PERSON                                  */
/*==============================================================*/
create unique index IND_ETMS_BASE_PERSON on TB_ETMS_BASE_PERSON (
EMP_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_POST                                     */
/*==============================================================*/
create table TB_ETMS_BASE_POST (
   ID                   bigint               identity,
   POST                 nvarchar(4)          null,
   POST_NAME            nvarchar(20)         not null,
   SORT                 int                  not null
)
go

alter table TB_ETMS_BASE_POST
   add constraint PK_ETMS_BASE_POST primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_POST                                    */
/*==============================================================*/
create index IND_ETMS_BASE_POST on TB_ETMS_BASE_POST (
POST_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_PROGRAM                                  */
/*==============================================================*/
create table TB_ETMS_BASE_PROGRAM (
   ID                   bigint               identity,
   PROGRAM_CODE         varchar(20)          not null,
   PROGRAM_NAME         varchar(64)          not null,
   PROGRAM_TYPE         bigint               not null,
   UPD_DATE             datetime             null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_PROGRAM
   add constraint PK_ETMS_BASE_PROGRAM primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_PROGNAME                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_PROGNAME on TB_ETMS_BASE_PROGRAM (
PROGRAM_NAME ASC
)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_PROGCODE                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_PROGCODE on TB_ETMS_BASE_PROGRAM (
PROGRAM_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_ROUTE                                    */
/*==============================================================*/
create table TB_ETMS_BASE_ROUTE (
   ID                   bigint               identity,
   FROM_PL              varchar(20)          not null,
   TO_PL                varchar(20)          not null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_ROUTE
   add constraint PK_ETMS_BASE_ROUTE primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_ROUTE                                   */
/*==============================================================*/
create unique index IND_ETMS_BASE_ROUTE on TB_ETMS_BASE_ROUTE (
FROM_PL ASC,
TO_PL ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_SATELLITE                                */
/*==============================================================*/
create table TB_ETMS_BASE_SATELLITE (
   ID                   bigint               identity,
   STL_NAME             VARCHAR(20)          not null,
   STL_ORBIT            VARCHAR(64)          null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_SATELLITE
   add constraint PK_ETMS_BASE_SATELLITE primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_SATELLITE                               */
/*==============================================================*/
create unique index IND_ETMS_BASE_SATELLITE on TB_ETMS_BASE_SATELLITE (
STL_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_SCHEDULE                                 */
/*==============================================================*/
create table TB_ETMS_BASE_SCHEDULE (
   ID                   bigint               identity,
   OP_ID                bigint               not null,
   CHANNEL_ID           bigint               not null,
   START_DATE           datetime             not null,
   END_DATE             datetime             null
)
go

alter table TB_ETMS_BASE_SCHEDULE
   add constraint PK_ETMS_BASE_SCHEDULE primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_SCHEDULE                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_SCHEDULE on TB_ETMS_BASE_SCHEDULE (
OP_ID ASC,
CHANNEL_ID ASC,
START_DATE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_STATION                                  */
/*==============================================================*/
create table TB_ETMS_BASE_STATION (
   ID                   bigint               not null,
   STATION_CODE         char(2)              not null,
   STATION_NAME         varchar(100)         not null,
   S_CODE               varchar(20)          not null,
   REGION_CODE          varchar(6)           not null,
   LONGITUDE            varchar(20)          null,
   LATITUDE             varchar(20)          null,
   STATION_ADDR         varchar(100)         null,
   ALTITUDE             numeric(10,2)        null,
   ACREAGE              numeric(10,2)        null,
   BUILD_DATE           datetime             null,
   START_DATE           datetime             null,
   RMKS                 varchar(200)         null,
   STATION_TYPE         bigint               null,
   STATION_BREV         varchar(1024)        null,
   GEO_INFOR            VARCHAR(1024)        null,
   DISTRICT_TYPE        bigint               null,
   LOC                  CHAR(3)              null,
   TRANSMIT_UNIT_TYPE   bigint               null
)
go

alter table TB_ETMS_BASE_STATION
   add constraint PK_EMS_STATION primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IDX_EMS_STATION                                       */
/*==============================================================*/
create unique index IDX_EMS_STATION on TB_ETMS_BASE_STATION (
STATION_CODE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_TACHE                                    */
/*==============================================================*/
create table TB_ETMS_BASE_TACHE (
   ID                   bigint               identity,
   TA_NAME              varchar(20)          not null,
   TRANS_TYPE           bigint               not null
)
go

alter table TB_ETMS_BASE_TACHE
   add constraint PK_ETMS_BASE_TACHE primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_TACHE                                   */
/*==============================================================*/
create unique index IND_ETMS_BASE_TACHE on TB_ETMS_BASE_TACHE (
TA_NAME ASC,
TRANS_TYPE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_TECHSYSTEM                               */
/*==============================================================*/
create table TB_ETMS_BASE_TECHSYSTEM (
   ID                   bigint               identity,
   TECH_CODE            varchar(4)           not null,
   TECH_NAME            varchar(60)          null,
   TRANS_TYPE           bigint               null
)
go

alter table TB_ETMS_BASE_TECHSYSTEM
   add constraint PK_ETMS_BASE_TECHSYSTEM primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_TECHNAME                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_TECHNAME on TB_ETMS_BASE_TECHSYSTEM (
TECH_NAME ASC,
TRANS_TYPE ASC
)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_TECHSYSTEM                              */
/*==============================================================*/
create unique index IND_ETMS_BASE_TECHSYSTEM on TB_ETMS_BASE_TECHSYSTEM (
TECH_CODE ASC,
TRANS_TYPE ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_BASE_TRANSFER                                 */
/*==============================================================*/
create table TB_ETMS_BASE_TRANSFER (
   ID                   bigint               identity,
   STL_ID               bigint               not null,
   SK_NAME              VARCHAR(20)          not null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_BASE_TRANSFER
   add constraint PK_ETMS_BASE_TRANSFER primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_TRANSFER                                */
/*==============================================================*/
create unique index IND_ETMS_BASE_TRANSFER on TB_ETMS_BASE_TRANSFER (
STL_ID ASC,
SK_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_CHECK                                    */
/*==============================================================*/
create table TB_ETMS_DUTY_CHECK (
   ID                   bigint               identity,
   DU_ID                bigint               null,
   CONTENT              varchar(1024)        not null,
   EMP_NAME             varchar(20)          not null,
   START_TIME           datetime             not null
)
go

alter table TB_ETMS_DUTY_CHECK
   add constraint PK_ETMS_DUTY_CHECK primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_DUTY                                     */
/*==============================================================*/
create table TB_ETMS_DUTY_DUTY (
   ID                   bigint               identity,
   GROUP_ID             bigint               not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   DAY_PART_COUNT       int                  not null,
   SCH_NAME             varchar(20)          not null,
   FIRST                bit                  null,
   LAST                 bit                  null,
   ON_DUTY_RECORD       varchar(1024)        null,
   CONFIRM              bit                  null,
   OFF_DUTY_RECORD      varchar(1024)        null,
   WEEK                 varchar(10)          null,
   WEATHER              varchar(100)         null,
   TEMPERATURE          varchar(10)          null,
   UPD_DATE             datetime             null,
   EMP_NAME             varchar(20)          null,
   PRE_DUTY             bigint               null
)
go

alter table TB_ETMS_DUTY_DUTY
   add constraint PK_ETMS_DUTY_DUTY primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_PATROL_TIME                              */
/*==============================================================*/
create table TB_ETMS_DUTY_PATROL_TIME (
   ID                   bigint               identity,
   DDATE                datetime             not null,
   EMP_NAME             varchar(20)          not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   CONTENT              VARCHAR(512)         null
)
go

alter table TB_ETMS_DUTY_PATROL_TIME
   add constraint PK_ETMS_DUTY_PATROL_TIME primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_PROMPT                                   */
/*==============================================================*/
create table TB_ETMS_DUTY_PROMPT (
   ID                   bigint               identity,
   DU_ID                bigint               null,
   CONTENT              varchar(512)         null,
   DDATE                datetime             not null,
   EMP_NAME             varchar(20)          not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null
)
go

alter table TB_ETMS_DUTY_PROMPT
   add constraint PK_ETMS_DUTY_PROMPT primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_RECORD                                   */
/*==============================================================*/
create table TB_ETMS_DUTY_RECORD (
   ID                   bigint               identity,
   DU_ID                bigint               null,
   START_TIME           datetime             not null,
   CONTENT              varchar(1024)        null,
   REC_TYPE             char(1)              null,
   ABN_ID               bigint               null,
   BROAD_ID             bigint               null,
   WARN_ID              bigint               null,
   PA_ID                bigint               null
)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint PK_ETMS_DUTY_RECORD primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_RULE_ITEM                                */
/*==============================================================*/
create table TB_ETMS_DUTY_RULE_ITEM (
   ID                   bigint               identity,
   GROUP_ID             bigint               not null,
   SCH_ID               bigint               not null,
   PERIOD_DAY_NUM       int                  not null
)
go

alter table TB_ETMS_DUTY_RULE_ITEM
   add constraint PK_EMS_DUTY_RULE_ITEM primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_SCHEDULE                                 */
/*==============================================================*/
create table TB_ETMS_DUTY_SCHEDULE (
   ID                   bigint               identity,
   RU_ID                bigint               not null,
   SCH_NAME             varchar(20)          not null,
   SCH_ORDER            int                  not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null
)
go

alter table TB_ETMS_DUTY_SCHEDULE
   add constraint PK_ETMS_DUTY_SCHEDULE primary key (ID)
go

/*==============================================================*/
/* Index: IDX_ETMS_DUTY_SCHEDULE                                */
/*==============================================================*/
create unique index IDX_ETMS_DUTY_SCHEDULE on TB_ETMS_DUTY_SCHEDULE (
RU_ID ASC,
SCH_NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_SCHEDULE_RULE                            */
/*==============================================================*/
create table TB_ETMS_DUTY_SCHEDULE_RULE (
   ID                   bigint               identity,
   DP_ID                bigint               not null,
   RULE_NAME            varchar(50)          not null,
   DAY_PART_COUNT       int                  not null,
   RMKS                 varchar(50)          null
)
go

alter table TB_ETMS_DUTY_SCHEDULE_RULE
   add constraint PK_ETMS_DUTY_SCHEDULE_RULE primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_STAFF_ON_DUTY                            */
/*==============================================================*/
create table TB_ETMS_DUTY_STAFF_ON_DUTY (
   ID                   bigint               identity,
   EMP_NAME             varchar(20)          not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   DU_M_ID              bigint               null,
   DU_W_ID              bigint               null,
   MONITER              char(1)              not null
)
go

alter table TB_ETMS_DUTY_STAFF_ON_DUTY
   add constraint PK_ETMS_DUTY_STAFF_ON_DUTY primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_DUTY_WARNING                                  */
/*==============================================================*/
create table TB_ETMS_DUTY_WARNING (
   ID                   bigint               identity,
   OP_ID                bigint               null,
   WARN_TIME            datetime             null,
   WARN_TYPE            bigint               null,
   PROCESS              varchar(256)         null,
   ANALYSIS             varchar(512)         null
)
go

alter table TB_ETMS_DUTY_WARNING
   add constraint PK_ETMS_DUTY_WARNING primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_CARD                                   */
/*==============================================================*/
create table TB_ETMS_REPAIR_CARD (
   ID                   bigint               identity,
   ACTIVE               char(1)           not null,
   NAME                 varchar(100)         not null,
   CELL_ID              bigint               not null,
   PERIOD_ID            bigint               not null,
   EQUIP_TYPE           varchar(3)           null,
   SHUTDOWN_TIME        numeric(6,2)         null,
   PROCESS_TIME         numeric(6,2)         null,
   MEASURE              varchar(600)         null,
   TOOLS                varchar(600)         null,
   OTHER                varchar(600)         null,
   ATTENTION            varchar(600)         null,
   METHODS              varchar(4000)        null,
   TECHNICAL_STANDARDS  varchar(1024)        null,
   REMARK               varchar(200)         null
)
go

alter table TB_ETMS_REPAIR_CARD
   add constraint PK_ETMS_REPAIR_CARD primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_CYCLE                                  */
/*==============================================================*/
create table TB_ETMS_REPAIR_CYCLE (
   ID                   bigint               identity,
   ACTIVE               char(1)              not null,
   NAME                 varchar(100)         not null,
   DEPT_ID              bigint               not null
)
go

alter table TB_ETMS_REPAIR_CYCLE
   add constraint PK_ETMS_REPAIR_CYCLE primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_REPAIR_CYCLE                                 */
/*==============================================================*/
create unique index IND_ETMS_REPAIR_CYCLE on TB_ETMS_REPAIR_CYCLE (
NAME ASC,
DEPT_ID ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_CYCLE_CELL                             */
/*==============================================================*/
create table TB_ETMS_REPAIR_CYCLE_CELL (
   ID                   bigint               identity,
   NAME                 varchar(100)         not null,
   CYCLE_ID             bigint               not null
)
go

alter table TB_ETMS_REPAIR_CYCLE_CELL
   add constraint PK_ETMS_REPAIR_CYCLE_CELL primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_REPAIR_CYCLE_CELL                            */
/*==============================================================*/
create unique index IND_ETMS_REPAIR_CYCLE_CELL on TB_ETMS_REPAIR_CYCLE_CELL (
NAME ASC,
CYCLE_ID ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_PERIOD                                 */
/*==============================================================*/
create table TB_ETMS_REPAIR_PERIOD (
   ID                   bigint               identity,
   NAME                 varchar(100)         not null,
   TYPE                 char(1)              not null,
   PREVIOUS_VALUE      int                  not null,
   "VALUE"             int                  null,
   START_DAY            datetime             null,
   END_DAY              datetime             null
)
go

alter table TB_ETMS_REPAIR_PERIOD
   add constraint PK_ETMS_REPAIR_PERIOD primary key nonclustered (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_REPAIR_PERIOD                                */
/*==============================================================*/
create unique index IND_ETMS_REPAIR_PERIOD on TB_ETMS_REPAIR_PERIOD (
NAME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_RECORD                                 */
/*==============================================================*/
create table TB_ETMS_REPAIR_RECORD (
   ID                   bigint               identity,
   DDATE                datetime             not null,
   TIME_LENGTH          int                  not null,
   GROUP_ID             bigint               not null,
   DEPTMENT             varchar(100)         null,
   MEASURE              varchar(512)         null,
   EXAMINE_RECORD       varchar(1024)        null,
   EXAMINE_PERSONS      varchar(100)         null,
   SECURITY             varchar(40)          null,
   CHECKER              varchar(40)          null,
   TEST                 varchar(512)         null,
   PRINCIPAL            varchar(40)          null,
   UPD_DATE             datetime             not null,
   PERSON_NAME          varchar(20)          not null,
   DP_ID                bigint
)
go

alter table TB_ETMS_REPAIR_RECORD
   add constraint PK_ETMS_REPAIR_RECORD primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_RECORD_EQUIP                           */
/*==============================================================*/
create table TB_ETMS_REPAIR_RECORD_EQUIP (
   RECORD_ITEM_ID       bigint               not null,
   EQUIP_ID             bigint               not null
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_RECORD_ITEM                            */
/*==============================================================*/
create table TB_ETMS_REPAIR_RECORD_ITEM (
   ID                   bigint               identity,
   CONTENT              varchar(512)         null,
   CYCLE_NAME           varchar(100)         null,
   TYPE                 char(1)              null,
   CARD_ID              bigint               null,
   RECORD_ID            bigint               null
)
go

alter table TB_ETMS_REPAIR_RECORD_ITEM
   add constraint PK_ETMS_REPAIR_RECORD_ITEM primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPAIR_RECORD_PERSON                          */
/*==============================================================*/
create table TB_ETMS_REPAIR_RECORD_PERSON (
   RECORD_ITEM_ID       bigint               not null,
   PERSON_ID            bigint               not null
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPT_BROAD_BY_TIME                            */
/*==============================================================*/
create table TB_ETMS_REPT_BROAD_BY_TIME (
   ID                   bigint               identity,
   OP_ID                bigint               not null,
   BROAD_REASON         bigint               not null,
   AUTO_FLAG            char(1)              not null,
   NOTIFY_PERSON        varchar(64)          not null,
   NOTIFIED             varchar(64)          not null,
   EMP_NAME             varchar(20)          not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             null,
   BROAD_BY_FLAG        char(1)              not null,
   STATION              bigint               not null,
   BROAD_RESULT         varchar(512)         null,
   RMKS                 varchar(512)         null,
   OTHER_REASON       varchar(64)            NULL,
   DEL_FLAG             char(1)              not null default '0'
)
go

alter table TB_ETMS_REPT_BROAD_BY_TIME
   add constraint PK_TB_ETMS_REPT_BROAD_BY_TIME primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_REPT_BROAD_BY_TIME                           */
/*==============================================================*/
create unique index IND_ETMS_REPT_BROAD_BY_TIME on TB_ETMS_REPT_BROAD_BY_TIME (
OP_ID ASC,
START_TIME ASC,
STATION ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_REPT_DEF                                      */
/*==============================================================*/
create table TB_ETMS_REPT_DEF (
   REPT_ID              varchar(5)           not null,
   REPT_NAME            varchar(60)          not null,
   DATA_SOURCE          varchar(4)           not null,
   REPT_DEPT            varchar(60)          null,
   IF_USING             char(1)              not null,
   MODEL_NAME           varchar(60)          not null,
   MODEL_INPUT          varchar(128)         null
)
go

alter table TB_ETMS_REPT_DEF
   add constraint PK_EMS_REPT_DEF primary key (REPT_ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPT_DTL                                      */
/*==============================================================*/
create table TB_ETMS_REPT_DTL (
   ID                   bigint               identity,
   REPT_ID              varchar(5)           not null,
   REPT_TIME            varchar(6)           not null,
   TEKOFFICER           varchar(20)          null,
   OFFICER              varchar(20)          null,
   GOVERNOR             varchar(20)          null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_REPT_DTL
   add constraint PK_ETMS_REPT_DTL primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPT_IMPT_PERIOD                              */
/*==============================================================*/
create table TB_ETMS_REPT_IMPT_PERIOD (
   ID                   bigint               not null,
   IMPT_PERIOD_NAME     varchar(40)          not null,
   YEAR                 char(4)              not null,
   START_DATE           datetime             not null,
   END_DATE             datetime             not null,
   SUBMIT_DATE          datetime             not null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_REPT_IMPT_PERIOD
   add constraint PK_ETMS_REPT_IMPT_PERIOD primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_REPT_OP_TIME                                  */
/*==============================================================*/
create table TB_ETMS_REPT_OP_TIME (
   ID                   bigint               identity,
   OP_ID                bigint               not null,
   REPT_TIME            varchar(6)           not null,
   TRANS_TYPE           bigint               not null,
   TRANS_MODE           bigint               null,
   BROAD_TIME           numeric(12,2)        not null,
   UPD_DATE             datetime             not null,
   EMP_NAME             varchar(20)          not null
)
go

alter table TB_ETMS_REPT_OP_TIME
   add constraint PK_ETMS_REPT_OP_TIME primary key (ID)
go

/*==============================================================*/
/* Index: IND_ETMS_REPT_OP_TIME                                 */
/*==============================================================*/
create unique index IND_ETMS_REPT_OP_TIME on TB_ETMS_REPT_OP_TIME (
OP_ID ASC,
REPT_TIME ASC
)
go

/*==============================================================*/
/* Table: TB_ETMS_STAY_DETAIL                                   */
/*==============================================================*/
create table TB_ETMS_STAY_DETAIL (
   ID                   bigint               identity,
   DP_ID                bigint               not null,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null
)
go

alter table TB_ETMS_STAY_DETAIL
   add constraint PK_ETMS_STAY_DETAIL primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_STAY_DETAIL_PERSON                            */
/*==============================================================*/

GO
CREATE TABLE [dbo].[TB_ETMS_STAY_DETAIL_PERSON](
	[DETAIL_ID] [bigint] NOT NULL,
	[EMP_NAME] [varchar](20) NOT NULL,
 CONSTRAINT [PK_ETMS_STAY_DETAIL_PERSON] PRIMARY KEY CLUSTERED 
(
	[DETAIL_ID] ASC,
	[EMP_NAME] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[TB_ETMS_STAY_DETAIL_PERSON]  WITH CHECK ADD  CONSTRAINT [FK_STAYDETPER_REF_STAYDET] FOREIGN KEY([DETAIL_ID])
REFERENCES [dbo].[TB_ETMS_STAY_DETAIL] ([ID])
GO
ALTER TABLE [dbo].[TB_ETMS_STAY_DETAIL_PERSON] CHECK CONSTRAINT [FK_STAYDETPER_REF_STAYDET]

/*==============================================================*/
/* Table: TB_ETMS_STAY_PERIOD                                   */
/*==============================================================*/
create table TB_ETMS_STAY_PERIOD (
   ID                   bigint               identity,
   RULE_ID              bigint               not null,
   PERIOD_INDEX         int                  not null
)
go

alter table TB_ETMS_STAY_PERIOD
   add constraint PK_ETMS_STAY_PERIOD primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_STAY_RULE                                     */
/*==============================================================*/
create table TB_ETMS_STAY_RULE (
   ID                   bigint               identity,
   DP_ID                bigint               not null,
   PERIOD_COUNT         int                  not null
)
go

alter table TB_ETMS_STAY_RULE
   add constraint PK_ETMS_STAY_RULE primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_STAY_SECTION                                  */
/*==============================================================*/
create table TB_ETMS_STAY_SECTION (
   ID                   bigint               identity,
   START_TIME           datetime             not null,
   END_TIME             datetime             not null,
   PERIOD_ID            bigint               not null
)
go

alter table TB_ETMS_STAY_SECTION
   add constraint PK_ETMS_STAY_SECTION primary key (ID)
go

/*==============================================================*/
/* Table: TB_ETMS_STAY_SECTION_PERSON                           */
/*==============================================================*/

CREATE TABLE [dbo].[TB_ETMS_STAY_SECTION_PERSON](
	[SECTION_ID] [bigint] NOT NULL,
	[EMP_NAME] [varchar](20) NOT NULL,
 CONSTRAINT [PK_ETMS_STAY_SECTION_PERSON] PRIMARY KEY CLUSTERED 
(
	[SECTION_ID] ASC,
	[EMP_NAME] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[TB_ETMS_STAY_SECTION_PERSON]  WITH CHECK ADD  CONSTRAINT [FK_STAYSECPER_REF_STAYSEC] FOREIGN KEY([SECTION_ID])
REFERENCES [dbo].[TB_ETMS_STAY_SECTION] ([ID])
GO
ALTER TABLE [dbo].[TB_ETMS_STAY_SECTION_PERSON] CHECK CONSTRAINT [FK_STAYSECPER_REF_STAYSEC]

/*==============================================================*/
/* Table: TB_SEC_DEPT_PER                                       */
/*==============================================================*/
create table TB_SEC_DEPT_PER (
   ID                   bigint               identity,
   EMP_ID               bigint               not null,
   DP_ID                bigint               not null,
   FUN_MODULE           nvarchar(50)         not null
)
go

alter table TB_SEC_DEPT_PER
   add constraint PK_TB_SEC_DEPT_PER primary key (ID)
go

/*==============================================================*/
/* Table: TB_SEC_PERMISSION                                     */
/*==============================================================*/
create table TB_SEC_PERMISSION (
   ID                   varchar(50)          not null,
   PER_NAME             varchar(50)          null,
   DECS                 varchar(50)          null,
   POSITION             int                  null
)
go

alter table TB_SEC_PERMISSION
   add constraint PK_PERMISSION primary key nonclustered (ID)
go

/*==============================================================*/
/* Table: TB_SEC_PER_ASS                                        */
/*==============================================================*/
create table TB_SEC_PER_ASS (
   ROLE_ID              varchar(50)          null,
   PER_ID               varchar(50)          null
)
go

/*==============================================================*/
/* Table: TB_SEC_POPEDOM_VIEW                                   */
/*==============================================================*/
create table TB_SEC_POPEDOM_VIEW (
   POPE_ID              VARCHAR(50)          not null,
   PA_POPE_ID           VARCHAR(50)          null,
   POPE_NAME            VARCHAR(50)          null,
   DECS                 VARCHAR(50)          null,
   POSITION             int                  null
)
go

alter table TB_SEC_POPEDOM_VIEW
   add constraint PK_SEC_POPEDOM_VIEW primary key nonclustered (POPE_ID)
go

/*==============================================================*/
/* Table: TB_SEC_POP_ASS                                        */
/*==============================================================*/
create table TB_SEC_POP_ASS (
   ROLE_ID              VARCHAR(50)          null,
   POPE_ID              VARCHAR(50)          null
)
go

/*==============================================================*/
/* Table: TB_SEC_PRI_ASS                                        */
/*==============================================================*/
create table TB_SEC_PRI_ASS (
   ROLE_ID              varchar(50)          null,
   EMP_ID               bigint               null
)
go

/*==============================================================*/
/* Table: TB_SEC_ROLE                                           */
/*==============================================================*/
create table TB_SEC_ROLE (
   ID                   varchar(50)          not null,
   ROLE_NAME            varchar(50)          null,
   ROLE_DESC            varchar(50)          null
)
go

alter table TB_SEC_ROLE
   add constraint PK_ROLE primary key nonclustered (ID)
go

/*==============================================================*/
/* View: VI_ETMS_BASE_OPERATION_P                               */
/*==============================================================*/
create view VI_ETMS_BASE_OPERATION_P with schemabinding as
select  [ID]    ,[OP_NAME]
      ,[OPERATION_TYPE]
      ,[TRANS_TYPE]
      ,[CAWAVE_P_ID]
      ,[PROGRAM_ID]
      ,[START_DATE]
      ,[END_DATE]
      ,[RMKS]
      ,[UPD_DATE]
      ,[EMP_NAME]
  FROM [dbo].[TB_ETMS_BASE_OPERATION]
where CAWAVE_P_ID is not null and PROGRAM_ID is not null
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_OPERATION_P                             */
/*==============================================================*/
create unique clustered index IND_ETMS_BASE_OPERATION_P on VI_ETMS_BASE_OPERATION_P (
CAWAVE_P_ID ASC,
PROGRAM_ID ASC,
START_DATE ASC
)
go

/*==============================================================*/
/* View: VI_ETMS_BASE_OPERATION_S                               */
/*==============================================================*/
create view VI_ETMS_BASE_OPERATION_S with schemabinding as
select  [ID]    ,[OP_NAME]
      ,[OPERATION_TYPE]
      ,[TRANS_TYPE]
      ,[CAWAVE_S_ID]
      ,[SK_ID]
      ,[CAWAVE_FRQ]
      ,[SYMBLE_RATE]
      ,[START_DATE]
      ,[END_DATE]
      ,[CHANNEL_RATE]
      ,[RMKS]
      ,[UPD_DATE]
      ,[EMP_NAME]
  FROM [dbo].[TB_ETMS_BASE_OPERATION]
where CAWAVE_S_ID is not null and SK_ID is not null
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_OPERATION_S                             */
/*==============================================================*/
create unique clustered index IND_ETMS_BASE_OPERATION_S on VI_ETMS_BASE_OPERATION_S (
CAWAVE_S_ID ASC,
SK_ID ASC,
START_DATE ASC
)
go

/*==============================================================*/
/* View: VI_ETMS_BASE_OPERATION_T                               */
/*==============================================================*/
create view VI_ETMS_BASE_OPERATION_T with schemabinding as
select  [ID]    ,[OP_NAME]
      ,[OPERATION_TYPE]
      ,[TRANS_TYPE]
      ,[TRANS_MODE]
      ,[CAWAVE_T_ID]
      ,[ROUTE_ID]
      ,[START_DATE]
      ,[END_DATE]
      ,[RMKS]
      ,[UPD_DATE]
      ,[EMP_NAME]
  FROM [dbo].[TB_ETMS_BASE_OPERATION]
where CAWAVE_T_ID is not null and ROUTE_ID is not null
go

/*==============================================================*/
/* Index: IND_ETMS_BASE_OPERATION_T                             */
/*==============================================================*/
create unique clustered index IND_ETMS_BASE_OPERATION_T on VI_ETMS_BASE_OPERATION_T (
CAWAVE_T_ID ASC,
ROUTE_ID ASC,
START_DATE ASC
)
go
CREATE TABLE [TB_ETMS_REPAIR_CARD_MODEL] (
  [ID] bigint IDENTITY(1, 1) NOT NULL,
  [ACTIVE] char(1) COLLATE Chinese_PRC_CI_AS NULL,
  [NAME] varchar(100) COLLATE Chinese_PRC_CI_AS NOT NULL,
  [EQUIP_TYPE] bigint NULL,
  [SHUTDOWN_TIME] numeric(6, 2) NULL,
  [PROCESS_TIME] numeric(6, 2) NULL,
  [MEASURE] varchar(600) COLLATE Chinese_PRC_CI_AS NULL,
  [TOOLS] varchar(600) COLLATE Chinese_PRC_CI_AS NULL,
  [OTHER] varchar(600) COLLATE Chinese_PRC_CI_AS NULL,
  [ATTENTION] varchar(600) COLLATE Chinese_PRC_CI_AS NULL,
  [METHODS] varchar(4000) COLLATE Chinese_PRC_CI_AS NULL,
  [TECHNICAL_STANDARDS] varchar(1024) COLLATE Chinese_PRC_CI_AS NULL,
  [REMARK] varchar(200) COLLATE Chinese_PRC_CI_AS NULL,
  CONSTRAINT [PK_ETMS_REPAIR_CARDMODEL] PRIMARY KEY CLUSTERED ([ID]),
  CONSTRAINT [FK3055A6F3D56C8077] FOREIGN KEY ([EQUIP_TYPE]) 
  REFERENCES [TB_ETMS_BASE_PARA_DTL] ([ID]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
)
GO
alter table TB_ETMS_ACCD_ABNORMAL
   add constraint FK_ABN_EQUIP_B_REF_EQUIP foreign key (EQUIP_B_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_ACCD_ABNORMAL
   add constraint FK_ABN_EQUIP_F_REF_EQUIP foreign key (EQUIP_F_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_ACCD_ABNORMAL
   add constraint FK_ABN_REF_ABN foreign key (ABN_ID)
      references TB_ETMS_ACCD_ABNORMAL (ID)
go

alter table TB_ETMS_ACCD_ABNORMAL
   add constraint FK_ABN_TRANSTYPE_REF_PARA foreign key (TRANS_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_ACCD_ABN_EQUIP
   add constraint FK_ABN_EQUIP_REF_ABN_B foreign key (ABN_B_ID)
      references TB_ETMS_ACCD_ABNORMAL (ID)
go

alter table TB_ETMS_ACCD_ABN_EQUIP
   add constraint FK_ABN_EQUIP_REF_ABN_O foreign key (ABN_O_ID)
      references TB_ETMS_ACCD_ABNORMAL (ID)
go

alter table TB_ETMS_ACCD_ABN_EQUIP
   add constraint FK_ABN_EQUIP_REF_EQUIP foreign key (EQUIP_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_ACCD_ABN_OPERATION
   add constraint FK_ABNOP_REF_OP foreign key (OP_ID)
      references TB_ETMS_BASE_OPERATION (ID)
go

alter table TB_ETMS_ACCD_ABN_OPERATION
   add constraint FK_FTOP_REF_FAULT foreign key (ABN_ID)
      references TB_ETMS_ACCD_ABNORMAL (ID)
go

alter table TB_ETMS_ACCD_DUTY_TIME
   add constraint FK_DUTYTIME_REF_ABNOP foreign key (ABN_OP_ID)
      references TB_ETMS_ACCD_ABN_OPERATION (ID)
go

alter table TB_ETMS_ACCD_DUTY_TIME
   add constraint FK_DUTYTIME_REF_PARA foreign key (DUTY_CODE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_CAWAVE_PG_REF
   add constraint FK_CAWAVEPG_REF_CAWAVE foreign key (CAWAVE_ID)
      references TB_ETMS_BASE_CAWAVE (ID)
go

alter table TB_ETMS_BASE_CAWAVE_PG_REF
   add constraint FK_CAWAVEPG_REF_PROGRAM foreign key (PROGRAM_ID)
      references TB_ETMS_BASE_PROGRAM (ID)
go

alter table TB_ETMS_BASE_CHANNEL
   add constraint FK_CHANNEL_REF_TECH foreign key (SYSTEM_ID)
      references TB_ETMS_BASE_TECHSYSTEM (ID)
go

alter table TB_ETMS_BASE_CHANNEL_EQUIP_REF
   add constraint FK_CHNEQUIP_REF_CHANNEL foreign key (CHANNEL_ID)
      references TB_ETMS_BASE_CHANNEL (ID)
go

alter table TB_ETMS_BASE_CHANNEL_EQUIP_REF
   add constraint FK_CHNEQUIP_REF_EQUIP foreign key (EQUIP_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_BASE_DEPT
   add constraint FK_DEPTTYPE_REF_PARA foreign key (DEPT_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_DP_TRTP
   add constraint FK_DPTRTP_REF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_ETMS_BASE_DP_TRTP
   add constraint FK_DPTRTP_REF_PARADTL foreign key (TRANS_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint FK_BASE_EQUIP_REF_EQUIPMOD foreign key (EQUIP_MODEL)
      references TB_ETMS_BASE_EQUIP_MODEL (ID)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint FK_EQUIP_ORI_REF_EQUIP foreign key (ORI_EQUIP_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint FK_EQUIP_PA_REF_EQUIP foreign key (PA_EQUIP_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint FK_EQUIP_REF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_ETMS_BASE_EQUIP
   add constraint FK_EQUIP_REF_TACHE foreign key (TA_ID)
      references TB_ETMS_BASE_TACHE (ID)
go

alter table TB_ETMS_BASE_GROUP
   add constraint FK_CLASS_ERF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_ETMS_BASE_GROUP
   add constraint FK_GROUP_REF_PARA foreign key (GROUP_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPERATION_REF_ROUTE foreign key (ROUTE_ID)
      references TB_ETMS_BASE_ROUTE (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPP_REF_CAWAVE foreign key (CAWAVE_P_ID)
      references TB_ETMS_BASE_CAWAVE (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPS_REF_CAWAVE foreign key (CAWAVE_S_ID)
      references TB_ETMS_BASE_CAWAVE (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPTION_REF_TRANSFER foreign key (SK_ID)
      references TB_ETMS_BASE_TRANSFER (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPTRTP_REF_PARA foreign key (TRANS_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OPT_REF_CAWAVE foreign key (CAWAVE_T_ID)
      references TB_ETMS_BASE_CAWAVE (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OP_REF_PROG foreign key (PROGRAM_ID)
      references TB_ETMS_BASE_PROGRAM (ID)
go

alter table TB_ETMS_BASE_OPERATION
   add constraint FK_OP_TRMD_REF_PARA foreign key (TRANS_MODE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_PARA_DTL
   add constraint FK_SYSPARADTL_REF_SYSPARA foreign key (PARA_TYPE)
      references TB_ETMS_BASE_PARA (PARA_TYPE)
go

alter table TB_ETMS_BASE_PERSON
   add constraint FK_PERSON_REF_CLASS foreign key (GROUP_ID)
      references TB_ETMS_BASE_GROUP (ID)
go

alter table TB_ETMS_BASE_PERSON
   add constraint FK_PERSON_REF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_ETMS_BASE_PERSON
   add constraint FK_PERSON_REF_POST foreign key (POST)
      references TB_ETMS_BASE_POST (ID)
go

alter table TB_ETMS_BASE_PROGRAM
   add constraint FK_PROGRAM_REF_PARA foreign key (PROGRAM_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_SCHEDULE
   add constraint FK_OPCHN_REF_CHANNEL foreign key (CHANNEL_ID)
      references TB_ETMS_BASE_CHANNEL (ID)
go

alter table TB_ETMS_BASE_SCHEDULE
   add constraint FK_OPCHN_REF_OPTRATION foreign key (OP_ID)
      references TB_ETMS_BASE_OPERATION (ID)
go

alter table TB_ETMS_BASE_STATION
   add constraint FK_EQUIP_DIST_REF_PARA foreign key (DISTRICT_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_STATION
   add constraint FK_STATION_RTPE_REF_PARA foreign key (STATION_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_STATION
   add constraint FK_STATION_UNIT_REF_PARA foreign key (TRANSMIT_UNIT_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_TACHE
   add constraint FK_TACH_REF_PARA foreign key (TRANS_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_TECHSYSTEM
   add constraint FK_TECHSYS_REF_PARA foreign key (TRANS_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_BASE_TRANSFER
   add constraint FK_TRANSFER_REF_SATELLITE foreign key (STL_ID)
      references TB_ETMS_BASE_SATELLITE (ID)
go

alter table TB_ETMS_DUTY_CHECK
   add constraint FK_DUTYCHK_REF_ONDUTY foreign key (DU_ID)
      references TB_ETMS_DUTY_DUTY (ID)
go

alter table TB_ETMS_DUTY_DUTY
   add constraint FK_DUTY_REF_GROUP foreign key (GROUP_ID)
      references TB_ETMS_BASE_GROUP (ID)
go

alter table TB_ETMS_DUTY_DUTY
   add constraint FK_PREDUTY_REF_DUTY foreign key (PRE_DUTY)
      references TB_ETMS_DUTY_DUTY (ID)
go

alter table TB_ETMS_DUTY_PROMPT
   add constraint FK_PROMPT_REF_ONDUTY foreign key (DU_ID)
      references TB_ETMS_DUTY_DUTY (ID)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint FK_DUTYREC_REF_ONDUTY foreign key (DU_ID)
      references TB_ETMS_DUTY_DUTY (ID)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint FK_DUTY_REC_REF_ABN foreign key (ABN_ID)
      references TB_ETMS_ACCD_ABNORMAL (ID)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint FK_DUTY_REC_REF_BROADBY foreign key (BROAD_ID)
      references TB_ETMS_REPT_BROAD_BY_TIME (ID)
go

alter table TB_ETMS_REPT_BROAD_BY_TIME
   add constraint FK_REPT_BROADBYRS_REF_PARA foreign key (BROAD_REASON)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_REPT_BROAD_BY_TIME
   add constraint FK_REPT_BROADBYST_REF_PARA foreign key (STATION)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint FK_DUTY_REC_REF_PATROLTIME foreign key (PA_ID)
      references TB_ETMS_DUTY_PATROL_TIME (ID)
go

alter table TB_ETMS_DUTY_RECORD
   add constraint FK_DUTY_REC_REF_WARN foreign key (WARN_ID)
      references TB_ETMS_DUTY_WARNING (ID)
go

alter table TB_ETMS_DUTY_RULE_ITEM
   add constraint FK_RULEITEM_REF_DUTYSCHE foreign key (SCH_ID)
      references TB_ETMS_DUTY_SCHEDULE (ID)
go

alter table TB_ETMS_DUTY_SCHEDULE
   add constraint FK_DUTYSCHE_REF_SCHERULE foreign key (RU_ID)
      references TB_ETMS_DUTY_SCHEDULE_RULE (ID)
go

alter table TB_ETMS_DUTY_SCHEDULE_RULE
   add constraint FK_SCHERULE_REF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_ETMS_DUTY_STAFF_ON_DUTY
   add constraint FK_DUTYSTAFF_REF_DUTY foreign key (DU_W_ID)
      references TB_ETMS_DUTY_DUTY (ID)
go

alter table TB_ETMS_DUTY_WARNING
   add constraint FK_WARN_REF_OPRATION foreign key (OP_ID)
      references TB_ETMS_BASE_OPERATION (ID)
go

alter table TB_ETMS_DUTY_WARNING
   add constraint FK_WARN_REF_PARA foreign key (WARN_TYPE)
      references TB_ETMS_BASE_PARA_DTL (ID)
go

alter table TB_ETMS_REPAIR_CARD
   add constraint FK_CARD_REF_CYCLECELL foreign key (CELL_ID)
      references TB_ETMS_REPAIR_CYCLE_CELL (ID)
go

alter table TB_ETMS_REPAIR_CARD
   add constraint FK_CARD_REF_PERIOD foreign key (PERIOD_ID)
      references TB_ETMS_REPAIR_PERIOD (ID)
go

alter table TB_ETMS_REPAIR_CYCLE_CELL
   add constraint FK_CYCLECELL_REF_CYCLE foreign key (CYCLE_ID)
      references TB_ETMS_REPAIR_CYCLE (ID)
go

alter table TB_ETMS_REPAIR_RECORD
   add constraint FK_REPAIRRECORD_REF_GROUP foreign key (GROUP_ID)
      references TB_ETMS_BASE_GROUP (ID)
go

alter table TB_ETMS_REPAIR_RECORD_EQUIP
   add constraint FK_RECDEQUIP_REF_EQUIP foreign key (EQUIP_ID)
      references TB_ETMS_BASE_EQUIP (ID)
go

alter table TB_ETMS_REPAIR_RECORD_EQUIP
   add constraint FK_RECDEQUIP_REF_RECDITEM foreign key (RECORD_ITEM_ID)
      references TB_ETMS_REPAIR_RECORD_ITEM (ID)
go

alter table TB_ETMS_REPAIR_RECORD_ITEM
   add constraint FK_RECDITEM_REF_RECORD foreign key (RECORD_ID)
      references TB_ETMS_REPAIR_RECORD (ID)
go

alter table TB_ETMS_REPAIR_RECORD_ITEM
   add constraint FK_RECDITME_REF_CARD foreign key (CARD_ID)
      references TB_ETMS_REPAIR_CARD (ID)
go

alter table TB_ETMS_REPAIR_RECORD_PERSON
   add constraint FK_RECDPERSON_REF_ITEM foreign key (RECORD_ITEM_ID)
      references TB_ETMS_REPAIR_RECORD_ITEM (ID)
go

alter table TB_ETMS_REPAIR_RECORD_PERSON
   add constraint FK_RECDPERSON_REF_PERSON foreign key (PERSON_ID)
      references TB_ETMS_BASE_PERSON (ID)
go

alter table TB_ETMS_REPT_BROAD_BY_TIME
   add constraint FK_BROADBY_REF_OPRATION foreign key (OP_ID)
      references TB_ETMS_BASE_OPERATION (ID)
go

alter table TB_ETMS_REPT_DTL
   add constraint FK_REPT_REF_REFTDEF foreign key (REPT_ID)
      references TB_ETMS_REPT_DEF (REPT_ID)
go

alter table TB_ETMS_STAY_PERIOD
   add constraint FK_STAYPER_REF_STAYRULE foreign key (RULE_ID)
      references TB_ETMS_STAY_RULE (ID)
go

alter table TB_ETMS_STAY_SECTION
   add constraint FK_STAYSEC_REF_STAYPER foreign key (PERIOD_ID)
      references TB_ETMS_STAY_PERIOD (ID)
go

alter table TB_SEC_DEPT_PER
   add constraint FK_SEC_DEPTPER_REF_DEPT foreign key (DP_ID)
      references TB_ETMS_BASE_DEPT (ID)
go

alter table TB_SEC_DEPT_PER
   add constraint FK_SEC_DEPTPER_REF_PERSON foreign key (EMP_ID)
      references TB_ETMS_BASE_PERSON (ID)
go

alter table TB_SEC_PER_ASS
   add constraint FK_PERASS_REF_PERMISSION foreign key (PER_ID)
      references TB_SEC_PERMISSION (ID)
go

alter table TB_SEC_PER_ASS
   add constraint FK_PERASS_REF_ROLE foreign key (ROLE_ID)
      references TB_SEC_ROLE (ID)
go

alter table TB_SEC_POP_ASS
   add constraint FK_POPASS_REF_POPEDOM foreign key (POPE_ID)
      references TB_SEC_POPEDOM_VIEW (POPE_ID)
go

alter table TB_SEC_POP_ASS
   add constraint FK_POPASS_REF_ROLE foreign key (ROLE_ID)
      references TB_SEC_ROLE (ID)
go

alter table TB_SEC_PRI_ASS
   add constraint FK_PRIASS_REF_PERSON foreign key (EMP_ID)
      references TB_ETMS_BASE_PERSON (ID)
go

alter table TB_SEC_PRI_ASS
   add constraint FK_PRIASS_REF_ROLE foreign key (ROLE_ID)
      references TB_SEC_ROLE (ID)
go

ALTER TABLE [TB_ETMS_REPAIR_CARD]
ADD [EQUIP_ID] bigint NULL
GO
ALTER TABLE [TB_ETMS_REPAIR_CARD]
ADD CONSTRAINT [FK_CARD_REF_EQUIP] FOREIGN KEY ([EQUIP_ID]) 
  REFERENCES [TB_ETMS_BASE_EQUIP] ([ID]) 
  ON UPDATE NO ACTION
  ON DELETE NO ACTION
GO
