package main.repository;

import main.domain.Book;
import main.domain.validators.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BookXmlRepository implements Repository<Long, Book> {

    private static Document document;
    protected Validator<Book> validator;


    public BookXmlRepository(Validator<Book> validator) {
        this.validator = validator;
    }


    @Override
    public Optional<Book> save(Book entity) {
        try {
            saveToXml(entity);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            throw new RuntimeException(e);

        }
        return Optional.of(entity);
    }


    public static void saveToXml(Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = getDocument();
        Element bookStoreElement = document.getDocumentElement();

        //CREATE A NEW BOOK element
        Element newBookElement = document.createElement("book");
        bookStoreElement.appendChild(newBookElement);

        newBookElement.setAttribute("Id", String.valueOf(book.getId()));

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
        Element bookStoreElement = getBookStoreElement();

        NodeList nodeList = bookStoreElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node bookNode = nodeList.item(i);
            if (bookNode instanceof Element) {
                Element bookElement = (Element) bookNode;
                String idValue = bookElement.getAttribute("Id").trim();
                Long id = Long.parseLong(idValue);
                Book book = getBookFromBookNode(bookElement);
                book.setId(id);
                books.add(book);
            }
        }
        return books;
    }

    private static Book getBookFromBookNode(Element bookElement) {
        Book book = new Book();

        Long id = Long.parseLong(bookElement.getAttribute("Id"));
        book.setId(id);

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

    @Override
    public Optional<Book> update(Book bookIDToUpdate) {


        Document document = getDocument();

        Element bookStoreElement = document.getDocumentElement();
        NodeList nodeList = bookStoreElement.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node bookNode = nodeList.item(i);
            if (bookNode instanceof Element) {
                Element bookElement = (Element) bookNode;
                Long bookID = Long.parseLong(bookElement.getAttribute("Id"));

                if (bookID.equals(bookIDToUpdate.getId())) {
                    bookElement.getElementsByTagName("title").item(0).setTextContent(bookIDToUpdate.getTitle());
                    bookElement.getElementsByTagName("author").item(0).setTextContent(bookIDToUpdate.getAuthor());

                }
            }


        }
        Transformer transformer = null;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        try {
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/book.xml")));
        } catch (TransformerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static Element getBookStoreElement() {

        Document document = getDocument();
        return document.getDocumentElement();
    }

    public static Document getDocument() {
        if (document != null) {
            return document;
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //newInstance of DBF
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        //create a new Document object
        try {
            document = documentBuilder.parse("data/book.xml");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //create a tree like object
        return document;
    }

    @Override

    public Optional<Book> delete(Long id)  {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        try {
            deleteFromXML(id);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static void deleteFromXML(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Element bookStoreElement = getBookStoreElement();
        Document document = getDocument();
        NodeList bookNodes = bookStoreElement.getElementsByTagName("book");


        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element bookElement = (Element) bookNodes.item(i);


            Long bookId = Long.parseLong(bookElement.getAttribute("Id"));

            if (idToDelete.equals(bookId)) {
                bookStoreElement.removeChild(bookElement);
                break;
            }
        }


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/book.xml")));

    }


    @Override
    public Optional<Book> findOne(Long idToFind) {

        Document document = getDocument();
        Element bookStoreElement = document.getDocumentElement();


        NodeList nodeList = bookStoreElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node bookNode = nodeList.item(i);
            if (bookNode instanceof Element) {
                Element bookElement = (Element) bookNode;
                String idValue = bookElement.getAttribute("Id").trim();


                Long id = Long.parseLong(idValue);
                if (Objects.equals(idToFind, id)) {

                    Book book = getBookFromBookNode(bookElement);
                    return Optional.ofNullable(book);

                }
            }
        }
        return Optional.empty();


    }

    @Override
    public Iterable<Book> findAll() {
        try {
            return loadData();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}