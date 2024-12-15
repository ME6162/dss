package com.rapidapi.core.dss.common.util;


import com.rapidapi.core.dss.common.annotations.CanUpdate;

import java.lang.reflect.Field;
import java.util.Map;

public final class DTOHelper {
    private DTOHelper() {

    }


    public static <T> long getIdFromDssId(String DssId, Class<T> entityType) {
        char expectedInitial = entityType.getSimpleName().charAt(0);
        if (DssId.charAt(0) != expectedInitial) {
            throw new IllegalArgumentException("Invalid DssId prefix for the given entity type.");
        }
        String numericPart = DssId.substring(1);
        try {
            return Long.parseLong(numericPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DssId format: " + DssId, e);
        }
    }


    public static <T> String getDssIdFromId(long id, Class<T> entityType) {
        String className = entityType.getName();
        char initial = className.charAt(0);

        String format = "%c%04d";
        return String.format(format, initial, id);
    }

    public static <T> T updateModel(T target, Map<String, Object> source){
        Class<?> clazz = target.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(CanUpdate.class) && source.containsKey(field.getName())) {
                try {
                    field.setAccessible(true);
                    Object value = source.get(field.getName());

                    // Check if the field is of an Enum type and handle the conversion from String
                    if (field.getType().isEnum() && value instanceof String) {
                        // Convert String to Enum
                        Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), (String) value);
                        field.set(target, enumValue);
                    } else {
                        field.set(target, value);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // Handle the case where the string value doesn't match any Enum value
                    System.err.println("Invalid value for enum: " + e.getMessage());
                }
            }
        }

        return target;
    }

}
