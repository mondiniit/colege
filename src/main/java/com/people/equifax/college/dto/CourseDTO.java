/**
 * 
 */
package com.people.equifax.college.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com 
 * version 1.0
 */

public class CourseDTO implements Serializable {
	private static final long serialVersionUID = -4817591512277259587L;

	private String name;
	
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
