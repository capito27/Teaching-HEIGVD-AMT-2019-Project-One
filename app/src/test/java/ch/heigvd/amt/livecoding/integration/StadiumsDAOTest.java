package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Stadium;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class StadiumsDAOTest {

    @EJB
    IStadiumsDAO stadiumsManager;

    @EJB
    IMatchesDAO matchesManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAStadium() {
        Stadium stadium = stadiumsManager.createStadium(new Stadium(0, "toto", "tata", 1));
        assertEquals("toto", stadium.getName());
        assertEquals("tata", stadium.getLocation());
        assertEquals(1, stadium.getViewerPlaces().intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAStadiumViaTheStadiumsDAO() {
        Stadium stadium = stadiumsManager.createStadium("toto", "tata", 1);
        assertEquals(stadium, stadiumsManager.getStadium(stadium.getId()));
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAStadium() {
        Stadium stadium = stadiumsManager.createStadium("toto", "tata", 1);
        int stadiumCount = stadiumsManager.getAllStadiums().size();
        stadiumsManager.deleteStadium(stadium);
        assertNull(stadiumsManager.getStadium(stadium.getId()));
        assertEquals(stadiumCount -1 , stadiumsManager.getAllStadiums().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldDeleteAllMatchesInStadiumWhenDeleted() {
        // guarentees that the stadium is at least in a match, all those methods MUST be tested in their respective tests
        Stadium stadium = matchesManager.getMatch(1).getLocation();
        int matchCount = matchesManager.getMatchCount();

        stadiumsManager.deleteStadium(stadium);
        assertTrue(matchCount > matchesManager.getMatchCount());
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAStadium() {
        Stadium stadiumOriginal = stadiumsManager.createStadium("toto", "tata", 1);
        assertEquals(stadiumOriginal, stadiumsManager.getStadium(stadiumOriginal.getId()));
        Stadium stadiumModified = stadiumOriginal.toBuilder().viewerPlaces(2).build();
        assertTrue(stadiumsManager.updateStadium(stadiumModified));
        assertNotEquals(stadiumOriginal, stadiumModified);
    }

    // mostly for code coverage completeness
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBreakWithInvalidInput() {
        assertNull(stadiumsManager.createStadium(null));
        assertNull(stadiumsManager.getStadium(-1));
        assertFalse(stadiumsManager.updateStadium(null));
        assertFalse(stadiumsManager.deleteStadium(-1));
        assertFalse(stadiumsManager.deleteStadium(null));
    }
}
