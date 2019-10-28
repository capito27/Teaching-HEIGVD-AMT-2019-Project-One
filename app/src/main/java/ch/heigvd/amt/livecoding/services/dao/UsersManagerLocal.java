package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.User;

import java.util.List;

public interface UsersManagerLocal {
    User findUserById(int userID);
    User findUserByUsername(String username);
}
