/*
 *
 * The DbUnit Database Testing Framework
 * Copyright (C)2002-2004, DbUnit.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package com.taiji.common.test;

import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;

import com.taiji.common.dbunit.DbUnitTool;

public abstract class DatabaseTestCase extends OpenSessionInUnitTests {

	@Before
	public void loadDefaultData() throws Exception {
		DbUnitTool.executeOperation(DatabaseOperation.CLEAN_INSERT, dataSource);
	}

	@After
	public void cleanDefaultData() throws Exception {
		DbUnitTool.executeOperation(DatabaseOperation.NONE, dataSource);
	}

	protected void openDbManager() {
		DbUnitTool.openH2DbManager();
	}
}
