package junit.org.rapidpm.microdao;

import org.junit.*;
import org.junit.rules.TestName;
import org.rapidpm.ddi.DI;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static junit.org.rapidpm.microdao.HsqlDBBaseTestUtils.GENERIC_DATABASE_HSQLDB_PATH;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by RapidPM - Team on 26.02.16.
 */
public abstract class BaseMicroDAOTest {

  //will run before @Before starting from jUNit 4.9
  @Rule public TestName testName = new TestName();

  private static Map<String, HsqlDBBaseTestUtils> testUtilsMap = new ConcurrentHashMap<>();

  @Before
  public void setUp() throws Exception {
    final String databaseBasicPath = GENERIC_DATABASE_HSQLDB_PATH + "/" + this.getClass().getName();
    final String databaseName = testName.getMethodName();

    final HsqlDBBaseTestUtils hsqlDBBaseTestUtils = HsqlDBBaseTestUtils
        .newBuilder()
        .withDatabaseBasicPath(databaseBasicPath)
        .withDatabaseName(databaseName)
        .withUseFileBasedDatabase(Boolean.TRUE)
        .build();

    hsqlDBBaseTestUtils.startServices();
    hsqlDBBaseTestUtils.initSchemaBaseSchema(BaseMicroDAOTest.class);
    hsqlDBBaseTestUtils.initSchemaTestSchema(this.getClass());

    testUtilsMap.put(databaseBasicPath + "/" +databaseName, hsqlDBBaseTestUtils);
    // TODO DI muss extra in ein JVM Singleton verpackt werden
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    DI.activateDI(this);
  }

  @After
  public void tearDown() throws Exception {
    final String databaseBasicPath = GENERIC_DATABASE_HSQLDB_PATH + "/" + MethodHandles.lookup().lookupClass().getName();
    final String databaseName = testName.getMethodName();
    final HsqlDBBaseTestUtils hsqlDBBaseTestUtils = testUtilsMap.get(databaseBasicPath + "/" + databaseName);
    if (hsqlDBBaseTestUtils != null) hsqlDBBaseTestUtils.stopServices();
    DI.clearReflectionModel();
  }


}
