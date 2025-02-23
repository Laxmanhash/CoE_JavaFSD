document.addEventListener("DOMContentLoaded", () => {
    fetch("courses.json")
        .then(response => response.json())
        .then(courses => {
            const featuredCourses = document.getElementById("featured-courses-list");
            featuredCourses.innerHTML = "";

            courses.slice(0, 3).forEach(course => {
                let courseCard = `
                    <div class="course-card">
                        <h2>${course.name}</h2>
                        <p>${course.description}</p>
                        <button onclick="location.href='pages/course-details.html'">Learn More</button>
                    </div>
                `;
                featuredCourses.innerHTML += courseCard;
            });
        })
        .catch(error => console.error("Error loading courses:", error));
});
