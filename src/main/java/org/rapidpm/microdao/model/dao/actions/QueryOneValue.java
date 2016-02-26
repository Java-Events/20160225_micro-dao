package org.rapidpm.microdao.model.dao.actions;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microdao.Constants;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Copyright (C) 2015 RapidPM
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
 * Created by RapidPM - Team on 13.02.16.
 */
public interface QueryOneValue<T> {

  default Optional<T> execute(JDBCConnectionPools connectionPools) {
    final HikariDataSource dataSource = connectionPools.getDataSource(dbPoolName());
    try {
      final Connection connection = dataSource.getConnection();
      final Statement statement = connection.createStatement();
      final String sql = createSQL();
      System.out.println("sql = " + sql);
      final ResultSet resultSet = statement.executeQuery(sql);
      final boolean next = resultSet.next();
      if (next) {
        final T value = getFirstElement(resultSet);

        final boolean error = resultSet.next();

        statement.close();
        dataSource.evictConnection(connection);

        if (error) throw new RuntimeException("too many values are selected with query");
        return Optional.ofNullable(value);
      } else {
        statement.close();
        dataSource.evictConnection(connection);
        return Optional.empty();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  default String dbPoolName() {
    return Constants.POOL_NAME_READER.value();
  }


  String createSQL();

  T getFirstElement(final ResultSet resultSet) throws SQLException;

}
