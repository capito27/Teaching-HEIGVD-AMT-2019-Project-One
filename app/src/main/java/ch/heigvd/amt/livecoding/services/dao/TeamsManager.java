package ch.heigvd.amt.livecoding.services.dao;


import ch.heigvd.amt.livecoding.model.Team;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
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
public class TeamsManager implements TeamsManagerLocal{

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    @Override
    public List<Team> findAllTeams() {
        List<Team> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.team");
            ResultSet rs = pstmt.executeQuery();
            Team.TeamBuilder tb = Team.builder();
            while (rs.next()) {
                tb.id(rs.getLong("idteam"))
                        .country(rs.getString("team_country"))
                        .name(rs.getString("team_name"));
                returnVal.add(tb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public Team findTeam(int id) {
        Team returnVal = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.team WHERE id = ?");
            ResultSet rs = pstmt.executeQuery(Integer.toString(id));
            if (rs.next()) {
                returnVal = Team.builder().id(rs.getLong("idteam"))
                        .country(rs.getString("team_country"))
                        .name(rs.getString("team_name")).build();
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }
}
