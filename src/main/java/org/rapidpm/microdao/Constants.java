package org.rapidpm.microdao;

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
public enum Constants {

  POOL_NAME_READER("microDAO-Pool-RO"),
  POOL_NAME_WRITER("microDAO-Pool-RW"),
  POOL_NAME_ADMIN("microDAO-Pool-ADMIN");

  Constants(final String value) {
    this.value = value;
  }

  private String value;

  public String value() {
    return value;
  }
}
