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
package com.ly.train.flower.core.loadbalance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.ly.train.flower.common.core.service.ServiceContext;
import com.ly.train.flower.common.util.AtomicPositiveInteger;
import com.ly.train.flower.core.akka.actor.wrapper.ActorWrapper;

/**
 * @author leeyazhou
 * 
 */
public class RoundLoadBalance extends AbstractLoadBalance {
  private static final String name = "RoundLoadBalance";

  private final ConcurrentMap<String, AtomicPositiveInteger> cache = new ConcurrentHashMap<>();

  @Override
  public ActorWrapper doChooseOne(List<ActorWrapper> actorRefs, ServiceContext serviceContext) {
    AtomicPositiveInteger counter = cache.get(serviceContext.getCurrentServiceName());
    if (counter == null) {
      counter = new AtomicPositiveInteger(new Random().nextInt(100));
      cache.put(serviceContext.getCurrentServiceName(), counter);
    }
    int index = counter.incrementAndGet() % actorRefs.size();
    return actorRefs.get(index);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("RoundLoadBalance [name=" + name + "]");
    return builder.toString();
  }


}
