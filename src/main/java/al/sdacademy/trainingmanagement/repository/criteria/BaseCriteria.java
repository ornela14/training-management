package al.sdacademy.trainingmanagement.repository.criteria;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class BaseCriteria {
    private Long id;
    private int pageNumber = 0;
    private int pageSize = 20;
    private String sortDirection = Sort.Direction.DESC.name();
    private String orderBy = "id";
}
