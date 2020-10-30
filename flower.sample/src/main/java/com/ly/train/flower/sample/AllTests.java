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
package com.ly.train.flower.sample;

import com.ly.train.flower.sample.condition.ConditionServiceSample;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author leeyazhou
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ConditionServiceSample.class,
    com.ly.train.flower.sample.programflow.Sample.class, com.ly.train.flower.sample.supervisor.Sample.class,
    com.ly.train.flower.sample.supervisor.BatchSample.class, com.ly.train.flower.sample.textflow.BatchSample.class,
    com.ly.train.flower.sample.textflow.Sample.class, com.ly.train.flower.sample.textflow.ServiceRouterSample.class})
public class AllTests {

}
