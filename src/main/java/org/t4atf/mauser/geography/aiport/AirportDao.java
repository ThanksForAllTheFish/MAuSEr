package org.t4atf.mauser.geography.aiport;

import org.t4atf.mauser.geography.city.City;

public interface AirportDao
{
  public Airport create(String code, String name);

  public void connect(Airport airport, City city);

  public Airport findOne(String code);
}
