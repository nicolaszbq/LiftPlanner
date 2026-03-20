const content = document.getElementById("contentArea");
let worksheets = [];
console.log(worksheets);

// ─── Inicialização ────────────────────────────────────────────────
worksheets.length =0;
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
    document.getElementById("viewModal").classList.remove("active");
    const user = await getUserInfo();
    if (!user) return;

    content.innerHTML = `
        <h2>Meu Perfil</h2>
        <div class="profile-area">
            <div class="profile-info">
                <img id="profilePhoto" class="pfpPhoto" src="" alt="Foto de perfil">
                <input id="photoInput" type="file" accept="image/*" hidden>
                <p>Username: <span id="userName"></span></p>
                <p>Email: <span id="userEmail"></span></p>
            </div>
        </div>
    `;
    

    const img = document.getElementById("profilePhoto");
    const input = document.getElementById("photoInput");

    img.addEventListener("click", () => input.click());
    input.addEventListener("change", onPhotoSelected);

    img.src = user.photoUrl ? user.photoUrl : "";
    document.getElementById("userName").textContent = user.username;
    document.getElementById("userEmail").textContent = user.email;
}

async function onPhotoSelected(e) {
  const file = e.target.files?.[0];
  if (!file) return;

  const user = JSON.parse(localStorage.getItem("user"));
  const formData = new FormData();
  formData.append("file", file); // nome "file" precisa bater com @RequestParam("file")

  const res = await fetch(`/users/uploadPhoto/${user.id}`, {
    method: "POST",
    body: formData
  });

  if (!res.ok) {
    alert("Falha ao enviar foto");
    return;
  }

  const photoUrl = await res.text(); // endpoint retorna string
  document.getElementById("profilePhoto").src = photoUrl;

  // opcional: manter localStorage atualizado
  user.photoUrl = photoUrl;
  localStorage.setItem("user", JSON.stringify(user));
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

//



// ─── Lista de fichas ─────────────────────────────────────────────
function renderWorksheetList() {
    document.getElementById("viewModal").classList.remove("active");
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
            <div class="worksheet-card" data-id="${ws.id}">
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

    content.querySelectorAll(".worksheet-card").forEach(card => {
        card.addEventListener("click", () => {
            const ws = worksheets.find(w => w.id === card.dataset.id);
            renderWorksheetViewModal(ws);
        });
    });
}

function toEmbedUrl(videoUrl){
    console.log(videoUrl);
    const url = new URL(videoUrl);
    const videoId = url.searchParams.get("v");  // retorna "abc123"
    const embedUrl = `https://www.youtube.com/embed/${videoId}`;
    return embedUrl;
}

function renderWorksheetViewModal(ws){
    const modal = document.getElementById("viewModal");
    const modalContent = document.getElementById("viewModalContent");

    modalContent.innerHTML = `
        <button class="closeViewModalBtn" onclick="closeViewModal()">Fechar</button>
        <p>Sua ficha!</p>
        <h1 id="worksheetName">${ws.name}</h1>
        <div id="divisionsArea">
        
        </div>
    `;

    const divisionsArea = document.getElementById("divisionsArea");
    ws.divisions.forEach(div => {
        const divEl = createDivisionElement(div.type, div.name);
        divisionsArea.appendChild(divEl);
        const exerciseArea = divEl.querySelector(".exerciseArea");
        (div.exercises || []).forEach(ex => {
            exerciseArea.appendChild(createExerciseElement(ex.name, ex.reps, ex.series, ex.description, ex.videoUrl));
        });
    });
    modal.classList.add("active");
    modal.onclick = (e) => { if (e.target === modal) modal.classList.remove("active"); }
}
async function closeViewModal() {
    const modal = document.getElementById("viewModal");
    modal.classList.remove("active");
}

function createDivisionElement(type, name) {
    const divEl = document.createElement("div");
    divEl.classList.add("division");
    divEl.innerHTML = `
        <h3>${type}</h3>
        <h2>${name}</h2>
        <div class="exerciseArea"></div>
    `;
    return divEl;
}

function createExerciseElement(name, reps, series, description, videoUrl) {
    const exEl = document.createElement("div");
    exEl.classList.add("exercise-container");
    const url = toEmbedUrl(videoUrl);
    exEl.innerHTML = `
        <p>${name}</p>
        <p>Repetições: ${reps}</p>
        <p>Series: ${series}</p>
        <p>Como fazer? </p>
        <p>${description}</p>
        <p>Exemplo: </p>
        <iframe src="${url}" frameborder="0" class="video-iframe"></iframe>
    `;
    return exEl;
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
        if(worksheets === null){
            //nao faça nada
        }else{
            worksheets.length = 0;
            worksheets.push(worksheetsList);
        }
        
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
