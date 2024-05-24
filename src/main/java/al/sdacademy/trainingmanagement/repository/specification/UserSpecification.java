package al.sdacademy.trainingmanagement.repository.specification;

import al.sdacademy.trainingmanagement.entity.UserEntity;
import al.sdacademy.trainingmanagement.repository.criteria.UserCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification extends SpecificationBuilder<UserEntity, UserCriteria> {
    @Override
    public Specification<UserEntity> filter(UserCriteria criteria) {
        Specification<UserEntity> specification = Specification.where(null);

        if (criteria.getFirstName() != null && !criteria.getFirstName().isEmpty()) {
            specification = specification.and(likeUpperSpecification("firstName", criteria.getFirstName()));
        }
        if (criteria.getLastName() != null && !criteria.getLastName().isEmpty()) {
            specification = specification.and(likeUpperSpecification("lastName", criteria.getLastName()));
        }
        if (criteria.getStatus() != null && !criteria.getStatus().getDisplayValue().isEmpty()) {
            specification = specification.and(equalsSpecification("status", criteria.getStatus()));
        }
        if (criteria.getCourse() != null) {
            specification = specification.and(equalsSpecification("course", criteria.getCourse()));
        }
        return specification;
    }
}
