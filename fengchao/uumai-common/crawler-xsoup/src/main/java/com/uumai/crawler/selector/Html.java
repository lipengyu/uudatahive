package com.uumai.crawler.selector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * Selectable html.<br>
 *
 * @author  <br>
 * @since 0.1.0
 */
public class Html extends HtmlNode {


	private static volatile boolean INITED = false;

	/**
	 * Disable jsoup html entity escape. It can be set just before any Html instance is created.
	 */
	public static boolean DISABLE_HTML_ENTITY_ESCAPE = true;

	/**
	 * Disable jsoup html entity escape. It is a hack way only for jsoup 1.7.2.
	 */
	private void disableJsoupHtmlEntityEscape() {
		if (DISABLE_HTML_ENTITY_ESCAPE && !INITED) {
			Entities.EscapeMode.base.getMap().clear();
			Entities.EscapeMode.extended.getMap().clear();
			INITED = true;
		}
	}

    /**
     * Store parsed document for better performance when only one text exist.
     */
    private Document document;

    public Html(String text) {
        try {
			disableJsoupHtmlEntityEscape();
            this.document = Jsoup.parse(text);
        } catch (Exception e) {
            this.document = null;
         }
    }

    public Html(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    protected List<Element> getElements() {
        return Collections.<Element>singletonList(getDocument());
    }

    /**
     * @param selector
     * @return
     */
    public String selectDocument(Selector selector) {
        if (selector instanceof ElementSelector) {
            ElementSelector elementSelector = (ElementSelector) selector;
            return elementSelector.select(getDocument());
        } else {
            return selector.select(getFirstSourceText());
        }
    }

    public List<String> selectDocumentForList(Selector selector) {
        if (selector instanceof ElementSelector) {
            ElementSelector elementSelector = (ElementSelector) selector;
            return elementSelector.selectList(getDocument());
        } else {
            return selector.selectList(getFirstSourceText());
        }
    }

    public static Html create(String text) {
        return new Html(text);
    }

    public  static void  main(String[] argx) throws Exception{

        URL url = new URL("http://list.suning.com/0-20006-0-0-1-9017.html");
        String path = url.getFile().substring(0, url.getFile().lastIndexOf('/'));
        String base = url.getProtocol() + "://" + url.getHost() + path;
        System.out.println("base is"+ base);


        File file=new File("/tmp/1.txt");
        System.out.println("file is"+ file.getName());
        StringBuffer buffer=new StringBuffer();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        Object href = new Html(buffer.toString())
                .xpath("//a[@class='i-collect searchBg collectI']/@href");
        System.out.println("href:"+ href);


//         List<String> xpathvaluelist = new Html(UrlUtils.fixAllRelativeHrefs(buffer.toString(),base))
//                .xpath("//a[@class='i-collect searchBg collectI']/@href").all();
//        for(String href:xpathvaluelist){
//            System.out.println("href:"+ href);
//        }

    }

}
