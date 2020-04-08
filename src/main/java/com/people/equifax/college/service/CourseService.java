/**
 * 
 */
package com.people.equifax.college.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.people.equifax.college.dto.CourseDTO;
import com.people.equifax.college.exception.GenericException;
import com.people.equifax.college.model.Course;
import com.people.equifax.college.repository.CourseRepository;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com 
 * version 1.0
 */

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> getAllCoursesPaginated(Integer pageNo, Integer pageSize, String sortBy)
			throws GenericException {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Course> pagedResultCourse = courseRepository.findAll(paging);

		if (pagedResultCourse.hasContent()) {
			return pagedResultCourse.getContent();
		} else {
			return new ArrayList<Course>();
		}
	}

	public List<CourseDTO> getAllCourses() {
		List<CourseDTO> courses = new ArrayList<>();
		Iterable<Course> coursesDataIterable = courseRepository.findAll();

		for (Course course : coursesDataIterable) {
			CourseDTO courseDTO = convertToDto(course);
			courses.add(courseDTO);
		}
		return courses;
	}

	public CourseDTO getCourse(Long courseId) throws GenericException {
		Optional<Course> optionalCourse;

		optionalCourse = courseRepository.findById(courseId);
		if (!optionalCourse.isPresent()) {
			throw new GenericException("Client Errors", HttpStatus.NOT_FOUND);
		}

		Course courseData = optionalCourse.get();
		CourseDTO courseDtoLocal = convertToDto(courseData);
		return courseDtoLocal;
	}

	public CourseDTO addNewCourse(CourseDTO courseDto) throws GenericException, SQLException {
		if (isNull(courseDto) || isNull(courseDto.getCode()) || isNull(courseDto.getName())
				|| courseDto.getCode().isEmpty() || courseDto.getName().isEmpty()|| courseDto.getCode().length()>4)  {
			throw new GenericException("Client Errors", HttpStatus.BAD_REQUEST);
		}

		Course courseLocal = convertToEntity(courseDto);
		courseRepository.save(courseLocal);
		return courseDto;
	}

	public CourseDTO updateCourse(Long courseId, CourseDTO courseDto) {
		CourseDTO courseDtoLocal = new CourseDTO();
		Optional<Course> optionalCourse = courseRepository.findById(courseId);

		if (optionalCourse.isPresent()) {
			Course courseLocal = optionalCourse.get();
			Course course = getCourse(courseLocal, courseDto);
			courseRepository.saveAndFlush(course);
			courseDtoLocal = convertToDto(course);
		}
		return courseDtoLocal;
	}

	public CourseDTO deleteCourse(Long courseId) throws GenericException {
		Optional<Course> optionalCourse;
		optionalCourse = courseRepository.findById(courseId);

		if (!optionalCourse.isPresent()) {
			throw new GenericException("Client Errors", HttpStatus.NOT_FOUND);
		}
		courseRepository.deleteById(courseId);
		Course courseData = optionalCourse.get();
		CourseDTO courseDtoLocal = convertToDto(courseData);

		return courseDtoLocal;
	}

	private Course getCourse(Course course, CourseDTO courseDto) {
		course.setName(courseDto.getName());
		course.setCode(courseDto.getCode());
		return course;
	}

	private CourseDTO convertToDto(final Course course) {
		CourseDTO courseDTO = new CourseDTO();
		courseDTO.setName(course.getName());
		courseDTO.setCode(course.getCode());
		return courseDTO;
	}

	private Course convertToEntity(CourseDTO courseDto) {
		Course courseLocal = new Course();
		courseLocal.setName(courseDto.getName());
		courseLocal.setCode(courseDto.getCode());
		return courseLocal;
	}

	private static boolean isNull(Object object) {
		return null == object;
	}

}
