package junit.org.rapidpm.microdao.model.dao.userdao.v001;

import junit.org.rapidpm.microdao.HsqlDBBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.rapidpm.microdao.model.User;
import org.rapidpm.microdao.model.dao.UserDAO;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

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
public class UserDAOTest extends HsqlDBBaseTest {


  @Inject UserDAO userDAO;

  @Test
  public void writeUser() throws Exception {

  }

  @Test
  public void loadUserByEmail001() throws Exception {
    final Optional<User> user = userDAO.loadUserByEmail("xx.xx@xx.xx");
    Assert.assertTrue(user.isPresent());
    Assert.assertEquals("xx.xx@xx.xx", user.get().getEmail());
  }

  @Test // this value will be from the UserDAOTest.sql
  public void loadUserByEmail002() throws Exception {
    final Optional<User> user = userDAO.loadUserByEmail("xx.xx@abc.de");
    Assert.assertTrue(user.isPresent());
    Assert.assertEquals("xx.xx@abc.de", user.get().getEmail());
  }
}