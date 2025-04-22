const ctx2 = document.getElementById('genderChart');
  new Chart(ctx2, {
    type: 'doughnut',
    data: {
      labels: ['남성', '여성'],
      datasets: [{
        label: '성별 투표 비율',
        data: [maleRatio, femaleRatio],
        backgroundColor: [
          'rgba(0, 153, 255, 0.7)',
          'rgba(255, 102, 204, 0.7)'
        ],
        borderColor: [
          'rgba(0, 153, 255, 1)',
          'rgba(255, 102, 204, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      cutout: '50%',
      responsive: true,
      plugins: {
        legend: {
          position: 'bottom',
          labels: {
            color: '#fff',
            font: {
              size: 14
            }
          }
        },
        tooltip: {
          callbacks: {
            label: function(context) {
              return `${context.label}: ${context.raw}%`;
            }
          }
        }
      }
    }
  });
