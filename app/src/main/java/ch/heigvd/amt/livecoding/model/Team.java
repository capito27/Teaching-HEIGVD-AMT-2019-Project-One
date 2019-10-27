package ch.heigvd.amt.livecoding.model;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class Team {
    private String name;
    private String Country;
}
