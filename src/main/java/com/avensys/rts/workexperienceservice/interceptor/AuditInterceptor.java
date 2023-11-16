package com.avensys.rts.workexperienceservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/***
 * This class is used to intercept the request and inject audit data into the request context.
 * @author Kotaiah
 */

public class AuditInterceptor implements HandlerInterceptor   {

    private final Logger log = LoggerFactory.getLogger(AuditInterceptor.class);

    /**
     * This method is used to intercept the request and inject audit data into the request context.
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Pre-handling request");

        // Get In-time in milliseconds and set in request context
        request.setAttribute("startTime", System.currentTimeMillis());

        // Get Thread ID and set in request context
        String threadId = RandomStringUtils.randomAlphanumeric(10);
        request.setAttribute("threadId", threadId);

        log.info("Pre-handling request :: THREAD_ID :: {} ",threadId);

        return true; // Continue the request processing chain
    }

}

