package com.varsha.ecommerce.controller;

import com.varsha.ecommerce.dto.AuthenticationDTO;
import com.varsha.ecommerce.entity.User;
import com.varsha.ecommerce.repository.UserRepository;
import com.varsha.ecommerce.service.CustomUserDetailsService;
import com.varsha.ecommerce.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public void createAuthToken(@RequestBody AuthenticationDTO dto, HttpServletResponse response) throws
            JSONException, IOException {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username and/or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        final String jwt = jwtService.generateToken(userDetails);
        response.getWriter().write(new JSONObject().put("userId", user.getId())
                .put("role", user.getRole())
                .toString()
        );

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }

}
