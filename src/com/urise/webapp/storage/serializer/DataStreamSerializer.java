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
            Map<ContactType, String> contacts = resume.getContactMap();

            writeWithException(contacts.entrySet(), dos, element -> {
                dos.writeUTF(element.getKey().name());
                dos.writeUTF(element.getValue());
            });

            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();

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
                        writeWithException(informationList, dos, information -> {
                            dos.writeUTF(information);
                        });
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = ((OrganizationSection) entry.getValue()).getOrganizationList();
                        writeWithException(organizationList, dos, organization -> {
                            dos.writeUTF(organization.getTitle());
                            dos.writeUTF(organization.getLink());

                            List<Position> positionList = organization.getPositionList();
                            writeWithException(positionList, dos, position -> {
                                dos.writeUTF(position.getDateStart().toString());
                                dos.writeUTF(position.getDateEnd().toString());
                                dos.writeUTF(position.getInformation());
                                dos.writeUTF(position.getDescription());
                            });
                        });
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
            Map<ContactType, String> contacts = resume.getContactMap();
            readWithException(dis, () -> {
                contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
            resume.setContactMap(contacts);

            Map<SectionType, AbstractSection> sectionMap = resume.getSectionMap();
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        sectionMap.put(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> informationList = new ArrayList<>();
                        readWithException(dis, () -> {
                            informationList.add(dis.readUTF());
                        });
                        sectionMap.put(sectionType, new ListTextSection(informationList));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        readWithException(dis, () -> {
                            String title = dis.readUTF();
                            String link = dis.readUTF();
                            Organization org = new Organization(title, link);

                            List<Position> positionList = new ArrayList<>();
                            readWithException(dis, () -> {
                                positionList.add(new Position(YearMonth.parse(dis.readUTF()), YearMonth.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                            });
                            org.getPositionList().addAll(positionList);

                            organizationList.add(org);

                            sectionMap.put(sectionType, new OrganizationSection(organizationList));
                        });
                        break;
                }
            });
            resume.setSectionMap(sectionMap);
            return resume;
        }
    }

    <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterInterface<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            writer.write(element);
        }
    }

    void readWithException(DataInputStream dis, ReaderInterface reader) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            reader.read();
        }
    }
}
