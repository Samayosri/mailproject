

import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import Registration from "./Registration/Registration";
import DivContent from "./DivContent/DivContent";
import "./App.css";
const App = () => {
  const [selectedFolder, setSelectedFolder] = useState("");
  
  const [signed, setSigned] = useState(() => {
    const savedSigned = sessionStorage.getItem("signed");
    console.log(savedSigned);
    return savedSigned ? JSON.parse(savedSigned) : false;
  });

  const [userName, setUserName] = useState(() => {
    const savedUserName = sessionStorage.getItem("userName");
    return savedUserName ? JSON.parse(savedUserName) :"";
  });
  const [window, setWindow] = useState(() => {
    const savedWindow = sessionStorage.getItem("window");
    return savedWindow ? JSON.parse(savedWindow) : "sign up";
  });

  const [userId, setUserId] = useState(() => {
    const savedUserId = sessionStorage.getItem("userId");
    return savedUserId ? JSON.parse(savedUserId) : null;
  });
  const [currentMails, setCurrentMails] = useState([]);

  useEffect(() => {
    sessionStorage.setItem("signed", JSON.stringify(signed));
    sessionStorage.setItem("window", JSON.stringify(window));
    sessionStorage.setItem("userId", JSON.stringify(userId));
    sessionStorage.setItem("userName", JSON.stringify(userName));
  }, [signed, window, userId,userName]);


  const [content, setContent] = useState("mails");
  const [folders, setFolders] = useState([]);

  function handleLogoutApp(){
    setWindow("sign up");
    setUserName("");
    setUserId(null);
    setSigned(false);
    setContent("mails");
  }
  return (
    <> 
      {(window === "sign up" || window === "sign in") && (
        <Registration
        setUserId={setUserId}
        window={window}
        setWindow={setWindow}
        signed={signed}
        setSigned={setSigned}
        setUserName={setUserName}
        setCurrentMails={setCurrentMails}
        currentMails={currentMails}
         
        />
      )}

      {window === "mail" && signed && (
        <Box sx={{ flexGrow: 1, height: "100%" }}>
          <DivContent
            setCurrentMails={setCurrentMails}
            currentMails={currentMails}
            content={content}
            setContent={setContent}
            selectedFolder={selectedFolder}
            folders={folders}
            setSelectedFolder={setSelectedFolder}
            setFolders={setFolders}
            userId={userId}
            userName={userName}
            handleLogoutApp={handleLogoutApp}
          />
        </Box>
      )}
    </>
  );
};

export default App;

