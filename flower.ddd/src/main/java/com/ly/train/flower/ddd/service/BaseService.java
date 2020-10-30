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
package com.ly.train.flower.ddd.service;

import com.ly.train.flower.common.core.service.Service;
import com.ly.train.flower.common.core.service.ServiceContext;
import com.ly.train.flower.common.exception.handler.ExceptionHandlerManager;
import com.ly.train.flower.common.logging.Logger;
import com.ly.train.flower.common.logging.LoggerFactory;
import com.ly.train.flower.ddd.config.AggregateLifecycle;

/**
 * @author leeyazhou
 */
abstract class BaseService extends AggregateLifecycle implements Service<Object, Object> {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public Object process(Object message, ServiceContext context) throws Throwable {
    try {
      return doProcess(message, context);
    } catch (Throwable throwable) {
      onError(message, context, throwable);
    }
    return null;
  }

  /**
   * 请求参数
   * 
   * @param param 请求参数
   * @param context 上下文
   * @param throwable 异常堆栈
   */
  public void onError(Object param, ServiceContext context, Throwable throwable) {
    ExceptionHandlerManager.getInstance().getExceptionHandler(throwable.getClass()).handle(context, throwable);
  }

  /**
   * 流程处理
   * 
   * @param message 参数
   * @param context 上下文
   * @return 返回结果
   * @throws Throwable any exception
   */
  public abstract Object doProcess(Object message, ServiceContext context) throws Throwable;


}
