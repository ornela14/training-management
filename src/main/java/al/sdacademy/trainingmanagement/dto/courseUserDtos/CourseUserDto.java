package al.sdacademy.trainingmanagement.dto.courseUserDtos;

import al.sdacademy.trainingmanagement.dto.BaseDto;
import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CourseUserDto extends BaseDto {
    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Course is required")
    private Long courseId;

    private UserStatusEnum status;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;
}
