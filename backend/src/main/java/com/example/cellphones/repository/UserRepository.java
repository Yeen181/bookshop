package com.example.cellphones.repository;
import com.example.cellphones.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("delete from User u where u.username = :username")
    void deleteUserByUsername(String username);

    @Query("select u from User u where (:searchText is null or (u.fullName like %:searchText%) " +
            "or (u.email like %:searchText%) " +
            "or (u.username like %:searchText%) " +
            "or (u.address like %:searchText%) " +
            "or (u.phoneNumber like %:searchText%)) " +
            "and :active = u.enabled"
    )
    Page<User> getPaginate(String searchText, Boolean active, Pageable pageable);
}
