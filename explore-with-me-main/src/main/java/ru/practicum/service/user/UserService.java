package ru.practicum.service.user;

import ru.practicum.dto.user.NewUserRequest;
import ru.practicum.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsersByAdmin(List<Long> ids, int from, int size);

    UserDto addNewUserByAdmin(NewUserRequest userRequest);

    void deleteUserByAdmin(long userId);
}