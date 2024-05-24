package al.sdacademy.trainingmanagement.exception;

public class StudentDeletedException extends RuntimeException {
    public StudentDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
