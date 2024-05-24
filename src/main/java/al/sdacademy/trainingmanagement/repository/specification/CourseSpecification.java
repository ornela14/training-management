package al.sdacademy.trainingmanagement.repository.specification;

import al.sdacademy.trainingmanagement.entity.CourseEntity;
import al.sdacademy.trainingmanagement.repository.criteria.CourseCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CourseSpecification extends SpecificationBuilder<CourseEntity, CourseCriteria> {

    @Override
    public Specification<CourseEntity> filter(CourseCriteria criteria) {
        Specification<CourseEntity> specification = Specification.where(null);

        if (criteria.getCourseName() != null) {
            specification = specification.and(likeUpperSpecification("courseName", criteria.getCourseName()));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(equalsSpecification("status", criteria.getStatus()));
        }
        if (criteria.getStudentName() != null) {
            specification = specification.and(likeUpperSpecification("courseUsers.user.firstName", criteria.getStudentName()));
        }
        return specification;
    }
}
