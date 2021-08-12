package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public void run(String...args){
        User admin = new User("admin", "admin", true);
        Role adminRole = new Role("admin", "ROLE_ADMIN");

        User user = new User("user", "user", true);
        Role userRole = new Role("user", "ROLE_USER");

        userRepository.save(admin);
        userRepository.save(user);
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        Department tech = new Department();
        tech.setName("Technology");
        Set<Employee> techEmployees = new HashSet<>();

        Employee me = new Employee();
        me.setFirstName("David");
        me.setLastName("Kim");
        me.setJobTitle("Developer");
        me.setDepartment(tech);

        tech.setEmployees(techEmployees);
        techEmployees.add(me);
        departmentRepository.save(tech);

    }
}
