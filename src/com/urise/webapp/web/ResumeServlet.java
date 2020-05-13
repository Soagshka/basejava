package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    public void init(ServletConfig config) throws ServletException {
        storage = new SqlStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (fullName.trim().isEmpty()) {
            response.sendRedirect("resume");
            return;
        }
        boolean isAlreadyExist = (uuid != null && !uuid.trim().isEmpty());
        Resume resume;
        if (isAlreadyExist) {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        } else {
            resume = new Resume(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value.trim().isEmpty()) {
                resume.getContactMap().remove(type);
            } else {
                resume.addContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String[] positionCount = request.getParameterValues(type.name() + "positionCount");
            Integer posCount = null;
            if (positionCount != null) {
                if (!positionCount[0].isEmpty()) {
                    posCount = Integer.parseInt(positionCount[0]);
                }
            }
            String value = request.getParameter(type.name());
            if (value != null) {
                if (value.trim().isEmpty()) {
                    resume.getSectionMap().remove(type);
                } else {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            resume.addSection(type, new SimpleTextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            resume.addSection(type, new ListTextSection(Arrays.asList(value.split("\\n"))));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            String[] title = request.getParameterValues(type.name());
                            String[] link = request.getParameterValues(type.name() + "link");
                            List<Organization> organizationList = new ArrayList<>();
                            if (title.length != 0) {
                                for (int i = 0; i < title.length; i++) {
                                    String[] startDates = request.getParameterValues(type.name() + i + "startDate");
                                    String[] endDates = request.getParameterValues(type.name() + i + "endDate");
                                    String[] information = request.getParameterValues(type.name() + i + "information");
                                    String[] descriptions = request.getParameterValues(type.name() + i + "description");
                                    List<Position> positionList = new ArrayList<>();
                                    for (int j = 0; j < information.length; j++) {
                                        Position position = new Position(YearMonth.parse(startDates[j]), YearMonth.parse(endDates[j]), information[j], descriptions[j]);
                                        positionList.add(position);
                                    }
                                    Organization organization = new Organization(title[i], link[i]);
                                    organization.getPositionList().addAll(positionList);
                                    organizationList.add(organization);
                                }
                            }
                            resume.addSection(type, new OrganizationSection(addNewOrganization(posCount, organizationList)));
                    }
                }
            } else {
                resume.addSection(type, new OrganizationSection(addNewOrganization(posCount, new ArrayList<>())));
            }
        }
        if (isAlreadyExist) {
            storage.update(resume);
        } else {
            storage.save(resume);
        }

        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        OrganizationSection organizationSection;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                addSection(resume);
                break;
            case "add":
                resume = new Resume();
                addSection(resume);
                break;
            case "remove-org":
                resume = storage.get(uuid);
                organizationSection = (OrganizationSection) resume.getSection(SectionType.valueOf(request.getParameter("section")));
                List<Organization> organizationList = organizationSection.getOrganizationList();
                organizationList.removeIf(organization -> organization.getTitle().equals(request.getParameter("name")));
                storage.update(resume);
                response.sendRedirect("resume?uuid=" + uuid + "&action=edit");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void addSection(Resume resume) {
        for (SectionType type : SectionType.values()) {
            AbstractSection section = resume.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = new SimpleTextSection();
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (section == null) {
                        section = new ListTextSection();
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    if (section == null) {
                        section = new OrganizationSection();
                    }
                    break;
            }
            resume.addSection(type, section);
        }
    }

    private List<Organization> addNewOrganization(Integer posCount, List<Organization> organizationList) {
        if (posCount != null) {
            Organization organization = new Organization("Новая организация", "");
            for (int i = 0; i < posCount; i++) {
                organization.getPositionList().add(new Position(null, null, "", null));
            }
            organizationList.add(organization);
        }
        return organizationList;
    }
}
