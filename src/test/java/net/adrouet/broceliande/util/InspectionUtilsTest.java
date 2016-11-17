package net.adrouet.broceliande.util;

import net.adrouet.broceliande.data.TestData;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class InspectionUtilsTest {

    @Test
    public void findFeatures() throws Exception {
        List<Method> features = InspectionUtils.findFeatures(TestData.class);
        TestData test = new TestData("M", 8, false);
        assertEquals(2, features.size());
    }

}