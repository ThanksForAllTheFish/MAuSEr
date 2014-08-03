package org.t4atf.mauser.geography.city;

import org.t4atf.mauser.geography.region.Region;

public interface CityDao
{
  public City create(String cityName);

  public City findByCode(String name);

  public void connect(City city, Region region);
}
