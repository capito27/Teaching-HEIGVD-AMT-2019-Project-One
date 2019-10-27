package ch.heigvd.amt.livecoding.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Match {
    private Team team1, team2;
    private Integer goals1, goals2;
    private Stadium location;
}
