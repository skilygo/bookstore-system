package com.lty.bookstore.system.backend.api.controller;


import com.lty.bookstore.system.backend.api.message.AuthRequest;
import com.lty.bookstore.system.backend.api.message.dto.AccessTokenDto;
import com.lty.bookstore.system.backend.application.service.AuthAppService;
import com.lty.bookstore.system.backend.base.Response;
import com.lty.bookstore.system.backend.impl.domain.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AuthController")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthAppService authAppService;

    /**
     * get Authorization token.
     */
    @PostMapping("/token")
    public Response<AccessTokenDto> auth2Token(@RequestBody AuthRequest request) {
        AccessToken accessToken = authAppService.auth2Token(request.getClientId(), request.getClientSecret());
        return Response.success(
                AccessTokenDto.builder()
                        .accessToken(accessToken.getAccessToken())
                        .expiresIn(accessToken.getExpiresIn())
                        .build()
        );
    }

}
