package capstone.backend.utils;

import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static capstone.backend.mapper.EmployeeMapper.mapEmployee;
import static capstone.backend.model.enums.UserRole.ADMIN;

public class EmployeeTestUtils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public static EmployeeDTO sampleUserDTO() {
        return EmployeeDTO
                .builder()
                .id(123L)
                .firstName("John")
                .lastName("Doe")
                .email("a@b.c")
                .phone("01234")
                .picture("img")
                .username("username")
                .password("password")
                .roles(Set.of(ADMIN))
                .build();
    }

    public static Employee sampleUser() {
        return Employee
                .builder()
                .id(123L)
                .firstName("John")
                .lastName("Doe")
                .email("a@b.c")
                .phone("01234")
                .picture("img")
                .username("username")
                .password("password")
                .roles(Set.of(ADMIN))
                .build();
    }


    public Employee userWithEncodedPassword(EmployeeDTO user) {
        Employee userToSave = mapEmployee(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return userToSave;
    }

}
