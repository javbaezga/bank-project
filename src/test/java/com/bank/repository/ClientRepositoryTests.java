package com.bank.repository;

import com.bank.entity.Client;
import com.bank.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class ClientRepositoryTests {
    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(clientRepository, notNullValue());
    }

    @Test
    public void findFirstClientById() {
        assertThat(clientRepository.findById(1L).orElse(null), notNullValue());
    }

    @Test
    public void findFirstClientByIdNumber() {
        assertThat(clientRepository.findByIdNumber("1234567890").orElse(null), notNullValue());
    }

    @Test
    public void updateFirstClient() {
        final Client client = clientRepository.findById(1L).orElse(null);
        assertThat(client, notNullValue());
        client.setFullName(client.getFullName() + "1");
        assertDoesNotThrow(() -> clientRepository.save(client));
    }

    @Test
    public void createNewClientThenDelete() {
        final Client client = new Client();
        client.setFullName("Juan Osorio");
        client.setGender(Person.Gender.M);
        client.setAge(25);
        client.setIdNumber("0123456789");
        client.setAddress("13 de junio y Equinoccial");
        client.setPhone("098874587");
        client.setUsername("juan.osorio");
        client.setPassword("12451245");
        client.setStatus(true);
        assertDoesNotThrow(() -> clientRepository.save(client));
        assertThat(clientRepository.findByIdNumber(client.getIdNumber()).orElse(null), notNullValue());
        assertDoesNotThrow(() -> clientRepository.delete(client));
        assertThat(clientRepository.findById(client.getId()).orElse(null), nullValue());
    }
}
