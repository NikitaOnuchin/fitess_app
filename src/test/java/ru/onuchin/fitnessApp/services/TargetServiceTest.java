package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.repositories.TargetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TargetServiceTest {

    private static final String NAME = "Похудение";

    @Mock
    private TargetRepository targetRepository;

    @InjectMocks
    private TargetService targetService;

    @Test
    void getTargetByNameTest() {
        final Target target = new Target(NAME);
        when(targetRepository.findByName(NAME)).thenReturn(target);

        final Optional<Target> actual = targetService.getTargetByName(NAME);

        assertNotNull(actual.orElse(null));
        assertEquals(target, actual.orElse(null));
    }
}