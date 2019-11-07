package ch.heigvd.amt.livecoding.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void itShouldHaveAConstructor() {
        User user = new User(0,"admin", "admin", "istrator", "admin@istrator.com", "toto");
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getFirstname());
        assertEquals("istrator", user.getLastname());
        assertEquals("admin@istrator.com", user.getEmail());
        assertEquals("toto", user.getPassword());
    }

    @Test
    void itShouldBePossibleToCreateTeams() {
        User user = User.builder()
                .username("admin")
                .firstname("admin")
                .lastname("istrator")
                .email("admin@istrator.com")
                .password("toto")
                .build();
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("admin", user.getFirstname());
        assertEquals("istrator", user.getLastname());
        assertEquals("admin@istrator.com", user.getEmail());
        assertEquals("toto", user.getPassword());
    }

    @Test
    public void itShouldBePossibleToCloneATeam() {
        User user = User.builder()
                .username("admin")
                .firstname("admin")
                .lastname("istrator")
                .email("admin@istrator.com")
                .password("toto")
                .build();
        User cloned = user.toBuilder().build();
        assertEquals(user, cloned);
        assertEquals(user.hashCode(),cloned.hashCode());
        assertNotSame(user, cloned);
    }
}
