function submitRate() {
    const scoreInput = document.querySelector('input[name="SCORE_SCORE"]:checked');
    const score = scoreInput ? scoreInput.value : null;

    const contentElement = document.querySelector('textarea[name="SCORE_CONTENT"]');
    const content = contentElement ? contentElement.value.trim() : "";

    const animeId = document.getElementById('animeId')?.value;
    const userId = document.getElementById('userId')?.value;

    console.log("별점:", score);
    console.log("내용:", content);
    console.log("애니ID:", animeId);
    console.log("유저ID:", userId);

    if (!score || !content || !animeId || !userId) {
        alert('별점, 댓글, 사용자 ID, 애니 ID를 모두 입력해주세요!');
        return;
    }

    fetch('/Schedule/ajaxInsert', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            SCORE_SCORE: score,
            SCORE_CONTENT: content,
            ANIME_ID: animeId,
            USER_ID: userId
        })
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            alert('등록 완료!');
            // 🔄 댓글, 평균, 차트 갱신 함수 호출
            refreshAll(animeId, userId);
			
        } else {
            alert('등록 실패!');
        }
    })
    .catch(err => {
        console.error(err);
        alert('오류가 발생했습니다.');
    });
}
//갱신 함수 refreshRateList
function refreshAll(animeId, userId) {
    fetch('/Schedule/refreshRate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ANIME_ID: animeId, USER_ID: userId })
    })
    .then(res => res.json())
    .then(data => {
        // 게시판(댓글) 갱신
        const listDiv = document.querySelector(".wrap:last-child");
        listDiv.innerHTML = "<h1>게시판 목록</h1>";
        data.list.forEach(dto => {
            // 별점(스타) html 생성
            const starsHtml = "⭐".repeat(dto.SCORE_SCORE); // 혹은 img 태그 반복
            listDiv.innerHTML += `
                <div style="margin-bottom:20px;">
                    <div style="color:white;"><strong>${dto.USER_ID}</strong> :</div>
                    <div style="color:gold; font-size:20px;">${starsHtml}</div>
                    <div style="color:white; margin-top:4px;"><span>${dto.SCORE_CONTENT}</span></div>
                    <hr style="margin-top:20px;"/>
                </div>
            `;
        });

        // 평균 평점, 등급 텍스트 갱신
        document.querySelector(".grade-section span").textContent = data.grade;
        document.querySelector(".grade-badge").textContent = data.mark;

        // 도넛 차트 갱신
        drawGenderChart(data.maleRatio, data.femaleRatio);
        // 막대 그래프 등 차트가 더 있다면 같이 update 호출!
		updateBarChart(data.scoreList, data.countList);
    });
}


function updateBarChart(scoreList, countList) {
	const chartCanvas = document.getElementById('myChart');
	   if (!chartCanvas) {
	       console.warn("⚠️ myChart 캔버스를 찾을 수 없습니다.");
	       return;
	   }

	    const ctx2 = chartCanvas.getContext('2d');

    // ✅ 차트가 존재하면 업데이트, 없으면 새로 생성
	// 존재하는 경우에만 update 호출
	   if (window.myChart && typeof window.myChart.update === 'function') {
	       window.myChart.data.labels = scoreList;
	       window.myChart.data.datasets[0].data = countList;
	       window.myChart.update();
    } else {
        // 새로 생성
		console.log("📌 myChart가 없어 새로 생성합니다.");
        window.myChart = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: scoreList,
                datasets: [{
                    label: '평점 분포',
                    data: countList,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.6)',
                        'rgba(54, 162, 235, 0.6)',
                        'rgba(255, 206, 86, 0.6)',
                        'rgba(75, 192, 192, 0.6)',
                        'rgba(153, 102, 255, 0.6)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)'
                    ],
                    borderWidth: 1,
                    barPercentage: 0.5,
                    categoryPercentage: 0.5
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    }
}
