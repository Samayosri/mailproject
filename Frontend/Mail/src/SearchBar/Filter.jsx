import React, { useState } from "react";
import {
  Box,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControlLabel,
  FormGroup,
  IconButton,

} from "@mui/material";
import PhotoFilterIcon from '@mui/icons-material/PhotoFilter';
const Filter = ({selectedFilters,setSelectedFilters,folderId,setFolderId,content}) => {

  const supportedFilters = content==="contacts"?["name","emaiAddress"] :  [
    "attachments",
    "body",
    "date",
    "importance",
    "receiversEmailAddress",
    "receiversName",
    "subject",
    "senderName",
    "senderEmailAddress",
  ];

  const [open, setOpen] = useState(false);

  const handleToggleFilter = (filter) => {
    setSelectedFilters((prev) =>
      prev.includes(filter)
        ? prev.filter((item) => item !== filter)
        : [...prev, filter]
    );
  };

  const handleToggleFolder = () => {
    setFolderId((prev) => !prev);
  };

  const handleClearAll = () => {
    setSelectedFilters([]);  // Clear all selected filters
    setFolderId(false);      // Reset the folder checkbox
  };

  const handleOpen = () => setOpen(true);
  const handleClose = () =>{
    setSelectedFilters(supportedFilters);
    setFolderId(false)
     setOpen(false);
    }

  const handleApply = () => {
    console.log("Selected Filters:", selectedFilters);
    console.log("Folder ID:", folderId);
    setOpen(false);
    
  };

  return (
    <Box>
      <IconButton variant="contained" onClick={handleOpen}>
      <PhotoFilterIcon></PhotoFilterIcon>     </IconButton>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Filter Options</DialogTitle>
        <DialogContent>
          <FormGroup>
            {supportedFilters.map((filter, index) => (
              <FormControlLabel
                key={index}
                control={
                  <Checkbox
                    checked={selectedFilters.includes(filter)}
                    onChange={() => handleToggleFilter(filter)}
                  />
                }
                label={filter}
              />
            ))}
            {content!=="contacts" &&<FormControlLabel
              control={
                <Checkbox checked={folderId} onChange={handleToggleFolder} />
              }
              label="Only Selected Folder"
            />}
          </FormGroup>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} variant="outlined" >Cancel</Button>
          <Button onClick={handleClearAll} variant="outlined">
            Clear All
          </Button>
          <Button onClick={handleApply} variant="contained"  color="primary">
            Apply
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Filter;
