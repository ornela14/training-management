package al.sdacademy.trainingmanagement.service.impl;

import al.sdacademy.trainingmanagement.converter.UserConverter;
import al.sdacademy.trainingmanagement.dto.authDtos.PasswordDto;
import al.sdacademy.trainingmanagement.dto.roleDtos.RoleEnum;
import al.sdacademy.trainingmanagement.dto.userDtos.NewUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UpdateUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserDto;
import al.sdacademy.trainingmanagement.entity.UserEntity;
import al.sdacademy.trainingmanagement.repository.CourseUserRepository;
import al.sdacademy.trainingmanagement.repository.RoleRepository;
import al.sdacademy.trainingmanagement.repository.UserEntityManagerRepository;
import al.sdacademy.trainingmanagement.repository.UserRepository;
import al.sdacademy.trainingmanagement.repository.criteria.UserCriteria;
import al.sdacademy.trainingmanagement.repository.specification.UserSpecification;
import al.sdacademy.trainingmanagement.security.Utils;
import al.sdacademy.trainingmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserSpecification specification;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseUserRepository courseUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserEntityManagerRepository userEMRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Page<UserDto> filterUsers(UserCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPageNumber(), criteria.getPageSize(),
                Sort.Direction.valueOf(criteria.getSortDirection()), criteria.getOrderBy());
        return userRepository
                .findAll(specification.filter(criteria), pageable)
                .map(userConverter::toDto);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userConverter.toDto(getStudentEntity(id));
    }

    @Override
    public UserDto saveUser(NewUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setAssigned(Boolean.FALSE);
        entity.setModifiedDate(LocalDate.now());
        entity.setCreatedDate(LocalDate.now());
        entity.setDeleted(Boolean.FALSE);
        entity.setRole(roleRepository.findByName(RoleEnum.STUDENT.toString()));
        entity.setPassword(bCryptPasswordEncoder.encode("sdastudent"));
        return userConverter.toDto(userRepository.save(entity));
    }

    @Override
    public UserDto updateUser(UpdateUserDto student) {
        UserEntity currentEntity = getStudentEntity(student.getId());
        UserEntity userEntity = userConverter.toUpdateStudentEntity(student, currentEntity);
        return userConverter.toDto(userRepository.save(userEntity));
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity student = getStudentEntity(id);
        courseUserRepository.getCourseUserEntitiesByUserId(id).forEach(e -> e.setDeleted(true));
        student.setDeleted(true);
        userRepository.save(student);
    }

    public UserEntity getStudentEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<UserDto> getUsersEM() {
        return userEMRepository.getAllRegisteredUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUnassignedUsers() {
        return userEMRepository.getAllUnassignedUsers()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto changePassword(PasswordDto passwordDto) {
        UserEntity user = userRepository
                .findByEmail(Utils.getCurrentEmail().orElseThrow(null))
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!authenticate(user, passwordDto.getCurrentPassword())) {
            throw new AccessDeniedException("Access denied. Cannot change password");
        }
        user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
        return userConverter.toDto(userRepository.save(user));
    }

    private boolean authenticate(UserEntity user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

}
