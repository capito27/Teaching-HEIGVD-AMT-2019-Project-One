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
    public List<Match> findAllMatches() {
        List<Match> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match INNER JOIN amt.team AS t1 ON team_idteam = t1.idteam INNER JOIN amt.team AS t2 ON team_idteam1 = t2.idteam INNER JOIN amt.stadium ON stadium_idstadium = idstadium");
            ResultSet rs = pstmt.executeQuery();

            Match.MatchBuilder mb = Match.builder();
            Team.TeamBuilder tb = Team.builder();
            Stadium.StadiumBuilder sb = Stadium.builder();

            while (rs.next()) {

                Team t1 = tb.id(rs.getLong("t1.idteam"))
                        .name(rs.getString("t1.team_name"))
                        .country(rs.getString("t1.team_country")).build();

                Team t2 = tb.id(rs.getLong("t2.idteam"))
                        .name(rs.getString("t2.team_name"))
                        .country(rs.getString("t2.team_country")).build();

                Stadium s = sb.id(rs.getLong("idstadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places")).build();

                mb.id(rs.getLong("idmatch"))
                        .goals1(rs.getInt("score"))
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
            ResultSet rs = pstmt.executeQuery(Integer.toString(id));

            if (rs.next()) {

                Team t1 = Team.builder().id(rs.getLong("t1.idteam"))
                        .name(rs.getString("t1.team_name"))
                        .country(rs.getString("t1.team_country")).build();

                Team t2 = Team.builder().id(rs.getLong("t2.idteam"))
                        .name(rs.getString("t2.team_name"))
                        .country(rs.getString("t2.team_country")).build();

                Stadium s = Stadium.builder().id(rs.getLong("idstadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places")).build();

                returnVal = Match.builder().id(rs.getLong("idmatch"))
                        .goals1(rs.getInt("score"))
                        .goals2(rs.getInt("score2"))
                        .team1(t1)
                        .team2(t2)
                        .location(s).build();

            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }
}
