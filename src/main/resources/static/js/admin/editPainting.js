var deleteComment = document.getElementById('deleteComment');
deleteComment.addEventListener('show.bs.modal', function(event) {
	var button = event.relatedTarget;
	var paintingId = button.getAttribute('data-bs-painting-id');
	var id = button.getAttribute('data-bs-id');
	var name = button.getAttribute('data-bs-name');
	var comment = button.getAttribute('data-bs-comment');
	var modalName = deleteComment.querySelector('#modalName');
	var modalComment = deleteComment.querySelector('#modalComment');

	modalName.textContent = name;
	modalComment.textContent = comment;

	var deleteCommentConfirm = document.getElementById('confirmDeleteComment');
	deleteCommentConfirm.addEventListener("click", function() {


		fetch('/admin/paintings/' + paintingId + '/comments/' + id, {
			method: 'DELETE', // or 'PUT'
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(data => {
			//console.log(data);
			location.reload();
		}).catch((error) => {
			console.error(error);
		});

	});
});