package net.adrouet.broceliande.util;

import net.adrouet.broceliande.data.TestData;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.Assert.*;

public class InspectionUtilsTest {

    @Test
    public void findFeatures() throws Exception {
        Set<Method> features = InspectionUtils.findFeatures(TestData.class);
        TestData test = new TestData("M", 8, "YES");
        assertEquals(2, features.size());
    }

}