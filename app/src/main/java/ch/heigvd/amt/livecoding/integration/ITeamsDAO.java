package ch.heigvd.amt.livecoding.integration;

import javax.ejb.Local;
import java.util.List;

import ch.heigvd.amt.livecoding.model.Team;

@Local
public interface ITeamsDAO {

    // Create
    Team createTeam(String name, String country);

    Team createTeam(Team team);

    // Read
    List<Team> getAllTeams();

    Team getTeam(long id);

    // Update
    boolean updateTeam(long id, String name, String country);

    boolean updateTeam(Team team);

    // Delete
    boolean deleteTeam(long id);

    boolean deleteTeam(Team team);
}

