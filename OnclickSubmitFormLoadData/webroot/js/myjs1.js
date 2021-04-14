/*
 *Tochangethislicenseheader,chooseLicenseHeadersinProjectProperties.
 *Tochangethistemplatefile,chooseTools|Templates
 *andopenthetemplateintheeditor.
 */


function enviar() {
    window.location.assign("/alunos");
}

function submeterForm() {
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






