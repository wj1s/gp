USE [master]
GO

EXEC msdb.dbo.sp_delete_database_backuphistory @database_name = N'tjetms-bes'
GO
USE [master]
GO
ALTER DATABASE [tjetms-bes] SET  SINGLE_USER WITH ROLLBACK IMMEDIATE
GO
ALTER DATABASE [tjetms-bes] SET  SINGLE_USER 
GO
USE [master]
GO
/****** 对象:  Database [tjetms-bes]    脚本日期: 03/09/2011 11:59:05 ******/
DROP DATABASE [tjetms-bes]
GO

USE [master]
GO
/****** 对象:  Login [abrsetms]    脚本日期: 03/09/2011 11:59:54 ******/
DROP LOGIN [abrsetms]
GO
