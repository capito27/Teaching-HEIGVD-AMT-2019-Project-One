package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UsersDAO implements IUsersDAO {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    private List<User> getUsersByRule(String rule) {
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
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnUser;
    }

    // Source : https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    private static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public User createUser(String username, String firstname, String lastname, String email, String password) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.`user` (`username`, `first_name`, `last_name`, `password`, `email`) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, password);
            pstmt.setString(5, email);
            int res = pstmt.executeUpdate();
            long key = -1;
            // if the statement was ran correctly, we get the generated key field
            if (res != 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    key = rs.getLong(1);
            }
            conn.close();
            // if we managed to get the id, we return the match
            return (key == -1) ? null : getUserById(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User createUser(User user) {
        return user == null ? null : createUser(user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword());
    }

    @Override
    public List<User> getAllUsers() {
        return getUsersByRule("");
    }

    @Override
    public User getUserById(long id) {
        List<User> users = getUsersByRule("WHERE id_user = " + id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> users = getUsersByRule("WHERE username = '" + username + "'");
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean updateUser(long id, String username, String firstname, String lastname, String email, String password) {
        Connection conn = null;
        try {
            String updateQuerry = "UPDATE amt.`user` SET ";
            boolean querryUpdated = false;

            // create the updateQuerry dynamically
            if (username != null) {
                updateQuerry += "username = ?,";
                querryUpdated = true;
            }

            if (firstname != null) {
                updateQuerry += "first_name = ?,";
                querryUpdated = true;
            }

            if (lastname != null) {
                updateQuerry += "last_name = ?,";
                querryUpdated = true;
            }

            if (email != null) {
                updateQuerry += "email = ?,";
                querryUpdated = true;
            }

            if (password != null) {
                updateQuerry += "password = ?,";
                querryUpdated = true;
            }

            // if no field was updated, don't run the statement, and return false
            if (!querryUpdated) {
                return false;
            }

            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateQuerry.substring(0, updateQuerry.length() - 1) + "WHERE id_user = ?;");
            int index = 1;
            // insert the values into the prepared statement
            if (username != null) {
                pstmt.setString(index++, username);
            }

            if (firstname != null) {
                pstmt.setString(index++, firstname);
            }

            if (lastname != null) {
                pstmt.setString(index++, lastname);
            }

            if (email != null) {
                pstmt.setString(index++, email);
            }

            if (password != null) {
                pstmt.setString(index++, password);
            }

            pstmt.setLong(index, id);

            int res = pstmt.executeUpdate();
            conn.close();
            // if we didn't change a single row, the update failed, so we return false.
            return res > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return user != null && updateUser(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
    }

    @Override
    public boolean deleteUser(long id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM amt.`user` WHERE id_user = ?");
            pstmt.setLong(1, id);
            int res = pstmt.executeUpdate();
            conn.close();
            // si le retour d'executeUpdate est 0, aucune ligne n'à été supprimé
            return res != 0;

        } catch (SQLException e) {
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return user != null && deleteUser(user.getId());

    }


}
