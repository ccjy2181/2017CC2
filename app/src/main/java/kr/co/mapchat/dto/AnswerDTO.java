package kr.co.mapchat.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AnswerDTO {
        private String user;
        private String comment;
        private Date regdate;
}