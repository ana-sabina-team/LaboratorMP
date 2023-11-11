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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientXmlRepository extends InMemoryRepository<Long, Client> {
    private static Document document; //se initialize o singura data

    public ClientXmlRepository(Validator<Client> validator) {
        super(validator);
    }
    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
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
        Optional<Client> optional = super.save(entity);
        return optional;
    }
    public static void saveToXml(Client client) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = getDocument();
        Element clientFileElement = document.getDocumentElement();

        Element newClientElement = document.createElement("client");
        clientFileElement.appendChild(newClientElement);

        Element newCNPElement = document.createElement("CNP");
        newCNPElement.setTextContent(client.getCNP());
        newClientElement.appendChild(newCNPElement);

        Element newLastNameElement = document.createElement("lastName");
        newLastNameElement.setTextContent(client.getLastName());
        newClientElement.appendChild(newLastNameElement);

        Element newFirstNameElement = document.createElement("firstName");
        newFirstNameElement.setTextContent(String.valueOf(client.getFirstName()));
        newClientElement.appendChild(newFirstNameElement);

        Element newAgeElement = document.createElement("age");
        newAgeElement.setTextContent(String.valueOf(client.getAge()));
        newClientElement.appendChild(newAgeElement);


        Transformer transformer= TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document),new StreamResult(new FileOutputStream("./data/clientFile.xml")));
    }

    public static List<Client> loadData() throws ParserConfigurationException, IOException, SAXException {

        ArrayList<Client> clients = new ArrayList<>();
        Element clientFileElement =getClientFileElement();

        NodeList nodeList = clientFileElement.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node clientNode = nodeList.item(i);
            if (clientNode instanceof Element) {
                Element clientElement = (Element) clientNode;
                Client client = getClientFromClientNode(clientElement);
                clients.add(client);
            }
        }
        return clients;
    }

    private static Client getClientFromClientNode(Element clientElement) {
        Client client = new Client();

        String CNP=getTextContentFromTag("CNP",clientElement);
        client.setCNP(CNP);

        String lastName=getTextContentFromTag("lastName",clientElement);
        client.setLastName(lastName);

        String firstName=getTextContentFromTag("firstName",clientElement);
        client.setFirstName(firstName);

        String age=getTextContentFromTag("age",clientElement);
        client.setAge(Double.valueOf(client.getAge()));

        return client;
    }
    private static String getTextContentFromTag(String tagName, Element clientElement) {
        NodeList taglist = clientElement.getElementsByTagName(tagName);
        Node titleNode = taglist.item(0);
        return titleNode != null ? titleNode.getTextContent() : null;
    }
    public static List<Client> deleteFromXmlByClientLastName(String lastNameToDelete) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Element clientFileElement = getClientFileElement();
        Document document = getDocument();


        NodeList clientNodes = clientFileElement.getElementsByTagName("client");


        for (int i = 0; i < clientNodes.getLength(); i++) {
            Element clientElement = (Element) clientNodes.item(i);
            String lastName = getTextContentFromTag("lastName", clientElement);
            if (lastNameToDelete.equals(lastName)) {
                clientFileElement.removeChild(clientElement);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/clientFile.xml")));

        return null;
    }

    public static List<Client> updateLastNameInXml(String lastNameToUpdate, String newLastName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Document document = getDocument();
        Element clientFileElement = document.getDocumentElement();

        NodeList clientNodes = clientFileElement.getElementsByTagName("client");

        for (int i = 0; i < clientNodes.getLength(); i++) {
            Element clientElement = (Element) clientNodes.item(i);
            String lastName = getTextContentFromTag("lastName", clientElement);

            if (lastNameToUpdate.equals(lastName)) {
                Element lastNameElement = (Element) clientElement.getElementsByTagName("lastName").item(1);
                lastNameElement.setTextContent(newLastName);
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream("data/clientFile.xml")));
        return null;
    }
    public static Element getClientFileElement() throws ParserConfigurationException, IOException, SAXException {

        Document document = getDocument();

        return document.getDocumentElement();
    }

    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        if (document != null) {
            return document;
        }
        //punem ce returneaza
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse("data/clientFile.xml");
        return document;
    }
}
