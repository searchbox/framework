/*
 * Copyright 2014 gamars.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.searchbox.framework.convert;

/**
 *
 * @author gamars
 */
@SearchConverter
public static class QueryConverter {
      implements Converter<String, EdismaxQuery.Condition> {

    @Override
    public EdismaxQuery.Condition convert(String source) {
      return new EdismaxQuery.Condition(source);
    }
  }

}
