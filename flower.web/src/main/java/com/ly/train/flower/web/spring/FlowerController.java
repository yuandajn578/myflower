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
package com.ly.train.flower.web.spring;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.ly.train.flower.common.annotation.Flower;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;
import com.ly.train.flower.core.akka.router.FlowRouter;
import com.ly.train.flower.core.service.container.FlowerFactory;
import com.ly.train.flower.core.service.container.ServiceFlow;

/**
 * 
 * @author leeyazhou
 */
public abstract class FlowerController implements InitializingBean, ApplicationContextAware {
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private FlowRouter flowRouter;
  private String flowerName;
  private int flowerNumber;
  private ApplicationContext applicationContext;
  private FlowerFactory flowerFactory;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  protected void doProcess(Object param, HttpServletRequest req) {
    AsyncContext context = null;
    if (req != null) {
      context = req.startAsync();
    }
    this.flowRouter.asyncCallService(param, context);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.flowerFactory = applicationContext.getBean(FlowerFactory.class);
    getFlowName();
    buildFlower();
    this.flowRouter = initFlowRouter();
  }

  /**
   * 初始化路由
   * 
   * @see com.ly.train.flower.core.akka.ServiceFacade#buildFlowRouter(String, int)
   * @return {@code ServiceRouter}
   */
  private FlowRouter initFlowRouter() {
    return flowerFactory.getServiceFacade().buildFlowRouter(getFlowName(), getFlowerNumber());
  }

  /**
   * 定义数据处理流程
   * 
   * @see ServiceFlow
   */
  public abstract void buildFlower();

  public ServiceFlow getServiceFlow() {
    return flowerFactory.getServiceFactory().getOrCreateServiceFlow(getFlowName());
  }

  /**
   * 获取流程名称
   * 
   * @return flowName
   */
  public String getFlowName() {
    if (flowerName == null) {
      Flower bindController = this.getClass().getAnnotation(Flower.class);
      this.flowerName = bindController.value();
    }
    return flowerName;
  }

  public int getFlowerNumber() {
    if (flowerNumber == 0) {
      Flower bindController = this.getClass().getAnnotation(Flower.class);
      this.flowerNumber = bindController.flowNumber();
      if (this.flowerNumber <= 0) {
        this.flowerNumber = 2 << 6;
      }
    }
    return flowerNumber;
  }

}
