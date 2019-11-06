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
        User user = usersManager.createUser(name, "tata", "titi", "tete", "tutu");
        assertEquals(name, user.getUsername());
        assertEquals("tata", user.getFirstname());
        assertEquals("titi", user.getLastname());
        assertEquals("tete", user.getEmail());
        assertEquals("tutu", user.getPassword());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAUsermViaTheUsersDAO() {
        String name = "toto" + (int) (Math.random() * 10000000);
        User user = usersManager.createUser(name, "tata", "titi", "tete", "tutu");
        assertEquals(user, usersManager.getUserById(user.getId()));
        assertEquals(user, usersManager.getUserByUsername(name));
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAUser() {
        User user = usersManager.createUser("toto" + (int) (Math.random() * 10000000), "tata", "titi", "tete", "tutu");
        usersManager.deleteUser(user);
        assertNull(usersManager.getUserById(user.getId()));
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
        User userOriginal = usersManager.createUser("toto" + (int) (Math.random() * 10000000), "tata", "titi", "tete", "tutu");
        assertEquals(userOriginal, usersManager.getUserById(userOriginal.getId()));
        User userModified = userOriginal.toBuilder().firstname("toto").build();
        assertTrue(usersManager.updateUser(userModified));
        assertNotEquals(userOriginal, userModified);
    }
}