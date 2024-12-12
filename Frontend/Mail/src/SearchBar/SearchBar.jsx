import { styled, alpha } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import SearchIcon from '@mui/icons-material/Search';
import InputAdornment from '@mui/material/InputAdornment';
import "./SearchBar.css"
function SearchBar() {
  return (
     <>
     <input className="search" type="text" placeholder='Search' />
     </>
  );
}

export default SearchBar;
