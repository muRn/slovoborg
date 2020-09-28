function postOpinion(opinion) {
    var request = new XMLHttpRequest();
    request.open('POST', '/opinion', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(opinion));
}