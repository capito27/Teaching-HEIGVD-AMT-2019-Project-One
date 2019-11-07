package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class TeamsDAOTest {

    @EJB
    ITeamsDAO teamManager;

    @EJB
    IMatchesDAO matchesManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateATeam() {
        Team team = teamManager.createTeam("toto", "tata");
        assertEquals("toto", team.getName());
        assertEquals("tata", team.getCountry());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveATeamViaTheTeamsDAO() {
        Team team = teamManager.createTeam("toto", "tata");
        assertEquals(team, teamManager.getTeam(team.getId()));
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteATeam() {
        Team team = teamManager.createTeam("toto", "tata");

        int teamCount = teamManager.getAllTeams().size();
        teamManager.deleteTeam(team);
        assertNull(teamManager.getTeam(team.getId()));
        assertEquals(teamCount -1 , teamManager.getAllTeams().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldDeleteAllMatchesWithTeamWhenDeleted() {
        // guarentees that the team is at least in a match, all those methods MUST be tested in their respective tests
        Team team = matchesManager.getMatch(1).getTeam1();
        int matchCount = matchesManager.getMatchCount();

        teamManager.deleteTeam(team);
        assertTrue(matchCount > matchesManager.getMatchCount());
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateATeam() {
        Team teamOriginal = teamManager.createTeam("toto", "tata");
        assertEquals(teamOriginal, teamManager.getTeam(teamOriginal.getId()));
        Team teamModified = teamOriginal.toBuilder().name("titi").build();
        assertTrue(teamManager.updateTeam(teamModified));
        assertNotEquals(teamOriginal, teamModified);
    }

    // mostly for code coverage completeness
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBreakWithInvalidInput() {
        assertNull(teamManager.createTeam(null));
        assertNull(teamManager.getTeam(-1));
        assertFalse(teamManager.updateTeam(null));
        assertFalse(teamManager.deleteTeam(-1));
        assertFalse(teamManager.deleteTeam(null));
    }
}
