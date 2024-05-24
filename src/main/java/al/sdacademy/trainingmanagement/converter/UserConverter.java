package al.sdacademy.trainingmanagement.converter;

import al.sdacademy.trainingmanagement.dto.userDtos.NewUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.SimplifiedUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UpdateUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserDto;
import al.sdacademy.trainingmanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements BidirectionalConverter<UserDto, UserEntity> {
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private CourseConverter courseConverter;

    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAssigned(dto.isAssigned());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        return entity;
    }

    public NewUserDto toNewUserDto(UserEntity entity) {
        NewUserDto dto = new NewUserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setBirthDate(entity.getBirthDate());
        entity.setBirthDate(dto.getBirthDate());
        return dto;
    }

    @Override
    public UserDto toDto(UserEntity entity) {
        CourseUserConverter courseUserConverter = new CourseUserConverter();
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setAssigned(entity.isAssigned());
        dto.setRole(roleConverter.toDto(entity.getRole()));
        dto.setUserCourses(courseUserConverter.toCourseUserDtoList(entity.getUserCourses()));
        return dto;
    }

    public SimplifiedUserDto toSimplifiedUserDto(UserEntity entity) {
        SimplifiedUserDto dto = new SimplifiedUserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setBirthDate(entity.getBirthDate());
        return dto;
    }

    public UserEntity toUpdateStudentEntity(UpdateUserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public List<UserDto> toUserDtoList(List<UserEntity> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
