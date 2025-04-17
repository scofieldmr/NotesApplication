package com.ms.notesapplication.service;

import com.ms.notesapplication.dto.*;

import java.util.List;

public interface UserService {

      SignupResponse userSignup(SignupRequest signupRequest);

      LoginJwtResponse userLogin(LoginRequest loginRequest);

      LoginJwtResponse userJwtLogin(LoginRequest loginRequest);

      //Admin functions
      UserDtoResponse updateUserRoleDetails(UserDtoRequest userDtoRequest);

      List<UserDtoResponse> getAllUserDetails();

      UserDtoResponse getUserById(Long id);


}
