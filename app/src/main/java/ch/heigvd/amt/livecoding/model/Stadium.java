package ch.heigvd.amt.livecoding.model;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class Stadium {
    private long id;
    private String name;
    private String location;
    private Integer viewerPlaces;
}
