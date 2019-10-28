package ch.heigvd.amt.livecoding.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class User {
    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
