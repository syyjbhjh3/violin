import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Main from "./pages/main/Main"; // Main 컴포넌트 경로
import Join from "./pages/join/Join"; // 회원가입 페이지 (예시)
import Login from "./pages/login/Login"; // 로그인 페이지 (예시)

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<Join />} />
          <Route path="/main" element={<Main />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
  );
}

export default App;