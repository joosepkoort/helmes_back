package com.helmes.proovitoo;

import com.helmes.proovitoo.model.HtmlRelation;
import com.helmes.proovitoo.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;



@SpringBootTest(classes = Main.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Main.class)
public class OtherTests {

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void treeEqualsConstructedTree() {
        try {
            List<Element> elementsOriginal = FileUtil.getOriginalElementAsString("utf-8", "index.html");
            List<HtmlRelation> relations = FileUtil.parseMultiSelect("utf-8", "index.html");
            Element constructedElement = FileUtil.constructSelectBox(relations);
            String relationsString = elementsOriginal.toString().replaceAll("(>)(\\s+)(<)", "$1$3"); // remove white space between tags;
            String constructedString = constructedElement.toString().replaceAll("(>)(\\s+)(<)", "$1$3"); // remove white space between tags;;
            Document doc1 = Jsoup.parse(relationsString);
            Document doc2 = Jsoup.parse(constructedString);
            assertEquals(doc1.text(), doc2.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
