package al.sdacademy.trainingmanagement.controller;

import al.sdacademy.trainingmanagement.dto.courseDtos.CourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.UpdateCourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.ValidatedCourseDto;
import al.sdacademy.trainingmanagement.repository.criteria.CourseCriteria;
import al.sdacademy.trainingmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    /**
     * Get all courses. if criteria is applied, courses are filter accordingly
     *
     * @param criteria filter object
     * @return ResponseEntity -> courses filtered list
     */
    @PostMapping("/filter")
    public ResponseEntity<Page<CourseDto>> listCourses(@RequestBody CourseCriteria criteria) {
        return new ResponseEntity<>(courseService.filterCourses(criteria), HttpStatus.OK);
    }

    /**
     * Retrieve course details
     *
     * @param id course id
     * @return ResponseEntity<CourseDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    /**
     * Update course
     *
     * @param course courseDto
     * @return ResponseEntity<CourseDto>
     */
    @PutMapping
    public ResponseEntity<CourseDto> updateCourse(@RequestBody UpdateCourseDto course) {
        return new ResponseEntity<>(courseService.updateCourse(course), HttpStatus.OK);
    }

    /**
     * Save new course
     *
     * @param course CourseDto
     * @return ResponseEntity<CourseDto>
     */
    @PostMapping
    public ResponseEntity<CourseDto> saveCourse(@RequestBody ValidatedCourseDto course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.OK);
    }

    /**
     * Delete course (soft deletion)
     *
     * @param id course id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
