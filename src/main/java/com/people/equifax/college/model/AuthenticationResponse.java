/**
 * 
 */
package com.people.equifax.college.model;

import java.io.Serializable;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com
 * version 1.0
 */

public class AuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 4634139983855465066L;
	private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
