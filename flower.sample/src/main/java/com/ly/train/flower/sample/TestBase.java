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
package com.ly.train.flower.sample;

import org.junit.BeforeClass;
import com.ly.train.flower.core.akka.ServiceFacade;
import com.ly.train.flower.core.service.container.FlowerFactory;
import com.ly.train.flower.core.service.container.ServiceFactory;
import com.ly.train.flower.core.service.container.ServiceLoader;
import com.ly.train.flower.core.service.container.simple.SimpleFlowerFactory;

/**
 * @author leeyazhou
 * 
 */
public class TestBase {

  protected static FlowerFactory flowerFactory;
  protected static ServiceFacade serviceFacade;

  protected static ServiceFactory serviceFactory;
  protected static ServiceLoader serviceLoader;

  @BeforeClass
  public static void before() {
    flowerFactory = new SimpleFlowerFactory();
    serviceFacade = flowerFactory.getServiceFacade();
    serviceFactory = flowerFactory.getServiceFactory();
    serviceLoader = serviceFactory.getServiceLoader();
  }
}
