package junit.org.rapidpm.microdao;

import org.apache.commons.io.IOUtils;
import org.hsqldb.server.Server;
import org.rapidpm.microdao.Constants;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;
import org.rapidpm.microservice.test.PortUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


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
public class HsqlDBBaseTestUtils {

  private String databaseBasicPath;
  private Boolean useFileBasedDatabase;
  private String databaseName;
  private String dbUserROPasswd;
  private String dbUserROName;
  private String dbUserRWPasswd;
  private String dbUserRWName;
  private String dbUserADMINPasswd;
  private String dbUserADMINName;

  private PrintWriter errorWriter = null;
  private PrintWriter logWriter = null;


  private String MY_FULL_DATABASE_PATH = databaseBasicPath + "/" + databaseName;
  private Server hsqlServer;
  private int nextFreePortForTest = -1;

  public int getNextFreePortForTest() {
    return nextFreePortForTest;
  }

  private HsqlDBBaseTestUtils(final Builder builder) {
    databaseBasicPath = builder.databaseBasicPath;
    useFileBasedDatabase = builder.useFileBasedDatabase;
    databaseName = builder.databaseName;
    dbUserROPasswd = builder.dbUserROPasswd;
    dbUserROName = builder.dbUserROName;
    dbUserRWPasswd = builder.dbUserRWPasswd;
    dbUserRWName = builder.dbUserRWName;
    dbUserADMINPasswd = builder.dbUserADMINPasswd;
    dbUserADMINName = builder.dbUserADMINName;
    errorWriter = builder.errorWriter;
    logWriter = builder.logWriter;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Builder newBuilder(@Nonnull final HsqlDBBaseTestUtils copy) {
    Builder builder = new Builder();
    builder.databaseBasicPath = copy.databaseBasicPath;
    builder.useFileBasedDatabase = copy.useFileBasedDatabase;
    builder.databaseName = copy.databaseName;
    builder.dbUserROPasswd = copy.dbUserROPasswd;
    builder.dbUserROName = copy.dbUserROName;
    builder.dbUserRWPasswd = copy.dbUserRWPasswd;
    builder.dbUserRWName = copy.dbUserRWName;
    builder.dbUserADMINPasswd = copy.dbUserADMINPasswd;
    builder.dbUserADMINName = copy.dbUserADMINName;
    builder.errorWriter = copy.errorWriter;
    builder.logWriter = copy.logWriter;
    return builder;
  }

  public void startServices() throws Exception {
    nextFreePortForTest = new PortUtils().nextFreePortForTest();
    if (useFileBasedDatabase) { // if stored on disc
      deleteDirectory(MY_FULL_DATABASE_PATH);
      new File(MY_FULL_DATABASE_PATH).mkdirs();
    } else {
      //memory only
    }

    final String pathFile = "file:" + MY_FULL_DATABASE_PATH;
    final String pathMemory = "mem:target/" + MY_FULL_DATABASE_PATH;
    final String url = "jdbc:hsqldb:hsql://127.0.0.1:" + nextFreePortForTest + "/" + databaseName;

    hsqlServer = new Server();
    hsqlServer.setDatabaseName(0, databaseName);
    hsqlServer.setDatabasePath(0, (useFileBasedDatabase) ? pathFile : pathMemory);
    hsqlServer.setPort(nextFreePortForTest);
    hsqlServer.setErrWriter(errorWriter);
    hsqlServer.setLogWriter(logWriter);
    hsqlServer.setSilent(false);
    hsqlServer.start();

    JDBCConnectionPools.instance()
        .addJDBCConnectionPool(Constants.POOL_NAME_READER.value())
        .withJdbcURL(url)
        .withUsername(dbUserROName)
        .withPasswd(dbUserROPasswd)
        .withAutoCommit(true)
        .done()
        .addJDBCConnectionPool(Constants.POOL_NAME_WRITER.value())
        .withJdbcURL(url)
        .withUsername(dbUserRWName)
        .withPasswd(dbUserRWPasswd)
        .withAutoCommit(true)
        .done()
        .addJDBCConnectionPool(Constants.POOL_NAME_ADMIN.value())
        .withJdbcURL(url)
        .withUsername(dbUserADMINName)
        .withPasswd(dbUserADMINPasswd)
        .withAutoCommit(true)
        .done()
        .connectPools();
  }


  private boolean deleteDirectory(final String path) {
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

  public void stopServices() throws Exception {
    JDBCConnectionPools.instance().shutdownPools();
    hsqlServer.shutdown();
    hsqlServer.stop();
//    DI.clearReflectionModel();
  }


  //@Before
  public void initSchema() {
    // this files are expected, loading will be in side framework

    executeSqlScript(HsqlDBBaseTestUtils.class.getResource("CLEAR_SCHEMA.sql").getPath());
    executeSqlScript(HsqlDBBaseTestUtils.class.getResource("CREATE_TARGET_DB.sql").getPath());
    executeSqlScript(HsqlDBBaseTestUtils.class.getResource("INSERT_BASIC_DATA.sql").getPath());

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
        final Connection macros_db = JDBCConnectionPools.instance().getDataSource(Constants.POOL_NAME_WRITER.value()).getConnection();
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


  public static final class Builder {
    private String databaseBasicPath = "_data/database/hsqldb";
    private Boolean useFileBasedDatabase = Boolean.FALSE;
    private String databaseName = "MICRODAO_DB";
    private String dbUserROPasswd = "";
    private String dbUserROName = "sa";
    private String dbUserRWPasswd = "";
    private String dbUserRWName = "sa";
    private String dbUserADMINPasswd = "";
    private String dbUserADMINName = "sa";
    private PrintWriter errorWriter = null;
    private PrintWriter logWriter = null;

    private Builder() {
    }

    @Nonnull
    public Builder withDatabaseBasicPath(@Nonnull final String databaseBasicPath) {
      this.databaseBasicPath = databaseBasicPath;
      return this;
    }

    @Nonnull
    public Builder withUseFileBasedDatabase(@Nonnull final Boolean useFileBasedDatabase) {
      this.useFileBasedDatabase = useFileBasedDatabase;
      return this;
    }

    @Nonnull
    public Builder withDatabaseName(@Nonnull final String databaseName) {
      this.databaseName = databaseName;
      return this;
    }

    @Nonnull
    public Builder withDbUserROPasswd(@Nonnull final String dbUserROPasswd) {
      this.dbUserROPasswd = dbUserROPasswd;
      return this;
    }

    @Nonnull
    public Builder withDbUserROName(@Nonnull final String dbUserROName) {
      this.dbUserROName = dbUserROName;
      return this;
    }

    @Nonnull
    public Builder withDbUserRWPasswd(@Nonnull final String dbUserRWPasswd) {
      this.dbUserRWPasswd = dbUserRWPasswd;
      return this;
    }

    @Nonnull
    public Builder withDbUserRWName(@Nonnull final String dbUserRWName) {
      this.dbUserRWName = dbUserRWName;
      return this;
    }

    @Nonnull
    public Builder withDbUserADMINPasswd(@Nonnull final String dbUserADMINPasswd) {
      this.dbUserADMINPasswd = dbUserADMINPasswd;
      return this;
    }

    @Nonnull
    public Builder withDbUserADMINName(@Nonnull final String dbUserADMINName) {
      this.dbUserADMINName = dbUserADMINName;
      return this;
    }

    @Nonnull
    public Builder withErrorWriter(@Nonnull final PrintWriter errorWriter) {
      this.errorWriter = errorWriter;
      return this;
    }

    @Nonnull
    public Builder withLogWriter(@Nonnull final PrintWriter logWriter) {
      this.logWriter = logWriter;
      return this;
    }

    @Nonnull
    public HsqlDBBaseTestUtils build() {
      return new HsqlDBBaseTestUtils(this);
    }
  }
}
