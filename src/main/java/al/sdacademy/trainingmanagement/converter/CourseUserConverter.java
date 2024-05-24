package al.sdacademy.trainingmanagement.converter;

import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.CourseUserListDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import al.sdacademy.trainingmanagement.entity.CourseEntity;
import al.sdacademy.trainingmanagement.entity.CourseUserEntity;
import al.sdacademy.trainingmanagement.entity.CourseUserId;
import al.sdacademy.trainingmanagement.entity.UserEntity;
import al.sdacademy.trainingmanagement.repository.CourseRepository;
import al.sdacademy.trainingmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseUserConverter implements BidirectionalConverter<CourseUserDto, CourseUserEntity> {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseConverter courseConverter;

    @Autowired
    private UserConverter userConverter;

    @Override
    public CourseUserDto toDto(CourseUserEntity entity) {
        CourseUserDto dto = new CourseUserDto();
        dto.setCourseId(entity.getId().getCourseId());
        dto.setUserId(entity.getId().getUserId());
        dto.setComment(entity.getComment());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(LocalDate.now());
        return dto;
    }

    public SimplifiedCourseUserDto toSimplifiedDto(CourseUserEntity entity) {
        SimplifiedCourseUserDto dto = new SimplifiedCourseUserDto();
        dto.setStudentName(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        dto.setCourseName(entity.getCourse().getCourseName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public CourseUserEntity toEntity(CourseUserDto dto) {
        CourseUserEntity entity = new CourseUserEntity();
        CourseUserId id = new CourseUserId(dto.getUserId(), dto.getCourseId());
        entity.setId(id);
        entity.setCourse(courseRepository.findById(dto.getCourseId()).orElseThrow(() -> new RuntimeException("Course does not exist")));
        entity.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User does not exist")));
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : UserStatusEnum.INTERESTED);
        entity.setComment(dto.getComment());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }

    public CourseUserEntity toUpdateCourseUserEntity(CourseUserDto dto, CourseUserEntity entity) {
        entity.setId(new CourseUserId(dto.getUserId(), dto.getCourseId()));
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(null);
        CourseEntity course = courseRepository.findById(dto.getCourseId()).orElseThrow(null);
        entity.setUser(user);
        entity.setCourse(course);
        entity.setComment(dto.getComment());
        entity.setStatus(dto.getStatus());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }

    public CourseUserListDto toCourseUserList(CourseUserEntity entity) {
        CourseUserListDto dto = new CourseUserListDto();
        dto.setId(entity.getId());
        dto.setCourseDto(courseConverter.toSimplifiedUserDto(entity.getCourse()));
        dto.setUserDto(userConverter.toSimplifiedUserDto(entity.getUser()));
        dto.setStatus(entity.getStatus());
        dto.setComment(entity.getComment());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

    public List<SimplifiedCourseUserDto> toCourseUserDtoList(List<CourseUserEntity> entities) {
        return entities.stream().map(this::toSimplifiedDto).collect(Collectors.toList());
    }
}
