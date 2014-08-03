package org.t4atf.mauser.rule;

public interface RuleRepository
{

  public Rules findRulesOrderedByLength(String departureAirportCode, String landingAirportCode);

}
