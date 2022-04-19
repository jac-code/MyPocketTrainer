const open_btn = document.querySelector(".open__modal");
const close_btn = document.querySelector("#close__modal");
const modal = document.querySelector(".modal");

/*=============== SHOW MODAL ===============*/
open_btn.addEventListener('click', open_modal);

function open_modal() {
    modal.style.display = "grid";
}

/*=============== CLOSE MODAL ===============*/
close_btn.addEventListener('click', close_modal);

function close_modal() {
    modal.style.display = "none";
}