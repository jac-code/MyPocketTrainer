package com.example.demo.controller.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Daily;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class WeeklyDAO {
    private List<Daily> dailies_in_weekly;
    private Map<Daily,String> mapping = new HashMap<>();
}