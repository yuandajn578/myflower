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
package com.ly.train.flower.core.util;

import org.junit.Ignore;
import org.junit.Test;
import com.ly.train.flower.common.util.Assert;
import com.ly.train.flower.common.util.HttpClient;

/**
 * @author leeyazhou
 * 
 */
public class HttpClientTest {
  @Test
  public void testGet() {
    String ret = HttpClient.builder().setUrl("http://www.baidu.com").build().get();
    Assert.notNull(ret, "success");
  }

  @Test
  @Ignore
  public void testPost() {
    String ret = HttpClient.builder().setUrl("http://www.baidu.com").setParam("name=liyazhou").build().post();
    Assert.notNull(ret, "success");
  }
}
