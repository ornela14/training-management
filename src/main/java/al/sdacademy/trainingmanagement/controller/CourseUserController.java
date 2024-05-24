package al.sdacademy.trainingmanagement.controller;

import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserListDto;
import al.sdacademy.trainingmanagement.repository.criteria.CourseUserCriteria;
import al.sdacademy.trainingmanagement.service.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("course-user")
public class CourseUserController {

    @Autowired
    private CourseUserService courseUserService;

    /**
     * Get all user-courses. if criteria is applied, user-courses are filtered accordingly
     *
     * @param criteria
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<CourseUserListDto>> getCourseUserList(@RequestBody CourseUserCriteria criteria) {
        return new ResponseEntity<>(courseUserService.getCourseUserList(criteria), HttpStatus.OK);
    }

    /**
     * Update course-user
     *
     * @param courseUserDto courseUserDto
     */
    @PutMapping
    public ResponseEntity<CourseUserDto> updateCourseUser(@RequestBody CourseUserDto courseUserDto) {
        return new ResponseEntity<>(courseUserService.editCourseUser(courseUserDto), HttpStatus.OK);
    }

    /**
     * Assign user to course
     *
     * @param courseUserDto CourseUserDto
     */
    @PostMapping("/assign-user")
    public ResponseEntity<Void> assignUserToCourse(@RequestBody CourseUserDto courseUserDto) {
        courseUserDto.setCreatedDate(LocalDate.now());
        courseUserDto.setModifiedDate(LocalDate.now());
        courseUserService.assignUserToCourse(courseUserDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Delete relationship between user and course
     *
     * @param courseId  courseId
     * @param studentId studentId
     */
    @PutMapping("/remove/{courseId}/{studentId}")
    public ResponseEntity<Void> removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseUserService.removeUserFromCourse(studentId, courseId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
