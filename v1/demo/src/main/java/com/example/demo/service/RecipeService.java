package com.example.demo.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecipeService {

    //@Value("${recipesAPI.url}")
    private String urlRecipes = "https://api.spoonacular.com/recipes/findByNutrients?apiKey=c560265e3d784567bbeb7dbf246fa624&number=3&random=true&maxCalories=";

    //@Value("${recipesAPIinfo1.url}")
    private String urlInfo1 = "https://api.spoonacular.com/recipes/";
    
    //@Value("${recipesAPIinfo2.url}")
    private String urlInfo2 = "/information?&apiKey=c560265e3d784567bbeb7dbf246fa624";



    public String getRecipes(int maxcalories) {
        // NECESITAREMOS LA URL
        System.out.print("\nFetching recipes...");
        final RestTemplate template = new RestTemplate(); //Clase template para hacer peticiones
        final String urlFinal = urlRecipes+maxcalories+"\n";
        System.out.print("\n" + urlFinal);
        final HttpMethod method = HttpMethod.GET;
        // 3er argumento body + headers
        final ResponseEntity<String> response = template.exchange(urlFinal, method, null,
                String.class); //Ultimo es lo que recibes. Para pasar el json a clase
        return response.getBody();
    }

    public String getRecipeLink(String id) {
        // NECESITAREMOS LA URL
        System.out.print("\nFetching info for " + id + " ...");
        final RestTemplate template = new RestTemplate(); //Clase template para hacer peticiones
        final String urlFinal = urlInfo1+id+urlInfo2;
        System.out.print("\n" + urlFinal);
        final HttpMethod method = HttpMethod.GET;
        // 3er argumento body + headers
        final ResponseEntity<String> response = template.exchange(urlFinal, method, null,
                String.class); //Ultimo es lo que recibes. Para pasar el json a clase
        return response.getBody();
    }
}
