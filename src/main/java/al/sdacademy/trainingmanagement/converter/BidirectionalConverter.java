package al.sdacademy.trainingmanagement.converter;

public interface BidirectionalConverter<D, E> {
    D toDto(E entity);

    E toEntity(D dto);

}
