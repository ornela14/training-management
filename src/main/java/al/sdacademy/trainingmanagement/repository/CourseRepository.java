package al.sdacademy.trainingmanagement.repository;

import al.sdacademy.trainingmanagement.dto.courseDtos.CourseStatus;
import al.sdacademy.trainingmanagement.entity.CourseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CourseRepository extends BaseJpaRepository<CourseEntity> {

    Optional<CourseEntity> findByCourseNameAndRegistrationStartDateAndStatus(
            String name,
            LocalDate registrationStartDate,
            CourseStatus status);
}