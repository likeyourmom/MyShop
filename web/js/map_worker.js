var map;
var first;
var second;
var third;

function initMap() {
    map = new ymaps.Map("map", {
        center: [49.803737, 73.087571],
        zoom: 17,
        controls: ['smallMapDefaultSet']
    });

    first = new ymaps.Placemark([49.806335, 73.053235],
        {balloonContent: "Первая юрта"},
        {
            iconLayout: 'default#image',
            iconImageHref: '../img/map.png',
            iconImageSize: [55, 40],
            iconImageOffset: [-28, -39]
        });

    second = new ymaps.Placemark([49.803737, 73.087571],
        {balloonContent: "Вторая юрта"},
        {
            iconLayout: 'default#image',
            iconImageHref: '../img/map.png',
            iconImageSize: [55, 40],
            iconImageOffset: [-28, -39]
        });
    third = new ymaps.Placemark([49.779054, 73.133193],
        {balloonContent: "Третья юрта"},
        {
            iconLayout: 'default#image',
            iconImageHref: '../img/map.png',
            iconImageSize: [55, 40],
            iconImageOffset: [-28, -39]
        });

    map.geoObjects.add(first);
    map.geoObjects.add(second);
    map.geoObjects.add(third);
}

function changeShop() {
    var shop = document.getElementById("shop-choice").value;

    map.setZoom(17);
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