package net.adrouet.broceliande.util;

import net.adrouet.broceliande.data.Feature;
import net.adrouet.broceliande.data.Target;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class InspectionUtils {

    public static <T> List<Method> findFeatures(Class<T> t) throws IntrospectionException {
        return Arrays.asList(MethodUtils.getMethodsWithAnnotation(t, Feature.class));
    }

    public static <T> Method findTarget(Class<T> t) throws IntrospectionException {
        return MethodUtils.getMethodsWithAnnotation(t, Target.class)[0];
    }

}
