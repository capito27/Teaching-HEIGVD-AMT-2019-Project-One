package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IUsersDAO {
    // Create
    User createUser(String username, String firstname, String lastname, String email, String password, boolean isAdmin);

    User createUser(User user);

    // Read
    List<User> getAllUsers();

    User getUserById(long id);


    User getUserByUsername(String username);

    // Update
    boolean updateUser(long id, String username, String firstname, String lastname, String email, String password, boolean isAdmin);

    boolean updateUser(User user);

    // Delete
    boolean deleteUser(long id);

    boolean deleteUser(User user);
}
