package kr.co.mapchat.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.Data;
@Data
public class MessageDTO implements Serializable {
        private String user;
        private String title;
        private String contents;
        private double location_latitude;
        private double location_longitude;
        private int range;
        private String image_string;
        private Date regdate;
        private Map<String, AnswerDTO> answer;

        //add data
        private String key;
}