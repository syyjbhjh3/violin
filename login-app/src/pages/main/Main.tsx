import React, { useEffect, useState } from "react";
import "../../assets/main.css";

function Main() {
    const [showText, setShowText] = useState(false);
    const [showSubText, setShowSubText] = useState(false);
    const [showButtons, setShowButtons] = useState(false);

    useEffect(() => {
        setTimeout(() => {
            setShowText(true);
        }, 500);

        setTimeout(() => {
            setShowSubText(true);
        }, 1500);

        setTimeout(() => {
            setShowButtons(true);
        }, 2500);
    }, []);

    return (
        <div className="container">
            <div className={`text ${showText ? "fade-in" : ""}`}>안녕하세요</div>
            <div className={`sub-text ${showSubText ? "fade-in" : ""}`}>처음이신가요?
                <span> </span>
                <a href="/join" className="join-link">회원가입</a>
            </div>
            <div className={`sub-text ${showButtons ? "fade-in" : ""}`}>기존 회원이신가요?
                <span> </span>
                <a href="/login" className="login-link">로그인</a>
            </div>
        </div>
    );
}

export default Main;
