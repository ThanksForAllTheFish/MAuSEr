package org.t4atf.route;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.t4atf.route.MauserLabel.AIRPORT;
import static org.t4atf.route.MauserLabel.allBut;
import static org.t4atf.route.MauserLabel.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MauserLabelTest
{
  @Test
  public void doNotExcludeAny()
  {
    MauserLabel[] all = allBut();
    
    assertThat(all, equalTo(values()));
  }
  
  @Test
  public void excludeAirport()
  {
    MauserLabel[] allBut = allBut(AIRPORT);
    
    List<MauserLabel> all = new ArrayList<>(Arrays.asList(values()));
    all.remove(AIRPORT);
    assertThat(all, containsInAnyOrder(allBut));
  }
}
