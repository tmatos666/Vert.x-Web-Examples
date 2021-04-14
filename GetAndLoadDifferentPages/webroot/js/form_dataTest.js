/* global fetch */

function atletas(){
    window.location.assign("/alunos/"+(Math.floor(Math.random() * 10) + 1));
}

window.addEventListener("load", function () {
    var data = new FormData();
    data.append( "teste", "teste1" );

    fetch('/testData', {
        method: 'POST',
        body: data
    })
    .then((res) => {
        if (res.status === 200)
            return res.json();
        else
            throw Error("Erro no servidor!!");
    })
    .then((data) => {
        data.title = 'Testing';
    })
    .catch((err) => console.log(err));
});