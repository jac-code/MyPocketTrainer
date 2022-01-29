const searchBtn = document.getElementById('search-btn');
const mealList = document.getElementById('meal');
const mealDetailsContent = document.querySelector('.meal-details-content');
const recipeCloseBtn = document.getElementById('recipe-close-btn');

const url_key = "&apiKey=7bb354a13517443fae3de15b4475d594";
const url_base = "https://api.spoonacular.com/recipes/complexSearch?addRecipeInformation=true&addRecipeNutrition=true";
const options_name = ['options_cuisine', 'options_type', 'options_intolerance', 'options_diet'];

// event listeners
searchBtn.addEventListener('click', getMealList);
mealList.addEventListener('click', getMealRecipe);
recipeCloseBtn.addEventListener('click', () => {
    mealDetailsContent.parentElement.classList.remove('showRecipe');
});

// get meal list that matches with the ingredients
function getMealList(){
    let searchInputTxt = document.getElementById('search-input').value.trim();
    var options_html = [];

    options_name.forEach(element => {
        var x = document.getElementById(element);
        options_html.push(x.options[x.selectedIndex].value);
    });

    var cont = 0;
    var url = url_base;

    options_html.forEach(element => {
        if (element != 'null' && cont == 0) {
            url += '&cuisine=' + element;
        }
        else if (element != 'null' && cont == 1) {
            url += '&type=' + element;
        }
        else if (element != 'null' && cont == 2) {
            url += '&intolerances=' + element;
        }
        else if (element != 'null' && cont == 3) {
            url += '&diet=' + element;
        }
        cont++;
    });

    if (searchInputTxt != null) {
        url += "&includeIngredients=" + searchInputTxt;
    }

    url += url_key;

    //fetch(`https://api.spoonacular.com/recipes/complexSearch?query=${searchInputTxt}&addRecipeInformation=true&addRecipeNutrition=true&apiKey=${KEY_SPOONACULAR}`)
    fetch(url)
    .then(response => response.json())
    .then(data => {
        let html = "";
        if(data.results){
            data.results.forEach(meal => {
                html += `
                    <div class = "meal-item" data-id = "${JSON.stringify(meal.id)}">
                        <div class = "meal-img">
                            <img src = "${meal.image}" alt = "food">
                        </div>
                        <div class = "meal-name">
                            <h3>${meal.title}</h3>
                            <a href = "#" class = "recipe-btn">Get Recipe</a>
                        </div>
                    </div>
                `;
            });
            mealList.classList.remove('notFound');
        } else{
            html = "Sorry, we didn't find any meal!";
            mealList.classList.add('notFound');
        }

        mealList.innerHTML = html;
    });
}

// get recipe of the meal
function getMealRecipe(e){
    e.preventDefault();
    if(e.target.classList.contains('recipe-btn')){
        let mealItem = e.target.parentElement.parentElement;
        console.log(mealItem);
        fetch(`https://api.spoonacular.com/recipes/${mealItem.dataset.id}/information?apiKey=7bb354a13517443fae3de15b4475d594`)
        .then(response => response.json())
        .then(data => mealRecipeModal(data));
    }
}

// create a modal
function mealRecipeModal(meal){
    console.log(meal);
    //meal = meal[0];
    let html = `
        <h2 class = "recipe-title">${meal.title}</h2>
        <p class = "recipe-category">${meal.cuisines[0]} ${meal.dishTypes[0]}</p>
        <div class = "recipe-instruct">
            <h3>Summary:</h3>
            <p>${meal.summary}</p>
        </div>
        <div class = "recipe-meal-img">
            <img src = "${meal.image}" alt = "">
        </div>
        <div class = "recipe-link">
            <a href = "${meal.spoonacularSourceUrl}" target = "_blank">Watch Recipe</a>
        </div>
    `;
    mealDetailsContent.innerHTML = html;
    mealDetailsContent.parentElement.classList.add('showRecipe');
}