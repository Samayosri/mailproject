import AddIcon from "@mui/icons-material/Add";
import { useState } from "react";
import { Menu, MenuItem, IconButton } from "@mui/material";

function SelectContacts({ onAddContact, contacts }) {
  const [anchorEl, setAnchorEl] = useState(null); 

  const handleOpenMenu = (event) => {
    setAnchorEl(event.currentTarget); 
  };

  const handleCloseMenu = () => {
    setAnchorEl(null); 
  };

  const handleAddContact = (email) => {
    onAddContact(email); 
    handleCloseMenu(); 
  };

  return (
    <div>
      <IconButton onClick={handleOpenMenu} color="primary">
        <AddIcon />
      </IconButton>

      <Menu
        anchorEl={anchorEl}
        open={Boolean(anchorEl)}
        onClose={handleCloseMenu}
      >
        {contacts.map((contact) => (
          <div key={contact.id}>
            <strong>{contact.name}</strong>
            {contact.emailAddress.map((email, index) => (
              <MenuItem key={index} onClick={() => handleAddContact(email)}>
                {email}
              </MenuItem>
            ))}
          </div>
        ))}
      </Menu>
    </div>
  );
}

export default SelectContacts;
