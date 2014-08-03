package org.t4atf.mauser.rule;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.neo4j.graphalgo.impl.util.PathImpl.singular;

import java.util.ArrayList;

import org.jmock.Expectations;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class RulesTest extends MauserUnitTesting
{

  @Test
  public void bestMatchWhenNoRuleIsFound()
  {
    Rules rules = givenAnEmptySetOfRules();
    
    Rule bestRule = rules.getBestMatch();
    assertThat(bestRule.toString(), equalTo("No rule found"));
  }

  private Rules givenAnEmptySetOfRules()
  {
    Rules rules = Rules.build(new ArrayList<Path>());
    return rules;
  }
  
  @Test
  public void bestMatch()
  {
    Rules rules = givenANonEmptySetOfRules();
    
    Rule bestRule = rules.getBestMatch();
    assertThat(bestRule.toString(), equalTo("MXP"));
  }

  private Rules givenANonEmptySetOfRules()
  {
    final Node node = context.mock(Node.class);
    final GraphDatabaseService database = context.mock(GraphDatabaseService.class);
    final Transaction tx = context.mock(Transaction.class);
    context.checking(new Expectations()
    {
      {
        allowing(node).getGraphDatabase(); will(returnValue(database));
        allowing(database).beginTx(); will(returnValue(tx));
        allowing(node).getProperty("name"); will(returnValue("MXP"));
        allowing(tx).close();
      }
    });
    Rules rules = Rules.build(singleton(singular(node)));
    return rules;
  }

  @Override
  protected Label getLocalizedLabel()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
