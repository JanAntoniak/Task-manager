function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

function showAllTasks() {

    $("#content").html("");
    jQuery.ajax({
        url: "/tasks",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            var idName = "";
            var state = 0;  // 0 - added, 1 - done, 2 - cancelled
            items.push("<thead><tr><th>Name</th><th>Description</th> " +
                "<th>Time</th><th>State</th><th>Set done</th><th>Set cancel</th></tr></thead>");
            items.push("<tbody>");
            $.each(resultData, function (key, val) {

                state = 0;
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    if(key2 == "name")
                        idName = val2;
                    if(key2 == "state")
                        if(val2 == "ADDED")
                            state = 0;
                        else if(val2 == "DONE")
                            state = 1;
                        else
                            state = 2;
                    items.push("<td " + "'>" + val2 + "</td>");
                });

                items.push('<td>');
                if(state == 0) {
                    items.push('<div class="radio">');
                    items.push('<input id="' + idName + '" onclick="setDone(\'' + idName + '\')"'+
                        ' type="button" name="optradio" >');
                }
                else if(state == 1) {
                    items.push('<a style="margin-left: 20px;" ><img ' +
                        'src="http://www.clipartkid.com/images/406/' +
                        ' simple-blue-tick-clip-art-at-clker-com-vector-clip-art-online-tVnL7Y-clipart.png"' +
                        ' style="width: 20px; height: 20px; " ></a>');
                }
                items.push('</td>');

                items.push('<td>');
                if(state == 0) {
                    items.push('<div class="radio">');
                    items.push('<input id="' + idName + '" onclick="setCancelled(\'' + idName + '\')" type="button" name="optradio" >');
                }
                else if(state == 2) {
                    items.push('<a style="margin-left: 20px;"><img ' +
                        'src="http://anonymousglobal.org/commanderx/blog/wp-content/uploads/2014/12/redX2.png"' +
                        ' style="width: 20px; height: 20px; align-items: center; "></a>');
                }
                items.push('</td>');



                items.push("</tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "allTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });

}

function setDone(name) {
    var data = JSON.stringify({name: name});
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/setDone");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function setCancelled(name) {
    var data = JSON.stringify({name: name});
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/setCancelled");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function showWhatToDo() {

    $("#content").html("");
    jQuery.ajax({
        url: "/getAddedTasks",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });

}

function showDoneTasks() {

    $("#content").html("");
    jQuery.ajax({
        url: "/getDoneTasks",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });

}


function showNoDeadline() {

    $("#content").html("");
    jQuery.ajax({
        url: "/getIndefinite",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });

}

function showCalendar() {

    $("#content").html("");
    jQuery.ajax({
        url: "/getDeadline",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th><th>Deadline</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    if(key2 == "deadline")
                        val2 = [val2];

                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });

}

function addNewTask() {

    $("#content").html("");
    var items = [];
    items.push('<form style="display: block; width: 100%" ><input id="dead" type="checkbox" ' + '' +
        'name="gender" onclick="addFormDead()"> DeadlineTask<br>'+
        '<input id="indef" type="checkbox" name="gender" value="female" onclick="addFormIndef()"> IndefiniteTask<br>' +
        '<input id="period" type="checkbox" name="gender" value="other" onclick="addFormPeriod()"> PeriodicTask' +
        '</form>');
    $("<a/>", {
        "class": "table",
        "id": "addedTasks",
        html: items.join("")
    }).appendTo(".data");
}

function addFormDead() {
    $("#content").html("");
    $('.form').html("");
    var items = [];
    items.push(' <form class="addForm">' +
        'Name:<br><input id="name" type="text" name="name" placeholder="Task"><br>' +
        'Description:<br><input id="desc" type="text" name="description" placeholder="Description"><br>' +
        'Estimated time [h]<br><input id="time" type="text" name="time" placeholder="10"><br>' +
        'Deadline [yy-mm-dd] <br><input id="deadL" type="text" name="deadline" placeholder="2017-10-10"><br>' +
        'Submit <br><input type="button" value="Submit" onclick="addDead()"/>' +
        '</form> '
    );
    $("<a/>", {
        "class": "form",
        "id": "form",
        html: items.join("")
    }).appendTo(".data");
}

function addDead() {

    var name = document.getElementById("name").value;
    var desc = document.getElementById("desc").value;
    var time = document.getElementById("time").value;
    var dead = document.getElementById("deadL").value;
    dead = parseInt(dead);
    time = parseInt(time);
    var data = JSON.stringify({ name: name, description: desc, time: time, state: "ADDED", deadline: dead});

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/addDeadlineTask");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function addFormPeriod() {
    $("#content").html("");
    $('.form').html("");
    var items = [];
    items.push(' <form class="addForm">' +
        'Name:<br><input id="name" type="text" name="name" placeholder="Task"><br>' +
        'Description:<br><input id="desc" type="text" name="description" placeholder="Description"><br>' +
        'Estimated time [h]<br><input id="time" type="text" name="time" placeholder="10"><br>' +
        'Period [days]<br><input id="period" type="text" name="period" placeholder="10"><br>' +
        'Submit <br><input type="button" value="Submit" onclick="addPeriod()"/>' +
        '</form> '
    );
    $("<a/>", {
        "class": "form",
        "id": "form",
        html: items.join("")
    }).appendTo(".data");
}

function addFormIndef() {
    $("#content").html("");
    $('.form').html("");
    var items = [];
    items.push(' <form class="addForm">' +
        'Name:<br><input id="name" type="text" name="name" placeholder="Task"><br>' +
        'Description:<br><input id="desc" type="text" name="description" placeholder="Description"><br>' +
        'Estimated time [h]<br><input id="time" type="text" name="time" placeholder="10"><br>' +
        'Submit <br><input type="button" value="Submit" onclick="addIndef()"/>' +
        '</form> '
    );
    $("<a/>", {
        "class": "form",
        "id": "form",
        html: items.join("")
    }).appendTo(".data");
}

function addPeriod() {
    var name = document.getElementById("name").value;
    var desc = document.getElementById("desc").value;
    var time = document.getElementById("time").value;
    var period = document.getElementById("period").value;
    period = parseInt(period);
    time = parseInt(time);
    var data = JSON.stringify({ name: name, description: desc, time: time, state: "ADDED", period: period});

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/addPeriodicTask");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function addIndef() {
    var name = document.getElementById("name").value;
    var desc = document.getElementById("desc").value;
    var time = document.getElementById("time").value;
    time = parseInt(time);
    var data = JSON.stringify({ name: name, description: desc, time: time, state: "ADDED"});

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/addIndefiniteTask");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function setDeadlineForm() {
    $("#content").html("");
    jQuery.ajax({
        url: "/getIndefinite",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });
    var items = [];
    items.push(' <form class="addForm">' +
        'Name:<br><input id="name" type="text" name="name" placeholder="Task"><br>' +
        'New deadline [h]:<br><input id="deadline" type="text" name="deadline" placeholder="10"><br>' +
        'Submit <br><input type="button" value="Submit" onclick="setDeadline()"/>' +
        '</form> '
    );
    $("<table/>", {
        "class": "table",
        "id": "addedTasks",
        html: items.join("")
    }).appendTo(".data");
}

function setDeadline() {
    var name = document.getElementById("name").value;
    var deadline = document.getElementById("deadline").value;
    deadline = parseInt(deadline);
    var data = JSON.stringify({ name: name, deadline: deadline});

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/setDeadline");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    showAllTasks();
}

function changeTimeForm() {
    $("#content").html("");
    jQuery.ajax({
        url: "/getTasks",
        type: "GET",

        contentType: 'application/json; charset=utf-8',
        success: function (resultData) {
            var items = [];
            items.push("<thead><tr><th>Name</th><th>Description</th>" +
                "<th>Time</th><th>State</th></tr></thead>");

            items.push("<tbody>");
            $.each(resultData, function (key, val) {
                items.push("<tr>");
                $.each(val, function (key2, val2) {
                    items.push("<td " + "'>" + val2 + "</td>");
                });
                items.push("<tr>");
            });
            items.push("</tbody>");

            $("<table/>", {
                "class": "table",
                "id": "addedTasks",
                html: items.join("")
            }).appendTo(".data");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        },

        timeout: 120000
    });
    var items = [];
    items.push(' <form class="addForm">' +
        'Name:<br><input id="name" type="text" name="name" placeholder="Task"><br>' +
        'New estimated time [h]:<br><input id="time" type="text" name="time" placeholder="10"><br>' +
        'Submit <br><input type="button" value="Submit" onclick="changeTime()"/>' +
        '</form> '
    );
    $("<table/>", {
        "class": "table",
        "id": "addedTasks",
        html: items.join("")
    }).appendTo(".data");
}

function changeTime() {
    var name = document.getElementById("name").value;
    var time = document.getElementById("time").value;
    time = parseInt(time);
    var data = JSON.stringify({ name: name, time: time});

    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", "/changeTime");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(data);
    changeTimeForm()
}