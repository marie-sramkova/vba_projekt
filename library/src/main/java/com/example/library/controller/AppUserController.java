package com.example.library.controller;

import com.example.library.encoder.AppUserPasswordEncoder;
import com.example.library.entity.AppuserEntity;
import com.example.library.model.AuthRequestModel;
import com.example.library.repository.AppuserRepository;
import com.example.library.service.JwtService;
import com.example.library.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AppUserController {

    private MyUserDetailsService myUserDetailsService;

    @Autowired
    public AppUserController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppuserRepository appuserRepository;
    @Autowired
    private AppUserPasswordEncoder passwordEncoder;

    // region authentication
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getJwtAuthToken(@RequestBody AuthRequestModel authRequestModel) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestModel.getUserName(), authRequestModel.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect userName and password", ex);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(authRequestModel.getUserName());
        final String jwt = jwtService.getJwt(userDetails);
        return jwt;
    }
    // endregion

    //region registration
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AppuserEntity> create(@RequestBody AppuserEntity user) {
        if (user != null && user.getName() != null && user.getPassword() != null &&
                (user.getName().length() > 0) && (user.getPassword().length() > 0)) {
            if (!appuserRepository.findById(user.getName()).isPresent()) {
                if (user.getRoles() == null || user.getRoles().isEmpty()) {
                    user.setRoles("user");
                }
                if (!user.getRoles().contains("user")) {
                    user.setRoles("user, ".concat(user.getRoles()));
                }
                AppuserEntity appuser = myUserDetailsService.registerUser(user);
                return ResponseEntity.ok(appuser);
            }
        }
        return ResponseEntity.badRequest().body(user);
    }
    //endregion

    //region resetPassword
    @RequestMapping(value = "/resetPassword",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> resetPassword(@RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUsername = userDetails.getUsername();
        Optional<AppuserEntity> oldPersistedAppUser = appuserRepository.findById(loggedUsername);
        if (oldPersistedAppUser.isPresent() && passwordEncoder.matches(oldPassword, oldPersistedAppUser.get().getPassword())) {
            if (newPassword != null && newPassword.length() > 0) {
                AppuserEntity newAppUser = new AppuserEntity(oldPersistedAppUser.get().getName(), newPassword, oldPersistedAppUser.get().getRoles());
                AppuserEntity newPersistedAppUser = myUserDetailsService.registerUser(newAppUser);
                if (newPersistedAppUser != null) {
                    return ResponseEntity.ok("Successfully changed.");
                }
            }
        }
        return ResponseEntity.badRequest().body("Failed to change password.");
    }
    //endregion
}
