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
package com.ly.train.flower.base.service.user;

import java.util.List;
import com.ly.train.flower.base.model.User;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.annotation.FlowerType;
import com.ly.train.flower.common.core.service.Service;
import com.ly.train.flower.common.core.service.ServiceContext;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;

/**
 * @author leeyazhou
 * 
 */
@FlowerService(type = FlowerType.AGGREGATE)
public class UserServiceD implements Service<List<User>, List<User>> {
  static final Logger logger = LoggerFactory.getLogger(UserServiceD.class);

  @Override
  public List<User> process(List<User> message, ServiceContext context) throws Throwable {
    logger.info("结束处理消息, message : {}", message);
    return message;
  }

}
