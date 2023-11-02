package main.repository;

import main.domain.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookXmlRepository {

    private static Document document; //se initialize o singura data


    public static void saveToXml(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse("data/book.xml");

        Element bookStoreElement = document.getDocumentElement();

        //CREATE A NEW BOOK element
        Element newBookElement = document.createElement("book");
        bookStoreElement.appendChild(newBookElement);
        //add new title
        Element newTitleElement = document.createElement("title");
        newTitleElement.setTextContent(book.getTitle());
        newBookElement.appendChild(newTitleElement);
        //add new author
        Element newAuthorElement = document.createElement("author");
        newAuthorElement.setTextContent(book.getAuthor());
        newBookElement.appendChild(newAuthorElement);
        //add the date

        Element newYearOfPublication = document.createElement("yearOfPublication");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yearOfPublicationAsString = book.getYearOfPublication().format(formatter);
        newYearOfPublication.setTextContent(yearOfPublicationAsString);
        newBookElement.appendChild(newYearOfPublication);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/book.xml")));


    }


    public static List<Book> loadData() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<Book> books = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse("data/book.xml");

        Element bookStoreElement = document.getDocumentElement();

        NodeList nodeList = bookStoreElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node bookNode = nodeList.item(i);
            if (bookNode instanceof Element) {
                Element bookElement = (Element) bookNode;
                Book book = getBookFromBookNode(bookElement);
                books.add(book);
            }
        }
        return books;
    }

    private static Book getBookFromBookNode(Element bookElement) {
        Book book = new Book();
        String title = getTextContentFromTag("title", bookElement);
        book.setTitle(title);
        String author = getTextContentFromTag("author", bookElement);
        book.setAuthor(author);
        String yearOfPublication = getTextContentFromTag("yearOfPublication", bookElement);
        if (yearOfPublication != null) {
            book.setYearOfPublication(LocalDate.parse(yearOfPublication));
        }
        return book;

    }


    private static String getTextContentFromTag(String tagname, Element bookElement) {
        NodeList taglist = bookElement.getElementsByTagName(tagname);
        Node titleNode = taglist.item(0);
        return titleNode != null ? titleNode.getTextContent() : null;
    }


    public static List<Book> deleteFromXmlByBookTitle(String titleToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Element bookStoreElement = getBookStoreElement();
        Document document = getDocument();


        NodeList bookNodes = bookStoreElement.getElementsByTagName("book");


        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element bookElement = (Element) bookNodes.item(i);
            String title = getTextContentFromTag("title", bookElement);
            if (titleToDelete.equals(title)) {
                bookStoreElement.removeChild(bookElement);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/book.xml")));

        return null;
    }


    public static void updateTitleinXML(String titleToUpdate, String newTitle) {

    }


    public static Element getBookStoreElement() throws ParserConfigurationException, IOException, SAXException {

        Document document = getDocument();
        Element bookStoreElement = document.getDocumentElement();

        return bookStoreElement;
    }

    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        if (document != null) {
            return document;
        }
        //punem ce returneaza
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse("data/book.xml");
        return document;
    }
}
