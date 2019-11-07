package ch.heigvd.amt.livecoding.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    final Team team = new Team(0,"F.C. Lausanne", "Switzerland");
    final User user = new User(0,"admin", "admin", "istrator", "admin@istrator.com", "toto", true);
    final Stadium stadium = new Stadium(0,"Sion", "Switzerland", 33);


    @Test
    void itShouldHaveAConstructor() {
        Match match = new Match(0,0, 1, team, team, stadium, user);
        assertEquals(0, match.getGoals1());
        assertEquals(1, match.getGoals2());
        assertEquals(team, match.getTeam1());
        assertEquals(team, match.getTeam2());
        assertEquals(stadium, match.getLocation());
        assertEquals(user, match.getUser());

    }

    @Test
    void itShouldBePossibleToCreateMatches() {
        Match match = Match.builder()
                .goals1(0)
                .goals2(1)
                .team1(team)
                .team2(team)
                .location(stadium)
                .user(user)
                .build();
        assertNotNull(match);
        assertEquals(0, match.getGoals1());
        assertEquals(1, match.getGoals2());
        assertEquals(team, match.getTeam1());
        assertEquals(team, match.getTeam2());
        assertEquals(stadium, match.getLocation());
        assertEquals(user, match.getUser());
    }

    @Test
    public void itShouldBePossibleToCloneAMatch() {
        Match match = Match.builder()
                .goals1(0)
                .goals2(1)
                .team1(team)
                .team2(team)
                .location(stadium)
                .user(user)
                .build();
        Match cloned = match.toBuilder().build();
        assertEquals(match, cloned);
        assertEquals(match.hashCode(),cloned.hashCode());
        assertNotSame(match, cloned);
    }
}
