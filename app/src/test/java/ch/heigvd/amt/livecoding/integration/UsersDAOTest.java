package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.User;
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
public class UsersDAOTest {

    @EJB
    IUsersDAO usersManager;

    @EJB
    IMatchesDAO matchesManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAUser() {
        String name = "toto" + (int) (Math.random() * 10000000);
        User user = usersManager.createUser(name, "tata", "titi", "tete", "tutu", false);
        assertEquals(name, user.getUsername());
        assertEquals("tata", user.getFirstname());
        assertEquals("titi", user.getLastname());
        assertEquals("tete", user.getEmail());
        assertEquals("tutu", user.getPassword());
        assertFalse(user.isAdmin());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAUsermViaTheUsersDAO() {
        String name = "toto" + (int) (Math.random() * 10000000);
        User user = usersManager.createUser(name, "tata", "titi", "tete", "tutu", false);
        assertEquals(user, usersManager.getUserById(user.getId()));
        assertEquals(user, usersManager.getUserByUsername(name));
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAUser() {
        User user = usersManager.createUser("toto" + (int) (Math.random() * 10000000), "tata", "titi", "tete", "tutu", false);
        int userCount =usersManager.getAllUsers().size();
        usersManager.deleteUser(user);
        assertNull(usersManager.getUserById(user.getId()));
        assertEquals(userCount-1, usersManager.getAllUsers().size());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldDeleteAllMatchesByUserWhenDeleted() {
        // guarentees that the user has at least a match, all those methods MUST be tested in their respective tests
        User team = matchesManager.getMatch(1).getUser();
        int matchCount = matchesManager.getMatchCount();

        usersManager.deleteUser(team);
        assertTrue(matchCount > matchesManager.getMatchCount());
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAUser() {
        User userOriginal = usersManager.createUser("toto" + (int) (Math.random() * 10000000), "tata", "titi", "tete", "tutu", false);
        assertEquals(userOriginal, usersManager.getUserById(userOriginal.getId()));
        User userModified = userOriginal.toBuilder().firstname("toto").build();
        assertTrue(usersManager.updateUser(userModified));
        assertNotEquals(userOriginal, userModified);
    }

    // mostly for code coverage completeness
    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldNotBreakWithInvalidInput() {
        assertNull(usersManager.createUser(null));
        assertNull(usersManager.getUserById(-1));
        assertNull(usersManager.getUserByUsername(""));
        assertFalse(usersManager.updateUser(null));
        assertFalse(usersManager.deleteUser(-1));
        assertFalse(usersManager.deleteUser(null));
    }
}