document.addEventListener("DOMContentLoaded", () => {
    fetch("instructors.json")
        .then(response => response.json())
        .then(instructors => {
            const instructorsList = document.getElementById("instructors-list");
            instructorsList.innerHTML = "";

            instructors.forEach(instructor => {
                let instructorCard = `
                    <div class="instructor-card">
                        <img src="${instructor.image}" alt="${instructor.name}">
                        <h2>${instructor.name}</h2>
                        <p>${instructor.specialization}</p>
                        <a href="${instructor.profile}" class="view-profile">View Profile</a>
                    </div>
                `;
                instructorsList.innerHTML += instructorCard;
            });
        })
        .catch(error => console.error("Error loading instructors:", error));
});
