package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.dao.DailyDAO;
import com.example.demo.model.Daily;

public interface DailyService {
    public void saveNewDaily(DailyDAO dailyDAO, String user_name);
    public void saveDaily(Daily daily) ;
    public Daily getDailyById(Long daily_id);
    public List<Daily> listDailiesByProfessional(String user_name);
	public List<Daily> listLinkedDailies(String user_name);
    public Daily getDailyByWeekDay(int week_day, String user_name);
}