package org.t4atf.mauser.geography.aiport;

import org.t4atf.mauser.geography.city.City;
import org.t4atf.mauser.neo4j.MauserDao;

public interface AirportDao extends MauserDao<Airport>
{
  public Airport create(String code, String name);

  public void connect(Airport airport, City city);
}