function move(dropDown) {
    let selectedOption = $('select[name="' + dropDown.name + '"] option:selected').remove().clone();

    let theOtherDropDown;
    if (dropDown.name === 'fructe'){
        theOtherDropDown = $('select[name="legume"]');
    } else {
        theOtherDropDown = $('select[name="fructe"]');
    }

    theOtherDropDown.append(selectedOption);
}
