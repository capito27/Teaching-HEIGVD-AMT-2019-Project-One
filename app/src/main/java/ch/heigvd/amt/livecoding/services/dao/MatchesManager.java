package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;

import javax.annotation.Resource;
import javax.ejb.Stateless;
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
            System.out.println("Schema : " + conn.getSchema());
            System.out.println("Catalog : " + conn.getCatalog());
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.match");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                int score1 = rs.getInt("score");
                int score2 = rs.getInt("score2");
                Team team1 = new Team("test", "proutLand");
                Team team2 = new Team("test2","proutLand2");
                Stadium location = new Stadium("Prout", "proutLand", 25000);
                returnVal.add(new Match(team1,team2,score1,score2,location));
            }
            conn.close();
        } catch(SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }
}
