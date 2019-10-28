package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Stadium;

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
public class StadiumsManager implements StadiumsManagerLocal {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    @Override
    public List<Stadium> findAllStadiums() {
        List<Stadium> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.stadium");
            ResultSet rs = pstmt.executeQuery();
            Stadium.StadiumBuilder sb = Stadium.builder();
            while (rs.next()) {
                sb.id(rs.getLong("idstadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places"));
                returnVal.add(sb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    public Stadium findStadium(int id) {
        Stadium returnVal = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.stadium WHERE id = ?");
            ResultSet rs = pstmt.executeQuery(Integer.toString(id));
            if (rs.next()) {
                returnVal = Stadium.builder().id(rs.getLong("idstadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places")).build();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnVal;
    }
}
