package com.lty.bookstore.system.backend.api.controller;

import com.lty.bookstore.system.backend.api.message.CreateUserBookInteractionRequest;
import com.lty.bookstore.system.backend.api.message.CreateUserRequest;
import com.lty.bookstore.system.backend.api.message.GetUserReponse;
import com.lty.bookstore.system.backend.api.message.UserSmsCaptchaRequest;
import com.lty.bookstore.system.backend.api.support.UserAssembler;
import com.lty.bookstore.system.backend.application.service.UserAppService;
import com.lty.bookstore.system.backend.base.Response;
import com.lty.bookstore.system.backend.filter.UserContextHolder;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private UserAssembler userAssembler;

    /**
     * login
     * @param createUserRequest
     * @return
     */
    @PostMapping("/public/login")
    public Response<?> login(@RequestBody CreateUserRequest createUserRequest) {
        return Response.success(userAppService.login(createUserRequest.getUserPhone(), createUserRequest.getSmsCaptcha()));
    }

    /**
     * send captcha
     * @param UserSmsCaptchaRequest
     * @return
     */
    @PostMapping("/public/sendSmsCaptcha")
    public Response<?> sendSmsCaptcha(@Valid @RequestBody UserSmsCaptchaRequest req) {
        userAppService.sendSmsCaptcha(req.getUserPhone());
        return Response.success();
    }

    /**
     * retrieve user info
     * @return
     */
    @GetMapping
    public Response<GetUserReponse> getUser() {
        User user = userAppService.getUser(UserContextHolder.getUserId());
        return Response.success(userAssembler.toUserResponse(user));
    }

    /**
     * user logout
     * @param beToken
     * @return
     */
    @PostMapping("/logout")
    public Response<?> logout(@RequestHeader("userToken") String beToken) {
        userAppService.removeToken(beToken);
        return Response.success();
    }

    /**
     * crate user's interaction
     * @param bookId
     * @param request
     * @return
     */
    @PostMapping("/book/{bookId}/interaction")
    public Response<?> createUserBookInteraction(@PathVariable String bookId, @RequestBody CreateUserBookInteractionRequest request) {
        userAppService.createUserBookInteraction(bookId, UserContextHolder.getUserId(), request.getViewTime());
        return Response.success();
    }
}
