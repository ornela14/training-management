package al.sdacademy.trainingmanagement.dto.roleDtos;

public enum RoleEnum {

    ADMIN("ADMIN"),

    STUDENT("STUDENT");

    final String roleName;

    RoleEnum(String role) {
        this.roleName = role;
    }
}
