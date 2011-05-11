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

import com.google.gwt.json.client.*;


/**
 * A class representing a Json primitive value. A primitive value
 * is either a String, a Java primitive, or a Java primitive
 * wrapper type.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 * @author Alex Bertram
 */
public final class JsonPrimitive extends JsonElement {
  private JSONValue value;

  JsonPrimitive(JSONValue value) {
    this.value = value;
  }

  /**
   * Create a primitive containing a boolean value.
   *
   * @param bool the value to create the primitive with.
   */
  public JsonPrimitive(Boolean bool) {
    this.value = JSONBoolean.getInstance(bool);
  }

  /**
   * Create a primitive containing a {@link Number}.
   *
   * @param number the value to create the primitive with.
   */
  public JsonPrimitive(Number number) {
    this.value = new JSONNumber(number.doubleValue());
  }

  /**
   * Create a primitive containing a String value.
   *
   * @param string the value to create the primitive with.
   */
  public JsonPrimitive(String string) {
    if(string == null) {
      this.value = JSONNull.getInstance();
    } else {
      this.value = new JSONString(string);
    }
  }

  /**
   * Create a primitive containing a character. The character is turned into a one character String
   * since Json only supports String.
   *
   * @param c the value to create the primitive with.
   */
  public JsonPrimitive(Character c) {
    setValue(c.toString());
  }

  /**
   * Create a primitive using the specified Object. It must be an instance of {@link Number}, a
   * Java primitive type, or a String.
   *
   * @param primitive the value to create the primitive with.
   */
  JsonPrimitive(Object primitive) {
    setValue(primitive);
  }

  void setValue(Object primitive) {
    if(primitive instanceof String) {
      this.value = new JSONString((String) primitive);
    } else if(primitive instanceof Character) {
      // convert characters to strings since in JSON, characters are represented as a single
      // character string
      char c = ((Character) primitive).charValue();
      this.value = new JSONString(String.valueOf(c));
    } else if(primitive instanceof Number) {
      this.value = new JSONNumber(((Number) primitive).doubleValue());
    } else if(primitive instanceof Boolean) {
      this.value = JSONBoolean.getInstance((Boolean) primitive);
    } else {
      throw new IllegalArgumentException(primitive.getClass().getName());
    }
  }

  /**
   * Check whether this primitive contains a boolean value.
   *
   * @return true if this primitive contains a boolean value, false otherwise.
   */
  public boolean isBoolean() {
    return value instanceof JSONBoolean;
  }

  /**
   * convenience method to get this element as a {@link Boolean}.
   *
   * @return get this element as a {@link Boolean}.
   * @throws ClassCastException if the value contained is not a valid boolean value.
   */
  @Override
  Boolean getAsBooleanWrapper() {
    return value.isBoolean().booleanValue();
  }

  /**
   * convenience method to get this element as a boolean value.
   *
   * @return get this element as a primitive boolean value.
   * @throws ClassCastException if the value contained is not a valid boolean value.
   */
  @Override
  public boolean getAsBoolean() {
    if (isBoolean()) {
      return getAsBooleanWrapper().booleanValue();
    } else {
      return Boolean.parseBoolean(getAsString());
    }
  }

  /**
   * Check whether this primitive contains a Number.
   *
   * @return true if this primitive contains a Number, false otherwise.
   */
  public boolean isNumber() {
    return value instanceof JSONNumber;
  }

  /**
   * convenience method to get this element as a Number.
   *
   * @return get this element as a Number.
   * @throws ClassCastException if the value contained is not a valid Number.
   */
  @Override
  public Number getAsNumber() {
  if (isNumber()) {
      return value.isNumber().doubleValue();
    } else {
      return Double.parseDouble(getAsString());
    }
  }

  /**
   * Check whether this primitive contains a String value.
   *
   * @return true if this primitive contains a String value, false otherwise.
   */
  public boolean isString() {
    return value instanceof JSONString;
  }

  /**
   * convenience method to get this element as a String.
   *
   * @return get this element as a String.
   * @throws ClassCastException if the value contained is not a valid String.
   */
  @Override
  public String getAsString() {
    if (isNumber()) {
      return getAsNumber().toString();
    } else if (isBoolean()) {
      return getAsBooleanWrapper().toString();
    } else {
      return value.isString().stringValue();
    }
  }

  /**
   * convenience method to get this element as a primitive double.
   *
   * @return get this element as a primitive double.
   * @throws ClassCastException if the value contained is not a valid double.
   */
  @Override
  public double getAsDouble() {
    if (isNumber()) {
      return getAsNumber().doubleValue();
    } else {
      return Double.parseDouble(getAsString());
    }
  }

  /**
   * convenience method to get this element as a float.
   *
   * @return get this element as a float.
   * @throws ClassCastException if the value contained is not a valid float.
   */
  @Override
  public float getAsFloat() {
    return (float)getAsDouble();
  }

  /**
   * convenience method to get this element as a primitive long.
   *
   * @return get this element as a primitive long.
   * @throws ClassCastException if the value contained is not a valid long.
   */
  @Override
  public long getAsLong() {
    if (isNumber()) {
      return getAsNumber().longValue();
    } else {
      return Long.parseLong(getAsString());
    }
  }

  /**
   * convenience method to get this element as a primitive short.
   *
   * @return get this element as a primitive short.
   * @throws ClassCastException if the value contained is not a valid short value.
   */
  @Override
  public short getAsShort() {
    return (short)getAsDouble();
  }

 /**
  * convenience method to get this element as a primitive integer.
  *
  * @return get this element as a primitive integer.
  * @throws ClassCastException if the value contained is not a valid integer.
  */
  @Override
  public int getAsInt() {
    return (int)getAsDouble();
  }

  @Override
  public byte getAsByte() {
    return (byte)getAsDouble();
  }

  @Override
  public char getAsCharacter() {
    return getAsString().charAt(0);
  }

  /**
   * convenience method to get this element as an Object.
   *
   * @return get this element as an Object that can be converted to a suitable value.
   */
  @Override
  Object getAsObject() {
    if(value.isNumber() != null)
      return value.isNumber().doubleValue();
    if(value.isBoolean() != null)
      return value.isBoolean().booleanValue();
    if(value.isString() != null)
      return value.isString().stringValue();

    return null;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    JsonPrimitive other = (JsonPrimitive)obj;
    if (value == null) {
      return other.value == null;
    }
    return value.equals(other.value);
  }

  @Override
  JSONValue getAsGwtValue() {
    return value;
  }
}
