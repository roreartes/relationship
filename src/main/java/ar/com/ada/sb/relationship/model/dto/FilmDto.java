package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class FilmDto {
    private Long id;
    @NotBlank(message = "tittle is required")
    private String tittle;
    @NotBlank(message = "the description is required")
    private String description;
    @JsonFormat(pattern = "yyyy")
    private Date year;
    @JsonIgnoreProperties(value = "films")
    private DirectorDto director;
    @JsonIgnoreProperties(value = "films")
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
