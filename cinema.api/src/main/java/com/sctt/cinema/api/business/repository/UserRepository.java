package com.sctt.cinema.api.business.repository;

import com.sctt.cinema.api.business.entity.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
