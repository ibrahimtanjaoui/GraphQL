package ma.xproce.videoservice.controllers;

import ma.xproce.videoservice.dtos.CreatorRequest;
import ma.xproce.videoservice.dtos.CreatorResponse;
import ma.xproce.videoservice.dtos.VideoRequest;
import ma.xproce.videoservice.dtos.VideoResponse;
import ma.xproce.videoservice.entities.Creator;
import ma.xproce.videoservice.entities.Video;
import ma.xproce.videoservice.mappers.VideoMapper;
import ma.xproce.videoservice.repositories.CreatorRepository;
import ma.xproce.videoservice.repositories.VideoRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@Controller
public class VideoGraphQlController {

    private final CreatorRepository creatorRepository;
    private final VideoRepository videoRepository;
    private final VideoMapper mapper;

    VideoGraphQlController(CreatorRepository creatorRepository,
                           VideoRepository videoRepository,
                           VideoMapper mapper) {
        this.creatorRepository = creatorRepository;
        this.videoRepository = videoRepository;
        this.mapper = mapper;
    }

    @QueryMapping
    public List<VideoResponse> videoList() {
        return videoRepository.findAll().stream()
                .map(mapper::toVideoResponse)
                .toList();
    }

    @QueryMapping
    public CreatorResponse creatorById(@Argument Long id) {
        Creator creator = creatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Creator %s not found", id)));
        return mapper.toCreatorResponse(creator);
    }

    @MutationMapping
    public CreatorResponse saveCreator(@Argument CreatorRequest creator) {
        Creator saved = creatorRepository.save(mapper.toCreator(creator));
        return mapper.toCreatorResponse(saved);
    }

    @MutationMapping
    public VideoResponse saveVideo(@Argument VideoRequest video) {
        Video entity = mapper.toVideo(video);
        // Le creator est nouveau : il faut l'enregistrer avant la vidéo,
        // sinon Hibernate lève une TransientPropertyValueException.
        Creator creator = creatorRepository.save(entity.getCreator());
        entity.setCreator(creator);
        return mapper.toVideoResponse(videoRepository.save(entity));
    }

    @SubscriptionMapping
    public Flux<VideoResponse> notifyVideoChange() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> {
                    // 1. créer un nouveau creator aléatoire
                    CreatorRequest creatorRequest =
                            new CreatorRequest("x" + new Random().nextInt(), "x@gmail.com");
                    Creator creator = creatorRepository.save(mapper.toCreator(creatorRequest));

                    // 2. récupérer la vidéo 1 et changer son creator
                    Video video = videoRepository.findById(1L)
                            .orElseThrow(() -> new RuntimeException("Video 1 not found"));
                    video.setCreator(creator);
                    videoRepository.save(video);

                    // 3. renvoyer la vidéo mise à jour
                    return mapper.toVideoResponse(video);
                });
    }
}