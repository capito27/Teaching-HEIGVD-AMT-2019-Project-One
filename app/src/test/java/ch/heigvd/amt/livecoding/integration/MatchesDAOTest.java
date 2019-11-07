package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Match;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MatchesDAOTest {

    @EJB
    IMatchesDAO manager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAMatch() {
        manager.createMatch(1, 1, 1, 1, 1, 1);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldIncreaseMatchCountWhenCreatingMatch() {
        int matchCount = manager.getMatchCount();
        manager.createMatch(1, 1, 1, 1, 1, 1);
        assertEquals(matchCount + 1, manager.getMatchCount());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAMatchViaTheMatchesDAO() {
        Match match =
                manager.createMatch(1, 1, 1, 1, 1, 1);
        assertEquals(match, manager.getMatch(match.getId()));
    }

    @Test
    public void itShouldBePossibleToRetrieveAllMatchesFromATeam() {
        List<Match> teamMatches = manager.getMatchesFromTeam(1),
                allMatches = manager.getAllMatches();

        // check that we actually got all matches
        assertEquals(manager.getMatchCount(), allMatches.size());

        // check that all matches we got are actually played by team1
        assertTrue(teamMatches.stream().allMatch(match -> (match.getTeam1().getId() == 1 || match.getTeam2().getId() == 1)));
        // check that the total number of matches not played by team 1 + the matches from team 1 is the total number of matches
        assertEquals(teamMatches.size() + allMatches.stream().filter(match -> (match.getTeam1().getId() != 1 && match.getTeam2().getId() != 1)).count(), allMatches.size());
    }

    @Test
    public void itShouldBePossibleToRetrieveAllMatchesFromAUser() {
        List<Match> userMatches = manager.getMatchesFromUser(1),
                allMatches = manager.getAllMatches();

        // check that we actually got all matches
        assertEquals(manager.getMatchCount(), allMatches.size());

        assertEquals(manager.getMatchCountFromUser(1), userMatches.size());

        // check that all matches we got are actually played in stadium 1
        assertTrue(userMatches.stream().allMatch(match -> match.getUser().getId() == 1));
        // check that the total number of matches not played in stadium 1 + the matches in stadium 1 is the total number of matches
        assertEquals(userMatches.size() + allMatches.stream().filter(match -> match.getUser().getId() != 1).count(), allMatches.size());
    }

    @Test
    public void itShouldBePossibleToRetrieveAllMatchesFromAStadium() {
        List<Match> stadiumMatch = manager.getMatchesFromStadium(1),
                allMatches = manager.getAllMatches();

        // check that we actually got all matches
        assertEquals(manager.getMatchCount(), allMatches.size());


        // check that all matches we got are actually played in stadium 1
        assertTrue(stadiumMatch.stream().allMatch(match -> match.getLocation().getId() == 1));
        // check that the total number of matches not played in stadium 1 + the matches in stadium 1 is the total number of matches
        assertEquals(stadiumMatch.size() + allMatches.stream().filter(match -> match.getLocation().getId() != 1).count(), allMatches.size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAMatch() {
        int matchCount = manager.getMatchCount();
        Match match = manager.createMatch(1, 1, 1, 1, 1, 1);
        assertEquals(matchCount + 1, manager.getMatchCount());
        manager.deleteMatch(match);
        assertEquals(matchCount, manager.getMatchCount());
        assertNull(manager.getMatch(match.getId()));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAMatch() {
        Match matchOriginal = manager.createMatch(1, 1, 1, 1, 1, 1);
        assertEquals(matchOriginal, manager.getMatch(matchOriginal.getId()));
        Match matchModified = matchOriginal.toBuilder().goals1(2).build();
        assertTrue(manager.updateMatch(matchModified));
        assertNotEquals(matchOriginal, matchModified);
    }

    // mostly for code coverage completeness
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBreakWithInvalidInput() {
        assertNull(manager.createMatch(0, 0, 0, 0, 0, 0));
        assertNull(manager.createMatch(null));
        assertNull(manager.createMatch(new Match(1, 0, 0, null, null, null, null)));
        // invalid offset or count should return an empty list
        assertArrayEquals(manager.getMatchesFromOffset(0, 0).toArray(), new ArrayList<Match>().toArray());
        assertArrayEquals(manager.getMatchesFromUserAndOffset(0, 0, 0).toArray(), new ArrayList<Match>().toArray());
        assertFalse(manager.updateMatch(null));
        assertFalse(manager.deleteMatch(-1));
        assertFalse(manager.deleteMatch(null));
    }
}
