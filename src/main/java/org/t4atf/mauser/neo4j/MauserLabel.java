package org.t4atf.mauser.neo4j;

import static java.util.Arrays.binarySearch;

import org.neo4j.graphdb.Label;

public enum MauserLabel implements Label
{
  CONTINENT, COUNTRY, REGION, CITY, AIRPORT, AIRLINE;

  public static MauserLabel[] allBut(MauserLabel... invalidLabels)
  {
    MauserLabel[] allValues = values();
    MauserLabel[] labels = new MauserLabel[allValues.length - invalidLabels.length];
    int i = 0;
    for(MauserLabel l : allValues) {
      if(binarySearch(invalidLabels, l) < 0) {
        labels[i++] = l;
      }
    }
    return labels;
  }

}
