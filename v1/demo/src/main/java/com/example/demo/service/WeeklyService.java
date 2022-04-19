package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dao.WeeklyDAO;
import com.example.demo.model.Weekly;

public interface WeeklyService {
    public void saveNewWeekly(WeeklyDAO dailyDAO, String user_name);
    public Weekly getWeeklyByClient(String client_user_name);
    public Weekly getWeeklyById(Long weekly_id);
    public List<Weekly> listWeekliesByProfessional(String user_name);
    public void linkWeeklyToClient(String client_user_name, String weekly_id);
}