package org.t4atf.mauser.geography.region;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class NonExistentRegion extends Region
{
  private final String name;

  public NonExistentRegion(String name)
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
    return "Region '" + name + "' not in registry";
  }
}
