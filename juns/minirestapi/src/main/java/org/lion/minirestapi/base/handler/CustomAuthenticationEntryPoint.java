package org.lion.minirestapi.base.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Object exceptionAttribute = request.getAttribute("exception");
        String code = exceptionAttribute == null
                ? JwtExceptionCode.NOT_FOUND_TOKEN.getCode()
                : exceptionAttribute.toString();


        JwtExceptionCode exceptionCode = JwtExceptionCode.fromCode(code);
        if(exceptionCode == null){
            log.error("Commence Occurred :: {}", authException.getMessage());
            exceptionCode = JwtExceptionCode.UNKNOWN_ERROR;
        }

//        if(isRestRequest(request)){
////            handleRestResponse(exception, request, response);
////        } else {
////            handlePageRequest(exception, request, response, authException);
////        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> error = new HashMap<>();
        error.put("code", exceptionCode.getCode());
        error.put("message", exceptionCode.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(error));

    }

    private void handleRestResponse(String exception, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.error(exception);
        JwtExceptionCode jwtExceptionCode = JwtExceptionCode.fromCode(exception);
        if(jwtExceptionCode == JwtExceptionCode.UNKNOWN_ERROR && exception == null){
            log.error("Unknown error :: {}", exception);
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        HashMap<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("code", jwtExceptionCode.getCode());
        errorInfo.put("message", jwtExceptionCode.getMessage());

        String responseJson = objectMapper.writeValueAsString(errorInfo);
        response.getWriter().println(responseJson);

    }

    private void handlePageRequest(String exception, HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        log.error(exception);
    }



    private boolean isRestRequest(HttpServletRequest request){
        String uri = request.getRequestURI();
        String header = request.getHeader("X-Requested-With");
        boolean isAPI = uri.startsWith("/api/") || uri.equals("/error");

        return "XMLHttpRequest".equals(header) && isAPI;
    }
}
