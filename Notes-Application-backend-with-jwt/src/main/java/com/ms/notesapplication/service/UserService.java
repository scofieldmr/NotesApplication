package com.ms.notesapplication.service;

import com.ms.notesapplication.dto.LoginJwtResponse;
import com.ms.notesapplication.dto.LoginRequest;
import com.ms.notesapplication.dto.SignupRequest;
import com.ms.notesapplication.dto.SignupResponse;

public interface UserService {

      SignupResponse userSignup(SignupRequest signupRequest);

      LoginJwtResponse userLogin(LoginRequest loginRequest);

      LoginJwtResponse userJwtLogin(LoginRequest loginRequest);
}
