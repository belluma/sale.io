package capstone.backend.security.service;

import capstone.backend.security.model.AppUser;
import capstone.backend.security.model.UserDTO;

public class UserMapper {
    public AppUser mapUser(UserDTO user) {
        return AppUser
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
public UserDTO mapUser(AppUser user) {
        return UserDTO
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
