document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("enrollment-form").addEventListener("submit", function (event) {
        event.preventDefault();
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const course = document.getElementById("course").value;

        if (name && email && course) {
            alert(`Thank you, ${name}! You have been enrolled in ${course}.`);
            this.reset();
        } else {
            alert("Please fill in all fields.");
        }
    });
});
