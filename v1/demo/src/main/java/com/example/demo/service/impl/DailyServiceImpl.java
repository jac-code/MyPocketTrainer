package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.controller.dao.DailyDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Daily;
import com.example.demo.model.Diet;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Routine;
import com.example.demo.model.Weekly;
import com.example.demo.repository.DailiesRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DailyService;
import com.example.demo.service.DietService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;
import com.example.demo.service.RoutineService;
import com.example.demo.service.WeeklyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyServiceImpl implements DailyService{
    @Autowired
    private DailiesRepository dailiesRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Autowired 
    private DietService dietService;

    @Autowired
    private RoutineService routineService;

    @Autowired
    private WeeklyService weeklyService;

    @Override
    public void saveDaily(Daily daily) {
        dailiesRepository.save(daily);
    }

    @Override
    public void saveNewDaily(DailyDAO dailyDAO, String user_name) {
        Daily daily = new Daily();
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());
        Diet diet = dietService.getDietById(Long.parseLong(dailyDAO.getDiet_id()));
        Routine routine = routineService.getRoutineById(Long.parseLong(dailyDAO.getRoutine_id()));

        daily.setDiet(diet);
        daily.setProfessional(p);
        daily.setRoutine(routine);

        dailiesRepository.save(daily);
    }

    @Override
    public Daily getDailyById(Long daily_id) {
        return dailiesRepository.findDailyById(daily_id);
    }

    @Override
    public List<Daily> listDailiesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        return dailiesRepository.findDailiesByProfessional(modelUser.getUser_id());
    }

    @Override
	public List<Daily> listLinkedDailies(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Client client = clientsService.getClientByUsername(modelUser.getUser_name());
        return client.getLinkedDailies();
	}

    @Override 
    public Daily getDailyByWeekDay(int week_day, String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Client client = clientsService.getClientByUsername(modelUser.getUser_name());

        Weekly w = client.getWeekly();  // esta hecho para que cada semana tenga un weekly nuevo
        Daily d = new Daily();
        List<Daily> dailies = w.getDailies();

        switch (week_day) {
            case 1:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Monday") {
                        d = daily;
                    }
                }
                break;
            case 2:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Tuesday") {
                        d = daily;
                    }
                }
                break;
            case 3:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Wednesday") {
                        d = daily;
                    }
                }
                break;
            case 4:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Thursday") {
                        d = daily;
                    }
                }
                break;
            case 5:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Friday") {
                        d = daily;
                    }
                }
                break;
            case 6:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Saturday") {
                        d = daily;
                    }
                }
                break;
            case 7:
                for (Daily daily : dailies) {
                    if (daily.getWeek_day() == "Sunday") {
                        d = daily;
                    }
                }
                break;
        
            default:
                break;
        }
        
        return d;
    }
}