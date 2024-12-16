import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import Registration from "./Registration/Registration";
import DivContent from "./DivContent/DivContent";
import "./App.css";

const App = () => {
  const [selectedFolder, setSelectedFolder] = useState("inbox");
  const [selectedMail, setSelectedMail] = useState(null);

  // Retrieve and parse session data
  const [signed, setSigned] = useState(() => {
    const savedSigned = sessionStorage.getItem("signed");
    return savedSigned ? JSON.parse(savedSigned) : false;
  });

  const [window, setWindow] = useState(()=>{
    const savedWindow=sessionStorage.getItem("window");
    return savedWindow?JSON.parse(savedWindow):"sign up";
  })
    const [userId, setUserId] = useState(() => {
    const savedUserId = sessionStorage.getItem("userId");
    return savedUserId ? JSON.parse(savedUserId) : 2;
  });
 
  useEffect(() => {
    sessionStorage.setItem("signed", JSON.stringify(signed));
    sessionStorage.setItem("window", JSON.stringify(window));
    sessionStorage.setItem("userId", JSON.stringify(userId));
  }, [signed, window,userId]);

  const [customerDTO, setCustomerDTO] = useState({
    id: null,
    role: null,
    points: null,
    name: null,
    mail: null,
    password: null,
    phoneNumber: null,
  });

  const [content, setContent] = useState("mails");

  const [folders, setFolders] = useState([
    { id: 2, name: "Sent", userId: 1 },
    { id: 1, name: "Trash", userId: 5 },
    { id: 3, name: "Drafts", userId: 6 },
  ]);

  return (
    <>
      {(window === "sign up" || window === "sign in") && (
        <Registration
          setUserId={setUserId}
          window={window}
          setWindow={setWindow}
          customerDTO={customerDTO}
          setCustomerDTO={setCustomerDTO}
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
