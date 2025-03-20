package ru.onuchin.fitnessApp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.onuchin.fitnessApp.models.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TargetRepositoryTest {

    private final TargetRepository targetRepository;

    @Autowired
    public TargetRepositoryTest(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    @Test
    void saveAndFindByNameTest() {
        Target target = new Target("Стресс похудение");
        targetRepository.save(target); // Сохраняем в H2

        Target foundTarget = targetRepository.findByName("Стресс похудение");

        assertNotNull(foundTarget);
        assertEquals("Стресс похудение", foundTarget.getName());
    }
}