package al.sdacademy.trainingmanagement.service.impl;

import al.sdacademy.trainingmanagement.converter.CourseUserConverter;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserListDto;
import al.sdacademy.trainingmanagement.entity.CourseUserEntity;
import al.sdacademy.trainingmanagement.entity.CourseUserId;
import al.sdacademy.trainingmanagement.entity.UserEntity;
import al.sdacademy.trainingmanagement.repository.CourseUserRepository;
import al.sdacademy.trainingmanagement.repository.UserRepository;
import al.sdacademy.trainingmanagement.repository.criteria.CourseUserCriteria;
import al.sdacademy.trainingmanagement.repository.specification.CourseSpecification;
import al.sdacademy.trainingmanagement.repository.specification.CourseUserSpecification;
import al.sdacademy.trainingmanagement.service.CourseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseUserServiceImpl implements CourseUserService {
    @Autowired
    CourseSpecification courseSpecification;
    @Autowired
    CourseUserSpecification courseUserSpecification;
    @Autowired
    private CourseUserConverter courseUserConverter;
    @Autowired
    private CourseUserRepository courseUserRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Page<CourseUserListDto> getCourseUserList(CourseUserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());

        Specification<CourseUserEntity> spec = courseUserSpecification.filter(criteria);
        return courseUserRepository.findAll(spec, pageable).map(courseUserConverter::toCourseUserList);
    }

    @Override
    public CourseUserDto editCourseUser(CourseUserDto courseUserDto) {
        CourseUserEntity currentCourseUserEntity = courseUserConverter.toEntity(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()));
        CourseUserEntity entity = courseUserConverter.toUpdateCourseUserEntity(courseUserDto, currentCourseUserEntity);
        entity.setCreatedDate(getCourseUserEntity(courseUserDto.getCourseId(), courseUserDto.getUserId()).getCreatedDate());
        return courseUserConverter.toDto(courseUserRepository.save(entity));
    }

    @Override
    public List<CourseUserListDto> getCourseUserListByCourseId(Long courseId) {
        return courseUserRepository.getCourseUserEntitiesByCourseId(courseId)
                .stream().map(courseUserConverter::toCourseUserList)
                .collect(Collectors.toList());
    }

    @Override
    public CourseUserDto assignUserToCourse(CourseUserDto dto) {
        CourseUserEntity courseUserEntity = courseUserRepository
                .findById(new CourseUserId(dto.getUserId(), dto.getCourseId()))
                .orElse(null);
        if (courseUserEntity != null) {
            courseUserEntity.setDeleted(false);
            courseUserRepository.save(courseUserEntity);
            return courseUserConverter.toDto(courseUserEntity);
        } else {
            Optional<UserEntity> opt = userRepository.findById(dto.getUserId());
            if(opt.isPresent()){
                opt.get().setAssigned(true);
                userRepository.save(opt.get());
            }
            CourseUserEntity saved = courseUserRepository.save(courseUserConverter.toEntity(dto));
            return courseUserConverter.toDto(saved);
        }
    }

    @Override
    public CourseUserDto getCourseUserEntity(Long courseId, Long userId) {
        return courseUserConverter.toDto(courseUserRepository.findById(new CourseUserId(userId, courseId))
                .orElseThrow(() -> new RuntimeException("Course user relation was not found")));
    }


    @Override
    public void removeUserFromCourse(Long userId, Long courseId) {
        CourseUserEntity entity = courseUserRepository.findByIdCourseIdAndIdUserId(courseId, userId);
        userRepository.getById(userId).setAssigned(false);

        entity.setDeleted(true);
        courseUserRepository.save(entity);
    }

}
