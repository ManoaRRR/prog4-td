package com.hei.project2p1.service;

import com.hei.project2p1.model.RegistrationNoTracker;
import com.hei.project2p1.repository.RegistrationNoTrackerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationNoTrackerService {
    private final RegistrationNoTrackerRepository repository;
    private final int ID = 1;

    private RegistrationNoTracker getNoTracker(){
        Long initialNo =0L;
        Optional<RegistrationNoTracker> registrationNoTracker = repository.findById(ID);
        if (registrationNoTracker.isEmpty()){
            RegistrationNoTracker noTracker= (new RegistrationNoTracker(ID,initialNo));
            repository.save(noTracker);
            registrationNoTracker = Optional.of(noTracker);
        }
        return registrationNoTracker.orElseThrow();
    }
    public Long getLastRegistrationNo(){
        RegistrationNoTracker registrationNoTracker = getNoTracker();
        return registrationNoTracker.getLastNo();
    }

    public void updateLastNo(Long last){
        RegistrationNoTracker registrationNoTracker = getNoTracker();
        registrationNoTracker.setLastNo(last);
        repository.save(registrationNoTracker);
    }
}
