package com.willjiang.warthunderlive;

import com.willjiang.warthunderlive.Network.API;

import junit.framework.TestCase;

/**
 * Created by Will on 1/18/16.
 */
public class APITest extends TestCase{
    API.periods p = API.periods.INFINITE;

    public void testSwitch() {
        assertEquals(p.getPeriod(), 0);
        assertEquals(p.nextPeriod(), 7);
        assertEquals(p.nextPeriod(), 30);
        assertEquals(p.nextPeriod(), 0);
    }
}
