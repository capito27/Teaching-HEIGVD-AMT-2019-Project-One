package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class Match {
    private long id;
    private Team team1, team2;
    private Integer goals1, goals2;
    private Stadium location;
}
