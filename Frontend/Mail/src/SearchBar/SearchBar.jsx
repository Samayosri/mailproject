
import "./SearchBar.css"
import React, { useState } from "react";

function SearchBar({ query,setQuery }) {
  // Function to handle input changes and trigger the search
  const handleInputChange = (event) => {
    const newQuery = event.target.value;
    setQuery(newQuery);
  };

  return (
    <input
      className="search"
      type="text"
      placeholder="Search"
      value={query}  // Bind the input to the query state
      onChange={handleInputChange}  // Trigger the function on every change
    />
  );
}

export default SearchBar;
