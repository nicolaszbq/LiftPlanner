if (!localStorage.getItem("token")) {
    window.location.href = "index.html";
}else if(!localStorage.getItem("user") && localStorage.getItem("user").role === "MEMBER"){
    window.location.href = "index.html";
}

const content = document.getElementById("contentArea");
let worksheets = [];

async function initUserArea() {
    worksheets.length = 0;
    await getUserWorksheet();
}

initUserArea();

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

async function returnToInitalPage() {
    window.location.href = "index.html";
}

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
    formData.append("file", file);

    const res = await fetch(`/users/uploadPhoto/${user.id}`, {
        method: "POST",
        body: formData
    });

    if (!res.ok) {
        alert("Falha ao enviar foto");
        return;
    }

    const photoUrl = await res.text();
    document.getElementById("profilePhoto").src = photoUrl;

    user.photoUrl = photoUrl;
    localStorage.setItem("user", JSON.stringify(user));
}

async function getUserInfo() {
    try {
        const userRaw = localStorage.getItem("user");
        if (!userRaw) return null;

        const user = JSON.parse(userRaw);
        const response = await fetch(`/users/findById/${user.id}`);
        if (!response.ok) throw new Error("Erro ao buscar usuario");

        return await response.json();
    } catch (error) {
        console.error(error);
        return null;
    }
}

function renderWorksheetList() {
    document.getElementById("viewModal").classList.remove("active");
    if (worksheets.length === 0) {
        content.innerHTML = `
            <h2>Minhas Fichas</h2>
            <p>Nenhuma ficha disponivel.</p>
        `;
        return;
    }

    let html = `<h2>Minhas Fichas</h2><div class="cards-container">`;

    worksheets.forEach(ws => {
        const divisionsHTML = (ws.divisions || []).map(d => `<li>${d.name}</li>`).join("");
        html += `
            <div class="worksheet-card" data-id="${ws.id}">
                <div class="worksheet-header">
                    <h3>${ws.name}</h3>
                </div>
                <div class="worksheet-body">
                    <p class="division-title">Divisoes:</p>
                    <ul class="division-list">${divisionsHTML}</ul>
                </div>
            </div>
        `;
    });

    html += `</div>`;
    content.innerHTML = html;

    content.querySelectorAll(".worksheet-card").forEach(card => {
        card.addEventListener("click", () => {
            const ws = worksheets.find(w => String(w.id) === card.dataset.id);
            if (ws) renderWorksheetViewModal(ws);
        });
    });
}

function toEmbedUrl(videoUrl) {
    if (!videoUrl) return "";

    try {
        const url = new URL(videoUrl);
        if (url.hostname.includes("youtu.be")) {
            return `https://www.youtube.com/embed/${url.pathname.replace("/", "")}`;
        }

        const videoId = url.searchParams.get("v");
        return videoId ? `https://www.youtube.com/embed/${videoId}` : "";
    } catch (error) {
        console.warn("URL de video invalida:", videoUrl, error);
        return "";
    }
}

function renderWorksheetViewModal(ws) {
    const modal = document.getElementById("viewModal");
    const modalContent = document.getElementById("viewModalContent");
    const divisions = ws.divisions || [];

    modalContent.innerHTML = `
        <div class="worksheet-modal-header">
            <div>
                <p class="modal-eyebrow">Sua ficha em etapas</p>
                <h1 id="worksheetName">${ws.name}</h1>
                <p class="worksheet-modal-subtitle">Abra uma divisao para ver os exercicios e expanda cada item para acessar os detalhes.</p>
            </div>
            <button class="closeViewModalBtn" type="button" onclick="closeViewModal()">Fechar</button>
        </div>
        <div id="divisionsArea" class="worksheet-division-list"></div>
    `;

    const divisionsArea = document.getElementById("divisionsArea");

    if (divisions.length === 0) {
        divisionsArea.innerHTML = `<p class="empty-state-message">Essa ficha ainda nao possui divisoes cadastradas.</p>`;
    } else {
        divisions.forEach(division => {
            divisionsArea.appendChild(createDivisionElement(division));
        });
    }

    modal.classList.add("active");
    modal.onclick = (e) => {
        if (e.target === modal) modal.classList.remove("active");
    };
}

async function closeViewModal() {
    const modal = document.getElementById("viewModal");
    modal.classList.remove("active");
}

