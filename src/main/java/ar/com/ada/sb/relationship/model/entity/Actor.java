package ar.com.ada.sb.relationship.model.entity;


import ar.com.ada.sb.relationship.model.dto.FilmDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Actor")
public class Actor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 25)
    private String gender;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDate birthday;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String bio;

@ManyToMany(mappedBy = "actors")
   private Set<Film> films;


    public Actor(Long id) {
        this.id = id;
    }

    public Actor(String name, String gender, LocalDate birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }
}




