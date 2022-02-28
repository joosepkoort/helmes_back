package com.helmes.proovitoo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import com.helmes.proovitoo.Main;
import com.helmes.proovitoo.model.HtmlRelation;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.util.*;

public class FileUtil {

    @Autowired
    static ResourceLoader resourceLoader;

    public static String readFileFromClasspath(String pathOnClasspath) {
        try (InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(pathOnClasspath)) {
            if (is == null) {
                throw new IllegalStateException("can't load file: " + pathOnClasspath);
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

            return buffer.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<Element> getOriginalElementAsString(String charset, String filename) {
        ClassPathResource resource = new ClassPathResource("/"+filename, Main.class);

        try {
            InputStream inputStream = resource.getInputStream();
            Document doc = Jsoup.parse(inputStream, charset, "");
            List<Element> elements = doc.getElementsByTag("select");
            return elements;
        } catch (Exception e) {

        }
        return null;
    }
    /**
     * Method that parses the index.html file and returns a list of relations between the tree elements.
     *
     * @return
     */
    public static List<HtmlRelation> parseMultiSelect(String charset, String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("/"+filename, Main.class);
        Stack<Integer> stack = new Stack<>();
        List<HtmlRelation> treeRelations = new ArrayList<>();
        Integer lastLevel = 0;
        Integer lastId = null;

        try {
            InputStream inputStream = resource.getInputStream();
            Document doc = Jsoup.parse(inputStream, charset, "");
            Elements elements2 = doc.getElementsByTag("option");
            stack.push(-1);
            for (Element element : elements2) {
                String value = element.ownText();
                Element option = element.getElementsByTag("option").get(0);
                String id = option.attr("value");
                String innerHtml = option.html();
                int count = StringUtils.countMatches(innerHtml, "&nbsp");
                int level = count / 4;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < level; i++) {
                    sb.append(" ");
                }
                if (level > lastLevel) {    // level increased
                    lastLevel = level;
                    stack.push(lastId);
                    lastId = Integer.valueOf(id);
                } // level decreased
                else if (level < lastLevel) {
                    Integer howmanyLevelsDecreased = (lastLevel - level);
                    for (int x = 0; x < howmanyLevelsDecreased; x++) {
                        stack.pop();
                    }
                    lastLevel = level;
                    lastId = Integer.valueOf(id);
                } else { //kui samal levelil..
                    lastId = Integer.valueOf(id);
                }
                treeRelations.add(new HtmlRelation(Integer.valueOf(id), value, stack.peek(), count));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return treeRelations;
    }

    /**
     * Method that reconstructs multiselect htmlelement from list of relations
     *
     * @param relations
     * @return
     */
    public static Element constructSelectBox(List<HtmlRelation> relations) {
        Element el = new Element("select");
        el.attr("multiple", "");
        el.attr("size", "5");

        Collections.reverse(relations);
        for (HtmlRelation relation : relations) {
            Element _el = new Element("option");
            String whitespaces = new String(new char[relation.getWhitespaces()]).replace("\0", "&nbsp;");
            _el.attr("value", String.valueOf(relation.getId()));

            _el.prepend(whitespaces + relation.getValue());
            el.prepend(String.valueOf(_el));
        }
        return el;
    }
}
