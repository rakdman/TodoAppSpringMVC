package com.todoproject.security;

import com.todoproject.model.User;
import com.todoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * This class is developed to implement basic web security
 *
 * @author Rakesh Kumar
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUsername(userName);

        if (user == null)
            throw new UsernameNotFoundException(userName);

        System.out.println(user);
        return user.map(CustomUserDetails::new).get();

    }

}
