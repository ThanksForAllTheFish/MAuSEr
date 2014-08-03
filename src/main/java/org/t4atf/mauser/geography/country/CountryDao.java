package org.t4atf.mauser.geography.country;

import org.t4atf.mauser.geography.continent.Continent;

public interface CountryDao
{
  public Country create(String cityName);

  public Country findByCode(String name);

  public void connect(Country country, Continent continent);
}
