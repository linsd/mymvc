package com.tasxxz.mymvc.ex;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 解决：
 * @ResponseBody注释的方法返回类型为String，客户端接收到的结果会乱码问题
 * spring-mvc.xml里配置<bean class="com.tasxxz.mymvc.ex.UTF8StringBeanPostProcessor" />
 */
public class UTF8StringBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof StringHttpMessageConverter){
			MediaType mediaType = new MediaType("text", "plain", Charset.forName("UTF-8"));
			List<MediaType> types = new ArrayList<MediaType>();
			types.add(mediaType);
			((StringHttpMessageConverter) bean).setSupportedMediaTypes(types);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}