/**
 *
 *
 *
 *
 *sorting, filter by javascript
 *
 *
 *
 *
 */

let table = document.getElementById("table")

//todo locale compare
function ascending_sort(a, b) {
    return a > b ? 1 : -1
}

function descending_sort(a, b) {
    return a > b ? -1 : 1
}

//todo neutral sort, 1 column

//index - number of column, type_sort - ascending_sort or descending_sort
function sort_rows(index, type_sort) {
    let rows = Array.from(table.rows).slice(1).sort((a, b) => {
        return type_sort(a.cells[index].innerHTML, b.cells[index].innerHTML)
    })
    table.tBodies[0].append(...rows)
}

let default_sort_buttons = document.getElementsByClassName("default_sort_buttons");
let ascending_sort_buttons = document.getElementsByClassName("ascending_sort_buttons");
let descending_sort_buttons = document.getElementsByClassName("descending_sort_buttons");

//number of column to display default sort icon when you click on other column
let number_of_columns = -1;

function show_default_sort_icon(index) {
    if (index >= 0) {
        default_sort_buttons[index].classList.remove("hidden_element");
        ascending_sort_buttons[index].classList.add("hidden_element");
        descending_sort_buttons[index].classList.add("hidden_element");
    }
}

//sorting and changing icons
function sort_event(event, index, buttons, sort_type) {

    event.currentTarget.classList.add("hidden_element");
    buttons[index].classList.remove("hidden_element")
    sort_rows(index + 1, sort_type)

    //save number of column to return default icon
    if (index !== number_of_columns) {
        show_default_sort_icon(number_of_columns)
        number_of_columns = index
    }
}

//init buttons
/*
for (let i = 0; i < default_sort_buttons.length; i++) {
    default_sort_buttons[i].addEventListener('click', (e) => {
        sort_event(e, i, ascending_sort_buttons, ascending_sort)
    });
    ascending_sort_buttons[i].addEventListener('click', (e) => {
        sort_event(e, i, descending_sort_buttons, descending_sort)
    })
}
for (let i = 0; i < descending_sort_buttons.length; i++) {
    descending_sort_buttons[i].addEventListener('click', (e) => {
        sort_event(e, i, ascending_sort_buttons, ascending_sort)
    })
}
*/

/*
    select with checkbox option
 */

//todo divide function
let expanded = false;

function showCheckboxes(id) {
    let checkboxes = document.getElementById(id);
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}

/*
 *  filter table
 */


//filter by checkbox
let rows = Array.from(table.rows).slice(1)

function filter_rows_by_checkbox(column, checked, form) {

    let hidden_class = "hidden_" + form.slice(5)

    if (checked.length > 0) {
        for (let i = 0; i < rows.length; i++) {
            rows[i].classList.add(hidden_class)
            for (let j = 0; j < checked.length; j++) {
                if (rows[i].cells[column].innerHTML === checked[j].value) {
                    rows[i].classList.remove(hidden_class)
                }
            }
        }
    } else {
        for (let i = 0; i < rows.length; i++) {
            rows[i].classList.remove(hidden_class)
        }
    }
}

let city_checkboxes = document.getElementsByClassName("city_checkbox")
let position_checkboxes = document.getElementsByClassName("position_checkbox")

function init_checkboxes_js(checkboxes, column, form) {
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].onchange = function () {
            let checked = document.getElementById(form).querySelectorAll("input[type=checkbox]:checked");
            filter_rows_by_checkbox(column, checked, form);
        }
    }
}

/*
init_checkboxes_js(city_checkboxes, 5, "form_city");
init_checkboxes_js(position_checkboxes, 3, "form_position")*/


//filter by typing

let tr = table.getElementsByTagName("tr")
let input_name = document.getElementById("employee_name")
let input_surname = document.getElementById("employee_surname")
let input_email = document.getElementById("employee_email")

function filter_rows_by_typing(input, column) {
    let td;
    let text_value;
    let str = input.value.toUpperCase();
    for (let i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[column];
        if (td) {
            text_value = td.textContent || td.innerText
            if (text_value.toUpperCase().indexOf(str) > -1) {
                tr[i].style.display = ""
            } else {
                tr[i].style.display = "none"
            }
        }
    }
}

input_name.onkeyup = function () {
    filter_rows_by_typing(input_name, 1)
};
input_surname.onkeyup = function () {
    filter_rows_by_typing(input_surname, 2)
}
input_email.onkeyup = function () {
    filter_rows_by_typing(input_email, 4)
}


/**
 *
 *
 *
 *
 *
 * sorting, filter by jdbc
 *
 *
 *
 *
 *
 */


//disable link of current page
let url = document.location.href;
let u = new URL(url);
let pages = document.getElementsByClassName("pages");
/*
let current_page = u.searchParams.get("page");
if (current_page === null || current_page === "") current_page = "1";

for (let p of pages) {
    if (p.innerHTML === current_page) {
        p.style.textDecoration = "none";
        p.classList.add("disabled_link")
    }
}
*/


