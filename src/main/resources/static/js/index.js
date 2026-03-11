document.addEventListener("DOMContentLoaded", function(){

    const user = localStorage.getItem("user");

    if (user != null) {
        
        const buttonUserArea = document.getElementById("userArea");
        const buttonDashboard = document.getElementById("dashboard");

        buttonUserArea.style.display = "none";
        buttonDashboard.style.display = "block";
    }

});