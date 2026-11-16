package net.skeleton.app;

import net.skeleton.infra.EntityStatus;
import net.skeleton.infra.ExampleEntity;
import net.skeleton.service.api.ExampleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SkeletonApplicationTest {

    @Autowired
    private ExampleService examples;

    @Test
    void assembledServicePersistsThroughJpa() {
        ExampleEntity input = ExampleEntity.builder()
                .name("template")
                .description("wiring check")
                .status(EntityStatus.ACTIVE)
                .build();

        ExampleEntity saved = examples.create(input);

        assertTrue(saved.getId() != null);
        assertEquals("template", examples.findById(saved.getId()).orElseThrow().getName());
    }
}
