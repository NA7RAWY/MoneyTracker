import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import LoginPage from "../components/LoginPage";

function Login({ setPage }) {
  const navigate = useNavigate();

  
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (token) {
      navigate("/main", { replace: true });
    }
  }, [navigate]); 

  return <LoginPage />;
}

export default Login;