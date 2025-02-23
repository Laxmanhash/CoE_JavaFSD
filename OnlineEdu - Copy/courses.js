document.addEventListener("DOMContentLoaded", () => {
    fetch("courses.json")
        .then(response => response.json())
        .then(courses => {
            const courseList = document.getElementById("courses-list");
            courseList.innerHTML = ""; // Clear any previous content

            courses.forEach(course => {
                let courseCard = `
                    <div class="course-card">
                        <h2>${course.name}</h2>
                        <p>${course.description}</p>
                        <p><strong>Instructor:</strong> ${course.instructor}</p>
                        <button onclick="enroll('${course.name}')">Enroll</button>
                    </div>
                `;
                courseList.innerHTML += courseCard;
            });
        })
        .catch(error => console.error("Error loading courses:", error));
});

function enroll(courseName) {
    alert(`Enrolled in ${courseName}!`);
}
