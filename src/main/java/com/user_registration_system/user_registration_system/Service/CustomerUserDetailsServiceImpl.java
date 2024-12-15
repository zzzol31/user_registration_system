package com.user_registration_system.user_registration_system.Service;

import com.user_registration_system.user_registration_system.Entity.User;
import com.user_registration_system.user_registration_system.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomerUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
