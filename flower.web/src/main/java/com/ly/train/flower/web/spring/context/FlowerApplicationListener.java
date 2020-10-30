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
package com.ly.train.flower.web.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;
import com.ly.train.flower.common.util.StringUtil;
import com.ly.train.flower.core.service.container.FlowerFactory;
import com.ly.train.flower.web.spring.boot.autoconfigure.FlowerProperties;

/**
 * @author leeyazhou
 * 
 */
public class FlowerApplicationListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
  static final Logger logger = LoggerFactory.getLogger(FlowerApplicationListener.class);
  private ApplicationContext applicationContext;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    registerFlowerService();
  }

  private void registerFlowerService() {
    String[] beanNames = applicationContext.getBeanDefinitionNames();
    applicationContext.getBean(FlowerProperties.class);
    FlowerFactory flowerFactory = applicationContext.getBean(FlowerFactory.class);
    for (String beanName : beanNames) {
      Class<?> beanType = applicationContext.getType(beanName);
      if (isFlowerService(beanType)) {
        com.ly.train.flower.common.core.service.FlowerService flowerService =
            (com.ly.train.flower.common.core.service.FlowerService) applicationContext.getBean(beanName);
        FlowerService flowerService2 = beanType.getAnnotation(FlowerService.class);
        String serviceName = beanType.getSimpleName();
        if (flowerService2 != null && StringUtil.isNotBlank(flowerService2.value())) {
          serviceName = flowerService2.value();
        }
        flowerFactory.getServiceFactory().registerFlowerService(serviceName, flowerService);
        // logger.info("注入服务实例 : " + flowerService);
      }
    }


  }

  private boolean isFlowerService(Class<?> beanType) {
    return AnnotatedElementUtils.hasAnnotation(beanType, FlowerService.class);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

}
