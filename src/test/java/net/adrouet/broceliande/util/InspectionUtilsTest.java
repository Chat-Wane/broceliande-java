package net.adrouet.broceliande.util;

import net.adrouet.broceliande.data.TestData;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.Assert.*;

public class InspectionUtilsTest {

    @Test
    public void findFeatures() throws Exception {
        Map<String, Method> features = InspectionUtils.findFeatures(TestData.class);
        TestData test = new TestData("M", 8, false);

        assertEquals(3, features.size());

        Method getSexMethod = features.get("sex");
        assertNotNull(getSexMethod);
        assertEquals(String.class, getSexMethod.getReturnType());
        assertEquals("M", getSexMethod.invoke(test));

        Method getAgeMethod = features.get("age");
        assertEquals(int.class, getAgeMethod.getReturnType());
    }

}