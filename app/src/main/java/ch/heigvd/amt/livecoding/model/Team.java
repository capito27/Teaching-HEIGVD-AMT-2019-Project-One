package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder
@Getter
@EqualsAndHashCode
public class Team {
    private long id;
    private String name;
    private String country;
}
