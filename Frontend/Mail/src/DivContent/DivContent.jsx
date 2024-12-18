import { useState, useEffect } from "react";
import axios from "axios";
import { Box, IconButton, FormControl, InputLabel, Select, MenuItem, Stack } from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import SideBar from "../SideBar/SideBar";
import Mail from "../Mail/Mail";
import MyContacts from "../Contacts/MyContacts";
import { use } from "react";

function DivContent({
  content,
  setContent,
  selectedFolder,
  folders,
  setFolders,
  setSelectedFolder,
  userId,
}) {
  const [allMails, setAllMails] = useState([]); // Store mails for all pages
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
          sort:sortBy

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
    <>
      <SideBar
        folders={folders}
        selectedFolder={selectedFolder}
        setSelectedFolder={setSelectedFolder}
        setFolders={setFolders}
        setContent={setContent}
        userId={userId}
      />

      <Box
        sx={{
          marginBottom: "10px",
          margin: "auto",
          width: "80%",
          display: "flex",
          alignItems: "center",
          gap: 2,
          justifyContent: "space-between",
          height: "30px",
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

 
      <Box sx={{ width: "80%", margin: "auto", background: "white" }}>
        
        {content === "mails" && (
          <Mail
            folders={folders}
            selectedFolder={selectedFolder}
            userId={userId}
            mails={currentMails} 
          />
        )}

        {content === "contacts" && (
          <>
          <MyContacts userId={userId}/>  
          </>
          
        

        )}
      </Box>
    </>
  );
}

export default DivContent;
