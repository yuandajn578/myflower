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
package com.ly.train.flower.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.ly.train.flower.common.core.config.FlowConfig;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;
import com.ly.train.flower.common.util.StringUtil;
import com.ly.train.flower.common.util.URL;
import com.ly.train.flower.common.util.concurrent.NamedThreadFactory;
import com.ly.train.flower.registry.config.ServiceInfo;

/**
 * @author leeyazhou
 * 
 */
public abstract class AbstractRegistry implements Registry {
  protected final Logger logger = LoggerFactory.getLogger(getClass());
  protected final ConcurrentMap<String, ServiceInfo> serviceInfoCache = new ConcurrentHashMap<>();
  protected final ConcurrentMap<String, FlowConfig> flowConfigCache = new ConcurrentHashMap<>();
  private static final ScheduledExecutorService executorService =
      Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("flower-registry"));

  protected final String infoRoot = "flower:info";
  protected final String configRoot = "flower:config";
  protected final URL url;

  public AbstractRegistry(URL url) {
    this.url = url;
    executorService.scheduleAtFixedRate(new Runnable() {

      @Override
      public void run() {
        try {
          doRegisterServiceInfos();
          List<ServiceInfo> tt = doGetProvider(null);
          if (tt != null && !tt.isEmpty()) {
            for (ServiceInfo info : tt) {
              serviceInfoCache.put(info.getServiceMeta().getServiceClassName(), info);
            }
          }
          List<FlowConfig> t2 = doGetFlowConfig(null);
          if (t2 != null && !t2.isEmpty()) {
            for (FlowConfig info : t2) {
              if (StringUtil.isNotBlank(info.getFlowName())) {
                flowConfigCache.put(info.getFlowName(), info);
              }
            }
          }
        } catch (Exception e) {
          logger.error("", e);
        }
      }
    }, 5L, 3L, TimeUnit.SECONDS);
  }


  @Override
  public boolean register(ServiceInfo serviceInfo) {
    serviceInfoCache.putIfAbsent(serviceInfo.getServiceMeta().getServiceClassName(), serviceInfo);
    return doRegister(serviceInfo);
  }

  @Override
  public List<ServiceInfo> getProvider(ServiceInfo serviceInfo) {
    List<ServiceInfo> ret = doGetProvider(serviceInfo);
    if (ret != null) {
      for (ServiceInfo i : ret) {
        serviceInfoCache.put(i.getServiceMeta().getServiceClassName(), i);
      }
    }
    return new ArrayList<ServiceInfo>(serviceInfoCache.values());
  }

  private void doRegisterServiceInfos() {
    for (Map.Entry<String, ServiceInfo> entry : serviceInfoCache.entrySet()) {
      doRegister(entry.getValue());
    }
  }

  @Override
  public URL getUrl() {
    return url;
  }


  @Override
  public boolean registerFlowConfig(FlowConfig flowConfig) {
    flowConfigCache.put(flowConfig.getFlowName(), flowConfig);
    return doRegisterFlowConfig(flowConfig);
  }

  @Override
  public List<FlowConfig> getFlowConfig(FlowConfig flowConfig) {
    List<FlowConfig> ret = doGetFlowConfig(flowConfig);
    if (ret != null) {
      for (FlowConfig i : ret) {
        if (StringUtil.isNotBlank(i.getFlowName())) {
          flowConfigCache.put(i.getFlowName(), i);
        }
      }
    }
    return new ArrayList<FlowConfig>(flowConfigCache.values());
  }


  public abstract boolean doRegister(ServiceInfo serviceInfo);


  public abstract List<ServiceInfo> doGetProvider(ServiceInfo serviceInfo);

  public abstract boolean doRegisterFlowConfig(FlowConfig flowConfig);

  public abstract List<FlowConfig> doGetFlowConfig(FlowConfig flowConfig);
}
