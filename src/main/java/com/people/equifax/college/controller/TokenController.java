/**
 * 
 */
package com.people.equifax.college.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.people.equifax.college.model.AuthenticationRequest;
import com.people.equifax.college.model.AuthenticationResponse;
import com.people.equifax.college.service.MyUserDetailService;
import com.people.equifax.college.util.JwtTokenUtil;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class TokenController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping("/GET/token")
	public ResponseEntity<Object> createAuthenticationToken()
			throws Exception {
		
		AuthenticationRequest authRequest = new AuthenticationRequest();

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
