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
package com.ly.train.flower.sample.condition.service;

import com.ly.train.flower.common.core.service.Service;
import com.ly.train.flower.common.core.service.ServiceContext;
import com.ly.train.flower.sample.condition.model.MessageC;
import com.ly.train.flower.sample.condition.model.MessageX;

public class ServiceC implements Service<MessageC, MessageX> {

  @Override
  /**
   * print service
   */
  public MessageX process(MessageC message, ServiceContext context) {
    System.out.println("I am Service C.");
    MessageX mx = new MessageX();
    mx.setCondition("serviceE,serviceD");
    return mx;
  }

}
