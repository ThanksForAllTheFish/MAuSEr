package org.t4atf.mauser.geography.continent;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class NonExistentContinent extends Continent
{
  private final String name;

  public NonExistentContinent(String name)
  {
    super(null);
    this.name = name;
  }

  @Override
  public long getId()
  {
    return 0L;
  }
  
  @Override
  public int hashCode()
  {
    return name.hashCode();
  }
  
  @Override
  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
  
  @Override
  public String toString()
  {
    return "Country '" + name + "' not in registry";
  }
}
