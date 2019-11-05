package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Team {
    private long id;
    @NonNull private String name;
    @NonNull private String country;
    private boolean isDeleted = false;

}
