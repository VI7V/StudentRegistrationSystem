package com.example.studentcourse.service;

import com.example.studentcourse.entity.Course;
import com.example.studentcourse.entity.Student;
import com.example.studentcourse.repository.CourseRepository;
import com.example.studentcourse.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Insert a new course
    public Course insertCourse(Course course) {
        return courseRepository.save(course);
    }
    
    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    // Get course by ID
    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }
    
    // Delete course by ID
    public boolean deleteCourse(Long courseId) {
        if (courseRepository.existsById(courseId)) {
            courseRepository.deleteById(courseId);
            return true;
        }
        return false;
    }
    
    // Update course
    public Course updateCourse(Long courseId, Course courseDetails) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setCourseName(courseDetails.getCourseName());
            course.setCapacity(courseDetails.getCapacity());
            return courseRepository.save(course);
        }
        return null;
    }
    
    // Enroll student in a course
    @Transactional
    public String enrollStudent(Long courseId, Long studentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        
        if (optionalCourse.isPresent() && optionalStudent.isPresent()) {
            Course course = optionalCourse.get();
            Student student = optionalStudent.get();
            
            if (course.isFull()) {
                return "Course is full. Cannot enroll student.";
            }
            
            course.getStudents().add(student);
            student.getCourses().add(course);
            
            courseRepository.save(course);
            return "Student enrolled successfully.";
        }
        return "Course or Student not found.";
    }
    
    // Remove student from a course
    @Transactional
    public String removeStudentFromCourse(Long courseId, Long studentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        
        if (optionalCourse.isPresent() && optionalStudent.isPresent()) {
            Course course = optionalCourse.get();
            Student student = optionalStudent.get();
            
            course.getStudents().remove(student);
            student.getCourses().remove(course);
            
            courseRepository.save(course);
            return "Student removed from course successfully.";
        }
        return "Course or Student not found.";
    }
}