package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean admin;
}
