var map;
var first;
var second;
var third;

function initMap() {
    console.log("Инициализируем карту");
    map = new ymaps.Map("map", {
        center: [49.803737, 73.087571],
        zoom: 15
    });

    map.controls.add('smallZoomControl', {top: 25, left: 5});

    first = new yamaps.Placemark([49.806335, 73.053235], {balloonContent: "Первая юрта"});
    second = new yamaps.Placemark([49.803737, 73.087571], {balloonContent: "Вторая юрта"});
    third = new yamaps.Placemark([49.779054, 73.133193], {balloonContent: "Третья юрта"});

    map.geoObjects.add(first);
    map.geoObjects.add(second);
    map.geoObjects.add(third);

    first.balloon.open();
    second.balloon.open();
    third.balloon.open();
}

function changeShop() {
    var shop = document.getElementById("shop-choice").value;

    switch (shop){
        case '1':
            map.setCenter([49.806335, 73.053235]);
            break;
        case '2':
            map.setCenter([49.803737, 73.087571]);
            break;
        case '3':
            map.setCenter([49.779054, 73.133193]);
            break;
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