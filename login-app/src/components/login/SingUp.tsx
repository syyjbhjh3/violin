import React, { ChangeEvent, FormEvent, useState } from "react";

interface State {
    name: string;
    email: string;
    password: string;
}

function SignUpForm() {
    // Define the state type
    const [state, setState] = useState<State>({
        name: "",
        email: "",
        password: "",
    });

    // Handle input change with type annotations
    const handleChange = (evt: ChangeEvent<HTMLInputElement>) => {
        const value = evt.target.value;
        setState({
            ...state,
            [evt.target.name]: value,
        });
    };

    // Handle form submission with type annotations
    const handleOnSubmit = (evt: FormEvent<HTMLFormElement>) => {
        evt.preventDefault();

        const { name, email, password } = state;
        alert(
            `You are signing up with name: ${name}, email: ${email}, and password: ${password}`
        );

        // Reset form state
        setState({
            name: "",
            email: "",
            password: "",
        });
    };

    return (
        <div className="form-container sign-up-container">
            <form onSubmit={handleOnSubmit}>
                <h1>Create Account</h1>
                <div className="social-container">
                    <a href="#" className="social">
                        <i className="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" className="social">
                        <i className="fab fa-google-plus-g"></i>
                    </a>
                    <a href="#" className="social">
                        <i className="fab fa-linkedin-in"></i>
                    </a>
                </div>
                <span>or use your email for registration</span>
                <input
                    type="text"
                    name="name"
                    value={state.name}
                    onChange={handleChange}
                    placeholder="Name"
                />
                <input
                    type="email"
                    name="email"
                    value={state.email}
                    onChange={handleChange}
                    placeholder="Email"
                />
                <input
                    type="password"
                    name="password"
                    value={state.password}
                    onChange={handleChange}
                    placeholder="Password"
                />
                <button>Sign Up</button>
            </form>
        </div>
    );
}

export default SignUpForm;