const form = document.getElementById("loginForm");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    console.log("LOGIN SENDO EXECUTADO");

    const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    if (!response.ok) {
        alert("Login inválido");
        return;
    }else{
        alert("Login efetuado com sucesso!")
    }

    const data = await response.json();

    localStorage.setItem("user", JSON.stringify(data));

    if (data.role === "TRAINER") {
        window.location.href = "../dashboard.html";
    } else {
        window.location.href = "../dashboard.html";
    }
});