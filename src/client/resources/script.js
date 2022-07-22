let map;

const munichCor = {lat: 48.137154, lng: 11.576124};

async function initMap() {
    // fetch data and wait for response
    const response = await fetch("./restaurants.json");
    // ADD ERROR HANDLING HERE (e.g. if (!response.success) etc.)
    // get data from response
    const data = await response.json();
    // get coordiantes from data
    var array = [];
    for (var i = 0; i < data.results.length; i++) {
        if (!data.results[i].hasOwnProperty('price_level')) {
            array[i] = [data.results[i].geometry.location.lat, data.results[i].geometry.location.lng, data.results[i].name, data.results[i].formatted_address, data.results[i].rating, "-"];
        } else {
            array[i] = [data.results[i].geometry.location.lat, data.results[i].geometry.location.lng, data.results[i].name, data.results[i].formatted_address, data.results[i].rating, data.results[i].price_level];
        }
    }
    // create new google map
    map = new google.maps.Map(document.getElementById("map"), {
        center: munichCor,
        zoom: 13
    });
    // create markers
    for (var k = 0; k < array.length; k++) {
        addMarker(array[k]);
    }
}

function addMarker(props) {
    const coordinates = {
        lat: props[0],
        lng: props[1]
    };

    var name = props[2];
    const address = props[3];
    const rating = props[4];
    const price = props[5];

    const icon = {
        url: 'restaurant-icon.png', // url
        scaledSize: new google.maps.Size(25, 40), // scaled size
    };

    var marker = new google.maps.Marker({
        position: coordinates,
        map: map,
        icon: icon
    });

    var infoWindow = new google.maps.InfoWindow({maxWidth: 400});
    google.maps.event.addListener(marker, 'click', function () {
        var markerContent = "<h1 style='font-size: 55px; margin-left: 10px'>" + name + "</h1>" +
            "<h2 style='font-size: 40px; margin-left: 10px'>" + address + "</h2>" +
            "<img src='star.png' width='30' height='30' style='display: inline; margin-left: 10px; vertical-align: middle'>" + "<h2 style='display: inline; font-size: 40px; vertical-align: middle; '>" + " :" + rating + "</h2>" +
            "<img src='dollarsign.png' width='30' height='30' style='display: inline; margin-left: 15px; vertical-align: middle'>" + "<h2 style='display: inline; font-size: 40px; vertical-align: middle'>" + " :" + price + "</h2>"
        ;
        infoWindow.setContent(markerContent);
        infoWindow.open(map, marker)
    });
}

window.initMap = initMap;

