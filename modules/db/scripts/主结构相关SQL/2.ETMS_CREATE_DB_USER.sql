USE [master]
GO
CREATE LOGIN [abrsetms] WITH PASSWORD=N'abrsetms123', DEFAULT_DATABASE=[tjetms-bes], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
EXEC master..sp_addsrvrolemember @loginame = N'abrsetms', @rolename = N'sysadmin'
GO
USE [tjetms-bes]
GO
CREATE USER [abrsetms] FOR LOGIN [abrsetms]
GO
USE [tjetms-bes]
GO
ALTER USER [abrsetms] WITH DEFAULT_SCHEMA=[dbo]
GO
USE [tjetms-bes]
GO
EXEC sp_addrolemember N'db_owner', N'abrsetms'
GO
