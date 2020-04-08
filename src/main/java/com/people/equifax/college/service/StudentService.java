/**
 * 
 */

  package com.people.equifax.college.service;
  
  import java.util.ArrayList; import java.util.List; import java.util.Optional;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.data.domain.Page; import
  org.springframework.data.domain.PageRequest; import
  org.springframework.data.domain.Pageable; import
  org.springframework.data.domain.Sort; import
  org.springframework.http.HttpStatus; import
  org.springframework.stereotype.Service;
  
  import com.people.equifax.college.dto.StudentDTO;  import
  com.people.equifax.college.exception.GenericException;
import
  com.people.equifax.college.model.Student;  import
  com.people.equifax.college.repository.StudentRepository;
  
 /**
	 * @author Rodolfo.Quiroz 
	 * rquiroz1988@gmail.com 
	 * version 1.0
	 */
@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> getAllStudentPaginated(Integer pageNo, Integer pageSize, String sortBy)
			throws GenericException {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Student> pagedResultCourse = studentRepository.findAll(paging);

		if (pagedResultCourse.hasContent()) {
			return pagedResultCourse.getContent();
		} else {
			return new ArrayList<Student>();
		}
	}

	public List<StudentDTO> getAllStudents() {
		List<StudentDTO> students = new ArrayList<>();
		Iterable<Student> studentsDataIterable = studentRepository.findAll();
		for (Student student : studentsDataIterable) {
			StudentDTO studentDto = convertToDto(student);
			students.add(studentDto);
		}
		return students;
	}

	public StudentDTO getStudent(Long studentId) throws GenericException {
		Optional<Student> optionalStudent;

		optionalStudent = studentRepository.findById(studentId);
		if (!optionalStudent.isPresent()) {
			throw new GenericException("Client Errors", HttpStatus.NOT_FOUND);
		}

		Student studentData = optionalStudent.get();
		StudentDTO studentDtoLocal = convertToDto(studentData);
		return studentDtoLocal;
	}

	public StudentDTO addNewStudent(StudentDTO studentDto) throws GenericException {
		if (isNull(studentDto) || isNull(studentDto.getCourse()) || studentDto.getName().isEmpty()
				|| studentDto.getLastName().isEmpty() || studentDto.getAge() < 18) {
			throw new GenericException("Client Errors", HttpStatus.BAD_REQUEST);
		}

		Student studentLocal = convertToEntity(studentDto);
		studentRepository.save(studentLocal);
		return studentDto;
	}
	
	public StudentDTO updateStudent(Long studentId, StudentDTO studentDto) {
		StudentDTO studentDtoLocal = new StudentDTO();
		Optional<Student> optionalStudent = studentRepository.findById(studentId);
		
		if (optionalStudent.isPresent()) {
			Student studentData = optionalStudent.get();
			Student student = getStudent(studentData, studentDto);
			studentRepository.saveAndFlush(student);
			studentDtoLocal = convertToDto(student);
		}
		return studentDtoLocal;
	}
	
	public StudentDTO deleteStudent(Long id) throws GenericException {
		Optional<Student> optionalStudent;
		optionalStudent = studentRepository.findById(id);
		
		if (!optionalStudent.isPresent()) {
			throw new GenericException("Client Errors", HttpStatus.NOT_FOUND);
		}
		studentRepository.deleteById(id);
		Student studentData = optionalStudent.get();
		StudentDTO  studentDtoLocal = convertToDto(studentData);
		
		return studentDtoLocal;
	}
	
	private Student getStudent(Student student, StudentDTO studentDto) {
		student.setName(studentDto.getName());
		student.setLastName(studentDto.getLastName());
		student.setAge(studentDto.getAge());
		student.setRut(studentDto.getRut());
		student.setCourse(studentDto.getCourse());
		return student;
	}
	
	private StudentDTO convertToDto(final Student student) {
		StudentDTO studentLocal = new StudentDTO();
		studentLocal.setId(student.getId());
		studentLocal.setRut(student.getRut());
		studentLocal.setName(student.getName());
		studentLocal.setLastName(student.getLastName());
		studentLocal.setAge(student.getAge());
		studentLocal.setCourse(student.getCourse());
		return studentLocal;
	}

	private Student convertToEntity(StudentDTO studentDto) {
		Student student = new Student();
		student.setRut(studentDto.getRut());
		student.setName(studentDto.getName());
		student.setLastName(studentDto.getLastName());
		student.setAge(studentDto.getAge());
		student.setCourse(studentDto.getCourse());
		return student;
	}
	
	private static boolean isNull(Object object) {
		return null == object;
	}

}
		 