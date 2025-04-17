package com.ms.notesapplication.service;

import com.ms.notesapplication.Mapper.UserMapper;
import com.ms.notesapplication.dto.*;
import com.ms.notesapplication.entity.MyUser;
import com.ms.notesapplication.entity.Roles;
import com.ms.notesapplication.exception.RoleNotFoundException;
import com.ms.notesapplication.exception.UserIdNotFoundException;
import com.ms.notesapplication.exception.UsernameAlreadyExistsException;
import com.ms.notesapplication.jwt.JwtUtils;
import com.ms.notesapplication.repository.RoleRepository;
import com.ms.notesapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public SignupResponse userSignup(SignupRequest signupRequest) {

        Optional<MyUser> user = userRepository.findByUsername(signupRequest.getUsername());

        if (user.isPresent()) {
            throw new UsernameAlreadyExistsException("Username is already in use by another user. Please try with another username");
        }
        MyUser myUser = new MyUser();
        myUser.setUsername(signupRequest.getUsername());
        myUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        Set<Roles> roles = new HashSet<>();

        for(String roleName : signupRequest.getRoles()) {
            Roles role = roleRepository.findByRole(roleName);
            if(role != null) {
                roles.add(role);
            }
            else{
                throw new RoleNotFoundException("Role not found with name " + roleName);
            }
        }

        myUser.setRoles(roles);
        MyUser newUser = userRepository.save(myUser);

        Set<String> roleResponse = newUser.getRoles().stream()
                .map((r)-> r.getRole()).collect(Collectors.toSet());

        System.out.println("Username : " + newUser.getUsername() + " Roles : " + roleResponse);

        return new SignupResponse(newUser.getUsername(),roleResponse);
    }

    @Override
    public LoginJwtResponse userLogin(LoginRequest loginRequest) {

        Optional<MyUser> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isEmpty())
            throw new UsernameNotFoundException("Username not found with name " + loginRequest.getUsername());

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        MyUser myUser = user.get();

        Set<String> roles = myUser.getRoles().stream()
                .map((r)-> r.getRole()).collect(Collectors.toSet());

        LoginJwtResponse loginJwtResponse = new LoginJwtResponse();
        loginJwtResponse.setUsername(myUser.getUsername());
        loginJwtResponse.setRoles(roles);
        loginJwtResponse.setType("Basic");
        loginJwtResponse.setToken("Authenticated User");

        System.out.println("Username : " + myUser.getUsername() + " Roles : " + roles);

        return loginJwtResponse;
    }

    @Override
    public LoginJwtResponse userJwtLogin(LoginRequest loginRequest) {
        Optional<MyUser> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with name " + loginRequest.getUsername());
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            throw new RuntimeException("Incorrect password");
        }


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtUtils.generateJwtToken(authentication);

        String username = jwtUtils.extractUsernameFromToken(jwtToken);

        Set<String> roles = jwtUtils.extractRolesFromToken(jwtToken);

        LoginJwtResponse loginJwtResponse = new LoginJwtResponse();
        loginJwtResponse.setUsername(username);
        loginJwtResponse.setRoles(roles);
        loginJwtResponse.setType("Bearer");
        loginJwtResponse.setToken(jwtToken);

        return loginJwtResponse;

    }

    @Override
    public UserDtoResponse updateUserRoleDetails(UserDtoRequest userDtoRequest) {

        MyUser user = userRepository.findById(userDtoRequest.getId())
                .orElseThrow(()-> new UserIdNotFoundException("User not found with id " + userDtoRequest.getId()));

        user.setUsername(userDtoRequest.getUsername());

        Set<Roles> rolesSet = new HashSet<>();

        for(String roleName : userDtoRequest.getRoles()) {
            Roles role = roleRepository.findByRole(roleName);
            if(role != null) {
                rolesSet.add(role);
            }
            else {
                throw new RoleNotFoundException("Role not found with name " + roleName);
            }
        }

        user.setRoles(rolesSet);

        MyUser updatedUser = userRepository.save(user);

        return UserMapper.userToUserDtoResponse(updatedUser);
    }

    @Override
    public List<UserDtoResponse> getAllUserDetails() {

        List<MyUser> users = userRepository.findAll();

        List<UserDtoResponse> userDtoResponseList = users.stream()
                .map((u)-> UserMapper.userToUserDtoResponse(u)).collect(Collectors.toList());

        return userDtoResponseList;
    }

    @Override
    public UserDtoResponse getUserById(Long id) {

        MyUser user = userRepository.findById(id)
                .orElseThrow(()-> new UserIdNotFoundException("User not found with id " + id));

        UserDtoResponse userDtoResponse = UserMapper.userToUserDtoResponse(user);

        System.out.println(userDtoResponse.toString());

        return userDtoResponse;
    }
}
