package al.sdacademy.trainingmanagement.exception;

public class CourseDeletedException extends RuntimeException {
    public CourseDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
