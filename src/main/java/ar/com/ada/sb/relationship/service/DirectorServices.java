package ar.com.ada.sb.relationship.service;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.DirectorDto;
import ar.com.ada.sb.relationship.model.entity.Director;
import ar.com.ada.sb.relationship.model.mapper.DirectorMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.CycleAvoidingmappingContext;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.DataCycleMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.DirectorCycleMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.FilmCycleMapper;
import ar.com.ada.sb.relationship.model.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("directorServices")
public class DirectorServices implements Services<DirectorDto> {

    @Autowired
    @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    @Autowired
    @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;


    private DirectorCycleMapper directorCycleMapper = DirectorCycleMapper.MAPPER;

    private CycleAvoidingmappingContext context = CycleAvoidingmappingContext.getInstance();



    @Override
    public List<DirectorDto> findAll() {
        List<Director> allDirectorsEntity = directorRepository.findAll();
        List<DirectorDto> directorDtosList = directorCycleMapper.toDto(allDirectorsEntity, context);

        return directorDtosList;
    }

    public DirectorDto findDirectorById(Long id) {
        Optional<Director> directorByIdOptional = directorRepository.findById(id);

        DirectorDto directorDto = null;

        if (directorByIdOptional.isPresent()) {
            Director directorById = directorByIdOptional.get();
            directorDto = directorCycleMapper.toDto(directorById, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }
        return directorDto;
    }

    @Override
    public DirectorDto save(DirectorDto dto) {
        Director directorToSave = directorCycleMapper.toEntity(dto, context);
        Director directorSaved = directorRepository.save(directorToSave);
        DirectorDto directorDtoSaved = directorCycleMapper.toDto(directorSaved, context);

        return directorDtoSaved;
    }


    public DirectorDto updateDirector(DirectorDto directorDtoToUpdate, Long id) {

        Optional<Director> byIdOptional = directorRepository.findById(id);
        DirectorDto directorDtoUpdated = null;

        if (byIdOptional.isPresent()) {
            Director directorById = byIdOptional.get();
            directorDtoToUpdate.setId(directorById.getId());
            Director directorToUpdate = directorCycleMapper.toEntity(directorDtoToUpdate, context);
            Director directorUpdated = directorRepository.save(directorToUpdate);
            directorDtoUpdated = directorCycleMapper.toDto(directorUpdated, context);


        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }

        return directorDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Director> byIdOptional = directorRepository.findById(id);

        if (byIdOptional.isPresent()) {
            Director directorToDelete = byIdOptional.get();

            directorRepository.delete(directorToDelete);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", id);
        }

    }



}
