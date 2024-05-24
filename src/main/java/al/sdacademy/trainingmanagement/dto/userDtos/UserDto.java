package al.sdacademy.trainingmanagement.dto.userDtos;

import al.sdacademy.trainingmanagement.dto.BaseDto;
import al.sdacademy.trainingmanagement.dto.courseUserDtos.SimplifiedCourseUserDto;
import al.sdacademy.trainingmanagement.dto.roleDtos.RoleDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

    private boolean assigned;

    private RoleDto role;

    private List<SimplifiedCourseUserDto> userCourses;

}
