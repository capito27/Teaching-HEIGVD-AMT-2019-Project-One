package ch.heigvd.amt.livecoding.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TeamTest {

    @Test
    void itShouldHaveAConstructor() {
        Team team = new Team(0,"F.C. Lausanne", "Switzerland");
        assertEquals("F.C. Lausanne", team.getName());
        assertEquals("Switzerland", team.getCountry());
    }

    @Test
    void itShouldBePossibleToCreateTeams() {
        Team fclausanne = Team.builder()
                .name("F.C. Lausanne")
                .country("Switzerland")
                .build();
        assertNotNull(fclausanne);
        assertEquals("F.C. Lausanne", fclausanne.getName());
    }

    @Test
    public void itShouldBePossibleToCloneATeam() {
        Team team = Team.builder()
                .name("F.C. Lausanne")
                .country("Switzerland")
                .build();
        Team cloned = team.toBuilder().build();
        assertEquals(team, cloned);
        assertNotSame(team, cloned);
    }
}
