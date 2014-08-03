package org.t4atf.mauser.geography.continent;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.CONTINENT;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class ContinentDaoTest extends MauserUnitTesting
{
  private ConcreteContinentDao dao;
  private String testCountryName = "Italia";
  private Node node = context.mock(Node.class);
  
  @Before
  public void init() {
    dao = new ConcreteContinentDao(transactionOperation);
  }

  @Test
  public void createContinent() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    Continent continent = dao.createEntity(node);
    
    assertThat(continent.getId(), equalTo(1L));
  }
  
  @Test
  public void createNonExistentContinent() throws Exception
  {
    Continent continent = dao.createNonExistentEntity(testCountryName);
    
    assertThat(continent.getId(), equalTo(0L));
  }
  
  @Test
  public void createABrandNewContinent()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).createNode(buildExpectedProperties(testCountryName), getLocalizedLabel());
      }
    });

    Continent continent = dao.create(testCountryName);

    assertThat(continent, notNullValue());
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
    return CONTINENT;
  }
}
