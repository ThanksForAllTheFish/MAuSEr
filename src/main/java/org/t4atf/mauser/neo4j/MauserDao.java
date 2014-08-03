package org.t4atf.mauser.neo4j;

public interface MauserDao<TYPE extends MauserBaseEntity>
{
  public TYPE findOne(String name);
}
