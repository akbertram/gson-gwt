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

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class representing an object type in Json. An object consists of name-value pairs where names
 * are strings, and values are any other type of {@link JsonElement}. This allows for a creating a
 * tree of JsonElements. The member elements of this object are maintained in order they were added.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 */
public final class JsonObject extends JsonElement {

  private final JSONObject inner;

  /**
   * Creates an empty JsonObject.
   */
  public JsonObject() {
    inner = new JSONObject();
  }

  JsonObject(JSONObject inner) {
    this.inner = inner;
  }

  /**
   * Adds a member, which is a name-value pair, to self. The name must be a String, but the value
   * can be an arbitrary JsonElement, thereby allowing you to build a full tree of JsonElements
   * rooted at this node.
   *
   * @param property name of the member.
   * @param value the member object.
   */
  public void add(String property, JsonElement value) {
    assert property != null && property.length() != 0;

    if (value == null) {
      inner.put(property, JSONNull.getInstance());
    } else {
      inner.put(property, value.getAsGwtValue());
    }
  }

  /**
   * Removes the {@code property} from this {@link JsonObject}.
   *
   * @param property name of the member that should be removed.
   * @return the {@link JsonElement} object that is being removed.
   * @since 1.3
   */
  public JsonElement remove(String property) {
    JsonElement toRemove = wrap(inner.get(property));
    inner.put(property, null);
    return toRemove;
  }

  /**
   * Convenience method to add a primitive member. The specified value is converted to a
   * JsonPrimitive of String.
   *
   * @param property name of the member.
   * @param value the string value associated with the member.
   */
  public void addProperty(String property, String value) {
    if(value == null) {
      add(property, JsonNull.createJsonNull());
    } else {
      add(property, new JsonPrimitive(value));
    }
  }

  /**
   * Convenience method to add a primitive member. The specified value is converted to a
   * JsonPrimitive of Number.
   *
   * @param property name of the member.
   * @param value the number value associated with the member.
   */
  public void addProperty(String property, Number value) {
    if(value == null) {
      add(property, JsonNull.createJsonNull());
    } else {
      add(property, createJsonElement(value));
    }
  }

  /**
   * Convenience method to add a boolean member. The specified value is converted to a
   * JsonPrimitive of Boolean.
   *
   * @param property name of the member.
   * @param value the number value associated with the member.
   */
  public void addProperty(String property, Boolean value) {
    if(value == null) {
      add(property, JsonNull.createJsonNull());
    } else {
      add(property, createJsonElement(value));
    }
  }

  /**
   * Convenience method to add a char member. The specified value is converted to a
   * JsonPrimitive of Character.
   *
   * @param property name of the member.
   * @param value the number value associated with the member.
   */
  public void addProperty(String property, Character value) {
    if(value == null) {
      add(property, JsonNull.createJsonNull());
    } else {
      add(property, new JsonPrimitive(value));
    }
  }

  /**
   * Creates the proper {@link JsonElement} object from the given {@code value} object.
   *
   * @param value the object to generate the {@link JsonElement} for
   * @return a {@link JsonPrimitive} if the {@code value} is not null, otherwise a {@link JsonNull}
   */
  private JsonElement createJsonElement(Object value) {
    if (value == null) {
      return JsonNull.createJsonNull();
    } else {
      return new JsonPrimitive(value);
    }
  }

  /**
   * Returns a set of members of this object. The set is ordered, and the order is in which the
   * elements were added.
   *
   * @return a set of members of this object.
   */
  public Set<Map.Entry<String, JsonElement>> entrySet() {
    LinkedHashSet<Map.Entry<String, JsonElement>> set = new LinkedHashSet<Map.Entry<String, JsonElement>>();
    for(String key : inner.keySet()) {
      set.add(new Entry(key));
    }
    return set;
  }

  /**
   * Convenience method to check if a member with the specified name is present in this object.
   *
   * @param memberName name of the member that is being checked for presence.
   * @return true if there is a member with the specified name, false otherwise.
   */
  public boolean has(String memberName) {
    return inner.containsKey(memberName);
  }

  /**
   * Returns the member with the specified name.
   *
   * @param memberName name of the member that is being requested.
   * @return the member matching the name. Null if no such member exists.
   */
  public JsonElement get(String memberName) {
    return wrap(inner.get(memberName));
  }

  /**
   * Convenience method to get the specified member as a JsonPrimitive element.
   *
   * @param memberName name of the member being requested.
   * @return the JsonPrimitive corresponding to the specified member.
   */
  public JsonPrimitive getAsJsonPrimitive(String memberName) {
    return (JsonPrimitive) get(memberName);
  }

  /**
   * Convenience method to get the specified member as a JsonArray.
   *
   * @param memberName name of the member being requested.
   * @return the JsonArray corresponding to the specified member.
   */
  public JsonArray getAsJsonArray(String memberName) {
    return (JsonArray) get(memberName);
  }

  /**
   * Convenience method to get the specified member as a JsonObject.
   *
   * @param memberName name of the member being requested.
   * @return the JsonObject corresponding to the specified member.
   */
  public JsonObject getAsJsonObject(String memberName) {
    return (JsonObject) get(memberName);
  }

  @Override
  JSONValue getAsGwtValue() {
    return inner;
  }

  private class Entry implements Map.Entry<String, JsonElement> {
    private String key;

    private Entry(String key) {
      this.key = key;
    }

    public String getKey() {
      return key;
    }

    public JsonElement getValue() {
      return wrap(inner.get(key));
    }

    public JsonElement setValue(JsonElement value) {
      JsonElement element = wrap(inner.get(key));
      add(key, value);
      return element;
    }
  }

}

