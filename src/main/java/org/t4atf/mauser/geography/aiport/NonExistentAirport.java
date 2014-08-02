package org.t4atf.mauser.geography.aiport;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class NonExistentAirport extends Airport
{
  private String code;

  NonExistentAirport(String code)
  {
    super(null);
    this.code = code;
  }
  
  @Override
  public long getId()
  {
    return 0L;
  }
  
  @Override
  public int hashCode()
  {
    return code.hashCode();
  }
  
  @Override
  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
  
  @Override
  public String toString()
  {
    return "Airport '" + code + "' not in registry";
  }
}
