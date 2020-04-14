package ar.com.ada.sb.relationship.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FilmDto {
    private Long id;
    private String tittle;
    private String description;
    private Date year;
    private DirectorDto director;
    private Set<ActorDto> actors;

    public FilmDto(Long id, String tittle, String description, Date year, DirectorDto director, Set<ActorDto> actors) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.year = year;
        this.director = director;
        this.actors = actors;
    }

    public FilmDto(String tittle, String description, Date year, DirectorDto director, Set<ActorDto> actors) {
        this.tittle = tittle;
        this.description = description;
        this.year = year;
        this.director = director;
        this.actors = actors;
    }

}
