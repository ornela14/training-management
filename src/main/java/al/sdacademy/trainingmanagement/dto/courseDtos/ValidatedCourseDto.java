package al.sdacademy.trainingmanagement.dto.courseDtos;

import al.sdacademy.trainingmanagement.validation.DatesCheck;
import al.sdacademy.trainingmanagement.validation.DatesChecks;
import al.sdacademy.trainingmanagement.validation.UniqueCourseValidation;

import javax.validation.Valid;

@Valid
@UniqueCourseValidation(
        courseName = "courseName",
        registrationStartDate = "registrationStartDate"
)

@DatesChecks(value = {
        @DatesCheck(
                first = "courseStartDate",
                second = "courseEndDate",
                message = "courseStartDate must be before courseEndDate"
        ),

        @DatesCheck(
                first = "registrationStartDate",
                second = "registrationEndDate",
                message = "registrationStartDate must be before registrationEndDate"
        )
})
public class ValidatedCourseDto extends CourseDto {

}
