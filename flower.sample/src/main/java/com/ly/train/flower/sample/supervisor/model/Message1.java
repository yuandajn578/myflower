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
package com.ly.train.flower.sample.supervisor.model;

import java.io.Serializable;
import com.ly.train.flower.core.service.message.FirstMessage;

public class Message1 implements FirstMessage, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Message2 m2;

  public Message2 getM2() {
    return m2;
  }

  public void setM2(Message2 m2) {
    this.m2 = m2;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Message1 [m2=");
    builder.append(m2);
    builder.append("]");
    return builder.toString();
  }

}
