package mapper;

import model.Role;
import model.User;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO convertUserToUserDTO(User user) {
        UserDTOBuilder builder = new UserDTOBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername());

        if (user.getRole() != null) {
            builder.setRole(user.getRole().getRole());
        } else {
            builder.setRole("");
        }

        return builder.build();
    }

    public static User convertUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());

        if (userDTO.getRole() != null) {
            Role role = new Role(null, userDTO.getRole(), null);

            user.setRole(role);
        }

        return user;
    }

    public static List<UserDTO> convertUserListToUserDTOList(List<User> users) {
        return users.parallelStream()
                .map(UserMapper::convertUserToUserDTO)
                .collect(Collectors.toList());
    }

    public static List<User> convertUserDTOListToUserList(List<UserDTO> userDTOS) {
        return userDTOS.parallelStream()
                .map(UserMapper::convertUserDTOToUser)
                .collect(Collectors.toList());
    }
}