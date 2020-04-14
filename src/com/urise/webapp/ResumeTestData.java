package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid1", "Григорий Кислин");

        Map<SectionType, Section> sectionMap = new HashMap<>();
        Map<ContactType, Section> contactType = new HashMap<>();
        Map<SectionType, List<Section>> complexSectionListMap = new HashMap<>();

        List<String> testList = new ArrayList<>();

        contactType.put(ContactType.TELEPHONE, new TextSection(clearAndAdd(testList, "+7(921) 855-0482")));
        contactType.put(ContactType.SKYPE, new TextSection(clearAndAdd(testList, "grigory.kislin")));
        contactType.put(ContactType.MAIL, new TextSection(clearAndAdd(testList, "gkislin@yandex.ru")));
        contactType.put(ContactType.PROFILE, new TextSection(clearAndAdd(testList, "https://www.linkedin.com/in/gkislin")));
        contactType.put(ContactType.PROFILE, new TextSection(clearAndAdd(testList, "https://github.com/gkislin")));
        contactType.put(ContactType.PROFILE, new TextSection(clearAndAdd(testList, "https://stackoverflow.com/users/548473/grigory-kislin")));
        contactType.put(ContactType.HOMEPAGE, new TextSection(clearAndAdd(testList, "http://gkislin.ru/")));

        sectionMap.put(SectionType.OBJECTIVE,
                new TextSection(clearAndAdd(testList, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")));
        sectionMap.put(SectionType.PERSONAL,
                new TextSection(clearAndAdd(testList, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.")));
        testList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        testList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity," +
                " Google Authenticator, Jira, Zendesk.");
        testList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        sectionMap.put(SectionType.ACHIEVEMENT, new TextSection(testList));
        testList.clear();
        testList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        testList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        testList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        sectionMap.put(SectionType.QUALIFICATIONS, new TextSection(testList));

        List<Section> complexTestList = new ArrayList<>();
        complexTestList.add(new ComplexTextSection("Java Online Projects", YearMonth.of(2013, 10),
                YearMonth.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        complexTestList.add(new ComplexTextSection("Wrike", YearMonth.of(2014, 10),
                YearMonth.of(2016, 1), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        complexSectionListMap.put(SectionType.EXPERIENCE, complexTestList);

        complexSectionListMap.clear();
        complexTestList.add(new ComplexTextSection("Coursera", YearMonth.of(2013, 03),
                YearMonth.of(2013, 05), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        complexTestList.add(new ComplexTextSection("Luxoft", YearMonth.of(2011, 03),
                YearMonth.of(2011, 04), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        complexSectionListMap.put(SectionType.EDUCATION, complexTestList);

        resume.setContactType(contactType);
        resume.setSectionMap(sectionMap);
        resume.setComplexSectionListMap(complexSectionListMap);

        System.out.println(resume.getSectionMap().get(SectionType.OBJECTIVE));

    }

    public static List<String> clearAndAdd(List<String> testList, String value) {
        testList.clear();
        testList.add(value);
        return new ArrayList<>(testList);
    }
}
