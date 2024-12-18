import { Box, Stack, Button, IconButton, TextField } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import Folder from "../Folder/Folder";
import { useState, useEffect } from "react";
import SearchBar from "../SearchBar/SearchBar";
import ComposeEmail from "../Mail/ComposeEmail";
import CreateNewFolderIcon from "@mui/icons-material/CreateNewFolder";
import axios from "axios";
import ContactsButton from "../Contacts/ContactsButton";

function SideBar({selectedFolder, setSelectedFolder, folders, setFolders, setContent, userId ,setMails,setTriggerFetch}) {
  const [isOpen, setIsOpen] = useState(false);
  const [newFolder, setNewFolder] = useState(false);
  const [folderName, setFolderName] = useState("");

  const handleOpen = () => setIsOpen(true);
  const handleClose = () => setIsOpen(false);

  useEffect(() => {
    const fetchFolders = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/folder/${userId}`);
        if (response.status === 200) {
          setFolders(response.data);
        }
      } catch (error) {
        console.error("Error fetching folders:", error);
      }
    };
    fetchFolders();
  }, [userId, setFolders]);

  const createNewFolder = () => {
    if (folderName) {
      const newFolderObject = { id: 4, name: folderName, userId: 8 };
      setFolders((prevFolders) => [...prevFolders, newFolderObject]);
      setFolderName("");
      setNewFolder(false);
    }
  };

  return (
    <Box
      sx={{
        width: 250,
        position: "fixed",
        top: 0,
        left: 0,
        bottom: 0,
        backgroundColor: "#f4f4f4",
        padding: 2,
        overflowY: "auto",
        display: "flex",
        flexDirection: "column",
      }}
    >
      {/* Folder creation and search bar */}
      <Stack spacing={2} sx={{ alignItems: "center", marginBottom: 4 }}>
        <IconButton
          size="large"
          aria-label="create new folder"
          onClick={() => setNewFolder(true)}
        >
          <CreateNewFolderIcon />
        </IconButton>
           <Button
            variant="contained"
            onClick={handleOpen}
            sx={{
              width: "90%",
              backgroundColor: "#aee7fe",
              color: "black",
            }}
          >
            New mail
          </Button>
          {isOpen && <ComposeEmail open={isOpen} onClose={handleClose} userId={userId} setTriggerFetch={setTriggerFetch} />}

        {newFolder && (
          <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <TextField
              label="Folder Name"
              variant="outlined"
              value={folderName}
              onChange={(e) => setFolderName(e.target.value)}
            />
            <Stack direction="row" spacing={1} sx={{ marginTop: 1 }}>
              <Button variant="contained" color="success" onClick={createNewFolder}>
                Create
              </Button>
              <Button variant="outlined" color="error" onClick={() => setNewFolder(false)}>
                Cancel
              </Button>
            </Stack>
          </Box>
        )}

        {/* Folders list */}
        {folders && folders.length > 0 ? (
          folders.map((folder) => (
            <Folder
              key={folder.id}
              name={folder.name}
              setContent={setContent}
              setSelectedFolder={setSelectedFolder}
              selectedFolder={selectedFolder}
              folders={folders}
              setMails={setMails}
            />
          ))
        ) : (
          <p>No folders available</p>
        )}
      </Stack>

      {/* Search bar and contacts button */}
    </Box>
  );
}

export default SideBar;


