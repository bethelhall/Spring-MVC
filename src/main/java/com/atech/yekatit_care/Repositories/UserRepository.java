package com.atech.yekatit_care.Repositories;


import com.atech.yekatit_care.Domains.Role;
import com.atech.yekatit_care.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByName(String name);
    public List<User> findAllByOrderByName();
    public void deleteUserByEmail(String email);
    public void deleteUserById(int id);
    @Query("SELECT us FROM User us WHERE us.selectedRole='DOCTOR'")
    List<User> findDoctors();


}

