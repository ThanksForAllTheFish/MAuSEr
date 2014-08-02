package org.t4atf.mauser.geography.region;

import static org.t4atf.mauser.neo4j.MauserLabel.REGION;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.country.Country;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class ConcreteRegionDao extends MauserBaseDao<Region> implements RegionDao
{
  ConcreteRegionDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, REGION);
  }
  
  @Override
  public Region create(String regionName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", regionName);
    return super.create(properties);
  }
  
  @Override
  public void connect(Region region, Country country)
  {
    region.placeIn(country, operation);
  }

  @Override
  protected Region createNonExistentEntity(String name)
  {
    return new NonExistentRegion(name);
  }

  @Override
  protected Region createEntity(Node node)
  {
    return new Region(node);
  }
}