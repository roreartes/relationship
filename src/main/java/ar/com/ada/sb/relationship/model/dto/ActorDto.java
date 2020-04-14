package ar.com.ada.sb.relationship.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ActorDto {

    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "Gender is required")
    private String Gender;
    @NotBlank(message = "Birthday is required")
    @Past(message = "the birthday must be past date")
    private LocalDate birthday;
    @NotBlank(message = "the biography is required")
    private String bio;

    private Set<FilmDto> films;

    public ActorDto(Long id, String name, String gender, LocalDate birthday, String bio, Set<FilmDto> films) {
        this.id = id;
        this.name = name;
        Gender = gender;
        this.birthday = birthday;
        this.bio = bio;
        this.films = films;
    }

    public ActorDto(String name, String gender, LocalDate birthday, String bio, Set<FilmDto> films) {
        this.name = name;
        Gender = gender;
        this.birthday = birthday;
        this.bio = bio;
        this.films = films;
    }
}
