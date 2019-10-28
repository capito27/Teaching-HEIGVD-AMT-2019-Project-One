package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;

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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_user = id_stadium");
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();

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

                mb.id(rs.getLong("id_match"))
                        .goals1(rs.getInt("score1"))
                        .goals2(rs.getInt("score2"))
                        .team1(t1)
                        .team2(t2)
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON team_idteam = t1.idteam INNER JOIN amt.team AS t2 ON team_idteam1 = t2.idteam INNER JOIN amt.stadium ON stadium_idstadium = idstadium WHERE idmatch = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();

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

                mb.id(rs.getLong("id_match"))
                        .goals1(rs.getInt("score1"))
                        .goals2(rs.getInt("score2"))
                        .team1(t1)
                        .team2(t2)
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
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON FK_team1 = t1.id_team INNER JOIN amt.team AS t2 ON FK_team2 = t2.id_team INNER JOIN amt.stadium ON FK_user = id_stadium ORDER BY id_match LIMIT ?,?");
            pstmt.setInt(1, offset);
            pstmt.setInt(2, count);
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();

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

                mb.id(rs.getLong("id_match"))
                        .goals1(rs.getInt("score1"))
                        .goals2(rs.getInt("score2"))
                        .team1(t1)
                        .team2(t2)
                        .location(s);

                returnVal.add(mb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }
}
