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
/**
 * 
 */
package com.ly.train.flower.base.service;

import java.util.Random;
import com.ly.train.flower.base.model.User;
import com.ly.train.flower.common.core.service.Service;
import com.ly.train.flower.common.core.service.ServiceContext;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;

/**
 * @author leeyazhou
 * 
 */
public class UserServiceC2 implements Service<User, User> {
  static final Logger logger = LoggerFactory.getLogger(UserServiceC2.class);

  @Override
  public User process(User message, ServiceContext context) throws Throwable {
    message.setDesc(message.getDesc() + " --> " + getClass().getSimpleName());
    message.setAge(message.getAge() + 1);
    logger.info("结束处理消息, message : {}", message);
    Thread.sleep(new Random().nextInt(2000));
    return message;
  }

}
