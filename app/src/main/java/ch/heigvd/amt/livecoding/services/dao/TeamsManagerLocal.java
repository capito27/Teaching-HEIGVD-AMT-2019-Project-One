package ch.heigvd.amt.livecoding.services.dao;

import javax.ejb.Local;
import java.util.List;

import ch.heigvd.amt.livecoding.model.Team;

@Local
public interface TeamsManagerLocal {

    List<Team> findAllTeams();
    Team findTeam(int id);
}

