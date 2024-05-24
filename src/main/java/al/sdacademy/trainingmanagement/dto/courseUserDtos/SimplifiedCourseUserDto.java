package al.sdacademy.trainingmanagement.dto.courseUserDtos;

import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;

@Data
public class SimplifiedCourseUserDto {
    private String courseName;
    private String studentName;
    private UserStatusEnum status;
}
