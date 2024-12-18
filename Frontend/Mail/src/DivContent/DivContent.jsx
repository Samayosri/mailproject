import { useState, useEffect } from "react";
import axios from "axios";
import RefreshIcon from '@mui/icons-material/Refresh';
import { 
  Box, IconButton, FormControl, InputLabel, Select, MenuItem, 
  Stack, Button, Dialog, DialogTitle, DialogContent, DialogActions 
} from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import SideBar from "../SideBar/SideBar";
import Mail from "../Mail/Mail";
import MyContacts from "../Contacts/MyContacts";
import SearchBar from "../SearchBar/SearchBar";
import ContactsButton from "../Contacts/ContactsButton";
import Filter from "../SearchBar/Filter";

function DivContent({
  content,
  setContent,
  selectedFolder,
  folders,
  setFolders,
  setSelectedFolder,
  userId,
}) {
  const [currentMails, setCurrentMails] = useState([]);
  const [page, setPage] = useState(0);
  const [sortBy, setSortBy] = useState("");
  const [checkedMails, setCheckedMails] = useState([]);
  const [triggerFetch, setTriggerFetch] = useState(false);
  const [moveDialogOpen, setMoveDialogOpen] = useState(false);
  const [selectedMoveFolder, setSelectedMoveFolder] = useState("");

  // Fetch mails for the current page
  const fetchMails = async (pageIndex) => {
    if (!selectedFolder) return;

    const folderID = folders.find(
      (folder) => folder.name === selectedFolder
    )?.id;
    if (!folderID) return;

    try {
      const response = await axios.get(`http://localhost:8080/mail/${folderID}`, {
        params: {
          pageNumber: pageIndex,
          pageSize: 5,
          sort: sortBy,
        },
      });

      if (response.status === 200 && response.data) {
        setCurrentMails(response.data);
      }
    } catch (error) {
      console.error("Error fetching mails:", error);
    }
  };

  useEffect(() => {
    setPage(0);
    if (selectedFolder || triggerFetch) {
      fetchMails(0);
      setTriggerFetch(false);
    }
  }, [selectedFolder, triggerFetch]);

  const handleSortChange = (event) => {
    setSortBy(event.target.value);
    setPage(0);
    fetchMails(0);
  };

  const handleNextPage = () => {
    const nextPage = page + 1;
    setPage(nextPage);
    fetchMails(nextPage);
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      const prevPage = page - 1;
      setPage(prevPage);
      fetchMails(prevPage);
    }
  };

  const handleMove = async () => {
    if (!selectedMoveFolder || checkedMails.length === 0) {
      console.error("No folder selected or no mails checked.");
      return;
    }

    const folderID = folders.find((folder) => folder.name === selectedMoveFolder)?.id;
    if (!folderID) {
      console.error("Invalid folder selected.");
      return;
    }

    const moveMails = {
      userId,
      destinationFolderId: folderID,
      mailIds: checkedMails,
    };

    try {
      const response = await axios.put("http://localhost:8080/mail/move", moveMails);

      if (response.status === 200) {
        setTriggerFetch(true);
        console.log("Mails moved successfully");
      }
    } catch (error) {
      console.error("Error moving mails:", error);
    }
    setMoveDialogOpen(false); // Close the dialog after moving mails
    setCheckedMails([]);
  };

  const handleDelete = () => {
    axios
      .delete("http://localhost:8080/mail/delete", {
        data: { userId, mailIds: checkedMails },
      })
      .then((response) => {
        console.log("Mails deleted:", response.data);
        setTriggerFetch(true);
      })
      .catch((error) => {
        console.error("Error deleting mails:", error);
      });
  };

  return (
    <Box sx={{ display: "flex" }}>
      {/* Left Sidebar */}
      <SideBar
        setTriggerFetch={setTriggerFetch}
        folders={folders}
        selectedFolder={selectedFolder}
        setSelectedFolder={setSelectedFolder}
        setFolders={setFolders}
        setContent={setContent}
        userId={userId}
      />

      {/* Main Content Area */}
      <Box
        sx={{
          marginLeft: "270px",
          width: "calc(100% - 250px)",
          padding: "20px",
          display: "flex",
          flexDirection: "column",
        }}
      >
        {/* Top Section: Search Bar and Contacts Button */}
        <Box
  sx={{
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 2,
  }}
>
<Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
    <Filter /> {/* Filter moved to the right of the SearchBar */}
    <SearchBar /> {/* SearchBar remains in the center */}
     <Button variant="contained">Search</Button> {/* Search button added to the left */}
    
  </Box>
  <ContactsButton setContent={setContent} /> {/* Moved to the far left */}
 
</Box>

        {/* Page Navigation and Sort */}
        <Box
          sx={{
            marginBottom: "10px",
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
            marginBottom: 2,
          }}
        >
          <div>
            <label>Page </label>
            <IconButton onClick={handlePreviousPage} disabled={page === 0}>
              <ArrowBackIosIcon />
            </IconButton>
            <label>{page + 1}</label>
            <IconButton
              onClick={handleNextPage}
              disabled={currentMails.length < 5}
            >
              <ArrowForwardIosIcon />
            </IconButton>
          </div>

          {/* Actions */}
          <Box
            style={{
              display: "flex",
              gap: "10px",
            }}
          >
            <Button variant="contained" onClick={() => setMoveDialogOpen(true)}>
              Move
            </Button>
            <Button variant="outlined" color="error" onClick={handleDelete}>
              Delete
            </Button>
            <IconButton onClick={()=>{fetchMails(0)}}>
            <RefreshIcon></RefreshIcon>
          </IconButton>
          </Box>

          {/* Sort dropdown */}
          <FormControl variant="standard" sx={{ minWidth: 150 }}>
            <InputLabel id="sort-label">Sort by:</InputLabel>
            <Select
              labelId="sort-label"
              id="sort-select"
              value={sortBy}
              onChange={handleSortChange}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="date">Date</MenuItem>
              <MenuItem value="priority">Importance</MenuItem>
            </Select>
          </FormControl>
        </Box>

        {/* Content */}
        <Box sx={{  backgroundColor: "#f4f4f4", padding: 2 }}>
          {content === "mails" && (
            <Mail
              folders={folders}
              selectedFolder={selectedFolder}
              userId={userId}
              mails={currentMails}
              setTriggerFetch={setTriggerFetch}
              checkedMails={checkedMails}
              setCheckedMails={setCheckedMails}
            />
          )}

          {content === "contacts" && <MyContacts userId={userId} />}
        </Box>
      </Box>

      {/* Move Dialog */}
      <Dialog open={moveDialogOpen} onClose={() => setMoveDialogOpen(false)}>
        <DialogTitle>Move Mails</DialogTitle>
        <DialogContent>
          <FormControl fullWidth>
            <InputLabel id="move-folder-label">Select Folder</InputLabel>
            <Select
              labelId="move-folder-label"
              value={selectedMoveFolder}
              onChange={(e) => setSelectedMoveFolder(e.target.value)}
            >
              {folders.map((folder) => (

              folder.name!="Trash"&&  <MenuItem key={folder.id} value={folder.name}>

                  {folder.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setMoveDialogOpen(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleMove} color="primary" variant="contained">
            Move
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}

export default DivContent;
