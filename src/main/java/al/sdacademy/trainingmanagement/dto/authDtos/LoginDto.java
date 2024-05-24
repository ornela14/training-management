package al.sdacademy.trainingmanagement.dto.authDtos;

import lombok.Data;

@Data
public class LoginDto {

    private String email;
    private String password;

}