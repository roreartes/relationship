package ar.com.ada.sb.relationship.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Bio is required")
    private String bio;
    @JsonIgnoreProperties(value = "director")
    private Set<FilmDto> films;

    public DirectorDto(Long id, String name, String bio, Set<FilmDto> films) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.films = films;
    }

    public DirectorDto(String name, String bio, Set<FilmDto> films) {
        this.name = name;
        this.bio = bio;
        this.films = films;
    }
}
