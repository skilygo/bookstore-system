package com.lty.bookstore.system.backend.api.support;

import com.lty.bookstore.system.backend.api.message.GetUserReponse;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserAssembler {

    public abstract GetUserReponse toUserResponse(User user);
}
