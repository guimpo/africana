/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.controller;

import com.utfpr.audiomanager.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.HttpMethod;
import org.apache.commons.io.IOUtils;
import static org.hibernate.internal.util.io.StreamCopier.BUFFER_SIZE;
import org.springframework.web.util.ContentCachingResponseWrapper;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author josevictor
 */
@WebFilter(filterName = "FiltroCache", urlPatterns = {"/api/audio2/*"})
public class FiltroCache implements Filter {
    
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    private static JedisPool pool = null;    
   

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        if (debug) {
            log("FiltroCache:doFilter()");
        }
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        ContentCachingResponseWrapper respWrapper = new ContentCachingResponseWrapper(resp);
        String hashUrl = null;
        String cachedContent = null;
        String value = "";
        Jedis jedis = null;
        
        try {
            jedis = pool.getResource();
            if (req.getMethod().compareTo(HttpMethod.GET) == 0) {
                hashUrl = stringifyHttpGetRequest(req);
                log("HTTP GET on [" + hashUrl + "]");
                value = jedis.get(hashUrl);
                if (value == null) {
                    log("Cache miss! on hashUrl " + hashUrl);
                    chain.doFilter(request, respWrapper);
                    String responseBody = IOUtils.toString(respWrapper.getContentInputStream(), UTF_8);
                    String cachedValue = responseBody;
                    if (hashUrl != null && cachedValue != null) {                        
                        log("Hashing url " + hashUrl);
                        jedis.set(hashUrl, cachedValue);
                        response.setContentType("application/json;charset=UTF-8");
                        response.getOutputStream().print(cachedValue);
                        response.getOutputStream().flush();
                    }
                } else {
                    log("Cache hit!");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getOutputStream().print(value);
                    response.getOutputStream().flush();
                }
            } else {
                String sessionId = req.getSession().getId();
                Set<String> keys = jedis.keys(sessionId + req.getRequestURL() + "*");
                for (String key : keys) {
                    jedis.del(key);
                }
                chain.doFilter(request, response);
            }         
        } finally {
            pool.returnResource(jedis);
        }
    }
    
    private String stringifyHttpGetRequest(HttpServletRequest req) {
        String sessionId = req.getSession().getId();
        log("USUARIO: " + sessionId);
        return sessionId + req.getRequestURL() + requestParamsToString(req);
    }
    
    private static String requestParamsToString(HttpServletRequest req) {
        StringBuilder builder = new StringBuilder();

        Map<String, String[]> params = req.getParameterMap();
        for (String param : params.keySet()) {
            for (String value : params.get(param)) {
                if (builder.length() == 0) {
                    builder.append(param).append("=").append(value);
                } else {
                    builder.append("&").append(param).append("=").append(value);
                }
            }
        }
        return builder.toString();
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
        pool.destroy();
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("FiltroCache:Initializing filter");
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
