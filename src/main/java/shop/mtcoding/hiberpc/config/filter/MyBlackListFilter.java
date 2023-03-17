package shop.mtcoding.hiberpc.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.util.ObjectUtils;

public class MyBlackListFilter implements Filter { // web.xml 에 등록하지 않고 스프링 컨테이너에 등록해보자 - 이유 DB 접근까지 가능하게 하려고 - @Conponent 를 이용할수도 - 권장하지 않음

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 필터의 주의점 - 버퍼를 reader로 읽어버리면 버퍼가 날라가니까 하지마.. 확인하려고 읽었으면 다시 채워서 다음필터로 보내야함
        // 설계적으로도 말이 안되는게 모든 버퍼를 읽을 필요가 있냐 ? - 요청된 주소로 갈때 인터셉터가 확인하면 되는데
        // 필터는 좀더 전역적인일을 해야해 - ip차단같은거, 진짜 블랙리스트 같은거
        
        String value = request.getParameter("value"); // x-www-form 형식의 데이터를 꺼내서 확인 - 욕을 써놨으면 필터링해볼까 ?
        if(ObjectUtils.isEmpty(value)){
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("value 파라미터를 전송해주세요");
            return;
        }
        if(value.equals("바보")){
            response.setContentType("text/plain; charset=utf-8");
            response.getWriter().println("당신은 블랙리스트가 되었습니다."); // 한글 넣으면 깨진다고 ? 디스패처 서블릿으로 들어오지 않기 때문이라고 함
            return;
        }
        chain.doFilter(request, response); // 정삭적일경우 필터체인을 타면됨
        
    }
    
}
