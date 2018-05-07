addEventListener('load', function () {
    document.oncontextmenu = function () {
        return false
    }
}, false);

var baseUrl = 'https://' + location.host;

function getCurrentYearAndMonth() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    return year + '-' + month
}

function getCurrentYear() {
    var date = new Date();
    var year = date.getFullYear();
    return year
}

function getCurrentMonth() {
    var date = new Date();
    var month = date.getMonth()+1;
    return month
}

function getDaysOfCurrentMonth(year, month) {
    var d = new Date(year, month, 0);
    return d.getDate();
}

function dateDiff(sDate2) {
    var sDate1=new Date();
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24);
    return iDays;
}

function showErrorMessage() {
    $('#progress_div').css('display', 'none');
    $('#status_div').css('display','block');
    $('#status_message').html('request error');
    setTimeout(function () {
        $('#status_div').css('display','none');
    }, 1500)
}

function showSuccessMessage(message) {
    $('#progress_div').css('display', 'none');
    $('#status_div').css('display','block');
    $('#status_message').html(message);
    setTimeout(function () {
        $('#status_div').css('display','none');
    }, 1500)
}