package ch.heigvd.amt.livecoding.model;

import lombok.*;


@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Stadium {
    private long id;
    @NonNull private String name;
    @NonNull private String location;
    @NonNull private Integer viewerPlaces;
    private boolean isDeleted = false;
}
