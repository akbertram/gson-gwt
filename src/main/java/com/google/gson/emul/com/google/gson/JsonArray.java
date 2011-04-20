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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

import java.util.Iterator;

/**
 * A class representing an array type in Json. An array is a list of {@link JsonElement}s each of
 * which can be of a different type. This is an ordered list, meaning that the order in which
 * elements are added is preserved.
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 */
public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
  private final JSONArray elements;

  /**
   * Creates an empty JsonArray.
   */
  public JsonArray() {
    elements = new JSONArray();
  }

  JsonArray(JSONArray elements) {
    this.elements = elements;
  }

  /**
   * Adds the specified element to self.
   *
   * @param element the element that needs to be added to the array.
   */
  public void add(JsonElement element) {
    if (element == null) {
      elements.set(elements.size(), JSONNull.getInstance());
    } else {
      elements.set(elements.size(), element.getAsGwtValue());
    }
  }

  /**
   * Adds all the elements of the specified array to self.
   *
   * @param array the array whose elements need to be added to the array.
   */
  public void addAll(JsonArray array) {
    for(JsonElement e : array) {
      add(e);
    }
  }

  /**
   * Reverses the elements of the array.
   */
  // void reverse() { }  NOT IMPLEMENTED

  /**
   * Returns the number of elements in the array.
   *
   * @return the number of elements in the array.
   */
  public int size() {
    return elements.size();
  }

  /**
   * Returns an iterator to navigate the elemetns of the array. Since the array is an ordered list,
   * the iterator navigates the elements in the order they were inserted.
   *
   * @return an iterator to navigate the elements of the array.
   */
  public Iterator<JsonElement> iterator() {
    return new Iterator<JsonElement>() {
      private int i=0;

      public boolean hasNext() {
        return i<=elements.size();
      }

      public JsonElement next() {
        return wrap(elements.get(i++));
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * Returns the ith element of the array.
   *
   * @param i the index of the element that is being sought.
   * @return the element present at the ith index.
   * @throws IndexOutOfBoundsException if i is negative or greater than or equal to the
   * {@link #size()} of the array.
   */
  public JsonElement get(int i) {
    return wrap(elements.get(i));
  }

  /**
   * convenience method to get this array as a {@link Number} if it contains a single element.
   *
   * @return get this element as a number if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid Number.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public Number getAsNumber() {
    if (elements.size() == 1) {
      return elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a {@link String} if it contains a single element.
   *
   * @return get this element as a String if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid String.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public String getAsString() {
    if (elements.size() == 1) {
      return elements.get(0).isString().stringValue();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a double if it contains a single element.
   *
   * @return get this element as a double if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid double.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public double getAsDouble() {
    if (elements.size() == 1) {
      return elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }


  /**
   * convenience method to get this array as a float if it contains a single element.
   *
   * @return get this element as a float if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid float.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public float getAsFloat() {
    if (elements.size() == 1) {
      return (float)elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a long if it contains a single element.
   *
   * @return get this element as a long if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid long.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public long getAsLong() {
    if (elements.size() == 1) {
      return Long.parseLong(elements.get(0).isNumber().toString());
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as an integer if it contains a single element.
   *
   * @return get this element as an integer if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid integer.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public int getAsInt() {
    if (elements.size() == 1) {
      return (int)elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public byte getAsByte() {
    if (elements.size() == 1) {
      return (byte)elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public char getAsCharacter() {
    if (elements.size() == 1) {
      return elements.get(0).isString().stringValue().charAt(0);
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a primitive short if it contains a single element.
   *
   * @return get this element as a primitive short if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid short.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public short getAsShort() {
    if (elements.size() == 1) {
      return (short)elements.get(0).isNumber().doubleValue();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as a boolean if it contains a single element.
   *
   * @return get this element as a boolean if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid boolean.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  public boolean getAsBoolean() {
    if (elements.size() == 1) {
      return elements.get(0).isBoolean().booleanValue();
    }
    throw new IllegalStateException();
  }

  /**
   * convenience method to get this array as an Object if it contains a single element.
   *
   * @return get this element as an Object if it is single element array.
   * @throws ClassCastException if the element in the array is of not a {@link JsonPrimitive} and
   * is not a valid Object.
   * @throws IllegalStateException if the array has more than one element.
   */
  @Override
  Object getAsObject() {
    if (elements.size() == 1) {
      return new JsonObject(elements.get(0).isObject());
    }
    throw new IllegalStateException();
  }


  @Override
  JSONValue getAsGwtValue() {
    return elements;
  }
}
