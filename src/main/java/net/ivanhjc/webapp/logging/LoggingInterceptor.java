package net.ivanhjc.webapp.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ivanhjc.utility.net.HttpRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder(request.getMethod()).append(" ").append(request.getRequestURL());
            if (!StringUtils.isEmpty(request.getQueryString())) {
                builder.append("?").append(request.getQueryString());
            }
            log.debug("Request Info: {}", builder);
        }

        if (log.isTraceEnabled()) {
            String parameterMap = "";
            try {
                ObjectMapper mapper = new ObjectMapper();
                parameterMap = mapper.writeValueAsString(HttpRequestUtils.getParameterMap(request));
            } catch (JsonProcessingException e) {
                log.error("parameterMap can't be written as JSON", e);
            }

            StringBuilder builder = new StringBuilder()
                    .append("Method: ").append(request.getMethod()).append("\n")
                    .append("Request URL: ").append(request.getRequestURL()).append("\n")
                    .append("Query String: ").append(request.getQueryString()).append("\n")
                    .append("Parameter Map: ").append(parameterMap).append("\n")
                    .append("Status: ").append(response.getStatus()).append("\n")
                    .append("Headers:\n");

            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String value = request.getHeader(name);
                builder.append("  ").append(name).append(": ").append(value).append("\n");
            }

            builder.append("Server Name: ").append(request.getServerName()).append("\n")
                    .append("Server Port: ").append(request.getServerPort()).append("\n")
                    .append("Request URI: ").append(request.getRequestURI()).append("\n")
                    .append("Servlet Path: ").append(request.getServletPath()).append("\n")
                    .append("Remote Address: ").append(request.getRemoteAddr()).append("\n")
                    .append("Remote Port: ").append(request.getRemotePort()).append("\n")
                    .append("Local Address: ").append(request.getLocalAddr()).append("\n")
                    .append("Local Port: ").append(request.getLocalPort()).append("\n")
                    .append("Remote Host (IP): ").append(HttpRequestUtils.getRemoteHost(request));
            log.trace("Request Info:\n{}", builder);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}