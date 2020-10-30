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
package com.ly.train.flower.core.service.container.util;

import com.ly.train.flower.core.service.Complete;
import com.ly.train.flower.core.service.web.Flush;
import com.ly.train.flower.core.service.web.HttpComplete;

/**
 * @author leeyazhou
 */
public class ServiceLoaderUtil {

  public static boolean isFlush(Object flowerService) {
    boolean flush = false;
    if (flowerService instanceof Flush) {
      flush = true;
    }
    com.ly.train.flower.common.annotation.FlowerService flowerServiceAnnotation =
        flowerService.getClass().getAnnotation(com.ly.train.flower.common.annotation.FlowerService.class);
    if (flowerServiceAnnotation != null) {
      if (flowerServiceAnnotation.flush()) {
        flush = true;
      }
    }
    return flush;
  }

  public static boolean isComplete(Object flowerService) {
    boolean complete = false;
    if (flowerService instanceof Complete || flowerService instanceof HttpComplete) {
      complete = true;
    }
    com.ly.train.flower.common.annotation.FlowerService flowerServiceAnnotation =
        flowerService.getClass().getAnnotation(com.ly.train.flower.common.annotation.FlowerService.class);
    if (flowerServiceAnnotation != null) {
      if (flowerServiceAnnotation.complete()) {
        complete = true;
      }
    }
    return complete;
  }
}
