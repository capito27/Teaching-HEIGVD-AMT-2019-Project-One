package ch.heigvd.amt.livecoding.services.dao;

import ch.heigvd.amt.livecoding.model.Match;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MatchesManagerLocal {
    List<Match> findAllMatches();
    Match findMatch(int id);
}
