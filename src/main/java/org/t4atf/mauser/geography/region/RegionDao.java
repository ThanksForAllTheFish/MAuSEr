package org.t4atf.mauser.geography.region;

import org.t4atf.mauser.geography.country.Country;
import org.t4atf.mauser.neo4j.MauserDao;

public interface RegionDao extends MauserDao<Region>
{
  public Region create(String cityName);

  public void connect(Region region, Country country);
}