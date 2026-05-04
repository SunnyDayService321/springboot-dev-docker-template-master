package com.example.demo.repositries;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.InquiryForm3;

@Repository
public interface InquiryRepository3 extends JpaRepository<InquiryForm3, Long>{
	Optional<InquiryForm3> findById(String id);
	List<InquiryForm3> findAll();
}

