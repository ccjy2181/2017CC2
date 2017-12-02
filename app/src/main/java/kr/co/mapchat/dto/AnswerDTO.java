package kr.co.mapchat.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AnswerDTO implements Serializable {
        private String user;
        private String comment;
        private Date regdate;
}