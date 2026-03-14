const content = document.getElementById("contentArea");
let worksheets = [];

// ─── Inicialização ────────────────────────────────────────────────
getUserWorksheet();

document.getElementById("btnView").addEventListener("click", function () {
    updateActiveButton(this);
    renderWorksheetList();
});

document.getElementById("btnProfile").addEventListener("click", function () {
    updateActiveButton(this);
    renderProfileArea();
});

function updateActiveButton(button) {
    document.querySelectorAll(".nav-btn").forEach(btn => btn.classList.remove("active"));
    button.classList.add("active");
}

// ─── Perfil ───────────────────────────────────────────────────────
async function renderProfileArea() {
    const user = await getUserInfo();
    if (!user) return;

    content.innerHTML = `
        <h2>Meu Perfil</h2>
        <div class="profile-area">
            <div class="profile-info">
                <img id="profilePhoto" class="pfpPhoto" src="" alt="Foto de perfil">
                <p>Username: <span id="userName"></span></p>
                <p>Email: <span id="userEmail"></span></p>
            </div>
        </div>
    `;

    const img = document.getElementById("profilePhoto");
    img.src = user.photoUrl ? user.photoUrl : "";
    document.getElementById("userName").textContent = user.username;
    document.getElementById("userEmail").textContent = user.email;
}

async function getUserInfo() {
    try {
        const userRaw = localStorage.getItem("user");
        if (!userRaw) return null;
        const user = JSON.parse(userRaw);
        const response = await fetch(`/users/findById/${user.id}`);
        if (!response.ok) throw new Error("Erro ao buscar usuário");
        return await response.json();
    } catch (error) {
        console.error(error);
        return null;
    }
}

// ─── Lista de fichas ─────────────────────────────────────────────
function renderWorksheetList() {
    if (worksheets.length === 0) {
        content.innerHTML = `
            <h2>Minhas Fichas</h2>
            <p>Nenhuma ficha disponível.</p>
        `;
        return;
    }

    let html = `<h2>Minhas Fichas</h2><div class="cards-container">`;

    worksheets.forEach(ws => {
        let divisionsHTML = ws.divisions.map(d => `<li>${d.name}</li>`).join("");
        html += `
            <div class="worksheet-card">
                <div class="worksheet-header">
                    <h3>${ws.name}</h3>
                </div>
                <div class="worksheet-body">
                    <p class="division-title">Divisões:</p>
                    <ul class="division-list">${divisionsHTML}</ul>
                </div>
            </div>
        `;
    });

    html += `</div>`;
    content.innerHTML = html;
}

async function getUserWorksheet() {
    const userRaw = localStorage.getItem("user");  
    if (!userRaw) {
        console.warn("getWorksheets: no user in localStorage");
        return;
    }
    const user = JSON.parse(userRaw);
    const userId = user.id;      
    console.log("USER ID: "+ userId);       

    if (!userId) {
        console.warn("getWorksheets: userId not found");
        return;
    }

    try {
        const response = await fetch(`/worksheets/findByUser/${userId}`);
        if (!response.ok) {
            console.error("Erro ao buscar fichas");
            return;
        }
        const worksheetsList = await response.json();
        console.log("Tipo:", typeof worksheetsList);
        console.log("Valor:", worksheetsList);
        worksheets.push(worksheetsList);
        renderWorksheetList();
    } catch (error) {
        console.error("Erro ao criar a requisição:", error);
    }
}

// ─── Logout ───────────────────────────────────────────────────────
function logout() {
    localStorage.removeItem("user");
    window.location.href = "index.html";
}
