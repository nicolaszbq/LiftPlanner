//lista de workheets
worksheets = [];

async function getUserWorksheet() {
    const userraw = localStorage.getItem("user");
    if(!userRaw){
        console.warn("getWorksheets: no user in localStorage");
        return;
    }
    const user = JSON.parse(userraw);
    const userId = user.id;

    if(!userId){
        console.warn("getWorksheets: userId not found");
        return;
    }

    try{
            const response = await fetch(`/worksheets/findByUser/${trainerId}`);

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