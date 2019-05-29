$(document).ready(function() {
    $("#mycarousel").carousel({interval: 2500});

    $("#carouselButton").click(function() {
        if ($("#carouselButton").children("span").hasClass("fa-pause")) {
            $("#mycarousel").carousel("pause");
            $("#carouselButton").children("span").removeClass("fa-pause");
            $("#carouselButton").children("span").addClass("fa-play");
        }
        else {
            $("#mycarousel").carousel("cycle");
            $("#carouselButton").children("span").removeClass("fa-play");
            $("#carouselButton").children("span").addClass("fa-pause");
        }
    });

    // Task 2
    $("#reserveTable").click(function() {
        $('#formModal').modal('show');
    });
    
    // Task 3
    $("#loginLink").click(function() {
        $('#loginModal').modal('show');
    });
})

// To activate the tooltip
// $(document).ready(function() {
//     $('[data-toggle="tooltip"]').tooltip();
// })
