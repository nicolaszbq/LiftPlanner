    const content = document.getElementById("contentArea");

    let selectedMember = null;

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
            const res = await fetch(`/users/members?q=${encodeURIComponent(query)}`);
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
            container.innerHTML = `<div class=\"result-empty\">Nenhum aluno encontrado</div>`;
            return;
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
        <div class="worksheet-card">
            
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
    html += `</div>`
        });

        
        content.innerHTML = html; // escreve tudo de uma vez
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
    
    async function getWorksheets() {
        const user = JSON.parse(localStorage.getItem("user"))
        trainerId = user.id;
        try{
            const response = await fetch(`http://localhost:8080/worksheets/findByTrainerId/${trainerId}`);

            if(!response){
                alert("Erro ao fazer a requisição")
            }
            const worksheetsList = await response.json();
            worksheetsList.forEach(w =>{
                console.log(w);
                worksheets.push(w);
            })
        }catch(error){
            console("Erro ao criar a requisição ", error)
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
            <input type="text" id="memberSearch" placeholder="Buscar aluno por nome">
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
            const resp = await fetch(`/users/members?q=${q}`);
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

    const worksheet = {
        name: name,
        // se houver um aluno selecionado, salvamos esse id como owner (`userId`)
        userId: selectedMember ? selectedMember.id : user.id,
        trainerId: user.id,
        divisions: divisions
    };

    try {
        const response = await fetch("http://localhost:8080/worksheets/createWorksheet", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(worksheet)
        });

        if (!response.ok) {
            throw new Error("Erro ao salvar ficha");
        }

        const data = await response.json();

        console.log("Salvo no banco:", data);
        alert("Ficha salva com sucesso!");

        renderWorksheetList();

    } catch (error) {
        console.error("Erro:", error);
        alert("Erro ao salvar no banco");
    }
}

