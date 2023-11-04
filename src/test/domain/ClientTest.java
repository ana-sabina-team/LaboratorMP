package test.domain;

import main.domain.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private static final Long ID = 1L;
    private static final Long NEW_ID =2L;
    private static final String CNP = "sn01";
    private static final String NEW_CNP = "sn02";
    private static final String LAST_NAME = "clientLastName";
    private static final String FIRST_NAME = "clientFirstName";
    private static final int AGE = 23;

    private Client client;

    @BeforeEach
    public void setUp() throws Exception {
        client = new Client(ID, CNP, LAST_NAME,FIRST_NAME, AGE);
        client.setId(ID);
    }

    @AfterEach
    public void tearDown() throws Exception {
        client = null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals(CNP, client.getCNP(), "CNP should be equal");
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        client.setCNP(NEW_CNP);
        assertEquals(NEW_CNP, client.getCNP(), "CNP should be equal");
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(ID, client.getId(), "Ids should be equal");
    }

    @Test
    public void testSetId() throws Exception {
        client.setId(NEW_ID);
        assertEquals(NEW_ID, client.getId(), "Ids should be equal");
    }
//
//    @Ignore
//    @Test
//    public void testGetName() throws Exception {
//        fail("Not tested yet.");
//    }

//    @Ignore
//    @Test
//    public void testSetName() throws Exception {
//        fail("Not tested yet.");
//    }
//
//    @Ignore
//    @Test
//    public void testGetGroup() throws Exception {
//        fail("Not tested yet.");
//    }
//
//    @Ignore
//    @Test
//    public void testSetGroup() throws Exception {
//        fail("Not tested yet.");
//    }
}