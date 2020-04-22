package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, AbstractSection> contacts = resume.getContactType();
            dos.writeInt(contacts.size());

            writeWithException(contacts.entrySet(), dos, element -> {
                dos.writeUTF(element.getKey().name());
                dos.writeUTF(((SimpleTextSection) element.getValue()).getInformation());
            });

            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();
            dos.writeInt(sectionMap.size());

            writeWithException(sectionMap.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SimpleTextSection) entry.getValue()).getInformation());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> informationList = ((ListTextSection) entry.getValue()).getInformation();
                        dos.writeInt(informationList.size());
                        for (String information : informationList) {
                            dos.writeUTF(information);
                        }
                        break;
                    default:
                        List<Organization> organizationList = ((OrganizationSection) entry.getValue()).getOrganizationList();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            dos.writeUTF(organization.getTitle());
                            dos.writeUTF(organization.getLink());

                            List<Position> positionList = organization.getPositionList();
                            dos.writeInt(positionList.size());
                            for (Position position : positionList) {
                                dos.writeUTF(position.getDateStart().toString());
                                dos.writeUTF(position.getDateEnd().toString());
                                dos.writeUTF(position.getInformation());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            Map<ContactType, AbstractSection> contacts = resume.getContactType();
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                contacts.put(ContactType.valueOf(dis.readUTF()), new SimpleTextSection(dis.readUTF()));
            }
            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();
            int sectionMapSize = dis.readInt();
            for (int i = 0; i < sectionMapSize; i++) {
                String sectionName = dis.readUTF();
                switch (sectionName) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        sectionMap.put(SectionType.valueOf(sectionName), new SimpleTextSection(dis.readUTF()));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        List<String> informationList = new ArrayList<>();
                        int informationListSize = dis.readInt();
                        for (int j = 0; j < informationListSize; j++) {
                            informationList.add(dis.readUTF());
                        }
                        sectionMap.put(SectionType.valueOf(sectionName), new ListTextSection(informationList));
                        break;
                    default:
                        List<Organization> organizationList = new ArrayList<>();
                        int organizationListSize = dis.readInt();
                        for (int k = 0; k < organizationListSize; k++) {
                            String title = dis.readUTF();
                            String link = dis.readUTF();
                            Organization org = new Organization(title, link);

                            List<Position> positionList = new ArrayList<>();
                            int positionListSize = dis.readInt();
                            for (int l = 0; l < positionListSize; l++) {
                                positionList.add(new Position(YearMonth.parse(dis.readUTF()), YearMonth.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            }
                            org.getPositionList().addAll(positionList);

                            organizationList.add(org);

                            sectionMap.put(SectionType.valueOf(sectionName), new OrganizationSection(organizationList));
                        }
                        break;
                }
            }
            resume.setContactType(contacts);
            return resume;
        }
    }

    <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterInterface<T> writer) throws IOException {
        for (T element : collection) {
            writer.write(element);
        }
    }
}
