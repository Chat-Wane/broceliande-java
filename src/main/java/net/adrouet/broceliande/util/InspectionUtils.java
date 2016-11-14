package net.adrouet.broceliande.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class InspectionUtils {

    public static <T> Map<String, Method> findFeatures(Class<T> t) throws IntrospectionException {
        PropertyDescriptor[] pds = Introspector.getBeanInfo(t, Object.class).getPropertyDescriptors();
        Map<String, Method> result = new HashMap<>();
        for (PropertyDescriptor pd : pds) {
            result.put(pd.getName(),pd.getReadMethod());
        }
        return result;
    }

}
