package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MatchesManagerLocal {
    int getMatchCount();
    List<Match> findAllMatches();
    Match findMatch(int id);
    List<Match> getXMatchesFromYThMatch(int offset, int count);
}
