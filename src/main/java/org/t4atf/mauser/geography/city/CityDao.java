package org.t4atf.mauser.geography.city;

public interface CityDao
{
  public City create(String cityName);

  public City findByName(String name);
}
