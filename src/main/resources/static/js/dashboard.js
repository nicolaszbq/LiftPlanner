    const content = document.getElementById("contentArea");

    getWorksheets();

    let worksheets = [];

    document.getElementById("btnView")
        .addEventListener("click", renderWorksheetList);

    document.getElementById("btnCreate")
        .addEventListener("click", renderCreateBuilder);

        
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
        try{
            const response = await fetch("http://localhost:8080/worksheets/findAllWorksheets");

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
        `;

        document.getElementById("addDivision")
            .addEventListener("click", addDivision);

        document.getElementById("saveWorksheet")
            .addEventListener("click", saveWorksheet);

        document.getElementById("cancel")
            .addEventListener("click", renderWorksheetList);
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
    const worksheet = {
        name: name,
        userId: "06a45414-4963-4ce0-9229-c5f7040bf04f",      // coloque o ID real depois
        trainerId: user.id,   // coloque o ID real depois
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