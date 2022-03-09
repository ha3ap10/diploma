package ru.netology.diploma;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.diploma.model.*;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DiplomaApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final int PORT = 5500;
    private static final String IMAGE = "transfer_back:0.0.1";
    private static final String URL = "http://localhost:";
    private static final String TRANSFER = "/transfer";
    private static final String CONFIRM = "/confirmOperation";

    @Container
    private static GenericContainer<?> myApp = new GenericContainer<>(IMAGE)
            .withExposedPorts(PORT);

    @Test
    void TestOk() {
        Transfer transfer = new Transfer(
                "1234567891234567",
                "4567891234567891",
                "123",
                "12/23",
                new Amount("RUR",1000000));

        ResponseEntity<SuccessTransfer> fromAppTransfer = restTemplate.postForEntity(
                URL + myApp.getMappedPort(PORT) + TRANSFER, transfer, SuccessTransfer.class);
        SuccessTransfer successTransfer = fromAppTransfer.getBody();

        assertEquals(HttpStatus.OK, fromAppTransfer.getStatusCode());

        Confirm confirm = new Confirm(successTransfer.getOperationId(), "123");
        ResponseEntity<SuccessConfirmation> fromAppConfirm = restTemplate.postForEntity(
                URL + myApp.getMappedPort(PORT) + CONFIRM, confirm, SuccessConfirmation.class);

        assertEquals(HttpStatus.OK, fromAppConfirm.getStatusCode());
    }

    @Test
    void transferTestError() {
        Transfer transfer = new Transfer(
                "1234567891234567",
                "4567891234567891",
                null,
                "12/23",
                new Amount("RUR",1000000));
        ResponseEntity<Object> fromApp = restTemplate.postForEntity(
                URL + myApp.getMappedPort(PORT) + TRANSFER, transfer, Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, fromApp.getStatusCode());
    }

    @Test
    void confirmTestError() {
        Confirm confirm = new Confirm("123", "123");

        ResponseEntity<Object> fromApp = restTemplate.postForEntity(
                URL + myApp.getMappedPort(PORT) + CONFIRM, confirm, Object.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, fromApp.getStatusCode());
    }

}
