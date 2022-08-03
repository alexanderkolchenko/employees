let table = document.getElementById("table")

let default_sort_buttons = document.getElementsByClassName("default_sort_buttons");
let ascending_sort_buttons = document.getElementsByClassName("ascending_sort_buttons");
let descending_sort_buttons = document.getElementsByClassName("descending_sort_buttons");

let city_checkboxes = document.getElementsByClassName("city_checkbox")
let position_checkboxes = document.getElementsByClassName("position_checkbox")

//number of column to display default sort icon when you click on other column
let number_of_columns = -1;

function showCheckboxes(id) {
    let checkboxes = document.getElementById(id);
    if (checkboxes.style.display === "block") {
        checkboxes.style.display = "none";
    } else {
        checkboxes.style.display = "block";
    }
}

function init_select_boxes(id, id2) {
    document.getElementById(id).addEventListener('click', e => showCheckboxes(id2))
}

init_select_boxes("select_box_position", "position_list")
init_select_boxes("select_box_city", "city_list")


let url                 = document.location.href;
let u                   = new URL(url);
let pages               = document.getElementsByClassName("pages");
let arr_columns         = ["name", "surname", "position", "email", "city"]
let current_sort        = u.searchParams.get("sorting");
let current_sort_column;

//init sorting picture on the first time
if (current_sort === null || current_sort === "") {
    current_sort_column = "id"
    current_sort = "asc"
} else {
    current_sort_column = current_sort.substring(0, current_sort.lastIndexOf('_'))
    current_sort = current_sort.substring(current_sort.lastIndexOf('_') + 1)
}

//disable link of current page
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
            console.log(count_rows_from_request)
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


let current_page = "?page=1&sorting=id_asc"
let request_url = location.href + current_page

//init checkbox
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
            clear_table()
            sendInfo(href);
        })
    }
}

init_checkboxes(city_checkboxes, "city")
init_checkboxes(position_checkboxes, "position")

function show_default_sort_icon(index) {
    if (index >= 0) {
        default_sort_buttons[index].classList.remove("hidden_element");
        ascending_sort_buttons[index].classList.add("hidden_element");
        descending_sort_buttons[index].classList.add("hidden_element");
    }
}

function sort_event(event, index, buttons, sort_type) {
    event.currentTarget.classList.add("hidden_element");
    buttons[index].classList.remove("hidden_element")
    if (index !== number_of_columns) {
        show_default_sort_icon(number_of_columns)
        number_of_columns = index
    }
    let href = new URL(request_url)
    href.searchParams.set("sorting", sort_type)
    request_url = href;
    clear_table()
    sendInfo(href)
}


//init sorting buttons

for (let i = 0; i < default_sort_buttons.length; i++) {
    default_sort_buttons[i].addEventListener('click', e => {
        e.preventDefault()
        let c_url = new URL(e.currentTarget.parentNode.href)
        sort_event(e, i, ascending_sort_buttons, c_url.searchParams.get("sorting"))
    })
    ascending_sort_buttons[i].addEventListener('click', e => {
        e.preventDefault()
        let c_url = new URL(e.currentTarget.parentNode.href)
        sort_event(e, i, descending_sort_buttons, c_url.searchParams.get("sorting"))
    })
    descending_sort_buttons[i].addEventListener('click', e => {
        e.preventDefault()
        let c_url = new URL(e.currentTarget.parentNode.href)
        sort_event(e, i, default_sort_buttons, c_url.searchParams.get("sorting"))
    })
}


//init input fields
let name_field = document.getElementById('employee_name')
let surname_field = document.getElementById('employee_surname')
let email_field = document.getElementById('employee_email')
let salary_field_min = document.getElementById('employee_salary_min')
let salary_field_max = document.getElementById('employee_salary_max')
let hire_date_field_min = document.getElementById('employee_hire_date_min')
let hire_date_field_max = document.getElementById('employee_hire_date_max')

function init_input_fields(field, label) {
    let timer = null
    field.addEventListener('keyup', e => {
        clearTimeout(timer)
        timer = setTimeout(function () {
            let href = new URL(request_url);
            if (field.value !== "") {
                href.searchParams.set(label, "%" + field.value + "%");
            } else {
                href.searchParams.delete(label)
            }
            request_url = href;
            clear_table()
            sendInfo(href)
        }, 800)
    })
}

function init_input_fields_salary(field) {
    let timer = null
    field.addEventListener('keyup', e => {
        clearTimeout(timer)
        timer = setTimeout(function () {
            let href = new URL(request_url);
            let min = salary_field_min.value
            let max = salary_field_max.value
            href.searchParams.set("salary", min + ":" + max);
            request_url = href;

            clear_table()
            sendInfo(href)
        }, 800)
    })
}

function init_input_fields_dates(field) {
    let timer = null
    field.addEventListener('change', e => {
        clearTimeout(timer)
        timer = setTimeout(function () {
            let href = new URL(request_url);
            let min = hire_date_field_min.value
            let max = hire_date_field_max.value
            href.searchParams.set("hire_date", min + ":-:" + max);
            request_url = href;

            clear_table()
            sendInfo(href)
        }, 800)
    })
}

init_input_fields_salary(salary_field_min)
init_input_fields_salary(salary_field_max)
init_input_fields_dates(hire_date_field_max)
init_input_fields_dates(hire_date_field_min)
init_input_fields(surname_field, "surname")
init_input_fields(name_field, "name")
init_input_fields(email_field, "email")

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

        clear_table()
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

function clear_table() {
    let rows = table.getElementsByTagName("tbody")[0].getElementsByTagName('tr')
    //todo change 20
    for (let i = 1; i <= 20; i++) {
        try {
            rows[1].remove()
        } catch (e) {
        }
    }
}