let arr_columns = ["name", "surname", "position", "email", "city"]
let current_sort = u.searchParams.get("sorting");
let current_sort_column;
if (current_sort === null || current_sort === "") {
    current_sort_column = "id"
    current_sort = "asc"
} else {
    current_sort_column = current_sort.split("_")[0]
    current_sort = current_sort.split("_")[1]
}
for (let i = 0; i < arr_columns.length; i++) {
    if (current_sort_column === arr_columns[i]) {
        if (current_sort === "asc") {
            default_sort_buttons[i].classList.add("hidden_element");
            ascending_sort_buttons[i].classList.remove("hidden_element");
        } else {
            ascending_sort_buttons[i].classList.add("hidden_element");
            default_sort_buttons[i].classList.add("hidden_element");
            descending_sort_buttons[i].classList.remove("hidden_element");
        }
    }
}

let request;
let count_rows_from_request = 0;

function sendInfo(url) {
    /* let v = document.form_city.querySelectorAll("input[type=checkbox]:checked");
     let url = "index.jsp?";
     // let url = new URL(location.href) + "?"
     for (let x of v.values()) {
         url += "&city=" + x.value;
     }*/

    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }

    try {
        request.onreadystatechange = getInfo;
        request.open("GET", url, true);
        request.send();
    } catch (e) {
        alert("Unable to connect to server");
    }

    function getInfo() {
        if (request.readyState === 4) {
            let result_set = request.responseText;
            let c = result_set.indexOf("id=count_row")

            count_rows_from_request = +result_set.substr(c + 12, 3)
            //todo change 300
            //redraw table
            let begin = result_set.indexOf("<tr>", 300)
            let end = result_set.indexOf("</table>");
            result_set = result_set.slice(begin, end)
            table.tBodies[0].insertAdjacentHTML("beforeend", result_set);
            redraw_links_of_pages()
        }
    }
}

//

//let request_url = "index.jsp?";
let current_page = "?page=1&sorting=id_asc"
let request_url = location.href + current_page

//init checkbox
/*for (let i = 0; i < city_checkboxes.length; i++) {
    city_checkboxes[i].addEventListener("change", e => {
        let v = document.form_city.querySelectorAll("input[type=checkbox]:checked");
        let href = new URL(request_url);
        let param = new URLSearchParams(href.search).getAll("city");

        if (param.length === 0) {
            for (let x of v.values()) {
                href.searchParams.append('city', x.value);
            }
        } else {
            href.searchParams.delete("city")
            for (let x of v.values()) {
                href.searchParams.append('city', x.value);
            }
        }
        request_url = href;
        filter_rows_by_jdbc()
        sendInfo(href);
    })
}*/
function init_checkboxes(checkboxes, form) {
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].addEventListener("change", e => {
            let v = document.getElementById("form_" + form).querySelectorAll("input[type=checkbox]:checked");
            let href = new URL(request_url);
            let param = new URLSearchParams(href.search).getAll(form);

            if (param.length === 0) {
                for (let x of v.values()) {
                    href.searchParams.append(form, x.value);
                }
            } else {
                href.searchParams.delete(form)
                for (let x of v.values()) {
                    href.searchParams.append(form, x.value);
                }
            }
            request_url = href;
            filter_rows_by_jdbc()
            sendInfo(href);
        })
    }
}

init_checkboxes(city_checkboxes, "city")
init_checkboxes(position_checkboxes, "position")

//init page links
for (let i = 0; i < pages.length; i++) {
    pages[0].style.textDecoration = "none"
    pages[i].addEventListener("click", e => {
        e.preventDefault()

        let url = new URL(request_url)
        url.searchParams.set("page", (i + 1).toString())
        request_url = url
        for (let t of pages) {
            t.classList.remove("disabled_link")
            t.style.textDecoration = "underline"
        }
        e.currentTarget.style.textDecoration = "none"
        e.currentTarget.classList.add("disabled_link")

        filter_rows_by_jdbc()
        sendInfo(url);
    })
}

function redraw_links_of_pages() {
    for (let i = 0; i < pages.length; i++) {
        pages[i].classList.remove("hidden_element")
    }
    if (count_rows_from_request !== 0) {
        let p = Math.ceil(count_rows_from_request / 5);
        for (let i = p; i < pages.length; i++) {
            pages[i].classList.add("hidden_element")
        }
    }
}


//todo rename clear
function filter_rows_by_jdbc() {

    let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName('tr')

    function clear_table() {
        //todo change 20
        for (let i = 1; i <= 20; i++) {
            try {
                rows[1].remove()
            } catch (e) {}
        }
    }

    clear_table()
}
