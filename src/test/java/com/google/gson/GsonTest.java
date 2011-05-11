/*
 * Copyright (C) 2011 bedatadriven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.gson;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Verify certain behavior of the Gson library
 */
public class GsonTest {

  @Test
  public void testDoubleConversion() {

    assertThat(new JsonPrimitive(13).getAsString(), equalTo("13"));
    assertThat(new JsonPrimitive(1.2).getAsString(), equalTo("1.2"));
    assertThat(new JsonPrimitive(1d/3d).getAsString(), equalTo("0.3333333333333333"));

  }


}
