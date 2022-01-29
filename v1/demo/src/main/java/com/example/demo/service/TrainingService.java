package com.example.demo.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrainingService {

    //@Value("${trainingAPI.url}")
    private String urlExercise = "https://wger.de/api/v2/exercise/?limit=1&offset=";


    public String getExercise() {
        // NECESITAREMOS LA URL
        System.out.print("\nFetching exercise...");
        final RestTemplate template = new RestTemplate(); //Clase template para hacer peticiones
        final String urlFinal = urlExercise + Math.round(Math.random()*(100-1+1)+1) + "\n";
        System.out.print("\n" + urlFinal);
        final HttpMethod method = HttpMethod.GET;
        // 3er argumento body + headers
        final ResponseEntity<String> response = template.exchange(urlFinal, method, null,
                String.class); //Ultimo es lo que recibes. Para pasar el json a clase
        return response.getBody();
    }

}
