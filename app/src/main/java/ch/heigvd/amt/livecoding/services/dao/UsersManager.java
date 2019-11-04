package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UsersManager implements UsersManagerLocal {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    private List<User> findUsersByRule(String rule) {
        ArrayList<User> returnUser = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.user " + rule);
            ResultSet rs = pstmt.executeQuery();
            User.UserBuilder ub = User.builder();
            while (rs.next()) {
                ub.id(rs.getLong("id_user"))
                        .email(rs.getString("email"))
                        .firstname(rs.getString("first_name"))
                        .lastname(rs.getString("last_name"))
                        .password(rs.getString("password"))
                        .username(rs.getString("username"));

                returnUser.add(ub.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnUser;

    }

    @Override
    public User findUserById(int userID) {
        List<User> users = findUsersByRule("WHERE id_user = " + userID);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> users = findUsersByRule("WHERE username = '" + username + "'");
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User createUser(User user) {
        User returnUser = null;
        try {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] encodedhash = digest.digest(
                    user.getPassword().getBytes(StandardCharsets.UTF_8));
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.user (username, first_name, last_name, password, email) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFirstname());
            pstmt.setString(3, user.getLastname());
            pstmt.setString(4, bytesToHex(encodedhash));
            pstmt.setString(5, user.getEmail());
            int nbOfLines = pstmt.executeUpdate();
            if (nbOfLines != 0) {
                returnUser = user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UsersManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnUser;
    }

    // Source : https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
