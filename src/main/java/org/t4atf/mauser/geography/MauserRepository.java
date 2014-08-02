package org.t4atf.mauser.geography;

import org.t4atf.mauser.geography.aiport.AirportDao;
import org.t4atf.mauser.geography.city.CityDao;

public class MauserRepository
{
  private final AirportDao airportDao;
  private final CityDao cityDao;

  public MauserRepository(AirportDao airportDao, CityDao cityDao)
  {
    this.airportDao = airportDao;
    this.cityDao = cityDao;
  }
}
