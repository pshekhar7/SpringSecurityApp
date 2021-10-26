package com.pshekhar.securitymodule.jwt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ApiKeyVerifier extends OncePerRequestFilter {
    private static final Map<String, String> clientMap = new HashMap<>();

    {
        clientMap.put("apikey123", "bigbasket");
        clientMap.put("apikey456", "Swiggy");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);
        } else {
            if (StringUtils.isBlank(request.getHeader("apikey"))) {
                String errorMessage = "Invalid request headers";
                log.error(errorMessage);
                JSONObject res = new JSONObject();
                res.put("error", errorMessage);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(res.toString());
                return;
            }

            String apiKey = request.getHeader("apikey");

            if (clientMap.containsKey(apiKey)) {
                log.info("Identified client as: " + clientMap.get(apiKey));
                request.setAttribute("client", apiKey);
                filterChain.doFilter(request, response);
            } else {
                String errorMessage = "Invalid apikey [" + apiKey + "]";
                log.error(errorMessage);
                JSONObject res = new JSONObject();
                res.put("error", errorMessage);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(res.toString());
                return;
            }
        }
    }
}
