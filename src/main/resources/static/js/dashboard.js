    const content = document.getElementById("contentArea");

    let selectedMember = null;
    const userv = localStorage.getItem("user");


    // debounce: evita chamar a API a cada tecla
    function debounce(fn, wait){
        let t;
        return function(...args){
            clearTimeout(t);
            t = setTimeout(() => fn.apply(this, args), wait);
        }
    }

    // busca membros no backend e renderiza o resultado
    async function searchMembers(query){
        const container = document.getElementById("memberResults");
        if(!container) return;
        if(!query || query.trim().length === 0){
            container.innerHTML = "";
            selectedMember = null;
            return;
        }

        try{
            const user = localStorage.getItem("user");
            const res = await fetch(`/users/findById/${user.id}`);
            if(!res.ok) return;
            const list = await res.json();
            showMemberResults(list);
        }catch(err){
            console.error("Erro ao buscar membros:", err);
        }
    }

    function showMemberResults(list) {
        const container = document.getElementById("memberResults");
        if(!container) return;
        container.innerHTML = "";
        if(!list || list.length === 0){
            //nao tente arrumar isso, ta totalmente QUEBRADO
            //container.innerHTML = `<div class=\"result-empty\">Nenhum aluno encontrado</div>`;
            return;
        }else{
        }
        list.forEach(u => {
            const div = document.createElement("div");
            div.className = "result-item";
            div.textContent = u.username + (u.email ? ` — ${u.email}` : "");
            div.dataset.id = u.id;
            div.addEventListener("click", () => {
                selectedMember = u;
                const searchEl = document.getElementById("memberSearch");
                if(searchEl) searchEl.value = u.username;
                container.innerHTML = "";
            });
            container.appendChild(div);
        });
    }

    getWorksheets();
    let worksheets = [];

    document.getElementById("btnProfile")
        .addEventListener("click", function(){
            updateActiveButton(this);
            renderProfileArea();
        })

    document.getElementById("btnView")
        .addEventListener("click", function(){
            updateActiveButton(this);
            renderWorksheetList();
        });

    document.getElementById("btnCreate")
        .addEventListener("click", function(){
            updateActiveButton(this);
            renderCreateBuilder();
        });

    function updateActiveButton(button) {
        document.querySelectorAll(".nav-btn").forEach(btn => {
            btn.classList.remove("active");
        });
        button.classList.add("active");
    }
        
    renderWorksheetList();

    async function renderProfileArea(){

    let html = `
        <h2>Meu Perfil</h2>
        <div class="profile-area">
            <div class="profile-info">
                <p>Username: <span id="userName"></span></p>
                <p>Email: <span id="userEmail"></span></p>
            </div>
        </div>
    `;

    content.innerHTML = html;

    const user = await getUserInfo();
    console.log("USER RECEBIDO:", user);
    console.log(user);

    document.getElementById("userName").textContent = user.username;
    document.getElementById("userEmail").textContent = user.email;
}

    // 📌 Render lista de fichas
    function renderWorksheetList(){
        let html = `
        <h2>Minhas Fichas</h2>
        <div class="cards-container">
        `;

        if(worksheets.length === 0){
            html += `<p>Nenhuma ficha criada ainda.</p>`;
            content.innerHTML = html;
            return;
        }

        worksheets.forEach((ws) => {

            let divisionsHTML = "";

            ws.divisions.forEach(div => {
                divisionsHTML += `<li>${div.name}</li>`;
            });

            html += `
        <div class="worksheet-card" data-id="${ws.id}">
            <div class="worksheet-header">
                <h3>${ws.name}</h3>
            </div>
            <div class="worksheet-body">
                <p class="division-title">Divisões:</p>
                <ul class="division-list">
                    ${divisionsHTML}
                </ul>
            </div>
        </div>
        `;
        });

        html += `</div>`;
        content.innerHTML = html;

        // Abre painel de edição ao clicar em um card
        content.querySelectorAll(".worksheet-card").forEach(card => {
            card.addEventListener("click", () => {
                const ws = worksheets.find(w => w.id === card.dataset.id);
                if (ws) renderEditBuilder(ws);
            });
        });
    }


    async function getExercises() {
        try{
            const response = await fetch("/exercises/getAllExercises");

            if(!response){
                alert("Erro ao fazer a requisição!");
            }
            const exerciseList = await response.json();
        }catch(error){console.log("Erro ao salvar Exercicio: ", error)}
    }

    async function getUserInfo() {
    try {
        const userRaw = localStorage.getItem("user");
        const user = JSON.parse(userRaw);

        const response = await fetch(`/users/findById/${user.id}`);

        if (!response.ok) {
            throw new Error("Erro ao buscar usuário");
        }

        return await response.json();

    } catch (error) {
        console.error(error);
    }
}
    
    async function getWorksheets() {
        const userRaw = localStorage.getItem("user");
        if(!userRaw){
            console.warn("getWorksheets: no user in localStorage");
            return;
        }
        const user = JSON.parse(userRaw);
        const trainerId = user && user.id;
        if(!trainerId){
            console.warn("getWorksheets: trainerId not found on user");
            return;
        }
        try{
            const response = await fetch(`/worksheets/findByTrainerId/${trainerId}`);

            if(!response){
                alert("Erro ao fazer a requisição")
                return;
            }
            const worksheetsList = await response.json();
            worksheetsList.forEach(w =>{
                console.log(w);
                worksheets.push(w);
            })
        }catch(error){
            console.error("Erro ao criar a requisição ", error);
        }
    }

    async function getIdByEmail() {
        const emailEl = document.getElementById("memberSearch");
        const email = emailEl ? emailEl.value.trim() : "";
        if(!email) return null;
        try{
            const response = await fetch(`/users/getId/${encodeURIComponent(email)}`);
            if(!response || !response.ok){
                console.warn("getIdByEmail: response not ok", response && response.status);
                return null;
            }
            // backend returns a plain text id (string), so parse as text
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

    // 📌 Tela de criação
    function renderCreateBuilder(){

        content.innerHTML = `
            <h2>Criar Nova Ficha</h2>

            <input type="text" id="worksheetName" placeholder="Nome da ficha">

            <div id="divisionsArea"></div>

            <button id="addDivision">Adicionar Divisão</button>
            <button id="saveWorksheet">Salvar Ficha</button>
            <button id="cancel">Cancelar</button>
            <input type="text" id="memberSearch" placeholder="Buscar aluno por EMAIL">
            <div id="memberResults" class="autocomplete-results"></div>
        `;

        document.getElementById("addDivision")
            .addEventListener("click", addDivision);

        document.getElementById("saveWorksheet")
            .addEventListener("click", saveWorksheet);

        document.getElementById("cancel")
            .addEventListener("click", renderWorksheetList);


        
        // conecta o input de busca de membro com debounce
        const memberSearchEl = document.getElementById("memberSearch");
        if(memberSearchEl){
            const handler = debounce((e) => {
                searchMembers(e.target.value);
            }, 300);
            memberSearchEl.addEventListener("input", handler);
            // limpar seleção ao focar/editar
            memberSearchEl.addEventListener("focus", () => { if(!memberSearchEl.value) selectedMember = null; });
        }
    }


    // 📌 Adicionar divisão
    function addDivision(){

        const divisionsArea = document.getElementById("divisionsArea");

        const division = document.createElement("div");
        division.classList.add("division");

        division.innerHTML = `
            <input type="text" class="divisionName" placeholder="Nome da divisão">
            <button class="addExercise">Adicionar Exercício</button>
            <div class="exerciseArea"></div>
        `;

        divisionsArea.appendChild(division);

        division.querySelector(".addExercise")
            .addEventListener("click", function(){

                const exerciseArea = division.querySelector(".exerciseArea");

                const exerciseContainer = document.createElement("div");
                exerciseContainer.classList.add("exercise-container");

                exerciseContainer.innerHTML = `
                    <input type="text" class="exerciseName" placeholder="Nome do exercício">
                    <input type="text" class="exerciseReps" placeholder="Repetições">
                    <input type="text" class="exerciseSeries" placeholder="Séries">
                    <input type="text" class="exerciseDescription" placeholder="Descrição">
                    <input type="text" class="exerciseUrl" placeholder="URL do vídeo">
                `;

                exerciseArea.appendChild(exerciseContainer);
            });
    }

    
    async function saveExercises() {
        let namee = document.getElementById("exerciseName").value;
        let reps = document.getElementById("exerciseReps").value;
        let series = document.getElementById("exerciseSeries").value;
        let description = document.getElementById("exerciseDescription").value;
        let videoUrl = document.getElementById("exerciseUrl").value;
        try{
            const response = await fetch("/exercises/saveExercise",{
                method:"POST",
                headers:{ "Content-Type": "application/json" },
                body: JSON.stringify({
                    namee,
                    reps,
                    series,
                    description,
                    videoUrl
                })
            })
            if(!response){
                alert("falha ao salvar exercicio");
            }

            const data = await response.json();
            alert("Exercicio salvo!");
        }catch(error){console.log("Erro ao salvar o exercicio ", error)}
    }

    async function logout(){
        localStorage.removeItem("user");
        window.location.href = "index.html";
    }

    // 📌 Abre o modal de edição de uma ficha existente
    function renderEditBuilder(ws) {
        const modal = document.getElementById("editModal");
        const modalContent = document.getElementById("editModalContent");

        modalContent.innerHTML = `
            <h2>Editar Ficha</h2>
            <input type="text" id="editWorksheetName" placeholder="Nome da ficha" value="${ws.name}">
            <div class="user-lookup-row">
                <input type="text" id="userEmailSearch" placeholder="Email do aluno">
                <button id="lookupUserBtn">Buscar Aluno</button>
            </div>
            <span id="userLookupFeedback" class="lookup-feedback"></span>
            <div id="editDivisionsArea"></div>
            <button id="addDivisionBtn">Adicionar Divisão</button>
            <div class="modal-actions">
                <button id="updateWorksheetBtn">Salvar Alterações</button>
                <button id="cancelEditBtn">Cancelar</button>
            </div>
        `;

        let resolvedUserId = null;

        const divisionsArea = document.getElementById("editDivisionsArea");
        ws.divisions.forEach(div => {
            const divEl = createDivisionElement(div.type, div.name);
            divisionsArea.appendChild(divEl);
            const exerciseArea = divEl.querySelector(".exerciseArea");
            (div.exercises || []).forEach(ex => {
                exerciseArea.appendChild(createExerciseElement(ex.name, ex.reps, ex.series, ex.description, ex.videoUrl));
            });
        });

        document.getElementById("addDivisionBtn").addEventListener("click", () => {
            divisionsArea.appendChild(createDivisionElement());
        });

        document.getElementById("lookupUserBtn").addEventListener("click", async () => {
            const email = document.getElementById("userEmailSearch").value.trim();
            const feedback = document.getElementById("userLookupFeedback");
            if (!email) {
                feedback.textContent = "Digite um email.";
                feedback.style.color = "var(--accent)";
                return;
            }
            try {
                const res = await fetch(`/users/getId/${encodeURIComponent(email)}`);
                if (!res.ok) {
                    feedback.textContent = "Aluno não encontrado.";
                    feedback.style.color = "var(--accent)";
                    resolvedUserId = null;
                    return;
                }
                const text = await res.text();
                resolvedUserId = text.trim().replace(/^"|"$/g, "");
                feedback.textContent = "Aluno encontrado!";
                feedback.style.color = "var(--success)";
            } catch(e) {
                feedback.textContent = "Erro ao buscar aluno.";
                feedback.style.color = "var(--accent)";
                resolvedUserId = null;
            }
        });

        document.getElementById("updateWorksheetBtn").addEventListener("click", () => updateWorksheet(ws.id, resolvedUserId));
        document.getElementById("cancelEditBtn").addEventListener("click", closeEditModal);

        // Fecha ao clicar no backdrop
        modal.onclick = (e) => { if (e.target === modal) closeEditModal(); };

        modal.classList.add("active");
    }

    // Fecha o modal de edição
    function closeEditModal() {
        document.getElementById("editModal").classList.remove("active");
    }

    // Cria um elemento de divisão para o formulário de edição
    function createDivisionElement(type, name) {
        const divEl = document.createElement("div");
        divEl.classList.add("division");
        divEl.innerHTML = `
            <select class="divisionType">
                <option value="PERDAY" ${type === 'PERDAY' ? 'selected' : ''}>Por Dia</option>
                <option value="PERBODYTYPE" ${type === 'PERBODYTYPE' ? 'selected' : ''}>Por Grupo Muscular</option>
                <option value="OTHER" ${type === 'OTHER' ? 'selected' : ''}>Outro</option>
            </select>
            <input type="text" class="divisionName" placeholder="Nome da divisão" value="${name || ''}">
            <button class="addExercise">Adicionar Exercício</button>
            <div class="exerciseArea"></div>
        `;
        divEl.querySelector(".addExercise").addEventListener("click", () => {
            divEl.querySelector(".exerciseArea").appendChild(createExerciseElement());
        });
        return divEl;
    }

    // Cria um elemento de exercício para o formulário de edição
    function createExerciseElement(name, reps, series, description, videoUrl) {
        const exEl = document.createElement("div");
        exEl.classList.add("exercise-container");
        exEl.innerHTML = `
            <input type="text" class="exerciseName" placeholder="Nome do exercício" value="${name || ''}">
            <input type="text" class="exerciseReps" placeholder="Repetições" value="${reps || ''}">
            <input type="text" class="exerciseSeries" placeholder="Séries" value="${series || ''}">
            <input type="text" class="exerciseDescription" placeholder="Descrição" value="${description || ''}">
            <input type="text" class="exerciseUrl" placeholder="URL do vídeo" value="${videoUrl || ''}">
        `;
        return exEl;
    }

    // Envia a atualização da ficha para a API
    async function updateWorksheet(worksheetId, resolvedUserId) {
        const name = document.getElementById("editWorksheetName").value.trim();
        if (!name) {
            alert("Digite um nome para a ficha.");
            return;
        }
        if (!resolvedUserId) {
            alert("Busque e confirme o email do aluno antes de salvar.");
            return;
        }

        const user = JSON.parse(localStorage.getItem("user"));
        const trainerId = user && user.id;

        const divisions = [];
        // Busca apenas dentro do modal para evitar conflitos com outros formulários
        const modalContent = document.getElementById("editModalContent");
        modalContent.querySelectorAll(".division").forEach(div => {
            const divisionName = div.querySelector(".divisionName").value.trim();
            const typeEl = div.querySelector(".divisionType");
            const divisionType = typeEl ? typeEl.value : "OTHER";
            if (!divisionName) return;

            const exercises = [];
            div.querySelectorAll(".exercise-container").forEach(exDiv => {
                const exName = exDiv.querySelector(".exerciseName").value.trim();
                if (!exName) return;
                exercises.push({
                    name: exName,
                    reps: parseInt(exDiv.querySelector(".exerciseReps").value) || 0,
                    series: parseInt(exDiv.querySelector(".exerciseSeries").value) || 0,
                    description: exDiv.querySelector(".exerciseDescription").value,
                    videoUrl: exDiv.querySelector(".exerciseUrl").value
                });
            });
            divisions.push({ name: divisionName, type: divisionType, exercises });
        });

        const body = { name, userId: resolvedUserId, trainerId, divisions };
        try {
            const res = await fetch(`/worksheets/update/${worksheetId}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body)
            });
            if (!res.ok) {
                const text = await res.text().catch(() => null);
                throw new Error(text || "Erro ao atualizar ficha");
            }
            alert("Ficha atualizada com sucesso!");
            closeEditModal();
            worksheets = [];
            await getWorksheets();
            renderWorksheetList();
        } catch(e) {
            console.error("Erro ao atualizar ficha:", e);
            alert("Erro ao atualizar: " + (e && e.message ? e.message : ""));
        }
    }

    async function saveWorksheet() {

    const name = document.getElementById("worksheetName").value;

    if (!name) {
        alert("Digite um nome para a ficha");
        return;
    }

    const divisions = [];

    document.querySelectorAll(".division").forEach(div => {

        const divisionName = div.querySelector(".divisionName").value;

        if (!divisionName) return;

        const exercises = [];

        div.querySelectorAll(".exercise-container").forEach(exDiv => {

            const exercise = {
                name: exDiv.querySelector(".exerciseName").value,
                reps: parseInt(exDiv.querySelector(".exerciseReps").value),
                series: parseInt(exDiv.querySelector(".exerciseSeries").value),
                description: exDiv.querySelector(".exerciseDescription").value,
                videoUrl: exDiv.querySelector(".exerciseUrl").value
            };

            if (exercise.name) {
                exercises.push(exercise);
            }
        });

        divisions.push({
            name: divisionName,
            exercises: exercises
        });
    });

    const user = JSON.parse(localStorage.getItem("user"))
    // se o usuário digitou um nome no input mas não clicou no resultado,
    // tentamos resolver esse texto para um usuário antes de salvar
    const memberSearchEl = document.getElementById("memberSearch");
    if(!selectedMember && memberSearchEl && memberSearchEl.value && memberSearchEl.value.trim().length>0){
        try{
            const q = encodeURIComponent(memberSearchEl.value.trim());
            const resp = await fetch(`/users/getMembers?q=${q}`);
            if(resp && resp.ok){
                const list = await resp.json();
                if(list && list.length>0){
                    selectedMember = list[0];
                }
            }
        }catch(e){
            console.warn('Busca rápida de membro falhou:', e);
        }
    }

    const userIdfr = selectedMember ? selectedMember.id : await getIdByEmail();
    console.log("UserIdFr: ", userIdfr);
    const worksheet = {
        name: name,
        // se houver um aluno selecionado, salvamos esse id como owner (`userId`)
        userId: userIdfr,
        trainerId: user.id,
        divisions: divisions
    };

    console.log("Sending worksheet:", JSON.stringify(worksheet));

    try {
        const response = await fetch("/worksheets/createWorksheet", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(worksheet)
        });

        if (!response.ok) {
            const text = await response.text().catch(() => null);
            console.error("POST /worksheets/createWorksheet failed", response.status, text);
            throw new Error(text || "Erro ao salvar ficha");
        }

        const data = await response.json();

        console.log("Salvo no banco:", data);
        alert("Ficha salva com sucesso!");

        renderWorksheetList();

    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao salvar no banco: " + (error && error.message ? error.message : ""));
    }
}

