var deleteExhibition = document.getElementById('deleteExhibition');
deleteExhibition.addEventListener('show.bs.modal', function(event) {
	var button = event.relatedTarget;
	var exhibitionId = button.getAttribute('data-bs-exhibition-id');
	var name = button.getAttribute('data-bs-exhibition-name');
	var modalName = deleteExhibition.querySelector('#modalName');

	modalName.textContent = name;

	var deleteExhibitionConfirm = document.getElementById('confirmDeleteExhibition');
	deleteExhibitionConfirm.addEventListener("click", function() {


		fetch('/admin/exhibitions/' + exhibitionId, {
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