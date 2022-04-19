package com.example.demo.controller.dao;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DailyDAO {
    private String week_day;
    private String diet_id;
    private String routine_id;
    private String client_id;
}