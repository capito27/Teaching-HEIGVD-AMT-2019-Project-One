package ch.heigvd.amt.livecoding.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StadiumTest {

    @Test
    void itShouldHaveAConstructor() {
        Stadium stadium = new Stadium(0,"Sion", "Switzerland", 33);

        assertEquals("Sion", stadium.getName());
        assertEquals("Switzerland", stadium.getLocation());
        assertEquals(33, stadium.getViewerPlaces());
    }

    @Test
    void itShouldBePossibleToCreateTeams() {
        Stadium stadium = Stadium.builder()
                .name("Sion")
                .location("Switzerland")
                .viewerPlaces(33)
                .build();
        assertNotNull(stadium);
        assertEquals("Sion", stadium.getName());
        assertEquals("Switzerland", stadium.getLocation());
        assertEquals(33, stadium.getViewerPlaces());
    }

    @Test
    public void itShouldBePossibleToCloneATeam() {
        Stadium stadium = Stadium.builder()
                .name("Sion")
                .location("Switzerland")
                .viewerPlaces(33)
                .build();
        Stadium cloned = stadium.toBuilder().build();
        assertEquals(stadium, cloned);
        assertNotSame(stadium, cloned);
    }

}
