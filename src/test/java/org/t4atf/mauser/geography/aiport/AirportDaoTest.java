package org.t4atf.mauser.geography.aiport;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class AirportDaoTest extends MauserUnitTesting
{
  private AirportDao dao;
  private String testAirportCode = "LIN";
  private Node node = context.mock(Node.class);

  @Before
  public void init()
  {
    super.init();
    dao = new AirportDao(transactionOperation);
  }
  
  @Override
  protected Label getLocalizedLabel()
  {
    return AIRPORT;
  }
  
  @Test
  public void createAirport() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    Airport airport = dao.createEntity(node);
    
    assertThat(airport.getId(), equalTo(1L));
  }
  
  @Test
  public void createNonExistentAirport() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    Airport airport = dao.createNonExistentEntity(testAirportCode);
    
    assertThat(airport.getId(), equalTo(0L));
  }
}