package com.atech.yekatit_care.Services;
import com.atech.yekatit_care.Domains.Patient;
import com.atech.yekatit_care.Domains.Role;
import com.atech.yekatit_care.Domains.User;
import com.atech.yekatit_care.Repositories.PatientRepository;
import com.atech.yekatit_care.Repositories.RoleRepository;
import com.atech.yekatit_care.Repositories.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PatientRepository patientRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SessionFactory sessionFactory;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository,
                       @Qualifier("roleRepository") RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public List<User> getAllUsers(){ return userRepository.findAllByOrderByName(); }
    public void deletePatient(int id){patientRepository.deletePatientById(id); }
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public void saveUser(User user, String role) {
        user.setPassword((bCryptPasswordEncoder.encode(user.getPassword())));
        user.setActive(1);
        Role userRole = roleRepository.findByRole(role);
        user.setRoles(new HashSet(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    public void updateUser(int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User ur = userRepository.getOne(id);
        userRepository.save(ur);
    }
    public List<User> getDoctors(){
       return  userRepository.findDoctors();
    }

}
