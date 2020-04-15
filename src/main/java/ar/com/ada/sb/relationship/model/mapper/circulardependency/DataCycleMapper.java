package ar.com.ada.sb.relationship.model.mapper.circulardependency;

import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;

import java.util.List;

public interface DataCycleMapper<D, E> {
    E toEntity(D dto, @Context CycleAvoidingmappingContext context);

    @InheritInverseConfiguration
    D toDto(E entity, @Context CycleAvoidingmappingContext context);


    List<E> toEntity(List<D> dtoList, @Context CycleAvoidingmappingContext context);

    @InheritInverseConfiguration
    List<D> toDto(List<E> entityList, @Context CycleAvoidingmappingContext context);

}
