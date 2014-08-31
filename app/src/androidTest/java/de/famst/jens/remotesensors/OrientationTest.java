package de.famst.jens.remotesensors;

import junit.framework.TestCase;

import de.famst.jens.remotesensors.biz.Orientation;

/**
 * Created by jens on 31/08/14.
 */
public class OrientationTest extends TestCase
{

    public void test_DefaultCTORInitalizesWithZero()
    {
        Orientation orient = new Orientation();

        assertEquals(0.0f, orient.getAzimuthInDegrees());
        assertEquals(0.0f, orient.getElevationInDegrees());
        assertEquals(0.0f, orient.getRollInDegrees());
    }

    public void test_CTORWithValuesInitializesAzElRo()
    {
        Orientation orient = new Orientation(1.0f, 2.0f, 3.0f);

        assertEquals(1.0f, orient.getAzimuthInDegrees());
        assertEquals(2.0f, orient.getElevationInDegrees());
        assertEquals(3.0f, orient.getRollInDegrees());
    }

    public void test_SubstractOffsetWorks()
    {
        Orientation orient = new Orientation(1.0f, 2.0f, 3.0f);
        Orientation offset = new Orientation(0.1f, 0.2f, 0.3f);

        orient = orient.substractOffset(offset);

        assertEquals(0.9f, orient.getAzimuthInDegrees());
        assertEquals(1.8f, orient.getElevationInDegrees());
        assertEquals(2.7f, orient.getRollInDegrees());
    }


}
