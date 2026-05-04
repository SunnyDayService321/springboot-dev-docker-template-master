package com.example.demo.repositries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.InquiryForm;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryForm, Long>{
	Optional<InquiryForm> findById(String id);
	List<InquiryForm> findAll();
}