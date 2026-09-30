package ma.xproce.videoservice.mappers;

import lombok.RequiredArgsConstructor;
import ma.xproce.videoservice.dtos.CreatorRequest;
import ma.xproce.videoservice.dtos.CreatorResponse;
import ma.xproce.videoservice.dtos.VideoRequest;
import ma.xproce.videoservice.dtos.VideoResponse;
import ma.xproce.videoservice.entities.Creator;
import ma.xproce.videoservice.entities.Video;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoMapper {

    private final ModelMapper modelMapper;

    public Creator toCreator(CreatorRequest request) {
        return modelMapper.map(request, Creator.class);
    }

    public Video toVideo(VideoRequest request) {
        return modelMapper.map(request, Video.class);
    }

    public CreatorResponse toCreatorResponse(Creator creator) {
        return modelMapper.map(creator, CreatorResponse.class);
    }

    public VideoResponse toVideoResponse(Video video) {
        return modelMapper.map(video, VideoResponse.class);
    }
}