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
    .then(res => {
        if (res.ok) {
            alert('등록 완료!');
            
        } else {
            alert('등록 실패!');
        }
    })
    .catch(err => {
        console.error(err);
        alert('오류가 발생했습니다.');
    });
}