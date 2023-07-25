package com.hei.project2p1.repository;

import com.hei.project2p1.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Integer> {
    List<Phone> findAllByEmployeeId(Integer OwnerId);
}
