function atletas(){
    window.location.assign("/alunos/"+(Math.floor(Math.random() * 10) + 1));
}


window.addEventListener("load", function (event) {
    let body = document.querySelector('title');
    let h1 = document.querySelector('h1');
    let table = document.querySelector('table');
    table.innerHTML = "<tr><th>Type</th>" +
            "<th>Name</th>" +
            "<th>Status</th>";

    fetch('/', {
        method: 'POST'
    })
    .then((res) => {
        if (res.status === 200)
            return res.json();
        else
            throw Error("Erro no servidor!!");
    })
    .then((data) => {
//        var obj = JSON.parse(data);
//        alert(data);
        h1.innerHTML = data.title;
        body.innerHTML = data.title;

//        var s1 = data.competicoes;
//        var s2 = data.competicoes["1"];
//        var s3 = data.competicoes["1"].Tipo;
//        var s4 = data.competicoes["1"].Nome;
        var s = Object.keys(data.listaAlunos).length;
        console.log(data.listaAlunos);
        for (j = 1; j <= s; j++) {
            table.innerHTML += "</tr><tr><td>" + data.listaAlunos[j].numero + "</td><td>" + data.listaAlunos[j].nome +
                    "</td><td>" + data.listaAlunos[j].email + "</td></tr>";
        }
        console.log("");
    })
    .catch((err) => console.log(err));

});