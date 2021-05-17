function uploadImage() {
   var formData = new FormData(document.getElementById('form1'));

    fetch('/loadimage', {
        method: 'POST',  
        body: formData
    })
            .then((res) => {
                if (res.status === 200)
                    return res.json();
                else
                    throw Error("Erro no servidor!!");
            })
            .then((data) => {
                let li = '<tr><th>Pasta e Nome do ficheiro (este foi renomeado e colocado na pasta uploads)</th></tr>';
                li = li + '<tr><td>' + data.nome + '</td><td>';
                document.getElementById("resposta").innerHTML = li;
                
                var str = data.nome;
                var res = str.replace("webroot\\", "");
  
                document.getElementById("imgUploaded").src = res;
            })
            .catch((err) => console.log(err));
}






