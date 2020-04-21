package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = new String("./storage");

    protected static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Ivanov");
    private static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Sidorov");
    private static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Petrov");
    private static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Markov");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test()
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3, "Karpov");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExists() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExists() {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExists() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> testListStorage = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        testListStorage.sort(RESUME_COMPARATOR);
        Assert.assertEquals(testListStorage, storage.getAllSorted());
        Assert.assertEquals(testListStorage.size(), storage.getAllSorted().size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void checkOrganizationSection() {
        List<Organization> testPositionList = new ArrayList<>();
        Organization coursera = new Organization("Coursera", "https://www.coursera.org/learn/progfun1", "");
        coursera.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2013, 03),
                YearMonth.of(2013, 5), "\"Functional Programming Principles in Scala\" by Martin Odersky"))));
        testPositionList.add(coursera);

        Organization luxoft = new Organization("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", "");
        luxoft.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(2011, 03),
                YearMonth.of(2011, 4), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""))));
        testPositionList.add(luxoft);

        Organization student = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "https://itmo.ru/ru/", "");
        student.getPositionList().addAll(new ArrayList<>(Arrays.asList(new Position(YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7), "Аспирантура (программист С, С++)"),
                new Position(YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7), "Инженер (программист Fortran, C)"))));
        testPositionList.add(student);
        OrganizationSection organizationSection = new OrganizationSection(testPositionList);

        Assert.assertEquals(organizationSection, RESUME_1.getSectionMap().get(SectionType.EDUCATION));
    }

    @Test
    public void checkListTextSection() {
        List<String> testList = new ArrayList<>();

        testList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        testList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        testList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");

        ListTextSection listTextSection = new ListTextSection(testList);

        Assert.assertEquals(listTextSection, RESUME_1.getSectionMap().get(SectionType.QUALIFICATIONS));
    }

    @Test
    public void checkSimpleTextSection() {
        SimpleTextSection simpleTextSection = new SimpleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        Assert.assertEquals(simpleTextSection, RESUME_1.getSectionMap().get(SectionType.OBJECTIVE));
    }
}