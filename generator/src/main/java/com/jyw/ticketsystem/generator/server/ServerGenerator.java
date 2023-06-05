package com.jyw.ticketsystem.generator.server;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
   static String toPath="generator\\src\\main\\java\\com\\jyw\\ticketsystem\\generator\\test\\";
   static String pomPath="generator\\pom.xml";
    static {
        new File(toPath).mkdirs();
    }

    public static void main(String[] args) throws Exception {
        String generatorPath = getGeneratorPath();

        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        System.out.println(table);
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());
        // FreemarkerUtil.initConfig("test.ftl");
        // Map<String, Object> param = new HashMap<>();
        // param.put("domain", "Test1");
        // FreemarkerUtil.generator(toPath + "Test1.java", param);
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
        return node.getText();
    }
}
