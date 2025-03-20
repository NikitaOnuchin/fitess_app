package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.repositories.TargetRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TargetService {

    private final TargetRepository targetRepository;

    @Autowired
    public TargetService(TargetRepository targetRepository) {
        this.targetRepository = targetRepository;
    }

    public Optional<Target> getTargetByName(String name) {
        return Optional.ofNullable(targetRepository.findByName(name));
    }
}
