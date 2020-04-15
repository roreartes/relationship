package ar.com.ada.sb.relationship.service;

import ar.com.ada.sb.relationship.component.BusinessLogicExceptionComponent;
import ar.com.ada.sb.relationship.exception.ApiEntityError;
import ar.com.ada.sb.relationship.exception.BusinessLogicException;
import ar.com.ada.sb.relationship.model.dto.FilmDto;
import ar.com.ada.sb.relationship.model.entity.Actor;
import ar.com.ada.sb.relationship.model.entity.Director;
import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.mapper.DirectorMapper;
import ar.com.ada.sb.relationship.model.mapper.FilmMapper;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.CycleAvoidingmappingContext;
import ar.com.ada.sb.relationship.model.mapper.circulardependency.FilmCycleMapper;
import ar.com.ada.sb.relationship.model.repository.ActorRepository;
import ar.com.ada.sb.relationship.model.repository.DirectorRepository;
import ar.com.ada.sb.relationship.model.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("filmServices")
public class FilmServices implements Services<FilmDto> {
    @Autowired
    @Qualifier("businessLogicExceptionComponent")
    private BusinessLogicExceptionComponent logicExceptionComponent;

    @Autowired
    @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    @Autowired
    @Qualifier("filmRepository")
    private FilmRepository filmRepository;
    @Autowired
    @Qualifier
    private DirectorRepository directorRepository;

    private FilmCycleMapper filmCycleMapper = FilmCycleMapper.MAPPER;

    private CycleAvoidingmappingContext context = CycleAvoidingmappingContext.getInstance();


    @Override
    public List<FilmDto> findAll() {
        List<Film> allFilmsEntity = filmRepository.findAll();
        List<FilmDto> allFilmDtos = filmCycleMapper.toDto(allFilmsEntity, context);
        return allFilmDtos;
    }

    public FilmDto findFilmById(Long id) {
        Optional<Film> filmById = filmRepository.findById(id);
        FilmDto filmDtoById = null;

        if (filmById.isPresent()) {
            Film film = filmById.get();
            filmDtoById = filmCycleMapper.toDto(film, context);
        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("film", id);
        }
        return filmDtoById;
    }

    @Override
    public FilmDto save(FilmDto dto) {
        Film filmToSave = filmCycleMapper.toEntity(dto, context);
        Film saveEntityFilm = filmRepository.save(filmToSave);
        FilmDto filmDtoSaved = filmCycleMapper.toDto(saveEntityFilm, context);
        return null;
    }

    public FilmDto updateFilm(FilmDto filmDtoToUpdate, Long id) {
        Optional<Film> filmByIdOptional = filmRepository.findById(id);
        FilmDto filmDtoUpdated = null;

        if (filmByIdOptional.isPresent()) {
            Film filmById = filmByIdOptional.get();
            filmDtoToUpdate.setId(filmById.getId());
            Film filmEntity = filmCycleMapper.toEntity(filmDtoToUpdate, context);
            Film filmSaved = filmRepository.save(filmEntity);
            filmDtoUpdated = filmCycleMapper.toDto(filmSaved, context);


        } else {

        }
        return filmDtoUpdated;
    }

    @Override
    public void delete(Long id) {
        Optional<Film> byIdOptional = filmRepository.findById(id);
        if (byIdOptional.isPresent()) {
            Film filmToDelete = byIdOptional.get();
            filmRepository.delete(filmToDelete);


        } else {
            logicExceptionComponent.throwExceptionEntityNotFound("Film", id);

        }
    }

    public FilmDto addActorToFilm(Long actorId, Long filmId) {

        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Actor> actorByIdOptional = actorRepository.findById(actorId);

        FilmDto filmDtoWithNewActor = null;

        if (!filmByIdOptional.isPresent()) {  // Valida existencia del film y del actor
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);
        }
        if (!actorByIdOptional.isPresent()) {
            logicExceptionComponent.throwExceptionEntityNotFound("Actor", actorId);
        }

        Film film = filmByIdOptional.get(); // extrae ambos ID
        Actor actorToAdd = actorByIdOptional.get();
//valida si existe algun actor con el mismo nombre
        boolean hasActorInFilm = film.getActors().stream().anyMatch(actor -> actor.getName().equals(actorToAdd.getName()));

        if (!hasActorInFilm) {
            film.addActor(actorToAdd); //asigna el actor al film
            Film filmWithNewActor = filmRepository.save(film); //lo guarde en BD
            filmDtoWithNewActor = filmCycleMapper.toDto(filmWithNewActor, context); // convierte a dto p devolver a controller

        } else {
            ApiEntityError apiEntityError = new ApiEntityError(
                    "Actor",
                    "Already exist",
                    "The actor with id '" + actorId + "' does not exist"
            );
            throw new BusinessLogicException(
                    "Actor already exist in the film",
                    HttpStatus.BAD_REQUEST,
                    apiEntityError
            );
        }
        return filmDtoWithNewActor;

    }

    public FilmDto addDirectorToFilm(Long directorId, Long filmId) {
        Optional<Film> filmByIdOptional = filmRepository.findById(filmId);
        Optional<Director> directorByIdOptional = directorRepository.findById(directorId);

        FilmDto filmDtoWithDirector = null;

        if (!filmByIdOptional.isPresent()) {  // Valida existencia del film y del actor
            logicExceptionComponent.throwExceptionEntityNotFound("Film", filmId);
        }
        if (!directorByIdOptional.isPresent()) {
            logicExceptionComponent.throwExceptionEntityNotFound("Director", directorId);
        }
        Film film = filmByIdOptional.get();
        Director directorToSet = directorByIdOptional.get();

        film.setDirector(directorToSet);
        Film filmWithDirector = filmRepository.save(film);
        filmDtoWithDirector = filmCycleMapper.toDto(filmWithDirector, context);

        return filmDtoWithDirector;

    }
}