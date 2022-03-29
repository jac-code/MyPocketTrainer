// SHOW & HIDE SIDEBAR
const menuBtn = document.querySelector('#menu__btn');
const closeBtn = document.querySelector('#close__btn');
const sidebar = document.querySelector('aside');

menuBtn.addEventListener('click', () => {
    sidebar.style.display = 'block';
})

closeBtn.addEventListener('click', () => {
    sidebar.style.display = 'none';
})