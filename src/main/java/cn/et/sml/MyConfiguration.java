package cn.et.sml;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * 创建一个bean：
 * <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"></bean>
 */

//java中创建bean，默认类名首字母小写为bean名
@Configuration
public class MyConfiguration {
	
	/**
	 * 定义四个属性，和数据库的配置文件一样的
	 */
	@Value("${spring.datasource.url}")
	private String uri;
	@Value("${spring.datasource.username}")
	private String userName;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	//写一个注解告诉它这会产生一个bean
	//往spring容器中注入一个bean的名字叫dataSource
	@Bean
	public DataSource dataSource() {
		
		//实际的实例
		DruidDataSource dds = new DruidDataSource();
		
		//创建数据源
		dds.setDriverClassName(driverClassName);
		dds.setUrl(uri);
		dds.setUsername(userName);
		dds.setPassword(password);
		
		return dds;
	}
	
	
	/*
	 * 注册一个servlet，所有的bean都可以这样注入
	 * http:localhost/druid
	 */
	@Bean
	public ServletRegistrationBean DruidStatView() {
		
		ServletRegistrationBean sr = new ServletRegistrationBean();
		
		sr.setServlet(new StatViewServlet());
		
		List<String> list = new ArrayList();
		list.add("/druid/*");
		
		sr.setUrlMappings(list);
		
		return sr;
	}
	
}
