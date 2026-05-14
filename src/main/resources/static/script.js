const form = document.getElementById('feedbackForm');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const feedback = {

        name: document.getElementById('name').value,

        email: document.getElementById('email').value,

        rating: document.getElementById('rating').value,

        comments: document.getElementById('comments').value
    };

    const response = await fetch('/api/feedback', {

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
});