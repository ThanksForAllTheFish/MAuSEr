package org.t4atf.mauser.geography.aiport;

import static org.t4atf.mauser.neo4j.MauserLabel.AIRPORT;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.city.City;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class ConcreteAirportDao extends MauserBaseDao<Airport> implements AirportDao
{
  public ConcreteAirportDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, AIRPORT);
  }
  
  @Override
  public Airport create(String code, String name)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", code);
    properties.put("longName", name);
    return create(properties);
  }
  
  @Override
  public void connect(Airport airport, City city) {
    airport.placeIn(city, operation);
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
