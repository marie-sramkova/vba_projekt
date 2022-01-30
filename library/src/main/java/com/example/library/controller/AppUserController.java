package com.example.library.controller;

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

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AppuserRepository appuserRepository;

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
                AppuserEntity appuser = appuserRepository.save(user);

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
        if (oldPersistedAppUser.isPresent() && oldPersistedAppUser.get().getPassword().equals(oldPassword)) {
            if (newPassword != null && newPassword.length() > 0) {
                AppuserEntity newAppUser = new AppuserEntity(oldPersistedAppUser.get().getName(), newPassword, oldPersistedAppUser.get().getRoles());
                AppuserEntity newPersistedAppUser = appuserRepository.save(newAppUser);
                if (newPersistedAppUser != null) {
                    return ResponseEntity.ok("Successfully changed.");
                }
            }
        }
        return ResponseEntity.badRequest().body("Failed to change password.");
    }
    //endregion

    //region resetRoles
    @RequestMapping(value = "/resetRoles",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> resetRoles(@RequestParam("newRoles") String newRoles) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUsername = userDetails.getUsername();
        Optional<AppuserEntity> oldPersistedAppUser = appuserRepository.findById(loggedUsername);
        if (oldPersistedAppUser.isPresent()) {
            if (newRoles != null && newRoles.length() > 0) {
                if (newRoles.contains("user")) {
                    AppuserEntity newAppUser = new AppuserEntity(oldPersistedAppUser.get().getName(), oldPersistedAppUser.get().getName(), newRoles);
                    AppuserEntity newPersistedAppUser = appuserRepository.save(newAppUser);
                    if (newPersistedAppUser != null) {
                        return ResponseEntity.ok("Successfully changed.");
                    }
                } else {
                    newRoles = "user, ".concat(newRoles);
                    AppuserEntity newAppUser = new AppuserEntity(oldPersistedAppUser.get().getName(), oldPersistedAppUser.get().getName(), newRoles);
                    AppuserEntity newPersistedAppUser = appuserRepository.save(newAppUser);
                    if (newPersistedAppUser != null) {
                        return ResponseEntity.ok("Successfully changed.");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("Failed to change roles.");
    }
    //endregion
}
