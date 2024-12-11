import React, { useState } from 'react';
import { Box, Grid, Paper } from '@mui/material';
import ComposeEmail from './Mail/ComposeEmail';
import MailContent from './Mail/MailContent';
import Mail from './Mail/Mail';
import SideBar from './SideBar/SideBar';
import SearchBar from './SearchBar/SearchBar';
import "./App.css";

const App = () => {
  const [selectedMail, setSelectedMail] = useState(null);
  const [signed, setSigned] = useState(false);
  const [window, setWindow] = useState("sign up");
  const [customerDTO, setCustomerDTO] = useState({
    id: null,
    role: null,
    points: null,
    name: null,
    mail: null,
    password: null,
    phoneNumber: null,
  });

  // Mails data state, storing actual mail data
  const [mails, setMails] = useState([
    {
      sender: "Ahmed",
      receiver: "Noor",
      subject: "React JS",
      body: "Hello, first mail"
    },
    {
      sender: "John",
      receiver: "Sara",
      subject: "Project Update",
      body: "Please check the latest update on the project."
    }
  ]);

  return (
  
  <SideBar></SideBar>
  );
};

export default App;
