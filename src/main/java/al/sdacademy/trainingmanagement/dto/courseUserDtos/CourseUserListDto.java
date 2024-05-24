package al.sdacademy.trainingmanagement.dto.courseUserDtos;

import al.sdacademy.trainingmanagement.dto.courseDtos.SimplifiedCourseDto;
import al.sdacademy.trainingmanagement.dto.userDtos.SimplifiedUserDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import al.sdacademy.trainingmanagement.entity.CourseUserId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseUserListDto {
    private CourseUserId id;
    private SimplifiedUserDto userDto;
    private SimplifiedCourseDto courseDto;
    private UserStatusEnum status;
    private String comment;
    private Double price;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
}
