/*
 *Tochangethislicenseheader,chooseLicenseHeadersinProjectProperties.
 *Tochangethistemplatefile,chooseTools|Templates
 *andopenthetemplateintheeditor.
 */


function enviar() {
    window.location.assign("/alunos");
}
function clickButton2() {
    window.location.assign("/page2");
}

function clickButton3() {
    window.location.assign("/");
}

function clickButton() {

//    var myObj;
//    myObj = {"name": "John", "email": "teste@gmail.com"};

    fetch('/novoAluno', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify({"name": "John", "email": "teste@gmail.com","id":1})
    })
            .then((res) => {
                if (res.status === 200)
                    return res.json();
                else
                    throw Error("Erro no servidor!!");
            })
            .then((data) => {
                let li = '<tr><th>Numero</th><th>Name</th><th>Email</th></tr>';
                li = li + '<tr><td>' + data.numero + '</td><td>' + data.nome + '</td><td>' +
                        data.email + '</td></tr>';
                document.getElementById("resposta").innerHTML = li;
            })
            .catch((err) => console.log(err));
}

function submeterForm() {
//    alert("yep");
    let form = document.getElementById('form1');
    let formdata = new FormData(form);
    fetch('/alunos', {
        method: 'POST',
        body: formdata
    })
            .then((res) => {
                if (res.status === 200)
                    return res.json();
                else
                    throw Error("Erro no servidor!!");
            })
            .then((data) => {
                let li = '<tr><th>Numero</th><th>Name</th><th>Email</th></tr>';
                li = li + '<tr><td>' + data.numero + '</td><td>' + data.nome + '</td><td>' +
                        data.email + '</td></tr>';
                document.getElementById("resposta").innerHTML = li;
            })
            .catch((err) => console.log(err));
}






