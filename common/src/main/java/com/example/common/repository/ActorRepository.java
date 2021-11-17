package com.example.common.repository;

import com.example.common.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
