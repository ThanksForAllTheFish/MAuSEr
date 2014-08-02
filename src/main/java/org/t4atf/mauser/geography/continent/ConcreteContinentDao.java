package org.t4atf.mauser.geography.continent;

import static org.t4atf.mauser.neo4j.MauserLabel.CONTINENT;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class ConcreteContinentDao extends MauserBaseDao<Continent> implements ContinentDao
{
  ConcreteContinentDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, CONTINENT);
  }
  
  @Override
  public Continent create(String countryName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", countryName);
    return super.create(properties);
  }

  @Override
  protected Continent createNonExistentEntity(String name)
  {
    return new NonExistentContinent(name);
  }

  @Override
  protected Continent createEntity(Node node)
  {
    return new Continent(node);
  }
}