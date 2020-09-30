function postOpinion(opinion) {
    var request = new XMLHttpRequest();
    request.open('POST', '/opinion', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onload = function() {
        changeCount(opinion);
    };
    request.onerror = function() {
        console.log('something went wrong while POSTing opinion');
    };
    request.send(JSON.stringify(opinion));
}

function changeCount(opinion) {
    let action = opinion.opinion === 1 ? 'like' : 'dislike';

    let buttonId = action + '_btn_' + opinion.definitionId;
    let countId = action + 's_cnt_' + opinion.definitionId;

    let button = document.querySelector('#' + buttonId);
    let count = document.querySelector('#' + countId);

    if (button.classList.contains('primary')) {
        button.classList.remove('primary');
        count.textContent = count.textContent - 1;
    } else {
        button.classList.add('primary');
        count.textContent = count.textContent - -1;
    }
}