package junit.org.rapidpm.microdao;

import org.rapidpm.microdao.Constants;
import org.apache.commons.io.IOUtils;
import org.hsqldb.server.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.rapidpm.ddi.DI;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;
import org.rapidpm.microservice.test.PortUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.rapidpm.microdao.Constants.DB_NAME;

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
 * Created by RapidPM - Team on 04.02.16.
 */
public class HsqlDBBaseTest {

  public static final String DATABASE_BASIC_PATH = "_data/database/hsqldb/";
  protected static Server hsqlServer;


  static int nextFreePortForTest = -1;

  @BeforeClass
  public static void beforeClass() throws Exception {

    nextFreePortForTest = new PortUtils().nextFreePortForTest();

    // if stored on disc
    //deleteDirectory(DATABASE_BASIC_PATH + "/" + Constants.DB_NAME.value());
    //new File(DATABASE_BASIC_PATH + DB_NAME.value()).mkdir();

    String url = "jdbc:hsqldb:hsql://127.0.0.1:" + nextFreePortForTest + "/" + DB_NAME.value();
    String user = "sa";
    String password = "";
    hsqlServer = new Server();
    hsqlServer.setDatabaseName(0, DB_NAME.value());
//    hsqlServer.setDatabasePath(0, "file:" + DATABASE_BASIC_PATH + DB_NAME);
    hsqlServer.setDatabasePath(0, "mem:target/" + DATABASE_BASIC_PATH + DB_NAME.value());
    hsqlServer.setPort(nextFreePortForTest);
    hsqlServer.setErrWriter(null);
    hsqlServer.setLogWriter(null);
    hsqlServer.setSilent(false);
    hsqlServer.start();

    JDBCConnectionPools.instance()
        .addJDBCConnectionPool(Constants.DB_NAME.value())
        .withJdbcURL(url)
        .withUsername(user)
        .withPasswd(password)
        .withTimeout(2000)
        .withAutoCommit(true)
        .done()
        .connectPools();
  }

  private static boolean deleteDirectory(final String path) {
    final File indexDirectory = new File(path);
    if (indexDirectory.exists()) {
      try {
        Files.walkFileTree(indexDirectory.toPath(), new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
          }
        });
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;
  }

  @AfterClass
  public static void after() throws Exception {
    JDBCConnectionPools.instance().shutdownPools();
    hsqlServer.shutdown();
    hsqlServer.stop();
    DI.clearReflectionModel();
  }


  public void createTestVolumeDirectory(String path) {
    deleteDirectory(path);
    new File(path).mkdirs();
  }


  @Before
  public void initSchema() {

    DI.clearReflectionModel();
    DI.activatePackages("org.rapidpm");
    DI.activatePackages(this.getClass());
    DI.activateDI(this);

    // this files are expected, loading will be in side framework

    executeSqlScript(HsqlDBBaseTest.class.getResource("CLEAR_SCHEMA.sql").getPath());
    executeSqlScript(HsqlDBBaseTest.class.getResource("CREATE_TARGET_DB.sql").getPath());
    executeSqlScript(HsqlDBBaseTest.class.getResource("INSERT_BASIC_DATA.sql").getPath());

    final URL testSqlResource = getClass().getResource(getClass().getSimpleName() + ".sql");
    if (testSqlResource != null) {
      String testSqlPath = testSqlResource.getPath();
      executeSqlScript(testSqlPath);
    } else {
      System.out.println("No SQL for " + getClass().getSimpleName());
    }


  }

  private void executeSqlScript(String filePath) {
    try (
        final InputStream sqlAsStream = new FileInputStream(filePath);
        final Connection macros_db = JDBCConnectionPools.instance().getDataSource(Constants.DB_NAME.value()).getConnection();
        final Statement statement = macros_db.createStatement()
    ) {
      final String sqlStatement = IOUtils.toString(sqlAsStream);
      statement.executeUpdate(sqlStatement);
      macros_db.commit();
    } catch (SQLException | IOException e) {
      e.printStackTrace();
      System.out.println("e = " + e);
    }

  }

}
