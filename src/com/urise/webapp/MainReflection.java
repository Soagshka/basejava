package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Resume r = new Resume("abc");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        // TODO : invoke r.toString via reflection
        Class clazz = r.getClass();
        Object object = clazz.newInstance();
        System.out.println("New Instance = " + object.toString());
        Field field2 = clazz.getDeclaredField("uuid");
        field2.setAccessible(true);
        String value = field2.get(object).toString();
        field.set(r, value);
        System.out.println("r = " + r);
    }
}
