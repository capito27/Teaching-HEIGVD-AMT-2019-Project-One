package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UsersManager implements UsersManagerLocal{

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    @Override
    public User findUserById(int userID) {
        User returnUser = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.user WHERE id_user = ?");
            ResultSet rs = pstmt.executeQuery(Integer.toString(userID));
            if(rs.next()) {
                returnUser= User.builder().id(rs.getLong("id_user"))
                        .email(rs.getString("email"))
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("lastname"))
                        .password("password")
                        .username("username").build();
            }
            conn.close();
        } catch(SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnUser;
    }

    @Override
    public User findUserByUsername(String username) {
        User returnUser = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.user WHERE username = ?");
            ResultSet rs = pstmt.executeQuery(username);
            if(rs.next()) {
                returnUser= User.builder().id(rs.getLong("id_user"))
                        .email(rs.getString("email"))
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("lastname"))
                        .password("password")
                        .username("username").build();
            }
            conn.close();
        } catch(SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnUser;
    }
}
