package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.controller.dao.WeeklyDAO;
import com.example.demo.model.Client;
import com.example.demo.model.Daily;
import com.example.demo.model.ModelUser;
import com.example.demo.model.Professional;
import com.example.demo.model.Weekly;
import com.example.demo.repository.WeekliesRepository;
import com.example.demo.service.ClientsService;
import com.example.demo.service.DailyService;
import com.example.demo.service.DietService;
import com.example.demo.service.ModelUserService;
import com.example.demo.service.ProfessionalsService;
import com.example.demo.service.RecipesService;
import com.example.demo.service.WeeklyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeeklyServiceImpl implements WeeklyService{
    @Autowired
    private WeekliesRepository weekliesRepository;

    @Autowired
    private ProfessionalsService professionalsService;

    @Autowired
    private DailyService dailyService;

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private ModelUserService modelUserService;

    @Autowired
    private ClientsService clientsService;

    @Autowired 
    private DietService dietService;

    @Override
    public void linkWeeklyToClient(String client_user_name, String weekly_id) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(client_user_name);
        Client client = clientsService.getClientByUsername(modelUser.getUser_name());
        Weekly weekly = weekliesRepository.findWeeklyById(Long.parseLong(weekly_id));

        client.setWeekly(weekly);
        clientsService.saveRegisteredClient(client);
    }

    @Override
    public void saveNewWeekly(WeeklyDAO weeklyDAO, String user_name) {
        Weekly weekly = new Weekly();
        List<Daily> dailies = new ArrayList<>();
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        Professional p = professionalsService.getProfessionalById(modelUser.getUser_id());
        
        // int cont = 0;
        // for (Daily daily : weeklyDAO.getDailies_in_weekly()) {
        //     switch (cont) {
        //         case 0:
        //             daily.setWeek_day("MONDAY");
        //             break;
        //         case 1:
        //             daily.setWeek_day("TUESDAY");
        //             break;
        //         case 2:
        //             daily.setWeek_day("WEDNESDAY");
        //             break;
        //         case 3:
        //             daily.setWeek_day("THURSDAY");
        //             break;
        //         case 4:
        //             daily.setWeek_day("FRIDAY");
        //             break;
        //         case 5:
        //             daily.setWeek_day("SATURDAY");
        //             break;
        //         case 6:
        //             daily.setWeek_day("SUNDAY");
        //             break;
        //         default:
        //             break;
        //     }

        //     cont++;

        //     if (cont == 7) {
        //         cont = 0;
        //     }

        //     dailies.add(daily);
        //     dailyService.saveDaily(daily);
        // }

        weekly.setDailies(weeklyDAO.getDailies_in_weekly());
        weekly.setProfessional(p);

        weekliesRepository.save(weekly);
    }

    @Override
    public Weekly getWeeklyById(Long weekly_id) {
        return weekliesRepository.findWeeklyById(weekly_id);
    }

    @Override
    public List<Weekly> listWeekliesByProfessional(String user_name) {
        ModelUser modelUser = modelUserService.getModelUserByUsername(user_name);
        return weekliesRepository.findByProfessional(modelUser.getUser_id());
    }

    @Override
    public Weekly getWeeklyByClient(String client_user_name) {
        Client client = clientsService.getClientByUsername(client_user_name);
        return client.getWeekly();
    }
}