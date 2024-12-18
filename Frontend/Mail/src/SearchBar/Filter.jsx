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
} from "@mui/material";

const Filter = () => {
  const supportedFilters = [
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

  const [selectedFilters, setSelectedFilters] = useState([]);
  const [folderId, setFolderId] = useState(false);
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

  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const handleApply = () => {
    console.log("Selected Filters:", selectedFilters);
    console.log("Folder ID:", folderId);
    setOpen(false);
    console.log(selectedFilters)
    console.log(folderId)
  };

  return (
    <Box>
      <Button variant="contained" onClick={handleOpen}>
       Filter
      </Button>

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
            <FormControlLabel
              control={
                <Checkbox checked={folderId} onChange={handleToggleFolder} />
              }
              label="Only Selected Folder"
            />
          </FormGroup>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleApply} variant="contained" color="primary">
            Apply
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Filter;
