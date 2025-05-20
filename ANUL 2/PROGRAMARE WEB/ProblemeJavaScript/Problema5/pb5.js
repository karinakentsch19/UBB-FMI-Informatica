let intervalId;
let currentIndex = 0;
const items = [];

function showItem(index) {
    for (let i = 0; i < items.length; i++){
        let a = items[i].getElementsByTagName('a')[0];
        a.style.pointerEvents = 'none';
        items[i].style.opacity = '0';
        items[i].style.zIndex = '0';
    }

    let a = items[index].getElementsByTagName('a')[0];
    a.style.pointerEvents = 'auto';
    items[index].style.opacity = '1';
    items[index].style.zIndex = '1';
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

document.addEventListener('DOMContentLoaded', function (){
    const prevBtn = document.getElementById('prevButton');
    const nextBtn = document.getElementById('nextButton');
    let lis = document.getElementById('poze').getElementsByTagName('ul')[0].getElementsByTagName('li');
    for (let i = 0; i < lis.length; i++){
        items.push(lis[i]);
    }
    showItem(currentIndex);
    nextBtn.addEventListener('click', nextItem);
    prevBtn.addEventListener('click', prevItem);
    startAutoScroll();
})