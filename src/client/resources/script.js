let map;
const munichCor = {lat: 48.137154, lng: 11.576124};

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: {lat: 48.137154, lng: 11.576124},
        zoom: 8,
    });

    const marker = new google.maps.Marker({
        position: munichCor,
        map: map
    });
}

window.initMap = initMap;

