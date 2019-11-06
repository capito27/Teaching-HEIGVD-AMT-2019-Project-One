package ch.heigvd.amt.livecoding.integration;


import ch.heigvd.amt.livecoding.model.Team;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
public class TeamsDAO implements ITeamsDAO {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    private List<Team> getTeamsByRule(String rule) {
        List<Team> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.team " + rule);
            ResultSet rs = pstmt.executeQuery();
            Team.TeamBuilder tb = Team.builder();
            while (rs.next()) {
                tb.id(rs.getLong("id_team"))
                        .country(rs.getString("team_country"))
                        .name(rs.getString("team_name"));
                returnVal.add(tb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;

    }

    @Override
    public Team createTeam(String name, String country) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.`team` (team_name, team_country) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, country);
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
            return (key == -1) ? null : getTeam(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Team createTeam(Team team) {
        return team == null ? null : createTeam(team.getName(), team.getCountry());
    }

    @Override
    public List<Team> getAllTeams() {
        return getTeamsByRule("");
    }

    @Override
    public Team getTeam(long id) {
        List<Team> teams = getTeamsByRule("WHERE id_team = " + id);
        return teams.isEmpty() ? null : teams.get(0);
    }

    @Override
    public boolean updateTeam(long id, String name, String country) {
        Connection conn = null;
        try {
            String updateQuerry = "UPDATE amt.`team` SET ";
            boolean querryUpdated = false;

            // create the updateQuerry dynamically
            if (name != null) {
                updateQuerry += "team_name = ?,";
                querryUpdated = true;
            }

            if (country != null) {
                updateQuerry += "team_country = ?,";
                querryUpdated = true;
            }
            // if no field was updated, don't run the statement, and return false
            if (!querryUpdated) {
                return false;
            }

            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(updateQuerry.substring(0, updateQuerry.length() - 1) + "WHERE id_team = ?;");
            int index = 1;
            // insert the values into the prepared statement
            if (name != null) {
                pstmt.setString(index++, name);
            }

            if (country != null) {
                pstmt.setString(index++, country);
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
    public boolean updateTeam(Team team, String name, String country) {
        return team != null && updateTeam(team.getId(), name, country);
    }

    @Override
    public boolean deleteTeam(long id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM amt.`team` WHERE id_team = ?");
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
    public boolean deleteTeam(Team team) {
        return team != null && deleteTeam(team.getId());
    }
}
