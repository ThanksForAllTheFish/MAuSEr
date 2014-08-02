package org.t4atf.mauser.geography.aiport;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.neo4j.graphdb.Node;

public class Airport
{
  private final Node airport;

  Airport(final Node airport)
  {
    this.airport = airport;
  }

  public long getId()
  {
    return airport.getId();
  }

  @Override
  public int hashCode()
  {
    return airport.getProperty("name").hashCode();
  }

  @Override
  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
