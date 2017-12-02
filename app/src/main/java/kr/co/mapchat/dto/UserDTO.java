package kr.co.mapchat.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
    private String token;
    private Date regdate;
    private double location_latitude;
    private double location_longitude;
}