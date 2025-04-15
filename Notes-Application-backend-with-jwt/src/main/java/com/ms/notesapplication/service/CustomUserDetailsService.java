package com.ms.notesapplication.service;

import com.ms.notesapplication.entity.CustomerUserDetails;
import com.ms.notesapplication.entity.MyUser;
import com.ms.notesapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MyUser> userExist =  userRepository.findByUsername(username);

        var userObj = userExist.get();

        if(userObj == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return CustomerUserDetails.build(userObj);
    }
}
