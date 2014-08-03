package org.t4atf.mauser.geography.continent;

public interface ContinentDao
{
  public Continent create(String cityName);

  public Continent findByCode(String name);
}
