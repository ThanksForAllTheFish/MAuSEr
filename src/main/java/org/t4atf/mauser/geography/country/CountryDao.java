package org.t4atf.mauser.geography.country;

import org.t4atf.mauser.geography.continent.Continent;
import org.t4atf.mauser.neo4j.MauserDao;

public interface CountryDao extends MauserDao<Country>
{
  public Country create(String cityName);

  public void connect(Country country, Continent continent);
}