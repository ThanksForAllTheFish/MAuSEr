package org.t4atf.mauser.geography.city;

import static org.t4atf.mauser.neo4j.MauserLabel.CITY;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.region.Region;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class ConcreteCityDao extends MauserBaseDao<City> implements CityDao
{
  ConcreteCityDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, CITY);
  }
  
  @Override
  public City create(String cityName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", cityName);
    return super.create(properties);
  }
  
  @Override
  public void connect(City city, Region region) {
    city.placeIn(region, operation);
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