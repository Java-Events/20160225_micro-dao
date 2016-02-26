package junit.org.rapidpm.microdao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.ddi.DI;

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
public class BaseMicroDAOTest {

  public static HsqlDBBaseTestUtils hsqlDBBaseTestUtils = HsqlDBBaseTestUtils
      .newBuilder()
      .withUseFileBasedDatabase(Boolean.TRUE)
      .build();

  @BeforeClass
  public static void setUpClass() throws Exception {
    hsqlDBBaseTestUtils.startServices();
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    hsqlDBBaseTestUtils.stopServices();
  }

  @Before
  public void setUp() throws Exception {
    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    DI.activateDI(this);
    hsqlDBBaseTestUtils.initSchema();
  }

  @After
  public void tearDown() throws Exception {
    DI.clearReflectionModel();
  }


}
