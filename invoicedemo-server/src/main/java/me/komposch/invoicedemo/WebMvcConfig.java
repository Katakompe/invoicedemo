package me.komposch.invoicedemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;


@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Enables Cors from the frontend.
     * This is done for local development and should be handled with care in productive applications and environments.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**");
    }

    /**
     * Publishes angular frontend webjar as static resource.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/invoicedemo-frontend/browser/");

    }

    /**
     * Forwards root path "/" to index.html resource of frontend webjar.
     */
    @Bean
    RouterFunction<ServerResponse> spaRouter() {
        ClassPathResource index = new ClassPathResource("META-INF/resources/invoicedemo-frontend/browser/index.html");
        return route().resource(path("/"), index).build();
    }
}

