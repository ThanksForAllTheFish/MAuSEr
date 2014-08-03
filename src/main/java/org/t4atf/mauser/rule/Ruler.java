package org.t4atf.mauser.rule;

public class Ruler
{
  private final RuleRepository ruleRepository;

  public Ruler(RuleRepository ruleRepository)
  {
    this.ruleRepository = ruleRepository;
  }

  public Rule findBestRule(String departureAirportCode, String landingAirportCode)
  {
    Rules rules = ruleRepository.findRulesOrderedByLength(departureAirportCode, landingAirportCode);
    return rules.getBestMatch();
  }
}
