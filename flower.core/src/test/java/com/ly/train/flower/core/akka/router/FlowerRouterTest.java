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
package com.ly.train.flower.core.akka.router;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.ly.train.flower.base.TestBase;
import com.ly.train.flower.base.model.User;
import com.ly.train.flower.base.service.MethodService;
import com.ly.train.flower.base.service.str.StringServiceA;
import com.ly.train.flower.base.service.str.StringServiceB;
import com.ly.train.flower.base.service.user.UserServiceA;
import com.ly.train.flower.base.service.user.UserServiceB;
import com.ly.train.flower.base.service.user.UserServiceC1;
import com.ly.train.flower.base.service.user.UserServiceC2;
import com.ly.train.flower.common.util.Pair;
import com.ly.train.flower.core.service.container.ServiceFlow;

/**
 * @author leeyazhou
 * 
 */
public class FlowerRouterTest extends TestBase {

  @Test
  public void testMethodService() throws Exception {
    final String flowName = generateFlowName();
    List<Pair<String, String>> list = new ArrayList<>();
    list.add(new Pair<String, String>(StringServiceA.class.getSimpleName(), StringServiceB.class.getSimpleName()));
    final String transformMessage1 = MethodService.class.getName() + ".transformMessage";
    list.add(new Pair<String, String>(StringServiceB.class.getSimpleName(), transformMessage1));
    list.add(new Pair<String, String>(transformMessage1, "transformMessage2"));

    ServiceFlow serviceFlow = ServiceFlow.getOrCreate(flowName, serviceFactory);
    serviceFlow.buildFlow(list);
    serviceFlow.build();

    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2 << 3);

    Object o = router.syncCallService("Hello world.");
    System.out.println("响应结果： " + o);
  }

  @Test
  public void testSyncCallServiceSimple() throws Exception {
    String flowName = generateFlowName();
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.build();
    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2 << 3);

    User user = new User();
    user.setName("响应式编程 ");
    user.setAge(2);

    Object o = router.syncCallService(user);
    System.out.println("响应结果： " + o);
    sleep = Integer.MAX_VALUE;
  }

  @Test
  public void testSyncCallServiceSimple2() throws Exception {
    String flowName = generateFlowName();
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.build();
    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2 << 3);
    User user = new User();
    String name = "响应式编程 ";
    user.setName(name);
    user.setAge(2);

    User o = (User) router.syncCallService(user);
    Assert.assertEquals(user.getAge() + 3, o.getAge());
    Assert.assertEquals(name, o.getName());
  }

  @Test
  public void testAsyncCallServiceSimple() throws Exception {
    String flowName = generateFlowName();
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC2.class);
    serviceFlow.build();
    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2);

    User user = new User();
    user.setName("响应式编程 ");
    user.setAge(2);
    router.asyncCallService(user);
  }


  @Test
  public void testSyncCallServiceMutliThread() throws Exception {
    String flowName = generateFlowName();
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC2.class);
    serviceFlow.build();
    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2 << 4);

    final int threadNum = 4;
    final int numPerThread = 10;
    for (int i = 0; i < threadNum; i++) {
      new Thread(() -> {

        for (int j = 0; j < numPerThread; j++) {

          User user = new User();
          user.setName("响应式编程 - " + j);
          user.setAge(2);
          try {
            Object o = router.syncCallService(user);
            System.out.println("响应结果 ： " + o);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }, "test-" + i).start();
    }
  }

  @Test
  public void testAsyncCallServiceMutliThread() throws Exception {
    sleep = 5000;
    String flowName = generateFlowName();
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC2.class);
    serviceFlow.build();
    final FlowRouter router = serviceFacade.buildFlowRouter(flowName, 2 << 4);

    final int threadNum = 4;
    final int numPerThread = 10;
    for (int i = 0; i < threadNum; i++) {
      new Thread(() -> {
        for (int j = 0; j < numPerThread; j++) {

          User user = new User();
          user.setName("响应式编程 - " + j);
          user.setAge(2);
          try {
            router.asyncCallService(user);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }).start();
    }
  }
}
