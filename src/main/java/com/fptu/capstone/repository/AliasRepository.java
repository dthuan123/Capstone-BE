package com.fptu.capstone.repository;

import com.fptu.capstone.entity.Alias;
import com.fptu.capstone.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AliasRepository extends JpaRepository<Alias, Integer> {
    List<Alias> findAliasByUserId(int id);
    Alias findByName(String name);
}
