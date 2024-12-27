package com.lty.bookstore.system.backend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lty.bookstore.system.backend.BookStoreProperties;
import com.lty.bookstore.system.backend.api.message.dto.UserInfoCache;
import com.lty.bookstore.system.backend.application.service.UserAppService;
import com.lty.bookstore.system.backend.base.BaseStatusCode;
import com.lty.bookstore.system.backend.base.Response;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import com.lty.bookstore.system.backend.utils.JsonUtils;
import com.lty.bookstore.system.backend.utils.RedisManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lty.bookstore.system.backend.application.service.AuthAppService.CLAIM_SCOPE;
import static com.lty.bookstore.system.backend.application.service.AuthAppService.SCOPE_SYSTEM;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthorizationContextFilter extends OncePerRequestFilter {

    private static final String AUTH_PATH = "/auth/token";

    private static final String BEARER_PREFIX = "Bearer ";

    private static final String USER_AUTHORIZE_TOKEN = "userToken";

    @Autowired
    private BookStoreProperties bookStoreProperties;

    @Autowired
    private RedisManager<String> redisManager;


    @Autowired
    private UserAppService userAppService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (requestUri.contains(AUTH_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            writerError(response);
            return;
        }
        String token = header.substring(BEARER_PREFIX.length());
        if (!parseToken(token)) {
            writerError(response);
            return;
        }

        String userToken = request.getHeader(USER_AUTHORIZE_TOKEN);
        if (needUserAuth(requestUri)) {
            logger.info("Uri with AuthorizeRequired: " + requestUri);
            if (!isTokenValid(userToken)) {
                response.getWriter().write(JsonUtils.toJson(Response
                        .fail(BaseStatusCode.ERROR_AUTH_FAIL)));
                return;
            }
        }
        if (!StringUtils.isEmpty(userToken)) {
            setUserContext(userToken);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.removeUserContext();
        }
    }

    private boolean needUserAuth(String uri) {
        return !uri.contains("public");
    }

    private static void writerError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");
        response.getWriter().write(JsonUtils
                .toJsonIfPresent(Response.fail("Authorization token is missing or invalid")));
    }

    private boolean parseToken(String token) {
        boolean result = false;
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(bookStoreProperties.getOpenApi().getSecret())).build().verify(token);
            String scope = jwt.getClaim(CLAIM_SCOPE).asString();
            if (SCOPE_SYSTEM.equals(scope)) {
                String sysId = jwt.getClaim(SCOPE_SYSTEM).asString();
                if (!StringUtils.isBlank(sysId)) {
                    result = true;
                }
            }
        } catch (Exception e) {
            logger.error("parseToken occur error:", e);
        }
        return result;
    }


    private boolean isTokenValid(String userToken) {
        boolean flag = true;
        String userCacheStr = redisManager.get(userToken);
        if (!StringUtils.isEmpty(userCacheStr)) {
            UserInfoCache userInfoCache = JsonUtils.toObjectIfPresent(userCacheStr, UserInfoCache.class);
            String phone = userInfoCache.getPhone();
            User user = userAppService.getUserByPhone(phone);
            if (user == null || StringUtils.isEmpty(userInfoCache.getUserId())
                    || !userInfoCache.getUserId().equals(user.getUserId())) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    private void setUserContext(String userToken) {
        String userCacheStr = redisManager.get(userToken);
        if (!StringUtils.isEmpty(userCacheStr)) {
            UserInfoCache userInfoCache = JsonUtils.toObjectIfPresent(userCacheStr, UserInfoCache.class);
            UserContext context = new UserContext();
            context.setToken(userInfoCache.getToken());
            context.setUserId(userInfoCache.getUserId());
            context.setPhone(userInfoCache.getPhone());
            UserContextHolder.setUserContext(context);
        }
    }
}
