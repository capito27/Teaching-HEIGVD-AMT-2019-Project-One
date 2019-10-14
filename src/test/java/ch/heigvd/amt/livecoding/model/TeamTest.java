package ch.heigvd.amt.livecoding.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeamTest {
    void itShouldBePossibleToCreateTeams() {
        Team fclausanne = Team.builder()
                .name("F.C. Lausanne")
                .Country("Switzerland")
                .build();
        assertNotNull(fclausanne);
        assertEquals("F.C. Lausanne", fclausanne.getName());
    }
}
