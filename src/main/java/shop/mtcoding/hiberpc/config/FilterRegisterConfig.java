package shop.mtcoding.hiberpc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.hiberpc.config.filter.MyBlackListFilter;

@Configuration // 설정파일에 붙여줌 ( Component 가 포함되어 있음 )
public class FilterRegisterConfig {
    // private final MyBlackListFilter myBlackListFilter;
    
    @Bean // Configuration + Bean 일때 클래스가 IOC에 등록되고 초기화될때 Bean을 찾아서 invoke()를 실행시켜준다 !! 이게 편해 
    public FilterRegistrationBean<?> blackListFilter(){
        FilterRegistrationBean<MyBlackListFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyBlackListFilter());
        registration.addUrlPatterns("/filter");
        registration.setOrder(1);
        return registration;
    }
}
