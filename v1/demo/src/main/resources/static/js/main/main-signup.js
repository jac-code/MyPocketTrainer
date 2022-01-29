const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");
// const sign_up_form_btn = document.querySelector("#sign-up-form-btn");

sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");
});

sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});

// const section_clients = document.getElementById('pricing-table-clients');
// const section_professionals = document.getElementById('pricing-table-professionals');
// const client_span = document.getElementById('client');
// const professional_span = document.getElementById('professional');

// client_span.addEventListener('click', ()=>{
//     // Remove classes first if they exist
//     section_clients.classList.remove('block')
//     section_professionals.classList.remove('none')

//     // Add classes
//     section_clients.classList.toggle('none')
//     section_professionals.classList.toggle('block')
// });

// professional_span.addEventListener('click', ()=>{
//     // Remove classes first if they exist
//     section_clients.classList.remove('block')
//     section_professionals.classList.remove('none')

//     // Add classes
//     section_clients.classList.toggle('none')
//     section_professionals.classList.toggle('block')
// });

