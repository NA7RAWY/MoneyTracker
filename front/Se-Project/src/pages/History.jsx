import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import HistoryPage from "../components/HistoryPage";

function History() {
  const navigate = useNavigate();

  
  useEffect(() => {
    const token = localStorage.getItem("authToken");
    if (!token) {
      
      navigate("/", { replace: true });
    }
  }, [navigate]); 

  return <HistoryPage />;
}

export default History;