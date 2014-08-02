package org.t4atf.mauser.geography.country;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.COUNTRY;
import static org.t4atf.mauser.neo4j.MauserRelations.CONTINENT;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.continent.Continent;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class CountryDaoTest extends MauserUnitTesting
{
  private ConcreteCountryDao dao;
  private String testCountryName = "Italia";
  private Node node = context.mock(Node.class);
  
  @Before
  public void init() {
    super.init();
    dao = new ConcreteCountryDao(transactionOperation);
  }

  @Test
  public void createCountry() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    Country country = dao.createEntity(node);
    
    assertThat(country.getId(), equalTo(1L));
  }
  
  @Test
  public void createNonExistentCountry() throws Exception
  {
    Country country = dao.createNonExistentEntity(testCountryName);
    
    assertThat(country.getId(), equalTo(0L));
  }
  
  @Test
  public void createABrandNewCountry()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testCountryName);
        oneOf(transactionOperation).createIndexedNode(buildExpectedProperties(testCountryName), index, getLocalizedLabel());
      }
    });

    Country country = dao.create(testCountryName);

    assertThat(country, notNullValue());
  }
  
  @Test
  public void placeCountryInContinent() throws Exception
  {
    final Node country = context.mock(Node.class, "country");
    final Node continent = context.mock(Node.class, "continent");
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).createRelationshipBetween(country, continent, CONTINENT);
      }
    });
    dao.connect(new Country(country), new Continent(continent));
  }

  private Map<String, Object> buildExpectedProperties(String testCityName)
  {
    Map<String, Object> properties = new HashMap<>();
    properties.put("name", testCityName);
    return properties;
  }

  @Override
  protected Label getLocalizedLabel()
  {
    return COUNTRY;
  }
}
