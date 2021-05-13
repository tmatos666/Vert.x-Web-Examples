function enviar() {
    window.location.assign("/teste");
}

function pedirUmAluno() {
    let id = document.getElementById("valor").value;
    window.location.assign("/aluno/" + id);
}

function getAluno() {
   var pathname = window.location.pathname.split("/");
    let idAluno = pathname[pathname.length-1];;

    fetch('/aluno/' + idAluno, {
        method: 'POST'       
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


function clickButton2() {
    window.location.assign("/page2");
}

function clickButton3() {
    window.location.assign("/");
}





