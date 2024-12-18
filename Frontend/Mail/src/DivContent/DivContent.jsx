import { useState, useEffect } from "react";
import axios from "axios";
import { Box, IconButton, FormControl, InputLabel, Select, MenuItem, Stack,Button } from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import SideBar from "../SideBar/SideBar";
import Mail from "../Mail/Mail";
import MyContacts from "../Contacts/MyContacts";
import SearchBar from "../SearchBar/SearchBar";
import ContactsButton from "../Contacts/ContactsButton";

function DivContent({
  content,
  setContent,
  selectedFolder,
  folders,
  setFolders,
  setSelectedFolder,
  userId,
}) {
  const [allMails, setAllMails] = useState([]); 
  const [page, setPage] = useState(0);
  const [sortBy, setSortBy] = useState(""); 
  const [currentMails, setCurrentMails] = useState([]);
  
  
  // Fetch mails for the current page
  const fetchMails = async (pageIndex) => {
    if (!selectedFolder) return;

    const folderID = folders.find((folder) => folder.name === selectedFolder)?.id;
    if (!folderID) return;

    try {
      const response = await axios.get(`http://localhost:8080/mail/${folderID}`, {
        params: {
          pageNumber: pageIndex,
          pageSize: 5,
          sort: sortBy
        },
      });

      if (response.status === 200 && response.data) {
        const newMails = response.data;
        setCurrentMails(newMails);
        setAllMails((prevMails) => {
          const updatedMails = [...prevMails];
          updatedMails[pageIndex] = newMails;
          return updatedMails;
        });
      }
    } catch (error) {
      console.error("Error fetching mails:", error);
    }
  };

  useEffect(() => {
    setPage(0);
    setAllMails([]);
    if (selectedFolder) {
      fetchMails(0);
    }
  }, [selectedFolder]);

  const handleSortChange = (event) => {
    setSortBy(event.target.value);
    setPage(0);
    setAllMails([]);
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

  return (
    <Box sx={{ display: "flex" }}>
      {/* Left Sidebar */}
      <SideBar
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
          marginLeft: "270px", // Offset content to the right of the sidebar
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
          <SearchBar />
          <ContactsButton setContent={setContent} />
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
            <IconButton onClick={handleNextPage} disabled={currentMails.length < 5}>
              <ArrowForwardIosIcon />
            </IconButton>
          </div>

      <Box
        style={{
          display: "flex",
        
          
          gap: "10px",
        }}
      >
        <Button variant="contained">Move</Button>
        <Button variant="outlined" color="error">
          Delete
        </Button>
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
        <Box sx={{ background: "white", padding: 2 }}>
          {content === "mails" && (
            <Mail
              folders={folders}
              selectedFolder={selectedFolder}
              userId={userId}
              mails={currentMails}
            />
          )}

          {content === "contacts" && (
            <MyContacts userId={userId} />
          )}
        </Box>
      </Box>
    </Box>
  );
}

export default DivContent;
