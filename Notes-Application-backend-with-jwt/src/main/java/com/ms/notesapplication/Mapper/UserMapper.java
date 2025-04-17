package com.ms.notesapplication.Mapper;

import com.ms.notesapplication.dto.UserDtoResponse;
import com.ms.notesapplication.entity.MyUser;
import com.ms.notesapplication.entity.Roles;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDtoResponse userToUserDtoResponse(MyUser myUser) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setUsername(myUser.getUsername());

        Set<Roles> rolesSet = myUser.getRoles();

        Set<String> roles = rolesSet.stream().map(Roles::getRole).collect(Collectors.toSet());
        userDtoResponse.setRoles(roles);

        return userDtoResponse;
    }
}
