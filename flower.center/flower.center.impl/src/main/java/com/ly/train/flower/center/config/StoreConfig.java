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
package com.ly.train.flower.center.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ly.train.flower.center.core.store.FlowConfigStore;
import com.ly.train.flower.center.core.store.ServiceInfoStore;
import com.ly.train.flower.common.util.ExtensionLoader;

/**
 * @author leeyazhou
 */
@Configuration
public class StoreConfig {

  @Value("${flower.center.store}")
  private String storeType;


  @Bean
  public FlowConfigStore flowConfigStore() {
    FlowConfigStore flowConfigStore = ExtensionLoader.load(FlowConfigStore.class).load(storeType);
    return flowConfigStore;
  }

  @Bean
  public ServiceInfoStore serviceInfoStore() {
    ServiceInfoStore serviceConfigStore = ExtensionLoader.load(ServiceInfoStore.class).load(storeType);
    return serviceConfigStore;
  }


}
