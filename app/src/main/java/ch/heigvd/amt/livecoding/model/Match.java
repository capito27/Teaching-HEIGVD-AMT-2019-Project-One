package ch.heigvd.amt.livecoding.model;

import lombok.*;


@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class Match {
    private long id;
    @NonNull private Integer goals1, goals2;
    @NonNull private Team team1, team2;
    @NonNull private Stadium location;
    @NonNull private User user;
    private boolean isDeleted = false;
}
