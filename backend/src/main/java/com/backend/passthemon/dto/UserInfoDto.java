package com.backend.passthemon.dto;

import com.backend.passthemon.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoDto {
    private String username;

    private Integer userid;

    private String image;

    public UserInfoDto(User user){
        this.username=user.getUsername();
        this.userid=user.getId();
        this.image=user.getImage();
    }

    public UserInfoDto(){}

    public static List<UserInfoDto> convert(List<User> userList){
        List<UserInfoDto> userInfoDtoList=new ArrayList<>();
        for (User user : userList) {
            userInfoDtoList.add(new UserInfoDto(user));
        }
        return userInfoDtoList;
    }

    public static UserInfoDto convert(User user){
        return new UserInfoDto(user);
    }
}
