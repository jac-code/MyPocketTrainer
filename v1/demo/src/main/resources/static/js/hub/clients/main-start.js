const cuisineList = ["African","American","British","Cajun","Caribbean","Chinese","Eastern European", "European","French","German","Greek","Indian","Irish","Italian","Japanese","Jewish","Korean","Latin American", "Mediterranean","Mexican","Middle Eastern","Nordic","Southern","Spanish","Thai","Vietnamese"];
const typesList = ["main course", "side dish","dessert","appetizer","salad","bread","breakfast","soup","beverage","sauce","marinade","fingerfood","snack","drink"];
const dietList = ["Gluten Free", "Ketogenic", "Pescetarian", "Vegan", "Ovo-Vegetarian", "Lacto-Vegetarian", "Vegetarian", "Paleo", "Primal", "Low FODMAP", "Whole30"];
const intoleranceList = ["Dairy","Egg","Gluten","Grain","Peanut","Seafood","Sesame","Shellfish","Soy","Sulfite","Tree Nut","Wheat"];

window.onload = function createCuisineOptions() {

    console.log(cuisineList);
    cuisineList.forEach(element => {
        var x = document.createElement("OPTION");
        x.setAttribute("value", element);
        var t = document.createTextNode(element);
        x.appendChild(t);
        document.getElementById("options_cuisine").appendChild(x);
    });

    dietList.forEach(element => {
        var x = document.createElement("OPTION");
        x.setAttribute("value", element);
        var t = document.createTextNode(element);
        x.appendChild(t);
        document.getElementById("options_diet").appendChild(x);
    });

    intoleranceList.forEach(element => {
        var x = document.createElement("OPTION");
        x.setAttribute("value", element);
        var t = document.createTextNode(element);
        x.appendChild(t);
        document.getElementById("options_intolerance").appendChild(x);
    });

    typesList.forEach(element => {
        var x = document.createElement("OPTION");
        x.setAttribute("value", element);
        var t = document.createTextNode(element);
        x.appendChild(t);
        document.getElementById("options_type").appendChild(x);
    });
}