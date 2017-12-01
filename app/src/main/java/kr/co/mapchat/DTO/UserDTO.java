package kr.co.mapchat.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
    private String token;
    private Date regdate;

}