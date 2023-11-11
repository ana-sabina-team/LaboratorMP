package main.repository;

import main.domain.Book;
import main.domain.Client;
import main.domain.validators.Validator;
import main.domain.validators.ValidatorException;
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
import java.util.Optional;

public class BookXmlRepository  extends InMemoryRepository<Long, Book>{

    private static Document document;

    public BookXmlRepository(Validator<Book> validator) {
        super(validator);
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        try {
            saveToXml(entity);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        Optional<Book> optional = super.save(entity);
        return optional;
    }
    public static void saveToXml(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = getDocument();
        Element bookStoreElement = document.getDocumentElement();


        Element newBookElement = document.createElement("book");
        bookStoreElement.appendChild(newBookElement);

        Element newTitleElement = document.createElement("title");
        newTitleElement.setTextContent(book.getTitle());
        newBookElement.appendChild(newTitleElement);

        Element newAuthorElement = document.createElement("author");
        newAuthorElement.setTextContent(book.getAuthor());
        newBookElement.appendChild(newAuthorElement);

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
        Element bookStoreElement = getBookStoreElement();

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


    public static List<Book> updateTitleInXml(String titleToUpdate, String newTitle) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = getDocument();
        Element bookStoreElement = document.getDocumentElement();

        NodeList bookNodes = bookStoreElement.getElementsByTagName("book");

        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element bookElement = (Element) bookNodes.item(i);
            String title = getTextContentFromTag("title", bookElement);

            if (titleToUpdate.equals(title)) {
                Element titleElement = (Element) bookElement.getElementsByTagName("title").item(0);
                titleElement.setTextContent(newTitle);
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/book.xml")));
        return null;
    }


    public static Element getBookStoreElement() throws ParserConfigurationException, IOException, SAXException {

        Document document = getDocument();
        return document.getDocumentElement();
    }

    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        if (document != null) {
            return document;
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse("data/book.xml");

        return document;
    }


}