package capstone.backend.utils;

import capstone.backend.security.model.Employee;
import capstone.backend.security.model.EmployeeDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static capstone.backend.security.service.UserMapper.mapUser;

public class TestUtils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public EmployeeDTO sampleUserDTO() {
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
                .build();
    }

    public EmployeeDTO sampleUserDTOWithEncryptedPassword() {
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
                .build();
    }

    public Employee sampleUser() {
        return Employee
                .builder()
                .id(123L)
                .username("username")
                .password("password")
                .build();
    }


    public Employee userWithEncodedPassword(EmployeeDTO user) {
        Employee userToSave = mapUser(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        return userToSave;
    }

}
