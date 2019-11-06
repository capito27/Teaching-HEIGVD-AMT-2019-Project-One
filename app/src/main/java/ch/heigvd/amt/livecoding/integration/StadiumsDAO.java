package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Stadium;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class StadiumsDAO implements IStadiumsDAO {

    @Resource(lookup = "jdbc/app")
    private DataSource dataSource;

    private List<Stadium> getStadiumByRule(String rule) {
        List<Stadium> returnVal = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM amt.stadium " + rule);
            ResultSet rs = pstmt.executeQuery();
            Stadium.StadiumBuilder sb = Stadium.builder();
            while (rs.next()) {
                sb.id(rs.getLong("id_stadium"))
                        .name(rs.getString("stadium_name"))
                        .location(rs.getString("stadium_location"))
                        .viewerPlaces(rs.getInt("stadium_viewer_places"));
                returnVal.add(sb.build());
            }
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(MatchesDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return returnVal;
    }

    @Override
    public Stadium createStadium(String name, String location, Integer viewerPlaces) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO amt.`stadium` (stadium_name, stadium_location, stadium_viewer_places) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            pstmt.setInt(3, viewerPlaces);
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
            return (key == -1) ? null : getStadium(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Stadium createStadium(Stadium stadium) {
        return stadium == null ? null : createStadium(stadium.getName(),
                stadium.getLocation(),
                stadium.getViewerPlaces());
    }

    @Override
    public List<Stadium> getAllStadiums() {
        return getStadiumByRule("");
    }

    public Stadium getStadium(long id) {
        List<Stadium> stadiums = getStadiumByRule(" WHERE id_stadium = " + id);
        return (stadiums.isEmpty()) ? null : stadiums.get(0);
    }

    @Override
    public boolean updateStadium(long id, String name, String location, Integer places) {

        Connection conn = null;
        try {
            Stadium stadium = getStadium(id);
            if (stadium == null)
                return false;

            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE amt.`stadium` SET stadium_name = ?, stadium_location = ?, stadium_viewer_places = ? WHERE id_stadium = ?;");
            // insert the values into the prepared statement
            pstmt.setString(1, name == null ? stadium.getName() : name);
            pstmt.setString(2, location == null ? stadium.getLocation() : location);
            pstmt.setInt(3, places == null ? stadium.getViewerPlaces() : places);
            pstmt.setLong(4, id);
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
    public boolean updateStadium(Stadium stadium) {
        return stadium != null && updateStadium(stadium.getId(), stadium.getName(), stadium.getLocation(), stadium.getViewerPlaces());
    }

    @Override
    public boolean deleteStadium(long id) {
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM amt.`stadium` WHERE id_stadium = ?");
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
    public boolean deleteStadium(Stadium stadium) {
        return stadium != null && deleteStadium(stadium.getId());
    }
}