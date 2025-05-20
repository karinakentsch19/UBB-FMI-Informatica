function move(dropDown) {
    let selectedElement = document.querySelector('select[name=' + dropDown.name + ']');
    let selectedIndex = selectedElement.selectedIndex;
    let selectedOption = selectedElement.options[selectedIndex];

    selectedElement.options[selectedIndex].remove();

    let theOtherDropDown;
    if (dropDown.name === 'fructe'){
        theOtherDropDown = document.querySelector('select[name=legume]');
    }
    else{
        theOtherDropDown = document.querySelector('select[name=fructe]');
    }

    theOtherDropDown.options.add(selectedOption)
}