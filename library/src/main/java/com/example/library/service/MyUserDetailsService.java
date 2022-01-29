package com.example.library.service;

import com.example.library.entity.AppuserEntity;
import com.example.library.repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    AppuserRepository appuserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppuserEntity> appuser = appuserRepository.findById(username);
        if (appuser.isPresent()){
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.asList(appuser.get().getRoles().split(",")).stream().forEach(authority -> {
                authorities.add(new SimpleGrantedAuthority(authority));
            });
            return new User(appuser.get().getName(), appuser.get().getPassword(), authorities);
        }else {
            throw new UsernameNotFoundException("User " + username + " does not exists.");
        }
    }
}