function createDivisionElement(division) {
    const divisionCard = document.createElement("section");
    divisionCard.className = "division-card";

    const headerButton = document.createElement("button");
    headerButton.type = "button";
    headerButton.className = "division-toggle";
    headerButton.setAttribute("aria-expanded", "false");

    const divisionMeta = document.createElement("div");
    divisionMeta.className = "division-meta";

    const divisionType = document.createElement("span");
    divisionType.className = "division-chip";
    divisionType.textContent = division.type || "Divisao";

    const divisionTitle = document.createElement("strong");
    divisionTitle.className = "division-name";
    divisionTitle.textContent = division.name || division.type || "Sem nome";

    const divisionCount = document.createElement("span");
    divisionCount.className = "division-count";
    const exercises = division.exercises || [];
    divisionCount.textContent = `${exercises.length} exercicio${exercises.length === 1 ? "" : "s"}`;

    const toggleIcon = document.createElement("span");
    toggleIcon.className = "accordion-icon";
    toggleIcon.setAttribute("aria-hidden", "true");
    toggleIcon.textContent = "+";

    divisionMeta.append(divisionType, divisionTitle, divisionCount);
    headerButton.append(divisionMeta, toggleIcon);

    const exerciseArea = document.createElement("div");
    exerciseArea.className = "exerciseArea";
    exerciseArea.style.display = "none";

    if (exercises.length === 0) {
        const emptyState = document.createElement("p");
        emptyState.className = "empty-state-message";
        emptyState.textContent = "Nenhum exercicio cadastrado nesta divisao.";
        exerciseArea.appendChild(emptyState);
    } else {
        exercises.forEach(exercise => {
            exerciseArea.appendChild(createExerciseElement(exercise));
        });
    }

    headerButton.addEventListener("click", () => {
        const isExpanded = headerButton.getAttribute("aria-expanded") === "true";
        headerButton.setAttribute("aria-expanded", String(!isExpanded));
        divisionCard.classList.toggle("is-open", !isExpanded);
        exerciseArea.style.display = isExpanded ? "none" : "flex";
    });

    divisionCard.append(headerButton, exerciseArea);
    return divisionCard;
}

function createExerciseElement(exercise) {
    const exerciseCard = document.createElement("article");
    exerciseCard.className = "exercise-container";

    const headerButton = document.createElement("button");
    headerButton.type = "button";
    headerButton.className = "exercise-toggle";
    headerButton.setAttribute("aria-expanded", "false");

    const exerciseTitle = document.createElement("span");
    exerciseTitle.className = "exercise-name";
    exerciseTitle.textContent = exercise.name || "Exercicio sem nome";

    const exerciseBadge = document.createElement("span");
    exerciseBadge.className = "exercise-summary";
    exerciseBadge.textContent = `${exercise.series || 0} x ${exercise.reps || 0}`;

    const toggleIcon = document.createElement("span");
    toggleIcon.className = "accordion-icon";
    toggleIcon.setAttribute("aria-hidden", "true");
    toggleIcon.textContent = "+";

    headerButton.append(exerciseTitle, exerciseBadge, toggleIcon);

    const details = document.createElement("div");
    details.className = "exercise-details";
    details.style.display = "none";

    const metrics = document.createElement("div");
    metrics.className = "exercise-metrics";
    metrics.appendChild(createMetricItem("Series", exercise.series || "-"));
    metrics.appendChild(createMetricItem("Repeticoes", exercise.reps || "-"));

    const descriptionBlock = document.createElement("div");
    descriptionBlock.className = "exercise-description";

    const descriptionLabel = document.createElement("h4");
    descriptionLabel.textContent = "Descricao";

    const descriptionText = document.createElement("p");
    descriptionText.textContent = exercise.description || "Sem orientacoes adicionais para este exercicio.";

    descriptionBlock.append(descriptionLabel, descriptionText);
    details.append(metrics, descriptionBlock);

    const videoUrl = toEmbedUrl(exercise.videoUrl);
    if (videoUrl) {
        const videoBlock = document.createElement("div");
        videoBlock.className = "exercise-video";

        const videoLabel = document.createElement("h4");
        videoLabel.textContent = "Exemplo";

        const iframe = document.createElement("iframe");
        iframe.src = videoUrl;
        iframe.className = "video-iframe";
        iframe.loading = "lazy";
        iframe.allow = "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture";
        iframe.allowFullscreen = true;
        iframe.referrerPolicy = "strict-origin-when-cross-origin";
        iframe.title = `Video demonstrativo do exercicio ${exercise.name || ""}`.trim();

        videoBlock.append(videoLabel, iframe);
        details.appendChild(videoBlock);
    }

    headerButton.addEventListener("click", () => {
        const isExpanded = headerButton.getAttribute("aria-expanded") === "true";
        headerButton.setAttribute("aria-expanded", String(!isExpanded));
        exerciseCard.classList.toggle("is-open", !isExpanded);
        details.style.display = isExpanded ? "none" : "flex";
    });

    exerciseCard.append(headerButton, details);
    return exerciseCard;
}

function createMetricItem(label, value) {
    const metric = document.createElement("div");
    metric.className = "exercise-metric";

    const metricLabel = document.createElement("span");
    metricLabel.className = "exercise-metric-label";
    metricLabel.textContent = label;

    const metricValue = document.createElement("strong");
    metricValue.className = "exercise-metric-value";
    metricValue.textContent = value;

    metric.append(metricLabel, metricValue);
    return metric;
}

async function getUserWorksheet() {
    const userRaw = localStorage.getItem("user");
    if (!userRaw) {
        console.warn("getWorksheets: no user in localStorage");
        return;
    }

    const user = JSON.parse(userRaw);
    const userId = user.id;

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
        worksheets.length = 0;

        if (Array.isArray(worksheetsList)) {
            worksheets.push(...worksheetsList);
        } else if (worksheetsList) {
            worksheets.push(worksheetsList);
        }

        renderWorksheetList();
    } catch (error) {
        console.error("Erro ao criar a requisicao:", error);
    }
}

function logout() {
    localStorage.removeItem("user");
    window.location.href = "index.html";
}
