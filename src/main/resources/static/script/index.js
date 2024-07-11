let myChart;

async function fetchAverage() {
    const equipmentId = document.getElementById('equipmentId').value;
    const period = document.getElementById('period').value;
    const response = await fetch(`/datasense/api/v1/equipments/average?period=${period}&equipmentId=${equipmentId}`);
    const data = await response.json();

    const ctx = document.getElementById('myChart').getContext('2d');

    if (myChart) {
        myChart.destroy();
    }

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: [`Average for ${period}`],
            datasets: [{
                label: 'Average Value',
                data: [data.average],
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
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