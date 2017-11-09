function loadComments() {
    var xmlhttp;

    if (window.XMLHttpRequest)
        xmlhttp = new XMLHttpRequest();
    else
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");

    xmlhttp.onreadystatechange = function() {
        console.log("Запрос отправлен");
        if (xmlhttp.readyState==4){
            document.getElementById("comments").innerHTML = xmlhttp.responseText;
            console.log("Ответ получен");
        }
    }

    xmlhttp.open("GET","/comments",true);
    xmlhttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xmlhttp.send();
}

function sendComment(){
    var xhr = new XMLHttpRequest();
    var sel_cat = document.getElementById("message").value;
    var result_cat = encodeURIComponent(sel_cat);

    xhr.open("POST", "/comments", true)
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
    xhr.send("text=" + result_cat);

    xhr.onreadystatechange = function (){
        if(xhr.readyState == 4){
            document.getElementById("message").value = "";
            loadComments();
        }
    }
}

function enableDateTimer() {
    document.getElementById("datetime").innerHTML = getDate(new Date());

    setInterval(function () {
        document.getElementById("datetime").innerHTML = getDate(new Date());
    },60*1000);
}

function getDate(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    return day+"."+month+"."+year+"  "+hour+":"+minute;
}
