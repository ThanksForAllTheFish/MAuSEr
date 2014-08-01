package org.t4atf.route.aiport;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.t4atf.route.Neo4jTest;
import org.t4atf.route.excpetions.NodeAlreadyExistent;
import org.t4atf.transaction.TransactionOperation;
import org.t4atf.transaction.TransactionWrapper;

public class AirportDaoTest extends Neo4jTest
{
  @Rule public ExpectedException exception = ExpectedException.none();
  
  private TransactionOperation transactionOperation = new TransactionWrapper(database);
  private final AirportDao dao = new AirportDao(transactionOperation);

  @Test
  public void createABrandNewAirportNode()
  {
    Airport airport = dao.create("LIN", "Linate");
    
    assertThat(airport.getId(), greaterThan(0L));
  }
  
  @Test
  public void newlyCreatedAirportIsAddedToIndex()
  {
    dao.create("MAD", "Barajas");
    
    exception.expect(NodeAlreadyExistent.class);
    dao.create("MAD", "Barajas");
  }

  @Test
  public void cannotCreateTwiceTheSameAirportNode()
  {
    exception.expect(NodeAlreadyExistent.class);
    dao.create("MXP", "Malpensa");
  }
  
  @Test
  public void airportFound()
  {
    Airport airport = dao.findByCode("MXP");
    
    assertThat(airport.getId(), greaterThan(0L));
  }
  
  @Test
  public void airportNotFound()
  {
    Airport airport = dao.findByCode("XXX");
    
    assertThat(airport.getId(), equalTo(0L));
  }
}
