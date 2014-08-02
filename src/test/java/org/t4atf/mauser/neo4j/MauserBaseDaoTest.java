package org.t4atf.mauser.neo4j;

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

public class MauserBaseDaoTest extends MauserUnitTesting
{
  private MauserBaseDao<Airport> dao;
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
  public void createABrandNewNode()
  {
    final Map<String, Object> properties = buildExpectedProperties(testAirportCode, "Linate");
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode);
        oneOf(transactionOperation).createIndexedNode(properties, index, getLocalizedLabel());
      }
    });

    Airport airport = dao.create(properties);

    assertThat(airport, notNullValue());
  }

  @Test
  public void cannotCreateSameNodeTwice()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode); will(throwException(new NodeAlreadyExistent("")));
      }
    });
    exception.expect(NodeAlreadyExistent.class);
    dao.create(buildExpectedProperties(testAirportCode, "Linate"));
  }

  @Test
  public void nodeFound()
  {
    final Node found = context.mock(Node.class);
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).find(testAirportCode, index); will(returnValue(found));
        allowing(found).getId(); will(returnValue(1L));
      }
    });
    Airport airport = dao.findByName(testAirportCode);

    assertThat(airport.getId(), equalTo(1L));
  }

  @Test
  public void nodeNotFound()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).find(testAirportCode, index); will(returnValue(null));
      }
    });
    MauserBaseEntity airport = dao.findByName(testAirportCode);

    assertThat(airport.toString(), equalTo("Airport '" + testAirportCode + "' not in registry"));
  }
}
