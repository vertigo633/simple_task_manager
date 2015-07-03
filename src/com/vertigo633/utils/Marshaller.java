package com.vertigo633.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.vertigo633.entities.Note;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vertigo633 on 12.06.2015.
 */
public class Marshaller {

    public static void marshall(List<Note> notes, String nameXmlFile) throws IOException {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("notes", List.class);
        xStream.processAnnotations(Note.class);

        String xml = xStream.toXML(notes);
        saveToFile(xml, nameXmlFile);
    }

    public static List<Note> unmarshall(String file) throws IOException, ClassNotFoundException {
        File xml_file = new File(file);
        if (xml_file.exists()) {
            XStream xStream = new XStream(new DomDriver());
            xStream.alias("notes", List.class);
            xStream.alias("note", Note.class);
            xStream.aliasField("name", Note.class, "name");
            xStream.aliasField("e_mail", Note.class, "e_mail");
            xStream.aliasField("phone_number", Note.class, "phone_number");
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());

            return (ArrayList<Note>) xStream.fromXML(xml_file);
        } else return new ArrayList<>();
    }

    public static void saveToFile(String xml, String nameFile) throws IOException {
        File file = new File(nameFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(xml);
        bufferedWriter.close();
    }
}
