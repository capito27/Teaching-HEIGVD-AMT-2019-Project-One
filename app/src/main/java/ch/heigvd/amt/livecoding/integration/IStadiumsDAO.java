package ch.heigvd.amt.livecoding.integration;

import ch.heigvd.amt.livecoding.model.Stadium;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IStadiumsDAO {

    // Create
    Stadium createStadium(String name, String location, Integer viewerPlaces);

    Stadium createStadium(Stadium stadium);

    // Read
    List<Stadium> getAllStadiums();

    Stadium getStadium(long id);

    // Update
    boolean updateStadium(long id, String name, String location, Integer places);

    boolean updateStadium(Stadium stadium);

    // Delete
    boolean deleteStadium(long id);

    boolean deleteStadium(Stadium stadium);

}
