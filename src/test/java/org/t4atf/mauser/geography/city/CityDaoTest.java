package org.t4atf.mauser.geography.city;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.CITY;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class CityDaoTest extends MauserUnitTesting
{
  private ConcreteCityDao dao;
  private String testCityName = "Milano";
  private Node node = context.mock(Node.class);
  
  @Before
  public void init() {
    super.init();
    dao = new ConcreteCityDao(transactionOperation);
  }

  @Test
  public void createAirport() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    City city = dao.createEntity(node);
    
    assertThat(city.getId(), equalTo(1L));
  }
  
  @Test
  public void createNonExistentAirport() throws Exception
  {
    City city = dao.createNonExistentEntity(testCityName);
    
    assertThat(city.getId(), equalTo(0L));
  }
  
  @Test
  public void createABrandNewAirport()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testCityName);
        oneOf(transactionOperation).createIndexedNode(buildExpectedProperties(testCityName), index, getLocalizedLabel());
      }
    });

    City city = dao.create(testCityName);

    assertThat(city, notNullValue());
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
    return CITY;
  }

}
