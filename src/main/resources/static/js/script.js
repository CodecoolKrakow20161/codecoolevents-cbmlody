function searchFilter() {
    var option, input, searchBarFilter, selectFilter, table, tr, tdName, tdCat, i;
    option = document.getElementById("cat-filter");
    input = document.getElementById("user-input");
    searchBarFilter = input.value.toUpperCase();
    selectFilter = option.value;
    table = document.getElementById("to-search");
    tr = table.getElementsByTagName("tr");


    for (i = 0; i < tr.length; i++) {
        tdName = tr[i].getElementsByTagName("td")[0];
        tdCat = tr[i].getElementsByTagName("td")[3];
        if (tdName && tdCat) {
            if (((searchBarFilter == "") && (selectFilter == "All")) ||
                ((tdName.innerHTML.toUpperCase().indexOf(searchBarFilter) > -1) && (tdCat.innerHTML.indexOf(selectFilter) > -1)) ||
                ((searchBarFilter == "") && (tdCat.innerHTML.indexOf(selectFilter) > -1)) ||
                ((tdName.innerHTML.toUpperCase().indexOf(searchBarFilter) > -1) && (selectFilter == "All"))) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}