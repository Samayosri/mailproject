import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Registration from './Registration/Registration'
import {Container} from "@mui/material" 

function App() {

  const [signed, setSigned] = useState(false);
  const [window, setWindow] = useState("sign up");
  const [customerDTO, setCustomerDTO] = useState(
    {
      id: null,
      role: null,
      points:null,
      name:null,
      mail: null,
      password:null,
      phoneNumber: null,
    }
  );

  return (
    <>
     
     {["sign in", "sign up"].includes(window) && (
        <Registration
          window={window}
          setWindow={setWindow}
          customerDTO={customerDTO}
          setCustomerDTO={setCustomerDTO}
          signed={signed}
          setSigned={setSigned}
        />
      )}
      <Container>Hello</Container>
     
    </>
  )
}

export default App
