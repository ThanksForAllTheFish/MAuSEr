package org.t4atf.mauser.geography.city;

import static org.t4atf.mauser.neo4j.MauserLabel.CITY;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class CityDao extends MauserBaseDao<City>
{
  CityDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, CITY);
  }
  
  public City create(String cityName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", cityName);
    return super.create(properties);
  }

  @Override
  protected City createNonExistentEntity(String name)
  {
    return new NonExistentCity(name);
  }

  @Override
  protected City createEntity(Node node)
  {
    return new City(node);
  }

}
