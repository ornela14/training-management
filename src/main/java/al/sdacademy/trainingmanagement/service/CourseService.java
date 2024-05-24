package al.sdacademy.trainingmanagement.service;

import al.sdacademy.trainingmanagement.dto.courseDtos.CourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.UpdateCourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.ValidatedCourseDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.sdacademy.trainingmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria CourseCriteria
     * @return CourseDto
     */
    Page<CourseDto> filterCourses(CourseCriteria criteria);

    /**
     * Retrieve course details
     *
     * @param id course id
     */
    CourseDto getCourseById(Long id);

    /**
     * Retrieve user assign to course view
     *
     * @return List<CourseDto>
     */
    List<CourseDto> getAllUnfilteredCourses();

    /**
     * Save course
     *
     * @param course CourseDto
     */
    CourseDto saveCourse(ValidatedCourseDto course);

    /**
     * Update course
     *
     * @param courseDto course dto
     * @return CourseDto
     */
    CourseDto updateCourse(UpdateCourseDto courseDto);

    /**
     * Delete course
     *
     * @param courseId course id
     */
    void deleteCourseById(Long courseId);


    /**
     * Retrieve course details
     *
     * @param courseId course id
     * @return List<CourseUserDto>
     */
    List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId);

}
