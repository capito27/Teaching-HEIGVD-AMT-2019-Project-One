package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;
import ch.heigvd.amt.livecoding.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MatchesManagerLocal {

    // Create
    Match createMatch(int score1, int score2, long team1, long team2, long stadium, long user);

    Match createMatch(Match match);

    // Read
    int getMatchCount();

    int getMatchCountFromUser(long userId);

    List<Match> getAllMatches();

    Match getMatch(long id);

    List<Match> getMatchesFromOffset(int offset, int count);

    List<Match> getMatchesFromUser(long userId);

    List<Match> getMatchesFromUserAndOffset(long userId, int offset, int count);

    // Update (all fields other than the first one are optional)
    boolean updateMatch(long id, Integer score1, Integer score2, Long team1, Long team2, Long stadium, Long user);

    boolean updateMatch(Match match, Integer score1, Integer score2, Team team1, Team team2, Stadium stadium, User user);

    // Delete
    boolean deleteMatch(long match_id);

    boolean deleteMatch(Match match);
}
