package com.example.producer1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Producer1Application.class)
@ActiveProfiles("producer1")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProducerRepositoryTest {

    @Autowired
    private Producer1Repository producerRepository;

    @Test
    void testDatabaseConnection() {
        System.out.println("=== TEST METHOD EXECUTED ===");
        assertThat(producerRepository).isNotNull();
        System.out.println("=== TEST PASSED ===");
    }

    @Test
    void testFindByAccount() {
        String existingAccount = "ACC_789012";

        List<Transaction1> transactions = producerRepository.findById(existingAccount);

        assertThat(transactions).isNotNull();
        assertThat(transactions).isNotEmpty();

        for (Transaction1 transaction : transactions) {
            System.out.println(transaction);

        }
    }


}
