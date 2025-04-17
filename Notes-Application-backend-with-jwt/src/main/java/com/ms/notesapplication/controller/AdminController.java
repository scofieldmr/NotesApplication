package com.ms.notesapplication.controller;

import com.ms.notesapplication.dto.UserDtoRequest;
import com.ms.notesapplication.dto.UserDtoResponse;
import com.ms.notesapplication.repository.RoleRepository;
import com.ms.notesapplication.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admincont")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/userlist")
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> userDtoResponseList = userService.getAllUserDetails();
        return new ResponseEntity<>(userDtoResponseList, HttpStatus.OK);
    }

    @GetMapping("/getUser/{u_id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable("u_id") Long id) {
        UserDtoResponse userDtoResponse = userService.getUserById(id);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

    @PutMapping("/updUser")
    public ResponseEntity<UserDtoResponse> updateUserDetails(@RequestBody
                                                                 @Validated({Default.class})UserDtoRequest userDtoRequest){
        UserDtoResponse userDtoResponse = userService.updateUserRoleDetails(userDtoRequest);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }

}
