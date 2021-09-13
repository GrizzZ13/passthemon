package com.backend.passthemon.dto;

import com.backend.passthemon.entity.Order;
import com.backend.passthemon.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private Integer state;
    private Double credit;
    private Integer gender;
    private String phone;
    private String refreshToken;

    public static UserDto convert(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .state(user.getState())
                .credit(user.getCredit())
                .gender(user.getGender())
                .phone(user.getPhone())
                .build();
    }
    public static List<UserDto> convert(List<User> userList){
        List<UserDto> result = new LinkedList<>();
        for (User user : userList){
            result.add(UserDto.convert(user));
        }
        return result;
    }
}
