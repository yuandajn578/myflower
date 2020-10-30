/**
 * Copyright © 2019 同程艺龙 (zhihui.li@ly.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ly.train.flower.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author leeyazhou
 * 
 */
public class CloneUtil {
  /**
   * 无需进行复制的特殊类型数组
   */
  private static final Class<?>[] needlessCloneClasses =
      new Class[] {String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class,
          Float.class, Double.class, Void.class, Object.class, Class.class};

  /**
   * 判断该类型对象是否无需复制
   * 
   * @param clazz 指定类型
   * @return 如果不需要复制则返回真，否则返回假
   */
  private static boolean isNeedlessClone(Class<?> clazz) {
    if (clazz.isPrimitive()) {
      // 基本类型
      return true;
    }
    for (Class<?> tmp : needlessCloneClasses) {
      // 是否在无需复制类型数组里
      if (clazz.equals(tmp)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 尝试创建新对象
   * 
   * @param value 原始对象
   * @return 新的对象
   * @throws IllegalAccessException ex
   */
  private static Object createObject(Object value) throws IllegalAccessException {
    try {
      return value.getClass().newInstance();
    } catch (InstantiationException e) {
      return null;
    } catch (IllegalAccessException e) {
      throw e;
    }
  }

  /**
   * 复制对象数据
   * 
   * @param value 原始对象
   * @param level 复制深度。小于0为无限深度，即将深入到最基本类型和Object类级别的数据复制；
   *        大于0则按照其值复制到指定深度的数据，等于0则直接返回对象本身而不进行任何复制行为。
   * @return 返回复制后的对象
   * @throws IllegalAccessException ex
   * @throws InstantiationException ex
   * @throws InvocationTargetException ex
   * @throws NoSuchMethodException ex
   */
  @SuppressWarnings("rawtypes")
  public static Object clone(Object value, int level)
      throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    if (value == null) {
      return null;
    }
    if (level == 0) {
      return value;
    }
    Class<?> clazz = value.getClass();
    if (isNeedlessClone(clazz)) {
      return value;
    }
    level--;
    if (value instanceof Collection) {
      // 复制新的集合
      @SuppressWarnings({"unchecked"})
      Collection<Object> tmp = (Collection) clazz.newInstance();
      for (Object v : (Collection<?>) value) {
        tmp.add(clone(v, level));// 深度复制
      }
      value = tmp;
    } else if (clazz.isArray()) {
      // 复制新的Array
      // 首先判断是否为基本数据类型
      if (clazz.equals(int[].class)) {
        int[] old = (int[]) value;
        value = (int[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(short[].class)) {
        short[] old = (short[]) value;
        value = (short[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(char[].class)) {
        char[] old = (char[]) value;
        value = (char[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(float[].class)) {
        float[] old = (float[]) value;
        value = (float[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(double[].class)) {
        double[] old = (double[]) value;
        value = (double[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(long[].class)) {
        long[] old = (long[]) value;
        value = (long[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(boolean[].class)) {
        boolean[] old = (boolean[]) value;
        value = (boolean[]) Arrays.copyOf(old, old.length);
      } else if (clazz.equals(byte[].class)) {
        byte[] old = (byte[]) value;
        value = (byte[]) Arrays.copyOf(old, old.length);
      } else {
        Object[] old = (Object[]) value;
        Object[] tmp = (Object[]) Arrays.copyOf(old, old.length, old.getClass());
        for (int i = 0; i < old.length; i++) {
          tmp[i] = clone(old[i], level);
        }
        value = tmp;
      }
    } else if (value instanceof Map) {
      // 复制新的MAP
      @SuppressWarnings("unchecked")
      Map<Object, Object> tmp = (Map<Object, Object>) clazz.newInstance();
      Map<?, ?> org = (Map<?, ?>) value;
      for (Map.Entry t : org.entrySet()) {
        tmp.put(t.getKey(), clone(t.getValue(), level));// 深度复制
      }

      value = tmp;
    } else {
      Object tmp = createObject(value);
      if (tmp == null) {
        // 无法创建新实例则返回对象本身，没有克隆
        return value;
      }
      Set<Field> fields = new HashSet<Field>();
      while (clazz != null && !clazz.equals(Object.class)) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        clazz = clazz.getSuperclass();
      }
      for (Field field : fields) {
        if (!Modifier.isFinal(field.getModifiers())) {
          // 仅复制非final字段
          field.setAccessible(true);
          field.set(tmp, clone(field.get(value), level));// 深度复制
        }
      }
      value = tmp;
    }
    return value;
  }

  /**
   * 浅表复制对象
   * 
   * @param value 原始对象
   * @return 复制后的对象，只复制一层
   */
  @SuppressWarnings("unchecked")
  public static <T> T clone(T value) {
    try {
      return (T) clone(value, 1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 深度复制对象
   * 
   * @param value 原始对象
   * @return 复制后的对象
   * @throws IllegalAccessException ex
   * @throws InstantiationException ex
   * @throws InvocationTargetException ex
   * @throws NoSuchMethodException ex
   */
  public static Object deepClone(Object value)
      throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    return clone(value, -1);
  }
}
