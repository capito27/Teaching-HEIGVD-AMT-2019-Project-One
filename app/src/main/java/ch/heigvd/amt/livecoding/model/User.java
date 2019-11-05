package ch.heigvd.amt.livecoding.model;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private long id;
    @NonNull private String username;
    @NonNull private String firstname;
    @NonNull private String lastname;
    @NonNull private String email;
    @NonNull private String password;
    private boolean isDeleted = false;
}
