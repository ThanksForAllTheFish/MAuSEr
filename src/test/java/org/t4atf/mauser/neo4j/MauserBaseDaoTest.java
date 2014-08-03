package org.t4atf.mauser.neo4j;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.ConstraintViolationException;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.excpetions.NodeAlreadyExistent;
import org.t4atf.mauser.geography.aiport.Airport;
import org.t4atf.mauser.geography.aiport.ConcreteAirportDao;

public class MauserBaseDaoTest extends MauserUnitTesting
{
  private MauserBaseDao<Airport> dao;
  private String testAirportCode = "LIN";

  @Before
  public void init()
  {
    dao = new ConcreteAirportDao(transactionOperation);
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
        oneOf(transactionOperation).createNode(properties, getLocalizedLabel());
      }
    });

    Airport airport = dao.create(properties);

    assertThat(airport, notNullValue());
  }

  @Test
  public void cannotCreateSameNodeTwice()
  {
    final Map<String, Object> properties = buildExpectedProperties(testAirportCode, "Linate");
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).createNode(properties, getLocalizedLabel()); will(throwException(new ConstraintViolationException("")));
      }
    });
    exception.expect(NodeAlreadyExistent.class);
    dao.create(properties);
  }

  @Test
  public void nodeFound()
  {
    final Node found = context.mock(Node.class);
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).findOne("name", getLocalizedLabel(), testAirportCode); will(returnValue(found));
        allowing(found).getId(); will(returnValue(1L));
      }
    });
    Airport airport = dao.findByCode(testAirportCode);

    assertThat(airport.getId(), equalTo(1L));
  }

  @Test
  public void nodeNotFound()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).findOne("name", getLocalizedLabel(), testAirportCode); will(returnValue(null));
      }
    });
    MauserBaseEntity airport = dao.findByCode(testAirportCode);

    assertThat(airport.toString(), equalTo("Airport '" + testAirportCode + "' not in registry"));
  }
}
