package ar.com.ada.sb.relationship.controller;

import ar.com.ada.sb.relationship.model.dto.FilmDto;
import ar.com.ada.sb.relationship.service.FilmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    @Qualifier("filmServices")
    private FilmServices filmServices;

    @GetMapping({"", "/"})
    public ResponseEntity getAllFilms() {
        List<FilmDto> allfilms = filmServices.findAll();
        return ResponseEntity.ok(allfilms);

    }

    @GetMapping({"{id}", "/{id}/"})
    public ResponseEntity getFilmsById(@PathVariable Long id) {
        FilmDto filmById = filmServices.findFilmById(id);

        return ResponseEntity.ok(filmById);

    }

    @PostMapping({"", "/"})
    public ResponseEntity addNewFilm(@Valid @RequestBody FilmDto filmDto) {
        FilmDto filmSaved = filmServices.save(filmDto);
        return ResponseEntity.ok(filmSaved);
    }

    @PutMapping({"{id}", "/{id}/"})
    public ResponseEntity updateFilmById(@Valid @RequestBody FilmDto filmDto, @PathVariable Long id) {
        FilmDto filmDtoUpdated = filmServices.updateFilm(filmDto, id);

        return ResponseEntity.ok(filmDtoUpdated);

    }

    @DeleteMapping({"{id}", "/{id}/"})
    public ResponseEntity deleteFilm(@PathVariable Long id) {
        filmServices.delete(id);

        return ResponseEntity.noContent().build();


    }

    @PutMapping({"/{filmId}/actors/{actorId}", "/{fildId}/actors/{actorId}"})
    public ResponseEntity addActorToFilm(@PathVariable Long filmId, @PathVariable Long actorId){
        FilmDto filmDtoWithNewActor = filmServices.addActorToFilm(actorId, filmId);
        return ResponseEntity.ok(filmDtoWithNewActor);
    }


    }




