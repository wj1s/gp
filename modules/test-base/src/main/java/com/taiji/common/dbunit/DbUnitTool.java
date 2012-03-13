/**
 * 
 */
package com.taiji.common.dbunit;

import gov.abrs.etms.common.util.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.h2.tools.Server;

/**
 * @author Administrator
 * 
 */
public class DbUnitTool {

	//根据XML定义的数据集合，根据传入的操作，对数据库进行操作
	public static void executeOperation(DatabaseOperation operation, DataSource dataSource) throws Exception {
		if (operation != DatabaseOperation.NONE) {
			IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
			DatabaseConfig config = connection.getConfig();
			config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
			try {
				Reader reader = Resource.getResourceAsReader("conf/dbunit/data/Data-utf8.xml", "UTF-8");
				IDataSet iDataSet = new FlatXmlDataSet(reader);
				//支持NULL值
				ReplacementDataSet reDataSet = new ReplacementDataSet(iDataSet);
				reDataSet.addReplacementObject("[null]", null);
				operation.execute(connection, reDataSet);
			} finally {
				connection.close();
			}
		}
	}

	public static File copyTableDataToXml(String tableName, IDatabaseConnection connection) {
		File file = null;
		try {
			QueryDataSet backupDataSet = new QueryDataSet(connection);
			backupDataSet.addTable(tableName);
			file = new File("c:\\" + tableName + ".xml");
			FlatXmlDataSet.write(backupDataSet, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void openH2DbManager() {
		Server server = new Server();
		try {
			server.main();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
