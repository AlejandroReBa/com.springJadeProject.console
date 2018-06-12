package com.springJadeProject.console;

//import org.apache.myfaces.webapp.StartupServletContextListener;
import com.sun.faces.config.ConfigureListener;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import java.util.EnumSet;

@SpringBootApplication
public class ConsoleApplication extends SpringBootServletInitializer{// implements ServletContextAware{

	public static void main(String[] args) {
		SpringApplication.run(ConsoleApplication.class, args);
	}

    //To configure JSF with Spring Boot, we need to create these two more beans.
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean<>(servlet, "*.xhtml");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public FilterRegistrationBean rewriteFilter() {
		FilterRegistrationBean rewriteFilter = new FilterRegistrationBean<>(new RewriteFilter());
		rewriteFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
				DispatcherType.ASYNC, DispatcherType.ERROR));
		rewriteFilter.addUrlPatterns("/*");

		return rewriteFilter;
	}


	//https://stackoverflow.com/questions/46187725/spring-boot-jsf-integration/46190826#46190826
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration",
//                Boolean.TRUE.toString());
//        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
//    }

//    @Bean
//    public FacesServlet facesServlet() {
//        return new FacesServlet();
//    }

    //https://stackoverflow.com/a/25509937/4733587
//    @Bean
//    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
//        return new ServletListenerRegistrationBean<>(
//                new ConfigureListener());
//    }
//
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//    }
}
