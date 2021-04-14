/* global fetch */

function enviar() {
    window.location.assign("/");
}

window.addEventListener("load", function () {
    let body = document.querySelector('title');
    let h1 = document.querySelector('h1');
    let table = document.getElementById('table');


    fetch('/alunos', {
        method: 'POST'
    })
            .then((res) => {
                if (res.status === 200)
                    return res.json();
                else
                    throw Error("Erro no servidor!!");
            })
            .then((data) => {
                data.title = 'Testing';
                h1.innerHTML = data.title;
                body.innerHTML = data.title;

                var s = 2;
                table.innerHTML = "";
                for (j = 1; j <= s; j++) {
                    table.innerHTML += "<div class='column' style='background-color:#aaa;'>" +
                            "<h2>Column " + j + "</h2>" +
                            "<p>" + data.nome + " -- " + data.numero + "</p>" +
                            "</div>";
                }
            })
            .catch((err) => console.log(err));
});