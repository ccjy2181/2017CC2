package kr.co.mapchat.DTO;

import lombok.Data;

@Data
public class MessageDTO {
        private String user;
        private String title;
        private String contents;
        private double location_latitude;
        private double location_longitude;
        private int range;
}