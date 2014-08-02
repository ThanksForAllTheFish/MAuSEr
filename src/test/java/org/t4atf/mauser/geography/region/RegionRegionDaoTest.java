package org.t4atf.mauser.geography.region;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.t4atf.mauser.neo4j.MauserLabel.REGION;
import static org.t4atf.mauser.neo4j.MauserRelations.COUNTRY;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.t4atf.mauser.geography.country.Country;
import org.t4atf.mauser.neo4j.MauserUnitTesting;

public class RegionRegionDaoTest extends MauserUnitTesting
{
  private ConcreteRegionDao dao;
  private String testRegionName = "Lombardia";
  private Node node = context.mock(Node.class);
  
  @Before
  public void init() {
    super.init();
    dao = new ConcreteRegionDao(transactionOperation);
  }

  @Test
  public void createRegion() throws Exception
  {
    context.checking(new Expectations()
    {
      {
        allowing(node).getId(); will(returnValue(1L));
      }
    });
    Region region = dao.createEntity(node);
    
    assertThat(region.getId(), equalTo(1L));
  }
  
  @Test
  public void createNonExistentRegion() throws Exception
  {
    Region region = dao.createNonExistentEntity(testRegionName);
    
    assertThat(region.getId(), equalTo(0L));
  }
  
  @Test
  public void createABrandNewRegion()
  {
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).checkIndex(index, "name", testRegionName);
        oneOf(transactionOperation).createIndexedNode(buildExpectedProperties(testRegionName), index, getLocalizedLabel());
      }
    });

    Region region = dao.create(testRegionName);

    assertThat(region, notNullValue());
  }
  
  @Test
  public void placeRegionInCountry() throws Exception
  {
    final Node region = context.mock(Node.class, "region");
    final Node country = context.mock(Node.class, "country");
    context.checking(new Expectations()
    {
      {
        oneOf(transactionOperation).createRelationshipBetween(region, country, COUNTRY);
      }
    });
    dao.connect(new Region(region), new Country(country));
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
    return REGION;
  }
}
