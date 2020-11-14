function postOpinion(opinion) {
    var request = new XMLHttpRequest();
    request.open('POST', '/opinion', true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onload = function() {
        changeCount(opinion);
        if (this.status === 401) {
            window.location.replace('/login');
            return;
        }
    };
    request.onerror = function() {
        console.log('something went wrong while POSTing opinion');
    };
    request.send(JSON.stringify(opinion));
}

function changeCount(opinion) {
    const action = opinion.opinion === 1 ? 'like' : 'dislike';

    const buttonId = action + '_btn_' + opinion.definitionId;
    const countId = action + 's_cnt_' + opinion.definitionId;

    const button = document.querySelector('#' + buttonId);
    const count = document.querySelector('#' + countId);

    const buttonPressedClass = 'is-info'

    if (button.classList.contains(buttonPressedClass)) {
        button.classList.remove(buttonPressedClass);
        count.textContent = count.textContent - 1;
    } else {
        button.classList.add(buttonPressedClass);
        count.textContent = count.textContent - -1;

        // checking if opposite button should be released
        const oppositeAction = opinion.opinion === 1 ? 'dislike' : 'like';
        const otherButton = document.querySelector('#' + oppositeAction + '_btn_' + opinion.definitionId);
        const otherCount = document.querySelector('#' + oppositeAction + 's_cnt_' + opinion.definitionId);
        if (otherButton.classList.contains(buttonPressedClass)) {
            otherButton.classList.remove(buttonPressedClass);
            otherCount.textContent = otherCount.textContent - 1
        }
    }
}