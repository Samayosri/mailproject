

import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import Registration from "./Registration/Registration";
import DivContent from "./DivContent/DivContent";
import "./App.css";

const App = () => {
  const [selectedFolder, setSelectedFolder] = useState("inbox");
  
  const [signed, setSigned] = useState(() => {
    const savedSigned = sessionStorage.getItem("signed");
    console.log(savedSigned);
    return savedSigned ? JSON.parse(savedSigned) : false;
  });

  const [window, setWindow] = useState(() => {
    const savedWindow = sessionStorage.getItem("window");
    return savedWindow ? JSON.parse(savedWindow) : "sign up";
  });

  const [userId, setUserId] = useState(() => {
    const savedUserId = sessionStorage.getItem("userId");
    return savedUserId ? JSON.parse(savedUserId) : null;
  });

  useEffect(() => {
    sessionStorage.setItem("signed", JSON.stringify(signed));
    sessionStorage.setItem("window", JSON.stringify(window));
    sessionStorage.setItem("userId", JSON.stringify(userId));
  }, [signed, window, userId]);


  const [content, setContent] = useState("mails");
  const [folders, setFolders] = useState([]);

 
  return (
    <> 
      {(window === "sign up" || window === "sign in") && (
        <Registration
        setUserId={setUserId}
        window={window}
        setWindow={setWindow}
        signed={signed}
        setSigned={setSigned}
         
        />
      )}

      {window === "mail" && signed && (
        <Box sx={{ flexGrow: 1, height: "100%" }}>
          <DivContent
            content={content}
            setContent={setContent}
            selectedFolder={selectedFolder}
            folders={folders}
            setSelectedFolder={setSelectedFolder}
            setFolders={setFolders}
            userId={userId}
          />
        </Box>
      )}
    </>
  );
};

export default App;

