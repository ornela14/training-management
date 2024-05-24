package al.sdacademy.trainingmanagement.dto.authDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}