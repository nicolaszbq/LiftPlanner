const form = document.getElementById("photoForm");

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const userId = localStorage.getItem("pendingUserId");
    const fileInput = document.getElementById("photoInput");
    console.log("USER ID: "+ userId);
    // FormData encapsula o arquivo automaticamente
    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    const response = await fetch(`http://localhost:8080/users/uploadPhoto/${userId}`, {
        method: "POST",
        body: formData
        // ← NÃO coloca Content-Type aqui. O browser faz isso sozinho.
    });

    if (!response.ok) {
        alert("Erro ao fazer upload da foto.");
        return;
    }

    localStorage.removeItem("pendingUserId"); // limpa o id temporário
    window.location.href = "../auth/login.html";
});

