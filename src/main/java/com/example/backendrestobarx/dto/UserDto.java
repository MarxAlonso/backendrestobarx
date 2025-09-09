package com.example.backendrestobarx.dto;

import com.example.backendrestobarx.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private User.Role role;
    private String phone;

    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPhone()
        );
    }
}