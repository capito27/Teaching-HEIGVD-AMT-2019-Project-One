package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
public class Match {
    private long id;
    private Integer goals1, goals2;
    private Team team1, team2;
    private Stadium location;
    private User user;
    private boolean isDeleted = false;
}
