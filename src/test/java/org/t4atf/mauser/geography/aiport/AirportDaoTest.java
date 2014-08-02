package org.t4atf.mauser.geography.aiport;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;
import static org.t4atf.mauser.neo4j.MauserRelations.CITY;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.city.City;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class AirportDaoTest extends MauserUnitTesting
{
  private ConcreteAirportDao dao;
  private String testAirportCode = "LIN";
  private Node node = context.mock(Node.class);

  @Before
  public void init()
  {
    super.init();
    dao = new ConcreteAirportDao(transactionOperation);
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
    Airport airport = dao.createNonExistentEntity(testAirportCode);
    
    assertThat(airport.getId(), equalTo(0L));
  }
  
  @Test
  public void createABrandNewAirport()
  {
    final String airportName = "Linate";
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode);
        oneOf(transactionOperation).createIndexedNode(buildExpectedProperties(testAirportCode, airportName), index, getLocalizedLabel());
      }
    });

    Airport airport = dao.create(testAirportCode, airportName);

    assertThat(airport, notNullValue());
  }
  
  @Test
  public void placeAirportInCity() throws Exception
  {
    final Node airport = context.mock(Node.class, "airport");
    final Node city = context.mock(Node.class, "city");
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).createRelationshipBetween(airport, city, CITY);
      }
    });
    dao.connect(new Airport(airport), new City(city));
  }

  @Override
  protected Label getLocalizedLabel()
  {
    return AIRPORT;
  }
}
