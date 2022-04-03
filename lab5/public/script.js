const submitForm = document.querySelector("#submit-form");
submitForm.addEventListener('submit', submit);

function isValidData(text) {
	return text.length > 3 && /[a-zA-Z]/.test(text) && /[0-9]/.test(text);
}

function submit(event) {
	let userName = document.getElementById('userName')
	let userNameValid = isValidData(userName.value)
	
	if(!userNameValid) userName.style["border-color"] = "red"
	else userName.style["border-color"] = null
	
	let password = document.getElementById('password')
	let passwordValid = isValidData(password.value)
	
	if(!passwordValid) password.style["border-color"] = "red"
	else password.style["border-color"] = null
	
	if(!userNameValid || !passwordValid) {
		event.preventDefault()
	}
}