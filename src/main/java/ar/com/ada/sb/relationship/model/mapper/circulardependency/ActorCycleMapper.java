package ar.com.ada.sb.relationship.model.mapper.circulardependency;

import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorCycleMapper extends  DataCycleMapper<ActorDto, Actor>{
    ActorCycleMapper MAPPER = Mappers.getMapper(ActorCycleMapper.class);
}
