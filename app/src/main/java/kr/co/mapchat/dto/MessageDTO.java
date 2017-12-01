package kr.co.mapchat.dto;

import android.graphics.Bitmap;

import lombok.Data;

@Data
public class MessageDTO {
        private String user;
        private String title;
        private String contents;
        private Bitmap image;
        private double location_latitude;
        private double location_longitude;
        private double wcong_x;
        private double wcong_y;
        private int range;
}