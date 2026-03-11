const user = localStorage.getItem("user");

if (user != null) {
    const userData = JSON.parse(user);

    if (userData.role === "member") {
        const buttonUserArea = document.getElementById("userArea");
        const buttonDashboard = document.getElementById("dashboard");

        buttonUserArea.style.display = "none";
        buttonDashboard.style.display = "block";
    } else {
        // mostra dashboard ou outras opções
        const buttonUserArea = document.getElementById("userArea");
        const buttonDashboard = document.getElementById("dashboard");

        buttonUserArea.style.display = "block";
        buttonDashboard.style.display = "none";
    }
}