// Basic client-side validation for the patient registration form
function validateForm() {
    const name = document.getElementById('name');
    const age = document.getElementById('age');
    const phone = document.getElementById('phone');

    if (name && name.value.trim().length === 0) {
        alert('Please enter the patient name.');
        name.focus();
        return false;
    }

    if (age && (age.value === '' || Number(age.value) <= 0)) {
        alert('Please enter a valid age.');
        age.focus();
        return false;
    }

    const phonePattern = /^[0-9+\-\s]{7,15}$/;
    if (phone && !phonePattern.test(phone.value.trim())) {
        alert('Please enter a valid phone number.');
        phone.focus();
        return false;
    }

    return true;
}
