package com.lty.bookstore.system.backend.application.service;

import cn.hutool.core.util.IdUtil;
import com.lty.bookstore.system.backend.api.message.UserLoginResponse;
import com.lty.bookstore.system.backend.api.message.dto.UserInfoCache;
import com.lty.bookstore.system.backend.base.BaseStatusCode;
import com.lty.bookstore.system.backend.constant.RedisKeyConstant;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import com.lty.bookstore.system.backend.impl.domain.entity.UserBookInteraction;
import com.lty.bookstore.system.backend.impl.repository.BookRepository;
import com.lty.bookstore.system.backend.impl.repository.UserBookInteractionRepository;
import com.lty.bookstore.system.backend.impl.repository.UserRepository;
import com.lty.bookstore.system.backend.utils.JsonUtils;
import com.lty.bookstore.system.backend.utils.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserAppService {

    @Resource
    private RedisManager redisManager;
    @Resource
    private CaptchaAppService captchaService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookInteractionRepository userBookInteractionRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }

    public UserLoginResponse login(String userPhone, String smsCaptcha) {
        boolean isSuccess = captchaService.verifySmsCaptcha(userPhone, smsCaptcha);
        if (!isSuccess) {
            throw new IllegalStateException(BaseStatusCode.ERROR_PARAM_VALID_FAIL.getCode());
        }
        User user = userRepository.getUserByPhone(userPhone);
        if (user == null) {
            user = new User();
            user.setPhone(userPhone);
            user.setUserName(userPhone);
            user.setUserId(IdUtil.getSnowflake().nextIdStr());
            userRepository.save(user);
        }
        String newToken = UUID.randomUUID().toString();
        UserInfoCache cache = setToken(user.getUserId(), newToken, userPhone);
        return UserLoginResponse.builder()
                .phone(userPhone)
                .userId(user.getUserId())
                .token(cache.getToken())
                .build();
    }

    public UserInfoCache setToken(String userId, String token, String phone) {
        UserInfoCache userInfoCache = new UserInfoCache();
        userInfoCache.setToken(token);
        userInfoCache.setUserId(userId);
        userInfoCache.setPhone(phone);
        if (redisManager.hasKey(token)) {
            return userInfoCache;
        } else {
            redisManager.set(token, JsonUtils.toJson(userInfoCache), RedisKeyConstant.USER_TOKEN_TIMEOUT, TimeUnit.DAYS);
        }
        return userInfoCache;
    }

    public void sendSmsCaptcha(String userPhone) {
        captchaService.sendSmsCaptcha(userPhone);
    }

    public void removeToken(String token) {
        redisManager.del(token);
    }

    public User getUserByPhone(String phone) {
        return userRepository.getUserByPhone(phone);
    }

    public void createUserBookInteraction(String bookId, String userId, Integer viewTime) {
        UserBookInteraction interaction = new UserBookInteraction();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User is not found."));
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("The book is not found."));
        interaction.setBook(book);
        interaction.setUser(user);
        interaction.setViewTime(viewTime);
        userBookInteractionRepository.save(interaction);
    }
}
