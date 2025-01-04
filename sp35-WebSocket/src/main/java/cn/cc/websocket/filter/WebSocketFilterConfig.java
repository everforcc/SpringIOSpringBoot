package cn.cc.websocket.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * servlet级别请求过滤
 */
@Slf4j
@Component
public class WebSocketFilterConfig implements Filter {

    // websocket请求过滤清单
    private static final String[] FILTER_LIST = {"/websocket"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String servletPath = httpServletRequest.getServletPath();

        log.info("servletPath: {}", servletPath);
        log.info("result: {}", servletPath.startsWith("/websocket"));
        // anyMatch： 有一个条件满足就返回true
        boolean match = Arrays.stream(FILTER_LIST).anyMatch(servletPath::startsWith);

        // 符合ws请求的url开始校验，其他请求一概放过
        if (match) {
            String token = httpServletRequest.getHeader("token");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 不包含过滤清单，直接放过请求
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
