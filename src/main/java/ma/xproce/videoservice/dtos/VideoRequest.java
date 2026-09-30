package ma.xproce.videoservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class VideoRequest {
    private String name;
    private String url;
    private String description;
    private String datePublication;   // String, comme dans le schéma GraphQL
    private CreatorRequest creator;
}