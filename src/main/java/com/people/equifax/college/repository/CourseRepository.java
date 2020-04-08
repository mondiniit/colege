/**
 * 
 */
package com.people.equifax.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.people.equifax.college.model.Course;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com
 * version 1.0
 */

public interface CourseRepository extends JpaRepository<Course, Long>{
}
