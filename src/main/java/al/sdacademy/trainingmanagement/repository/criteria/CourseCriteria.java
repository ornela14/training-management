package al.sdacademy.trainingmanagement.repository.criteria;

import al.sdacademy.trainingmanagement.dto.courseDtos.CourseStatus;
import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseCriteria extends BaseCriteria {

    private String courseName;

    private CourseStatus status;

    private UserStatusEnum studentStatus;

    private String studentName;

    private LocalDate registrationStartPeriod;

    private LocalDate registrationEndPeriod;

}
