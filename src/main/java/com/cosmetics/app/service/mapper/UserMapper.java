package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.User;
import com.cosmetics.app.model.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    List<UserDto> toListDto(List<User> users);
}
