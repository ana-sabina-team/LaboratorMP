package main.repository;

import main.domain.Client;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientXmlRepository implements Repository<Long, Client> {
    private static Document document;
    protected Validator<Client> validator;
    public ClientXmlRepository(Validator<Client> validator) {
        this.validator =validator;
    }


    @Override
    public Optional<Client> save(Client entity){
        try {
            saveToXml(entity);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            throw new RuntimeException(e);

        }
        return Optional.of(entity);
    }


    public static void saveToXml(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = getDocument();
        Element clientFileElement = document.getDocumentElement();


        Element newClientElement = document.createElement("client");
        clientFileElement.appendChild(newClientElement);

        newClientElement.setAttribute("Id", String.valueOf(client.getId()));

        Element newCNPElement = document.createElement("CNP");
        newCNPElement.setTextContent(client.getCNP());
        newClientElement.appendChild(newCNPElement);

        Element newLastNameElement = document.createElement("lastName");
        newLastNameElement.setTextContent(client.getLastName());
        newClientElement.appendChild(newLastNameElement);

        Element newFirstNameElement = document.createElement("firstName");
        newFirstNameElement.setTextContent(client.getFirstName());
        newClientElement.appendChild(newFirstNameElement);

        Element newAgeElement = document.createElement("age");
        newAgeElement.setTextContent(String.valueOf(client.getAge()));
        newClientElement.appendChild(newAgeElement);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/client.xml")));
    }


    public static List<Client> loadData() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Client> clients = new ArrayList<>();
        Element clientFileElement = getClientFileElement();

        NodeList nodeList = clientFileElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node clientNode = nodeList.item(i);
            if (clientNode instanceof Element) {
                Element clientElement = (Element) clientNode;
                String idValue = clientElement.getAttribute("Id").trim();
                Long id = Long.parseLong(idValue);
                Client client = getClientFromClientNode(clientElement);
                client.setId(id);
                clients.add(client);
            }
        }
        return clients;
    }

    private static Client getClientFromClientNode(Element clientElement) {
        Client client = new Client();

        Long id = Long.parseLong(clientElement.getAttribute("Id"));
        client.setId(id);

        String CNP = getTextContentFromTag("CNP", clientElement);
        client.setCNP(CNP);
        String lastName = getTextContentFromTag("lastName", clientElement);
        client.setLastName(lastName);
        String age=getTextContentFromTag("age",clientElement);
        client.setAge(Double.parseDouble(age));

        return client;
    }


    private static String getTextContentFromTag(String tagname, Element clientElement) {
        NodeList taglist = clientElement.getElementsByTagName(tagname);
        Node titleNode = taglist.item(0);
        return titleNode != null ? titleNode.getTextContent() : null;
    }

    @Override
    public Optional<Client> update(Client clientIDToUpdate) {


        Document document = getDocument();

        Element clientFileElement = document.getDocumentElement();
        NodeList nodeList = clientFileElement.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node clientNode = nodeList.item(i);
            if (clientNode instanceof Element) {
                Element clientElement = (Element) clientNode;
                Long clientID = Long.parseLong(clientElement.getAttribute("Id"));

                if (clientID.equals(clientIDToUpdate.getId())) {
                    clientElement.getElementsByTagName("lastName").item(0).setTextContent(clientIDToUpdate.getLastName());
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
            transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/clientFile.xml")));
        } catch (TransformerException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static Element getClientFileElement() {
        Document document = getDocument();
        return document.getDocumentElement();
    }

    public static Document getDocument() {
        if (document != null) {
            return document;
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        try {
            document = documentBuilder.parse("data/clientFile.xml");
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return document;
    }

        public Optional<Client> delete(Long id) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        deleteFromXML(id);
        return null;
    }


    public static void deleteFromXML(Long idToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Element clientFileElement = getClientFileElement();
        Document document = getDocument();
        NodeList clientNodes = clientFileElement.getElementsByTagName("client");


        for (int i = 0; i < clientNodes.getLength(); i++) {
            Element clientElement = (Element) clientNodes.item(i);


            Long clientId = Long.parseLong(clientElement.getAttribute("Id"));

            if (idToDelete.equals(clientId)) {
                clientFileElement.removeChild(clientElement);
                break;
            }
        }


        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/clientFile.xml")));

    }


    @Override
    public Optional<Client> findOne(Long idToFind) {

        Document document = getDocument();
        Element clientFileElement = document.getDocumentElement();


        NodeList nodeList = clientFileElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node clientNode = nodeList.item(i);
            if (clientNode instanceof Element) {
                Element clientElement = (Element) clientNode;
                String idValue = clientElement.getAttribute("Id").trim();


                Long id = Long.parseLong(idValue);
                if (Objects.equals(idToFind, id)) {

                    Client client = getClientFromClientNode(clientElement);
                    return Optional.ofNullable(client);

                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Client> findAll() {
        try{
            return loadData();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}