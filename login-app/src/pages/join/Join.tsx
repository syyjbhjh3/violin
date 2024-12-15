import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../assets/login.css";
import SignInForm from "../../components/login/SignIn";
import SignUpForm from "../../components/login/SingUp";

const Join = () => {
    const [type, setType] = useState("signIn");
    const navigate = useNavigate();  // 페이지 이동을 위해 useNavigate 사용

    const handleOnClick = (text: "signIn" | "signUp") => {
        if (text !== type) {
            setType(text);
        }
    };

    const containerClass =
        "container " + (type === "signUp" ? "right-panel-active" : "");

    return (
        <div className="Join">
            <div className={containerClass} id="container">
                {/* 조건에 맞는 폼을 표시 */}
                {type === "signUp" ? <SignUpForm /> : <SignInForm />}
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h1>Welcome Back!</h1>
                            <p>
                                To keep connected with us please login with your personal info
                            </p>
                            <button
                                className="ghost"
                                id="signIn"
                                onClick={() => handleOnClick("signIn")}
                            >
                                Sign In
                            </button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h1>Hello, Friend!</h1>
                            <p>Enter your personal details and start your journey with us</p>
                            <button
                                className="ghost"
                                id="signUp"
                                onClick={() => handleOnClick("signUp")}
                            >
                                Sign Up
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Join;