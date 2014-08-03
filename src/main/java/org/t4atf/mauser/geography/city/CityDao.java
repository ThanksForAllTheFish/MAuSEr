package org.t4atf.mauser.geography.city;

import org.t4atf.mauser.geography.region.Region;
import org.t4atf.mauser.neo4j.MauserDao;

public interface CityDao extends MauserDao<City>
{
  public City create(String cityName);

  public void connect(City city, Region region);
}