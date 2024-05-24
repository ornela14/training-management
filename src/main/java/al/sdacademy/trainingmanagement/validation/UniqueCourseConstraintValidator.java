package al.sdacademy.trainingmanagement.validation;

import al.sdacademy.trainingmanagement.dto.courseDtos.CourseStatus;
import al.sdacademy.trainingmanagement.entity.CourseEntity;
import al.sdacademy.trainingmanagement.repository.CourseRepository;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Optional;

public class UniqueCourseConstraintValidator implements ConstraintValidator<UniqueCourseValidation, Object> {

    @Autowired
    private CourseRepository repository;
    private String courseName;
    private String registrationStartDate;

    @Override
    public void initialize(UniqueCourseValidation constraintAnnotation) {
        courseName = constraintAnnotation.courseName();
        registrationStartDate = constraintAnnotation.registrationStartDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        String name = (String) beanWrapper.getPropertyValue(courseName);
        LocalDate sDate = (LocalDate) beanWrapper.getPropertyValue(registrationStartDate);
        Optional<CourseEntity> opt = repository.
                findByCourseNameAndRegistrationStartDateAndStatus(name, sDate, CourseStatus.READY_TO_START);
        boolean valid = opt.isEmpty();
        if (!valid) {
            String message = "Kursi me keto parametra: /n" + "emri: " + name + "/n" +
                    "registration start date: " + sDate + "/n" +
                    "status: " + CourseStatus.READY_TO_START + " EKZISTON!";

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }
        return valid;
    }
}
