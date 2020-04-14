package ar.com.ada.sb.relationship.model.entity;

import ar.com.ada.sb.relationship.model.dto.DirectorDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 90)
    private String tittle;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "YEAR")
    private Date year;

    @ManyToOne //ManyToOne siempre debe estar donde esta la clave foranea
    @JoinColumn(name = "Director_id", nullable = false)
    private Director director;

    @ManyToMany
    @JoinTable(name = "Actor_has_Film",
            joinColumns = @JoinColumn(name = "Film_id"),
            inverseJoinColumns = @JoinColumn(name = "Actor_id"))

    private Set<Actor> actors;


    public Film(Long id) {
        this.id = id;
    }

    public Film(String tittle, String description) {
        this.tittle = tittle;
        this.description = description;
    }

    public void addActor(Actor actor){
        this.actors.add(actor);
    }
}

