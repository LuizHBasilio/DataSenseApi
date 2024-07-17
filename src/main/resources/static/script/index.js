let myChart;

async function getAverage() {
    const equipmentId = document.getElementById("equipmentId").value;
    const period = document.getElementById("period").value;

    const response = await fetch(`/datasense/api/v1/equipments/average?period=${period}&equipmentId=${equipmentId}`);
    const data = await response.json();

    const ctx = document.getElementById("myChart").getContext("2d");

    // Verifica se o gráfico já foi criado
    if (myChart) {
        // Atualiza os dados e redesenha o gráfico
        myChart.data.datasets[0].data = [data.average];
        myChart.data.datasets[0].label = `Average for ${equipmentId} over ${period}`;
        myChart.update();
    } else {
        // Cria um novo gráfico
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Average Value'],
                datasets: [{
                    label: `Average for ${equipmentId} over ${period}`,
                    data: [data.average],
                    backgroundColor: ['rgba(75, 192, 192, 0.2)'],
                    borderColor: ['rgba(75, 192, 192, 1)'],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
}