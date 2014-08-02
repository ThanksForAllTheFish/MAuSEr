package org.t4atf.route.aiport;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.route.MauserLabel.AIRPORT;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.t4atf.route.MauserUnitTesting;
import org.t4atf.route.excpetions.NodeAlreadyExistent;
import org.t4atf.transaction.TransactionOperation;

public class AirportDaoTest extends MauserUnitTesting
{
  private final TransactionOperation transactionOperation = context.mock(TransactionOperation.class);
  private final Index<Node> index = context.mock(Index.class);
  private AirportDao dao;
  private String testAirportCode = "LIN";

  @Before
  public void init()
  {
    context.checking(new Expectations()
    {
      {
        allowing(transactionOperation).createIndexFor(AIRPORT);
        will(returnValue(index));
      }
    });
    dao = new AirportDao(transactionOperation);
  }

  @Test
  public void createABrandNewAirportNode()
  {
    String airportName = "Linate";
    final Map<String, Object> expectedProperties = buildExpectedProperties(testAirportCode, airportName);

    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testAirportCode);
        oneOf(transactionOperation).createIndexedNode(expectedProperties, index, AIRPORT);
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
