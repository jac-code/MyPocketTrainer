(function () {
    var slideContainer = $('.price-box-container');

    slideContainer.slick();

    $('.plan-img svg').hide();
    $('.slick-active').find('.plan-img svg').fadeIn(200);

    slideContainer.on('beforeChange', function (event, slick, currentSlide, nextSlide) {
        $('.slick-active').find('.plan-img svg').fadeOut(1000);
    });

    slideContainer.on('afterChange', function (event, slick, currentSlide) {
        $('.slick-active').find('.plan-img svg').fadeIn(200);
    });
})();

// .slick-prev {
//     left: 100px;
//     z-index: 999;
// }

// .slick-next {
//     right: 100px;
//     z-index: 999;
// }