package al.sdacademy.trainingmanagement.repository;

import al.sdacademy.trainingmanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserEntityManagerRepository {
    @Autowired
    private EntityManager entityManager;

    public List<UserEntity> getAllRegisteredUsers() {
        TypedQuery<UserEntity> query = entityManager
                .createQuery("select u from UserEntity u left join CourseUserEntity cu on u.id = cu.user.id where cu.status='1'", UserEntity.class);
        return query.getResultList();
    }

    public List<UserEntity> getAllUnassignedUsers() {
        TypedQuery<UserEntity> query = entityManager
                .createQuery("select u from UserEntity u  where u.id not in (select distinct cu.id.userId  from CourseUserEntity cu where cu.deleted=false )", UserEntity.class);
        return query.getResultList();
    }
}
