package fr.eservices.drive.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

// set this class as a "Configuration" component to contribute to spring context
@Configuration
// enable spring web mvc
@EnableWebMvc
// scan packages for component and controllers
@ComponentScan(basePackages = {"fr.eservices.drive.dao","fr.eservices.drive.web"})
public class AppConfig implements WebApplicationInitializer {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(ctx));
		registration.setLoadOnStartup(1);
		registration.addMapping("*.html", "*.json");
	}
	
	// expose this as a Bean for spring context
	@Bean
	public ViewResolver viewResolver() {
		// create and configure an InternalResourceViewResolver
		InternalResourceViewResolver rex = new InternalResourceViewResolver();
		rex.setPrefix("/WEB-INF/views/");
		rex.setSuffix(".jsp");
		return rex;
	}

}
