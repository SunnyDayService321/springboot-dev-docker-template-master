package com.example.demo.repositries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.InquiryForm;
import com.example.demo.models.ItemForm;
import com.example.demo.models.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>{
//	Optional<ItemForm> findById(Long id);
//	List<ItemForm> findAll();
//	void delete(Long id);
//	void delete();
	}