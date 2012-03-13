package com.taiji.common.dbunit;

import java.sql.Types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

public class H2DataTypeFactory extends DefaultDataTypeFactory {

	@Override
	public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
		if (sqlType == Types.BOOLEAN) {
			return DataType.BOOLEAN;
		}

		return super.createDataType(sqlType, sqlTypeName);
	}
}