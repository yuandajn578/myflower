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
package com.ly.train.flower.center.core.config;

import java.nio.charset.Charset;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @author leeyazhou
 * 
 */
@Configuration
public class FastJsonConfiguration implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(
        SerializerFeature.DisableCircularReferenceDetect, // SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.PrettyFormat);
    fastJsonConfig.setCharset(Charset.forName("UTF-8"));
    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    converter.setFastJsonConfig(fastJsonConfig);
    converters.add(0, converter);
  }

}
