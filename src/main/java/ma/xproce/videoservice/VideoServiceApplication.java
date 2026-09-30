package ma.xproce.videoservice;

import ma.xproce.videoservice.entities.Creator;
import ma.xproce.videoservice.entities.Video;
import ma.xproce.videoservice.repositories.CreatorRepository;
import ma.xproce.videoservice.repositories.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VideoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CreatorRepository creatorRepository,
                            VideoRepository videoRepository) {
        return args -> {
            List<Creator> creators = List.of(
                    Creator.builder().name("Mohamed").email("mohamed@gmail.com").build(),
                    Creator.builder().name("Sara").email("sara@gmail.com").build(),
                    Creator.builder().name("Yassine").email("yassine@gmail.com").build()
            );
            creatorRepository.saveAll(creators);

            List<Video> videos = List.of(
                    Video.builder()
                            .name("Spring Boot pour débutants")
                            .url("https://youtube.com/watch?v=abc1")
                            .description("Introduction à Spring Boot")
                            .datePublication(LocalDate.of(2024, 1, 15))
                            .creator(creators.get(0))
                            .build(),
                    Video.builder()
                            .name("Spring Data JPA")
                            .url("https://youtube.com/watch?v=abc2")
                            .description("Les repositories et les relations")
                            .datePublication(LocalDate.of(2024, 3, 2))
                            .creator(creators.get(0))
                            .build(),
                    Video.builder()
                            .name("Angular en 1 heure")
                            .url("https://youtube.com/watch?v=abc3")
                            .description("Les bases d'Angular")
                            .datePublication(LocalDate.of(2024, 5, 20))
                            .creator(creators.get(1))
                            .build(),
                    Video.builder()
                            .name("Docker de zéro")
                            .url("https://youtube.com/watch?v=abc4")
                            .description("Conteneurisation d'une application")
                            .datePublication(LocalDate.of(2024, 7, 9))
                            .creator(creators.get(2))
                            .build()
            );
            videoRepository.saveAll(videos);

            // Vérification
            videoRepository.findAll().forEach(v ->
                    System.out.println(v.getName() + " -> " + v.getCreator().getName()));
        };
    }
}
