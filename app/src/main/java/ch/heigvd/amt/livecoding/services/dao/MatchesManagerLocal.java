package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;
import ch.heigvd.amt.livecoding.model.Stadium;
import ch.heigvd.amt.livecoding.model.Team;
import ch.heigvd.amt.livecoding.model.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MatchesManagerLocal {
    int getMatchCount();
    List<Match> findAllMatches();
    Match findMatch(int id);
    List<Match> getXMatchesFromYThMatch(int offset, int count);
    List<Match> getMatchesFromUser(long userId);

    boolean createMatch(int score1, int score2, int team1, int team2, int stadium, int user);
}
