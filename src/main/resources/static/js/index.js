document.addEventListener("DOMContentLoaded", function(){
    const buttonUserArea = document.getElementById("userArea");
    const buttonDashboard = document.getElementById("trainerArea");
    const loginButton = document.getElementById("loginBtn");
    const registerButton = document.getElementById("registerBtn");

    if (buttonUserArea && buttonDashboard) {
        const userRaw = localStorage.getItem("user");
        const user = JSON.parse(userRaw);
        console.log("USER RAW: "+ userRaw);
        console.log("USER CONVERTED: "+ user);

        if(userRaw){
            loginButton.style.display = "none";
            registerButton.style.display = "none";
            const role = user.role;
            if(role == "TRAINER"){
                console.log("O USUARIO É TREINADOR");
                console.log(role);
                buttonUserArea.style.display = "none";
                buttonDashboard.style.display = "block";
            }else if(role == "MEMBER"){
                console.log("O USUARIO É MEMBRO");
                console.log(role);
                buttonUserArea.style.display = "block";
                buttonDashboard.style.display = "none";
            }else{
                console.log("O USUARIO É ADMIN")
                console.log(role);
            }
        }else{
            buttonDashboard.style.display = "none";
            buttonUserArea.style.display = "none";
            console.log("NENHUM USUARIO LOGGADO");
        }
    } else {
      console.error("Elementos não encontrados no DOM.");
    }
});