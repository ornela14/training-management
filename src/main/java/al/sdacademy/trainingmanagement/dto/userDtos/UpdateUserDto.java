package al.sdacademy.trainingmanagement.dto.userDtos;

import al.sdacademy.trainingmanagement.dto.BaseDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UpdateUserDto extends BaseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate modifiedDate;

}
