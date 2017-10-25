var map;
var first;
var second;
var third;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 59.967, lng: 30.31},
        zoom: 14
    });
    first = new google.maps.Marker({
        position: {lat:59.99105,lng:30.3186},
        label: "Первая юрта",
        map: map
    });
    first.addListener('click', function() {
        map.setCenter(first.position);
        document.getElementById("shop-choice").value = 1;
        map.setZoom(14);
    });

    second = new google.maps.Marker({
        position: {lat:60.004,lng:30.2814},
        label: "Вторая юрта",
        map: map
    });
    second.addListener('click', function() {
        map.setCenter(second.position);
        document.getElementById("shop-choice").value = 2;
        map.setZoom(14);
    });

    third = new google.maps.Marker({
        position: {lat:59.971665,lng:30.2581},
        label: "Третья юрта",
        map: map
    });
    third.addListener('click', function() {
        map.setCenter(third.position);
        document.getElementById("shop-choice").value = 3;
        map.setZoom(14);
    });
    map.setCenter(second.position);
}

function changeShop() {
    var shop = document.getElementById("shop-choice").value;
    switch (shop){
        case '1':map.setCenter(first.position);break;
        case '2':map.setCenter(second.position);break;
        case '3':map.setCenter(third.position);break;
    }
}

function changeType() {
    if(document.getElementById("type-choice").checked){
        document.getElementById("shop").style.display = "none";
        document.getElementById("courier").style.display = "block";
    }
    else {
        document.getElementById("shop").style.display = "block";
        document.getElementById("courier").style.display = "none";
    }
}