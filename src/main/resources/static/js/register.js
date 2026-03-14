const form = document.getElementById("registerForm");
let pendingUserId;
form.addEventListener("submit", async (e) =>{
    //impede que o usuario tente dar refresh na pagina
    e.preventDefault();
    console.log("js funcionando");
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    
    console.log("Email: "+ data.email);
    
    console.log("OLD USER ID: "+ pendingUserId);

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
    pendingUserId = await getIdByEmail(data.email);
    console.log("NEW USER ID: "+ pendingUserId);


    localStorage.setItem("pendingUserId", pendingUserId);
    alert("Cadastro realizado com sucesso!");
    window.location.href = "../photoUpload.html";

})

async function getIdByEmail(email) {
        if(!email) return null;
        try{
            const response = await fetch(`/users/getId/${encodeURIComponent(email)}`);
            if(!response || !response.ok){
                console.warn("getIdByEmail: response not ok", response && response.status);
                return null;
            }
            
            const text = await response.text();
            if(!text) return null;
            const id = text.trim().replace(/^"|"$/g, "");
            console.log("getIdByEmail -> id:", id);
            return id;
            
        }catch(error){
            console.error("getIdByEmail error: ", error);
            return null;
        }
    }