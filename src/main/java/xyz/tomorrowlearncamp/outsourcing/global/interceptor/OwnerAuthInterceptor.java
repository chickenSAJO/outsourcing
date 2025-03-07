package xyz.tomorrowlearncamp.outsourcing.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.ErrorUserMessage;
import xyz.tomorrowlearncamp.outsourcing.domain.user.enums.Usertype;
import xyz.tomorrowlearncamp.outsourcing.global.exception.UnauthorizedRequestException;

public class OwnerAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getAttribute("usertype").toString();

        // 권한이 OWNER가 아닌 경우
        if (!Usertype.OWNER.name().equals(auth)) {
            throw new UnauthorizedRequestException(ErrorUserMessage.UNAUTHORIZED.getErrorMassage());
        }

        return true;
    }
}
