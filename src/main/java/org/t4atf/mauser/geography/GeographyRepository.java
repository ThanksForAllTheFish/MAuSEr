package org.t4atf.mauser.geography;

import org.t4atf.mauser.geography.aiport.AirportDao;
import org.t4atf.mauser.geography.city.CityDao;
import org.t4atf.mauser.geography.continent.ContinentDao;
import org.t4atf.mauser.geography.country.CountryDao;
import org.t4atf.mauser.geography.region.RegionDao;

public class GeographyRepository
{
  private final AirportDao airportDao;
  private final CityDao cityDao;
  private final RegionDao regionDao;
  private final CountryDao countryDao;
  private final ContinentDao continentDao;

  public GeographyRepository(AirportDao airportDao, CityDao cityDao, RegionDao regionDao, CountryDao countryDao, ContinentDao continentDao)
  {
    this.airportDao = airportDao;
    this.cityDao = cityDao;
    this.regionDao = regionDao;
    this.countryDao = countryDao;
    this.continentDao = continentDao;
  }
}
