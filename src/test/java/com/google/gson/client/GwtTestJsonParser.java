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

package com.google.gson.client;

import com.google.gson.*;
import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestJsonParser extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.google.gson.GsonTest";
  }

  public void testObjectWithPrimitives() {
    JsonParser parser = new JsonParser();
    JsonElement elem = parser.parse("{ name: 'Alex', age: 28, married: true, assignment: null }");

    assertEquals("name", "Alex", elem.getAsJsonObject().getAsJsonPrimitive("name").getAsString());
    assertEquals("age", 28, elem.getAsJsonObject().getAsJsonPrimitive("age").getAsInt());
    assertTrue("married", elem.getAsJsonObject().getAsJsonPrimitive("married").getAsBoolean());
    assertTrue("assignment", elem.getAsJsonObject().get("assignment").isJsonNull());
    assertNull("missing property", elem.getAsJsonObject().get("notHere"));
  }

  public void testObjectWithArray() {
    JsonParser parser = new JsonParser();
    JsonElement elem = parser.parse("{ name: 'Alex', alias: ['Al', 13, 'Abu Noor'] }");
    JsonObject object = elem.getAsJsonObject();

    assertTrue("isJsonArray", object.get("alias").isJsonArray());

    JsonArray array = object.get("alias").getAsJsonArray();
    assertEquals(3, array.size());
    assertEquals("Al", array.get(0).getAsString());
    assertEquals(13, array.get(1).getAsInt());
    assertTrue("13.0".equals(array.get(1).getAsString()) || "13".equals(array.get(1).getAsString()));
  }

  public void testTypeCoercion() {
    JsonParser parser = new JsonParser();
    JsonElement elem = parser.parse("{ si: '13', i: 13, bs: 'true', b: false }");
    JsonObject object = elem.getAsJsonObject();


    // GWT seems to treat Number class differently than the JRE,
    // so JsonElement.getAsString() will be have differently on the JRE / GWT
   // Number d = 42d;
   // assertEquals("42.0", d.toString());

    assertEquals(13, object.get("si").getAsInt());
    assertTrue("13.0".equals(object.get("i").getAsString()) || "13".equals(object.get("i").getAsString()));
    assertEquals(true, object.get("bs").getAsBoolean());
    assertEquals("false", object.get("b").getAsString());
  }

  public void testToString() {

    JsonArray arr = new JsonArray();
    arr.add(new JsonPrimitive(21));
    arr.add(new JsonPrimitive(44));
    arr.add(new JsonPrimitive("zap"));

    JsonObject obj = new JsonObject();
    obj.add("foo", new JsonPrimitive("bar"));
    obj.add("size", new JsonPrimitive(13));
    obj.add("list", arr);


    assertEquals("{'foo':'bar', 'size':13, 'list':[21,44,'zap']}".replace('\'', '\"'), obj.toString());

  }
}
