const form = document.getElementById("registerForm");

form.addEventListener("submit", async (e) =>{
    //impede que o usuario tente dar refresh na pagina
    e.preventDefault();
    console.log("js funcionando");
    const formData = new FormData(form);

    const data = Object.fromEntries(formData.entries());

    const response = await fetch("http://localhost:8080/auth/register",{
        method: "POST",
        headers:{
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    });

    if(!response.ok){
        alert("Erro ao cadastrar!")
        return;
    }

    alert("Cadastro realizado com sucesso!");
    window.location.href = "login.html";

})