package net.korriganed.broceliande.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.korriganed.broceliande.data.Feature;
import net.korriganed.broceliande.data.FeatureType;
import net.korriganed.broceliande.data.Target;
import net.korriganed.broceliande.data.TargetType;

public abstract class InspectionUtils {

	private static final Logger LOG = LoggerFactory.getLogger(InspectionUtils.class);

	public static <T> Set<Method> findFeatures(Class<T> t) {
		return new HashSet<>(Arrays.asList(MethodUtils.getMethodsWithAnnotation(t, Feature.class)));
	}

	public static <T> Method findTarget(Class<T> t) {
		return MethodUtils.getMethodsWithAnnotation(t, Target.class)[0];
	}

	public static FeatureType getFeatureType(Method m) {
		Feature annotation = m.getAnnotation(Feature.class);
		return annotation.value();
	}

	public static TargetType getTargetType(Method m) {
		Target annotation = m.getAnnotation(Target.class);
		return annotation.value();
	}

	public static <T> Map<String, Method> findSetter(Class<T> clazz) {
		try {
			Map<String, Method> result = new HashMap<>();
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (PropertyDescriptor prop : props) {
				result.put(prop.getName().toLowerCase(), prop.getWriteMethod());
			}
			return result;

		} catch (IntrospectionException e) {
			LOG.error("Could not introspect class : {}", e.getMessage());
			throw new RuntimeException("Could not introspect class", e);
		}

	}

	public static <T> T invokeGetter(Object o, Method m) {
		try {
			return (T) m.invoke(o);
		} catch (IllegalAccessException e) {
			LOG.error("Could not access method: {}", e.getMessage());
			throw new RuntimeException("Could not access method", e);
		} catch (InvocationTargetException e) {
			LOG.error("Could not access method: {}", e.getTargetException().getMessage());
			throw new RuntimeException("Target Exception", e.getTargetException());
		}
	}

}
