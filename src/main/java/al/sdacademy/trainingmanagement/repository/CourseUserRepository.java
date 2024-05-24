package al.sdacademy.trainingmanagement.repository;

import al.sdacademy.trainingmanagement.entity.CourseUserEntity;
import al.sdacademy.trainingmanagement.entity.CourseUserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseUserRepository extends BaseJpaRepository<CourseUserEntity> {
    CourseUserEntity findByIdCourseIdAndIdUserId(Long courseId, Long userId);

    List<CourseUserEntity> getByIdCourseId(Long courseId);

    List<CourseUserEntity> getCourseUserEntitiesByCourseId(Long courseId);

    List<CourseUserEntity> getCourseUserEntitiesByCourseCourseName(String courseName);

    List<CourseUserEntity> getCourseUserEntitiesByUserId(Long userId);

    Optional<CourseUserEntity> findById(CourseUserId courseUserId);
}
