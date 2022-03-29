package com.example.demo.controller.dao;

import java.util.List;

import com.example.demo.model.Daily;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class WeeklyDAO {
    private List<Daily> dailies;
}