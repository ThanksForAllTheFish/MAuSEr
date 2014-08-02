package org.t4atf.mauser.geography.country;

import static org.t4atf.mauser.neo4j.MauserLabel.COUNTRY;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.continent.Continent;
import org.t4atf.mauser.neo4j.MauserBaseDao;
import org.t4atf.mauser.transaction.TransactionOperation;

public class ConcreteCountryDao extends MauserBaseDao<Country> implements CountryDao
{
  ConcreteCountryDao(TransactionOperation transactionOperation)
  {
    super(transactionOperation, COUNTRY);
  }
  
  @Override
  public Country create(String regionName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", regionName);
    return super.create(properties);
  }

  @Override
  public void connect(Country country, Continent continent)
  {
    country.placeIn(continent, operation);
  }

  @Override
  protected Country createNonExistentEntity(String name)
  {
    return new NonExistentCountry(name);
  }

  @Override
  protected Country createEntity(Node node)
  {
    return new Country(node);
  }
}