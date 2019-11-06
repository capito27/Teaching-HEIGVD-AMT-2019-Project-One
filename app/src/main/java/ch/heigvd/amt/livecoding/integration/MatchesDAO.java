package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;
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
public class MatchesDAO implements IMatchesDAO {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;


    private List<Match> getMatchesByRule(String rule) {
        List<Match> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_stadium = id_stadium INNER JOIN amt.user ON FK_user = id_user " + rule);
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();
            User.UserBuilder ub = User.builder();

            while (rs.next()) {

                Team t1 = tb.id(rs.getLong("t1.id_team"))
                        .name(rs.getString("t1.team_name"))
                        .country(rs.getString("t1.team_country")).build();

                Team t2 = tb.id(rs.getLong("t2.id_team"))
                        .name(rs.getString("t2.team_name"))
                        .country(rs.getString("t2.team_country")).build();

                Stadium s = sb.id(rs.getLong("id_stadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places")).build();

                User u = ub.id(rs.getLong("id_user"))
                        .username(rs.getString("username"))
                        .email(rs.getString("email"))
                        .firstname(rs.getString("first_name"))
                        .lastname(rs.getString("last_name"))
                        .password(rs.getString("password")).build();

                mb.id(rs.getLong("id_match"))
                        .goals1(rs.getInt("score1"))
                        .goals2(rs.getInt("score2"))
                        .team1(t1)
                        .team2(t2)
                        .user(u)
                        .location(s);

                returnVal.add(mb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    private int getMatchCount(String rule) {
        int matchCount = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM amt.match " + rule);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                matchCount = rs.getInt("total");
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return matchCount;
    }

    @Override
    public Match createMatch(int score1, int score2, long team1, long team2, long stadium, long user) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.`match` (score1, score2, FK_team1, FK_team2, FK_stadium, FK_user) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, score1);
            pstmt.setInt(2, score2);
            pstmt.setLong(3, team1);
            pstmt.setLong(4, team2);
            pstmt.setLong(5, stadium);
            pstmt.setLong(6, user);
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
            return (key == -1) ? null : getMatch(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Match createMatch(Match match) {
        return match == null ? null : createMatch(match.getGoals1(),
                match.getGoals2(),
                match.getTeam1().getId(),
                match.getTeam2().getId(),
                match.getLocation().getId(),
                match.getUser().getId());
    }

    @Override
    public int getMatchCount() {
        return getMatchCount("");
    }

    @Override
    public int getMatchCountFromUser(long userId) {
        return getMatchCount("WHERE FK_user = " + userId);
    }

    @Override
    public List<Match> getAllMatches() {
        return getMatchesByRule("");
    }

    @Override
    public Match getMatch(long id) {
        List<Match> matches = getMatchesByRule("WHERE id_match = " + id);
        return (!matches.isEmpty()) ? matches.get(0) : null;
    }

    @Override
    public List<Match> getMatchesFromOffset(int offset, int count) {
        return getMatchesByRule("ORDER BY id_match LIMIT " + offset + "," + count);
    }

    @Override
    public List<Match> getMatchesFromUser(long userId) {
        return getMatchesByRule("WHERE FK_user = " + userId);
    }

    @Override
    public List<Match> getMatchesFromTeam(long teamId) {
        return getMatchesByRule("WHERE FK_team1 = " + teamId + " OR FK_team2 = " + teamId);
    }

    @Override
    public List<Match> getMatchesFromStadium(long stadiumId) {
        return getMatchesByRule("WHERE FK_stadium = " + stadiumId);
    }

    @Override
    public List<Match> getMatchesFromUserAndOffset(long userId, int offset, int count) {
        return getMatchesByRule("WHERE FK_user = " + userId + " ORDER BY id_match LIMIT " + offset + "," + count);
    }

    @Override
    public boolean updateMatch(long id, Integer score1, Integer score2, Long team1, Long team2, Long stadium, Long user) {
        Connection conn = null;
        try {
            Match match = getMatch(id);
            if (match == null)
                return false;

            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE amt.`match` SET score1 = ?, score2 = ?, FK_team1 = ?, FK_team2 = ?, FK_stadium = ?, FK_user = ? WHERE id_match = ?;");
            // insert the values into the prepared statement
            pstmt.setInt(1, score1 == null ? match.getGoals1() : score1);
            pstmt.setInt(2, score2 == null ? match.getGoals2() : score2);
            pstmt.setLong(3, team1 == null ? match.getTeam1().getId() : team1);
            pstmt.setLong(4, team2 == null ? match.getTeam2().getId() : team2);
            pstmt.setLong(5, stadium == null ? match.getLocation().getId() : stadium);
            pstmt.setLong(6, user == null ? match.getUser().getId() : user);
            pstmt.setLong(7, id);
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
    public boolean updateMatch(Match match) {
        return match != null && updateMatch(match.getId(),
                match.getGoals1(),
                match.getGoals2(),
                match.getTeam1().getId(),
                match.getTeam2().getId(),
                match.getLocation().getId(),
                match.getUser().getId());
    }

    @Override
    public boolean deleteMatch(long match_id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM amt.match WHERE id_match = ?");
            pstmt.setLong(1, match_id);
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
    public boolean deleteMatch(Match match) {
        return match != null && deleteMatch(match.getId());
    }

}
