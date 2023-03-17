package shop.mtcoding.hiberpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import shop.mtcoding.hiberpc.config.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // WebMvcConfigurerAdapter는 WebMvcConfigurer 구현했는데 자기가 다 오버라이딩함 - 강제성을 없애버림
    // 어뎁터는 결국 수많은 오버라이딩을 전부 적어야하는 코드가 귀찮아서 만든건데
    // WebMvcConfigurer 자체가 디폴트 메소드를 가져버리니까 어뎁터의 존재 이유가 사라진것...그럼 디폴트 메소드의 존재 이유가 뭐였지 ?
    // 인터페이스를 여기저기서 사용했을때 추상메소드를 변경해버리면 참조하는 모든 코드들이 망가져버린다.
    // 이때 디폴트 메소드를 이용해서 기능을 추가하면 기존 코드는 수정할 필요가 없어진다.
    // 디폴트 메소드를 추가하지 않으면 추상메소드를 추가해야하는데 인터페이스에 필요로 인해서 추가 했지만 결국 해당 인터페이스를 사용하는 모든 코드는 해당 메소들르 구현해야하는 문제가 발생함
    // 디폴트 메소드를 사용함으로써 OCP를 지키는 효과도 있다.
    // 즉 인터페이스의 보완을 계속해서 진행할수 있게 된다.
    // 이러한 행위를함은 결국 ISP  인터페이스 분리 법칙을 지키기 위함인데 인터페이스를 분리시켜서 인터페이스를 사용하는 다른 코드들에 영향을 주지 않기 위함.
    // 디폴트 메소드를 사용하면 해당 인터페이스를 구현하는 클래스에는 강제성이 없기때문에 필요한 메소드만 구현하면 된다.
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/s/*");
    }
}
