package al.sdacademy.trainingmanagement.service.impl;

import al.sdacademy.trainingmanagement.converter.CourseConverter;
import al.sdacademy.trainingmanagement.converter.CourseUserConverter;
import al.sdacademy.trainingmanagement.dto.courseDtos.CourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.CourseStatus;
import al.sdacademy.trainingmanagement.dto.courseDtos.UpdateCourseDto;
import al.sdacademy.trainingmanagement.dto.courseDtos.ValidatedCourseDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.sdacademy.trainingmanagement.entity.CourseEntity;
import al.sdacademy.trainingmanagement.repository.CourseRepository;
import al.sdacademy.trainingmanagement.repository.CourseUserRepository;
import al.sdacademy.trainingmanagement.repository.criteria.CourseCriteria;
import al.sdacademy.trainingmanagement.repository.specification.CourseSpecification;
import al.sdacademy.trainingmanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseSpecification courseSpecification;

    @Autowired
    private CourseConverter converter;

    @Autowired
    private CourseUserConverter courseUserConverter;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Override
    public CourseDto getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(converter::toDto)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public List<CourseDto> getAllUnfilteredCourses() {
        return converter.toCourseDtoList(courseRepository.findAll());
    }

    @Override
    public Page<CourseDto> filterCourses(CourseCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        Specification<CourseEntity> spec = courseSpecification.filter(criteria);
        return courseRepository
                .findAll(spec, pageable)
                .map(converter::toDto);
    }

    @Override
    public CourseDto updateCourse(UpdateCourseDto courseDto) {
        CourseEntity currentEntity = getCourseEntity(courseDto.getId());
        currentEntity.setModifiedDate(LocalDate.now());
        CourseEntity entity = converter.toUpdateCourseEntity(courseDto, currentEntity);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public CourseDto saveCourse(ValidatedCourseDto courseDto) {
        CourseEntity entity = converter.toEntity(courseDto);
        return converter.toDto(courseRepository.save(entity));
    }

    @Override
    public void deleteCourseById(Long id) {
        courseUserRepository
                .getCourseUserEntitiesByCourseCourseName(getCourseById(id).getCourseName())
                .forEach(e -> e.setDeleted(true));
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course does not exist"));
        course.setStatus(CourseStatus.FINISHED);
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @Override
    public List<SimplifiedCourseUserDto> getAllStudentsByCourseId(Long courseId) {
        return courseUserRepository.getByIdCourseId(courseId)
                .stream()
                .filter(c -> !c.isDeleted())
                .map(courseUserConverter::toSimplifiedDto)
                .collect(Collectors.toList());
    }

    private CourseEntity getCourseEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }


}

