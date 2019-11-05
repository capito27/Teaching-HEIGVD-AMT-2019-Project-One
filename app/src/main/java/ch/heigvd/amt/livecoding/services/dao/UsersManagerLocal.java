package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.User;

import javax.ejb.Local;

@Local
public interface UsersManagerLocal {
    // Create
    User createUser(String username, String firstname, String lastname, String email, String password);

    User createUser(User user);

    // Read
    User getUserById(long id);

    User getUserByUsername(String username);

    // Update
    boolean updateUser(long id, String username, String firstname, String lastname, String email, String password);

    boolean updateUser(User user, String username, String firstname, String lastname, String email, String password);

    // Delete
    boolean deleteUser(long id);

    boolean deleteUser(User user);
}
