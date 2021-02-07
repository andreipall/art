var deletePainting = document.getElementById('deletePainting');
deletePainting.addEventListener('show.bs.modal', function(event) {
	var button = event.relatedTarget;
	var paintingId = button.getAttribute('data-bs-painting-id');
	var name = button.getAttribute('data-bs-painting-name');
	var modalName = deletePainting.querySelector('#modalName');

	modalName.textContent = name;

	var deletePaintingConfirm = document.getElementById('confirmDeletePainting');
	deletePaintingConfirm.addEventListener("click", function() {


		fetch('/admin/paintings/' + paintingId, {
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