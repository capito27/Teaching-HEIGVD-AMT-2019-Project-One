package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;
import ch.heigvd.amt.livecoding.model.User;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceContext;
import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MatchesManager implements MatchesManagerLocal {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    @Override
    public int getMatchCount() {
        int matchCount = 0;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS total FROM amt.match");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                matchCount = rs.getInt("total");
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return matchCount;
    }

    @Override
    public List<Match> findAllMatches() {
        List<Match> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_stadium = id_stadium INNER JOIN amt.user ON FK_user = id_user");
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
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public Match findMatch(int id) {
        Match returnVal = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_stadium = id_stadium INNER JOIN amt.user ON FK_user = id_user WHERE id_match = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();
            User.UserBuilder ub = User.builder();

            if (rs.next()) {

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

                returnVal = mb.build();

            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public List<Match> getXMatchesFromYThMatch(int offset, int count) {
        List<Match> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_stadium = id_stadium INNER JOIN amt.user ON FK_user = id_user ORDER BY id_match LIMIT ?,?");
            pstmt.setInt(1, offset);
            pstmt.setInt(2, count);
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
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public List<Match> getMatchesFromUser(long userId) {
        List<Match> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_stadium = id_stadium INNER JOIN amt.user ON FK_user = id_user WHERE FK_user = ?");
            pstmt.setLong(1, userId);
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
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public boolean createMatch(int score1, int score2, int team1, int team2, int stadium, int user) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.`match` (score1, score2, FK_team1, FK_team2, FK_stadium, FK_user) VALUES(?,?,?,?,?,?)");
            pstmt.setInt(1, score1);
            pstmt.setInt(2, score2);
            pstmt.setLong(3, team1);
            pstmt.setLong(4, team2);
            pstmt.setLong(5, stadium);
            pstmt.setLong(6, user);
            int res = pstmt.executeUpdate();
            conn.close();
            return res != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
