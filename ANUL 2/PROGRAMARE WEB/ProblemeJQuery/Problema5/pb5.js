let intervalId;
let currentIndex = 0;
const items = [];

function showItem(index) {
    for (let i = 0; i < items.length; i++) {
        let a = $(items[i]).find('a').eq(0);
        a.css('pointer-events', 'none');
        $(items[i]).css({
            'opacity': '0',
            'z-index': '0'
        });
    }

    let a = $(items[index]).find('a').eq(0);
    a.css('pointer-events', 'auto');
    $(items[index]).css({
        'opacity': '1',
        'z-index': '1'
    });
}

function nextItem() {
    stopAutoScroll();
    startAutoScroll();
    currentIndex = (currentIndex + 1) % items.length;
    showItem(currentIndex);
}

function prevItem() {
    stopAutoScroll();
    startAutoScroll();
    currentIndex = (currentIndex - 1 + items.length) % items.length;
    showItem(currentIndex);
}

function startAutoScroll() {
    intervalId = setInterval(nextItem, 5000);
}

function stopAutoScroll() {
    clearInterval(intervalId);
}

$(document).ready(function () {
    const prevBtn = $('#prevButton');
    const nextBtn = $('#nextButton');
    let lis = $('#poze').find('ul').eq(0).find('li');
    lis.each(function() {
        items.push(this);
    });
    showItem(currentIndex);
    nextBtn.on('click', nextItem);
    prevBtn.on('click', prevItem);
    startAutoScroll();
});
