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
package com.ly.train.flower.core.akka.actor.wrapper;

import com.ly.train.flower.common.core.message.Message;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;
import akka.actor.ActorRef;

/**
 * @author leeyazhou
 * 
 */
public class ActorLocalWrapper implements ActorWrapper {
  private static final Logger logger = LoggerFactory.getLogger(ActorLocalWrapper.class);
  private String serviceName;
  private final ActorRef actorRef;

  public ActorLocalWrapper(ActorRef actorRef) {
    this.actorRef = actorRef;
  }

  public void tell(Message message) {
    actorRef.tell(message, ActorRef.noSender());
  }

  public void tell(Message message, ActorRef sender) {
    if (logger.isDebugEnabled()) {
      logger.debug("Local message. serviceName : {}, actor : {}, message : {}, sender : {}", serviceName, actorRef,
          message, sender);
    }
    if (sender == null) {
      sender = ActorRef.noSender();
    }
    actorRef.tell(message, sender);
  }

  @Override
  public String getServiceName() {
    return serviceName;
  }

  /**
   * @return the actorRef
   */
  public ActorRef getActorRef() {
    return actorRef;
  }

  public ActorLocalWrapper setServiceName(String serviceName) {
    this.serviceName = serviceName;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ActorLocalWrapper [serviceName=");
    builder.append(serviceName);
    builder.append(", actorRef=");
    builder.append(actorRef);
    builder.append("]");
    return builder.toString();
  }


}
