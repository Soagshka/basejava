package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.YearMonth;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        printAll(createResume("uuid1", "Григорий Кислин"));
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        Map<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);
        Map<ContactType, AbstractSection> contactType = new EnumMap<>(ContactType.class);

        List<String> testList = new ArrayList<>();

        contactType.put(ContactType.TELEPHONE, new SimpleTextSection("+7(921) 855-0482"));
        contactType.put(ContactType.SKYPE, new SimpleTextSection("grigory.kislin"));
        contactType.put(ContactType.MAIL, new SimpleTextSection("gkislin@yandex.ru"));
        contactType.put(ContactType.LINKEDIN, new SimpleTextSection("https://www.linkedin.com/in/gkislin"));
        contactType.put(ContactType.GITHUB, new SimpleTextSection("https://github.com/gkislin"));
        contactType.put(ContactType.STACKOVERFLOW, new SimpleTextSection("https://stackoverflow.com/users/548473/grigory-kislin"));
        contactType.put(ContactType.HOMEPAGE, new SimpleTextSection("http://gkislin.ru/"));

        sectionMap.put(SectionType.OBJECTIVE,
                new SimpleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        sectionMap.put(SectionType.PERSONAL,
                new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        testList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        testList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity," +
                " Google Authenticator, Jira, Zendesk.");
        testList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        sectionMap.put(SectionType.ACHIEVEMENT, new ListTextSection(testList));
        testList.clear();
        testList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        testList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        testList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        sectionMap.put(SectionType.QUALIFICATIONS, new ListTextSection(testList));

        List<Organization> testPositionList = new ArrayList<>();

        Organization org1 = new Organization("Java Online Projects", "Создание, организация и проведение Java онлайн проектов и стажировок.");
        org1.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2013, 10),
                YearMonth.now(), "Автор проекта"))));
        testPositionList.add(org1);

        Organization wrike = new Organization("Wrike", "Проектирование и разработка онлайн платформы управления проектами" +
                " Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        wrike.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2014, 10),
                YearMonth.of(2016, 1), "Старший разработчик (backend)"))));
        testPositionList.add(wrike);

        sectionMap.put(SectionType.EXPERIENCE, new ComplexTextSection(testPositionList));
        testPositionList.clear();

        Organization coursera = new Organization("Coursera", null);
        coursera.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2013, 03),
                YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky"))));
        testPositionList.add(coursera);

        Organization luxoft = new Organization("Luxoft", null);
        luxoft.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2011, 03),
                YearMonth.of(2011, 4), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""))));
        testPositionList.add(luxoft);

        Organization student = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null);
        student.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7), "Аспирантура (программист С, С++)"),
                new Position(YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7), "Инженер (программист Fortran, C)"))));
        testPositionList.add(student);

        sectionMap.put(SectionType.EDUCATION, new ComplexTextSection(testPositionList));

        resume.setContactType(contactType);
        resume.setSectionMap(sectionMap);

        return resume;
    }

    public static void printAll(Resume resume) {
        System.out.println(ContactType.TELEPHONE.getTitle());
        System.out.println(resume.getContactType().get(ContactType.TELEPHONE).toString());
        System.out.println(ContactType.SKYPE.getTitle());
        System.out.println(resume.getContactType().get(ContactType.SKYPE).toString());
        System.out.println(ContactType.MAIL.getTitle());
        System.out.println(resume.getContactType().get(ContactType.MAIL).toString());
        System.out.println(ContactType.LINKEDIN.getTitle());
        System.out.println(resume.getContactType().get(ContactType.LINKEDIN).toString());
        System.out.println(ContactType.GITHUB.getTitle());
        System.out.println(resume.getContactType().get(ContactType.GITHUB).toString());
        System.out.println(ContactType.STACKOVERFLOW.getTitle());
        System.out.println(resume.getContactType().get(ContactType.STACKOVERFLOW).toString());
        System.out.println(ContactType.HOMEPAGE.getTitle());
        System.out.println(resume.getContactType().get(ContactType.HOMEPAGE).toString());

        System.out.println(SectionType.OBJECTIVE.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.OBJECTIVE).toString());
        System.out.println(SectionType.PERSONAL.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.PERSONAL).toString());
        System.out.println(SectionType.ACHIEVEMENT.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.ACHIEVEMENT).toString());
        System.out.println(SectionType.QUALIFICATIONS.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.QUALIFICATIONS).toString());
        System.out.println(SectionType.EXPERIENCE.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.EXPERIENCE).toString());
        System.out.println(SectionType.EDUCATION.getTitle());
        System.out.println(resume.getSectionMap().get(SectionType.EDUCATION).toString());
    }
}
