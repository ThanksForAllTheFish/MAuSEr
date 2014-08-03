package org.t4atf.mauser.geography.region;

import org.t4atf.mauser.geography.country.Country;

public interface RegionDao
{
  public Region create(String cityName);

  public Region findByCode(String name);
  
  public void connect(Region region, Country country);
}
