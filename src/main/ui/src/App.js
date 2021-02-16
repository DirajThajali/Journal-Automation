import React, { useState, useEffect } from "react";
import "./App.css";
import axios from "axios";

const backendAPI = "http://localhost:8080/";

const Data = () => {
  const [data, setData] = useState([]);
  const fetchData = () => {
    axios
      .get(backendAPI)
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);
  console.log(data);

  return data.map((obj, index) => {
    return (
      <div key={index}>
        <h3>{obj.topic}</h3>
        <h6>{obj.date}</h6>
        <p>{obj.entry}</p>
      </div>
    );
  });
};

function App() {
  return <Data />;
}

export default App;
