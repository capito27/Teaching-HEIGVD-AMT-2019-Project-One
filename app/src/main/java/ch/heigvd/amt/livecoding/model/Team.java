package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
public class Team {
    private long id;
    private String name;
    private String country;
    private boolean isDeleted = false;
}
