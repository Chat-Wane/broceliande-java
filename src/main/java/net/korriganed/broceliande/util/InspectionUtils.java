package net.korriganed.broceliande.util;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import net.korriganed.broceliande.data.Feature;
import net.korriganed.broceliande.data.FeatureType;
import net.korriganed.broceliande.data.Target;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

	public static <T> Map<String,Method> findSetter(Class<T> clazz){
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
			throw new RuntimeException("Could not introspect class",e);
		}

	}

	public static Comparable invokeGetter(Object target, Method m) {
		try {
			return (Comparable) m.invoke(target);
		} catch (IllegalAccessException e) {
			LOG.error("Could not access method: {}", e.getMessage());
			throw new RuntimeException("Could not access method",e);
		} catch (InvocationTargetException e) {
			LOG.error("Could not access method: {}", e.getTargetException().getMessage());
			throw new RuntimeException("Target Exception", e.getTargetException());
		}
	}

	public static Number invokeGetterForNumber(Object target, Method m) {
		try {
			Object o = m.invoke(target);
			if(o instanceof Number) {
				return (Number) o;
			}else{
				LOG.error("Can't cast result of {} to Number", m.getName());
				throw new RuntimeException("Can't cast result of getter to Number");
			}
		} catch (IllegalAccessException e) {
			LOG.error("Could not access method: {}", e.getMessage());
			throw new RuntimeException("Could not access method",e);
		} catch (InvocationTargetException e) {
			LOG.error("Could not access method: {}", e.getTargetException().getMessage());
			throw new RuntimeException("Target Exception", e.getTargetException());
		}
	}

}
