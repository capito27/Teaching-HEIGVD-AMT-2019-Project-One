package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Stadium;

import javax.ejb.Local;
import java.util.List;

@Local
public interface StadiumsManagerLocal {

    List<Stadium> findAllStadiums();
    Stadium findStadium(int id);
}
