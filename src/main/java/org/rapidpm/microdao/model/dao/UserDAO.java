package org.rapidpm.microdao.model.dao;

import org.rapidpm.microdao.model.User;
import org.rapidpm.microdao.model.dao.actions.QueryOneValue;
import org.rapidpm.microdao.model.dao.actions.Update;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Optional;

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
 * Created by RapidPM - Team on 25.02.16.
 */
public class UserDAO {

  @Inject JDBCConnectionPools connectionPools;


  public void writeUser(User user) {
    ((Update) () -> insertOneValue(user)).update(connectionPools);
  }

  public Optional<User> loadUserByEmail(final String email) {
    return ((QueryOneUser) () -> queryOneValueByEmail(email)).execute(connectionPools);
  }


  private static final String INSERT_PRE = "INSERT INTO USERS (\'login', 'name', 'company', 'location', 'email', " +
      "'created_at', 'type', 'fake', 'deleted', 'long', 'lat', 'country_code', 'state', 'city')";

  private String insertOneValue(final User user) {

    final String createdAt = user.getCreatedAt() != null ? new Timestamp(user.getCreatedAt().toInstant(ZoneOffset.UTC).getEpochSecond()).toString() : "null";
    final String faked = user.getFake() ? "1" : "0";
    final String deleted = user.getDeleted() ? "1" : "0";

    final String insertPost = "VALUES (" +
        "'" + user.getLogin() + "', " +
        "'" + user.getName() + "', " +
        "'" + user.getCompany() + "', " +
        "'" + user.getLocation() + "'," +
        "'" + user.getEmail() + "', " +
        "'" + createdAt + "', " +
        "'" + user.getType() + "', " +
        faked + ", " +
        deleted + ", " +
        user.getLongitude() + ", " +
        user.getLat() + ", " +
        "'" + user.getCountryCode() + "', " +
        "'" + user.getState() + "', " +
        "'" + user.getCity() + "');";
    return INSERT_PRE + " " + insertPost;
  }

  private String queryOneValueByEmail(String email) {
    // this could be build nicer...
    return "select * from USERS u where u.email = '" + email + "'";
  }

  //queryOnevalue
  private interface QueryOneUser extends QueryOneValue<User> {
    @Override
    default User getFirstElement(final ResultSet resultSet) throws SQLException {
      if (resultSet != null) {
        return new User(//boring code......
            resultSet.getLong("id"),
            resultSet.getString("login"),
            resultSet.getString("name"),
            resultSet.getString("company"),
            resultSet.getString("location"),
            resultSet.getString("email"),
            (resultSet.getTimestamp("created_at") != null) ? resultSet.getTimestamp("created_at").toLocalDateTime() : null,
            resultSet.getString("type"),
            resultSet.getBoolean("fake"),
            resultSet.getBoolean("deleted"),
            resultSet.getDouble("long"),
            resultSet.getDouble("lat"),
            resultSet.getString("country_code"),
            resultSet.getString("state"),
            resultSet.getString("city")
        );
      } else return null; // will be inside an Optional
    }
  }
}
