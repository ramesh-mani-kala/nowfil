package com.turf.playarena.repository;

import com.turf.playarena.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select * from user where email=:email",nativeQuery = true)
    User findByEmail(String email);

    User findByPhoneNumber(String username);

    @Query(value = "select * from user where name=:name",nativeQuery = true)
    User findByName(String name);
}
