var deleteUser = document.getElementById('deleteUser');
deleteUser.addEventListener('show.bs.modal', function(event) {
	var button = event.relatedTarget;
	var userId = button.getAttribute('data-bs-user-id');
	var name = button.getAttribute('data-bs-user-username');
	var modalName = deleteUser.querySelector('#modalName');

	modalName.textContent = name;

	var deleteUserConfirm = document.getElementById('confirmDeleteUser');
	deleteUserConfirm.addEventListener("click", function() {


		fetch('/admin/users/' + userId, {
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