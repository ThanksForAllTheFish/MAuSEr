package org.t4atf.mauser.geography.continent;

import org.t4atf.mauser.neo4j.MauserDao;

public interface ContinentDao extends MauserDao<Continent>
{
  public Continent create(String cityName);
}