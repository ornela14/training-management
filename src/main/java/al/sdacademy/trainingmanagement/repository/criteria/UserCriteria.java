package al.sdacademy.trainingmanagement.repository.criteria;

import al.sdacademy.trainingmanagement.dto.userDtos.UserStatusEnum;
import lombok.Data;

@Data
public class UserCriteria extends BaseCriteria {
    private String firstName;
    private String lastName;
    private Long course;
    private UserStatusEnum status;
}
