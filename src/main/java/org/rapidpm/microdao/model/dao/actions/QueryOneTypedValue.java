package org.rapidpm.microdao.model.dao.actions;

import java.sql.ResultSet;
import java.sql.SQLException;

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
public interface QueryOneTypedValue<T> extends QueryOneValue<T> {

  interface QueryOneInteger extends QueryOneTypedValue<Integer> {
    default Integer getFirstElement(final ResultSet resultSet) throws SQLException {
      return resultSet.getInt(1);
    }
  }

  interface QueryOneFloat extends QueryOneTypedValue<Float> {
    default Float getFirstElement(final ResultSet resultSet) throws SQLException {
      return resultSet.getFloat(1);
    }
  }

  interface QueryOneString extends QueryOneTypedValue<String> {
    default String getFirstElement(final ResultSet resultSet) throws SQLException {
      return resultSet.getString(1);
    }
  }


}
