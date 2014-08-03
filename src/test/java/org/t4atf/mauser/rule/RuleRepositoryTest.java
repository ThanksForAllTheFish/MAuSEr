package org.t4atf.mauser.rule;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.t4atf.mauser.geography.aiport.AirportDao;
import org.t4atf.mauser.geography.aiport.ConcreteAirportDao;
import org.t4atf.mauser.neo4j.Neo4jTest;
import org.t4atf.mauser.transaction.TransactionWrapper;

public class RuleRepositoryTest extends Neo4jTest
{
  private AirportDao airportDao = new ConcreteAirportDao(new TransactionWrapper(database));
  private RuleRepository repository = new ConcreteRuleRepository(airportDao);
  
  @Test
  public void test()
  {
    Rules rules = repository.findRulesOrderedByLength("MXP", "CIA");
    
    assertThat(rules.toString(), equalTo("MXP_Milano_Lombardia_Italia_Lazio_Roma_CIA"));
  }
}
