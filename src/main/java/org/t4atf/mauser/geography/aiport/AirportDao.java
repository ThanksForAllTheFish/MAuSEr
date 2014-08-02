package org.t4atf.mauser.geography.aiport;

import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class AirportDao extends MauserBaseDao<Airport>
{
  public AirportDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, AIRPORT);
  }

  @Override
  protected Airport createEntity(Node node)
  {
    return new Airport(node);
  }

  @Override
  protected Airport createNonExistentEntity(String code)
  {
    return new NonExistentAirport(code);
  }
}
