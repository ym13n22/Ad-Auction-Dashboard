package org.softengproj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * testing
 */
public class BounceTest {
    @Test
    public void isBounce_bounceTimerUpperBound_returnsTrue() {
        Boolean result = Bounce.isBounce("2015-01-01 12:01:15", "2015-01-01 12:01:45", "No", "7");
        Boolean expectedResult = true;
        assertEquals(expectedResult, result);
    }

    @Test
    public void isBounce_bounceTimerLowerBound_returnsFalse() {
        Boolean result = Bounce.isBounce("2015-01-01 12:01:15", "2015-01-01 12:01:46", "No", "7");
        Boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }

    @Test
    public void isBouncePages_numPagesUpperBound_returnsTrue() {
        Boolean result = Bounce.isBouncePages("1");
        Boolean expectedResult = true;
        Bounce.setTimedBounce(false);
        assertEquals(expectedResult, result);
    }

    @Test
    public void isBouncePages_numPagesLowerBound_returnsFalse() {
        Boolean result = Bounce.isBouncePages("2");
        Boolean expectedResult = false;
        Bounce.setTimedBounce(false);
        assertEquals(expectedResult, result);
    }

    @Test
    public void isBounce_conversionIsTrue_returnsFalse() {
        Boolean result = Bounce.isBounce("2015-01-01 12:01:15", "2015-01-01 12:01:47", "Yes", "7");
        Boolean expectedResult = false;
        assertEquals(expectedResult, result);
    }
}
