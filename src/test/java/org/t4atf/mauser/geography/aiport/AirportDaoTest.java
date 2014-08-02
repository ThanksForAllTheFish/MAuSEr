package org.t4atf.mauser.geography.aiport;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.excpetions.NodeAlreadyExistent;
import org.t4atf.mauser.geography.aiport.Airport;
import org.t4atf.mauser.geography.aiport.AirportDao;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class AirportDaoTest extends MauserUnitTesting
{
  private AirportDao dao;
  private String testAirportCode = "LIN";

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
  public void createABrandNewAirportNode()
  {
    final String airportName = "Linate";
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode);
        oneOf(transactionOperation).createIndexedNode(buildExpectedProperties(testAirportCode, airportName), index, AIRPORT);
      }
    });

    Airport airport = dao.create(testAirportCode, airportName);

    assertThat(airport, notNullValue());
  }

  @Test
  public void cannotCreateTwiceTheSameAirportNode()
  {
    String airportName = "Linate";
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode); will(throwException(new NodeAlreadyExistent("")));
      }
    });
    exception.expect(NodeAlreadyExistent.class);
    dao.create(testAirportCode, airportName);
  }

  @Test
  public void airportFound()
  {
    final Node found = context.mock(Node.class);
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).find(testAirportCode, index); will(returnValue(found));
      }
    });
    Airport airport = dao.findByCode(testAirportCode);

    assertThat(airport, equalTo(new Airport(found)));
  }

  @Test
  public void airportNotFound()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).find(testAirportCode, index); will(returnValue(null));
      }
    });
    Airport airport = dao.findByCode(testAirportCode);

    assertThat(airport.toString(), equalTo("Airport '" + testAirportCode + "' not in registry"));
  }

  private Map<String, Object> buildExpectedProperties(final String airportCode, String airportName)
  {
    final Map<String, Object> expectedProperties = new HashMap<>();
    expectedProperties.put("name", airportCode);
    expectedProperties.put("longName", airportName);
    return expectedProperties;
  }
}
