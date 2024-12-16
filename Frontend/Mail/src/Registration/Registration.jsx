import "./Registration.css";
import { useState, useEffect } from "react";
import {Container} from "@mui/material" 
import axios from 'axios'

function Registration({ window, setWindow, customerDTO, setCustomerDTO, signed, setSigned }) {
  const [noteMessage, setNoteMessage] = useState("");//to handle massage error to be visible in form  
  function showNoteMessage(message) {
    setNoteMessage(message);
    setTimeout(() => setNoteMessage(""), 2000);// Hide the msg after 2sec
  }
  function validData(signUp) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/i;
    let msg = "";

    if ((!formData.email || !formData.password) || (signUp && (!formData.name || !formData.confirmPass || !formData.phone))) {
      msg = "Please fill in all required fields!";
    } else if (!emailRegex.test(formData.email)) {
      msg = "Invalid email format!";
    }
    else if ((formData.password != formData.confirmPass) && signUp) {
      msg = "Passwords don't match!!"

    }
    else if (!/^\d{11}$/.test(formData.phone) && signUp) {
      msg = "Phone number must be exactly 11 digits!";
    }
    if (msg) {
      showNoteMessage(msg);
      return false;
    }
    return true;
  }


  async function handleRegistration(signup) {
    if (validData(signup)) {
      const customerData =
      {
        id: null,
        role: null,
        points: null,
        name: formData.name,
        mail: formData.email,
        password: formData.password,
        phoneNumber: formData.phone,
      };
      setSigned(true);
      setWindow("mail");
      /*
      const url = signup
        ? "http://localhost:8080/customer/signup"
        : "http://localhost:8080/customer/login";

      try {
        const response = await axios.post(url, customerData);

        if (response.status === 201) {
          setCustomerDTO(response.data);
          setSigned(true);
          setWindow("mail");
        } 
      } catch (error) {
        if (error.response?.status === 400) {
          // Account not found or other bad request error
          console.error("Error 400:", error.response.data);
          showNoteMessage(error.response.data); // Displays "account not found signup to continue"
        } else {
          // Generic error handler
          console.error("Unexpected Error:", error);
          showNoteMessage("An unexpected error occurred. Please try again.");
        }
      }*/
    }
  }


  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPass: "",
    phone: ""
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };


  //sign-up and sign-in button options to handle two cases
  const signUp = [{ name: "Name", id: 1 }, { name: "E-mail Address", id: 2 }, { name: "Password", id: 3 },
  { name: "Confirm Password", id: 4 }, { name: "Phone Number", id: 5 }, { name: "Sign UP", id: 6 },
  ];

  const signIn = [
    { name: "E-mail Address", id: 1 }, { name: "Password", id: 2 }, { name: "Sign IN", id: 3 },
  ];


  const signUpOptions = signUp.map((op) => {
    if (op.name === "Password" || op.name === "Confirm Password") {
      return (
        <input
          name={op.name === "Password" ? "password" : "confirmPass"}
          type="password"
          key={op.id}
          className="signUp-options"
          placeholder={op.name}
          value={op.name === "Password" ? formData.password : formData.confirmPass}
          onChange={(e) =>
            handleInputChange(e)
          }
        />
      );
    }

    if (op.name === "Sign UP") {
      return (
        <>
          {noteMessage && <h1 style={{ fontSize: "1rem" }}>{noteMessage}</h1>}

          <input
            type="submit"
            key={op.id}
            className={"signUp"}
            onClick={() => handleRegistration(true)}
            value={op.name}
          />
        </>

      );
    }

    return (
      <input
        type="text"
        name={
          op.name === "Name"
            ? "name"
            : op.name === "E-mail Address"
              ? "email"
              : "phone"
        }
        key={op.id}
        className="signUp-options"
        placeholder={op.name}
        value={
          op.name === "Name"
            ? formData.name
            : op.name === "E-mail Address"
              ? formData.email
              : formData.phone
        }
        onChange={(e) =>
          handleInputChange(e)
        }
      />
    );
  });


  const signInOptions = signIn.map((op) => {
    if (op.name === "Password") {
      return (
        <input
          name="password"
          type="password"
          key={op.id}
          className="signUp-options"
          placeholder={"Enter " + op.name}
          value={formData.password}
          onChange={(e) => handleInputChange(e)}
        />
      );
    }

    if (op.name === "Sign IN") {
      return (
        <>
          {noteMessage && <h1 style={{ fontSize: "1rem" }}>{noteMessage}</h1>}
          <input
            type="submit"
            key={op.id}
            className="signUp"
            onClick={() => { handleRegistration(false) }}
            value={op.name}
          />

        </>
      );
    }

    return (
      <input
        type="text"
        name="email"
        key={op.id}
        className="signUp-options"
        placeholder={op.name}
        value={formData.email}
        onChange={(e) => handleInputChange(e)}
      />
    );
  });


  function SignUp_SignIn() {
    return window === "sign in" ? signInOptions : signUpOptions;
  }
  
  return (
    <>
      <Container>
       <div className="regist">
         <h1 style={{ color: "black" }}>
           {window === "sign in" ? "Welcome Back" : "Create Account"}
         </h1>
         {SignUp_SignIn()}

         {window === "sign in" && (
           <a
             className="create-account"
             href="##"
             style={{ color: "blue" }}
             onClick={() => {
               setWindow("sign up")
             }}
           >
             Create new Account
           </a>
         )}
       </div>
       {window == "sign up" && <button className="login" onClick={() => { setWindow("sign in") }}>Login</button>} 

      </Container>
     
    </>
  );
}

export default Registration;
