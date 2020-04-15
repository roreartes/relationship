package ar.com.ada.sb.relationship.service;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.ActorDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.mapper.ActorMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.ActorCycleMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.CycleAvoidingmappingContext;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.DirectorCycleMapper;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("actorServices")
public class ActorServices implements Services<ActorDto> {

    @Autowired
    @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    private ActorCycleMapper actorCycleMapper = ActorCycleMapper.MAPPER;

    private CycleAvoidingmappingContext context = CycleAvoidingmappingContext.getInstance();

    @Autowired
    @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Override
    public List<ActorDto> findAll() {
        List<Actor> actorsEntityList = actorRepository.findAll();
        List<ActorDto> actorsDtoList = actorCycleMapper.toDto(actorsEntityList, context);

        return actorsDtoList;
    }

    public ActorDto findActorById(Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDto = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDto = actorCycleMapper.toDto(actorById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);

        }
        return actorDto;
    }

    @Override
    public ActorDto save(ActorDto dto) {
        Actor actorToSave = actorCycleMapper.toEntity(dto, context);  //(lo pasa a entity))
        Actor actorsaved = actorRepository.save(actorToSave); // lo guardo en la base de datos
        ActorDto actorDtoSaved = actorCycleMapper.toDto(actorsaved, context); // se guardo, ya convertido en dto. se retorna
        return actorDtoSaved;
    }


    public ActorDto updateActor(ActorDto actorDtoToUpdate, Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);
        ActorDto actorDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Actor actorById = byIdOptional.get();
            actorDtoToUpdate.setId(actorById.getId());
            Actor actorToUpdate = actorCycleMapper.toEntity(actorDtoToUpdate, context);
            Actor actorUpdated = actorRepository.save(actorToUpdate);
            actorDtoUpdated = actorCycleMapper.toDto(actorUpdated, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }
        return actorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Actor> byIdOptional = actorRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Actor actorToDelete = byIdOptional.get();
            actorRepository.delete(actorToDelete);

        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", id);
        }

    }


}
