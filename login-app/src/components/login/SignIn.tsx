import React, { ChangeEvent, FormEvent, useState } from "react";

// State 타입 정의
interface State {
    email: string;
    password: string;
}

function SignInForm() {
    const [state, setState] = useState<State>({
        email: "",
        password: ""
    });

    const handleChange = (evt: ChangeEvent<HTMLInputElement>) => {
        const value = evt.target.value;
        setState({
            ...state,
            [evt.target.name]: value
        });
    };

    const handleOnSubmit = (evt: FormEvent<HTMLFormElement>) => {
        evt.preventDefault();

        const { email, password } = state;
        alert(`You are logging in with email: ${email} and password: ${password}`);

        // 상태 초기화
        setState({
            email: "",
            password: ""
        });
    };

    return (
        <div className="form-container sign-in-container">
            <form onSubmit={handleOnSubmit}>
                <h1>Sign in</h1>
                <div className="social-container">
                    <a href="#" className="social">
                        <i className="fab fa-facebook-f" />
                    </a>
                    <a href="#" className="social">
                        <i className="fab fa-google-plus-g" />
                    </a>
                    <a href="#" className="social">
                        <i className="fab fa-linkedin-in" />
                    </a>
                </div>
                <span>or use your account</span>
                <input
                    type="email"
                    placeholder="Email"
                    name="email"
                    value={state.email}
                    onChange={handleChange}
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={state.password}
                    onChange={handleChange}
                />
                <a href="#">Forgot your password?</a>
                <button type="submit">Sign In</button>
            </form>
        </div>
    );
}

export default SignInForm;