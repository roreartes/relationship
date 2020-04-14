package ar.com.ada.sb.relationship.component.data;

import ar.com.ada.sb.relationship.model.entity.Director;
import ar.com.ada.sb.relationship.model.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DirectorLoaderData implements ApplicationRunner {

    @Autowired @Qualifier("directorRepository")
    private DirectorRepository directorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Director> directorList = Arrays.asList(
                new Director("Luc Besson", "Bio de Luc"),
                new Director("Noah Baumbach", "Bio de Noah"),
                new Director("Dennis Dugan", "Bio de Dennis"),
                new Director("Peyton Reed", "Bio de Peyton"),
                new Director("Wolfgang Petersen", "Bio de Wolfgang"),
                new Director("David Fincher", "Bio de David"),
                new Director("Martin Scorsese", "Bio de Martin"),
                new Director("Alejandro González Iñárritu", "Bio de Alejandro")

                );
        directorList.forEach(director -> directorRepository.save(director));

    }
}
