package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
public class Stadium {
    private long id;
    private String name;
    private String location;
    private Integer viewerPlaces;
    private boolean isDeleted = false;
}
