package com.omkar.hmdrfserver.controller;

import com.omkar.hmdrfserver.exchanges.ApiResponse;
import com.omkar.hmdrfserver.exchanges.JwtAuthenticationRequest;
import com.omkar.hmdrfserver.exchanges.JwtAuthenticationResponse;
import com.omkar.hmdrfserver.exchanges.SignUpUser;
import com.omkar.hmdrfserver.security.JwtTokenUtil;
import com.omkar.hmdrfserver.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Register, Authenticate, Authorize and Ping User
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String pingOpen() {
        log.info("Ping called!");
        return "PONG!";
    }

    @PostMapping(value = "/username/get")
    public @ResponseBody ResponseEntity<?> pingUnsecure(@RequestHeader("Authorization") String token) {
        log.info("Call Get username with TOKEN : {}",token);
        String name = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return ResponseEntity.ok(new ApiResponse(true, name));
    }

    @GetMapping(value = "/validate")
    public @ResponseBody ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        String name = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        log.info("Call Get VALIDATE token for user : {}", name);
        return ResponseEntity.ok(new ApiResponse(true, name));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody SignUpUser user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    /**
     * After registering, use this to authenticate and generate JWT token
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        log.info("AUTHENTICATE request for user: {} pass: {}",authenticationRequest.getUsername(), authenticationRequest.getPassword());
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(true, token));
    }

//    public ResponseEntity<?> getUsername()

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }
}
