package org.t4atf.mauser.rule;

import java.util.Collection;

import org.neo4j.graphdb.Path;
import org.t4atf.mauser.geography.aiport.Airport;
import org.t4atf.mauser.geography.aiport.AirportDao;

public class ConcreteRuleRepository implements RuleRepository
{
  private final AirportDao airportDao;

  public ConcreteRuleRepository(AirportDao airportDao)
  {
    this.airportDao = airportDao;
  }

  /* (non-Javadoc)
   * @see org.t4atf.mauser.rule.RuleRepository#findRulesOrderedByLength(java.lang.String, java.lang.String)
   */
  @Override
  public Rules findRulesOrderedByLength(String departureAirportCode, String landingAirportCode)
  {
    Airport departure = airportDao.findOne(departureAirportCode);
    Airport landing = airportDao.findOne(landingAirportCode);
    Collection<Path> paths = departure.flyTo(landing);
    
    return Rules.build(paths);
  }
}