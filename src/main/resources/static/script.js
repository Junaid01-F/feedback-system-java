const form = document.getElementById('feedbackForm');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const feedback = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        rating: parseInt(document.getElementById('rating').value),
        comments: document.getElementById('comments').value
    };

    try {

const response = await fetch('https://feedback-system-java-2.onrender.com/api/feedback', {
            method: 'POST',

            headers: {
                'Content-Type': 'application/json'
            },

            body: JSON.stringify(feedback)
        });

        if (response.ok) {

            document.getElementById('message').innerHTML =
                '<span style="color:green">Feedback Submitted Successfully!</span>';

            form.reset();

        } else {

            document.getElementById('message').innerHTML =
                '<span style="color:red">Something went wrong!</span>';
        }

    } catch (error) {

        console.error(error);

        document.getElementById('message').innerHTML =
            '<span style="color:red">Server Error!</span>';
    }
});