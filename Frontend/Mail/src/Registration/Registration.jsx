import "./Registration.css";
import { useState, useEffect } from "react";
import { Container } from "@mui/material";
import axios from 'axios';


function Registration({ setUserId, window, setWindow, signed, setSigned,setUserName,setCurrentMails,currentMails }) {
  const [noteMessage, setNoteMessage] = useState(""); // to handle error messages
  // Function to display a message and hide it after 2 seconds
  function showNoteMessage(message) {
    setNoteMessage(message);
    setTimeout(() => setNoteMessage(""), 2000); // Hide the msg after 2 seconds
  }
  useEffect(() => {
  console.log("Updated window:", window);
  console.log("Updated signed:", signed);
}, [window, signed]);


  // Function to validate the form data
  function validData(signUp) {
    console.log(formData);
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/i;
    let msg = "";

    if (!formData.email || !formData.password || (signUp&&!formData.name)) {
      msg = "Please fill in all required fields!";
    } else if (!emailRegex.test(formData.email)) {
      msg = "Invalid email format!";
    }

    if (msg) {
      showNoteMessage(msg);
      return false;
    }
    return true;
  }

  // Function to handle registration or login
  async function handleRegistration(signup) {
    if (validData(signup)) {
      const customerData = {
        id: null,
        name: formData.name,
        emailAddress: formData.email,
        password: formData.password,
      };

      const url = signup
        ? "http://localhost:8080/user/signup"
        : "http://localhost:8080/user/login";

      try {
        const response = await axios.post(url, customerData);
        if (response.status === 201) {
          location.reload();
          console.log("response is",response.data);
          setUserName(response.data.name);
          setUserId(response.data.id);
          setWindow("mail");
          setSigned(true);
        }
      } catch (error) {
        if (error.response?.status === 400) {
          // Account not found or other bad request error
          console.error("Error 400:", error.response.data);
          showNoteMessage(error.response.data); // Displays "account not found, sign up to continue"
        } else {
          // Generic error handler
          console.error("Unexpected Error:", error);
          showNoteMessage("An unexpected error occurred. Please try again.");
        }
      }
    }
  }

  // Form data state
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });

  // Function to handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Sign-up form options
  const signUp = [
    { name: "Name", id: 1 },
    { name: "E-mail Address", id: 2 },
    { name: "Password", id: 4 },
    { name: "Sign UP", id: 5 },
  ];

  // Sign-in form options
  const signIn = [
    { name: "E-mail Address", id: 2 },
    { name: "Password", id: 3 },
    { name: "Sign IN", id: 4 },
  ];

  // Rendering sign-up form fields
  const signUpOptions = signUp.map((op) => {
    if (op.name === "Password") {
      return (
        <input
          name="password"
          type="password"
          key={op.id}
          className="signUp-options"
          placeholder={op.name}
          value={formData.password}
          onChange={handleInputChange}
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
            className="signUp"
            onClick={() => handleRegistration(true)}
            value={op.name}
          />
        </>
      );
    }

    return (
      <input
        type="text"
        name={op.name === "Name" ? "name" : "email"}
        key={op.id}
        className="signUp-options"
        placeholder={op.name}
        value={op.name === "Name" ? formData.name : formData.email}
        onChange={handleInputChange}
      />
    );
  });

  // Rendering sign-in form fields
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
          onChange={handleInputChange}
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
            onClick={() => handleRegistration(false)}
            value={op.name}
          />
        </>
      );
    }

    return (
      <input
        type="text"
        name={"email"}
        key={op.id}
        className="signUp-options"
        placeholder={op.name}
        value={formData.email}
        onChange={handleInputChange}
      />
    );
  });

  // Conditional rendering for Sign-Up or Sign-In form
  function SignUp_SignIn() {
    return window === "sign in" ? signInOptions : signUpOptions;
  }

  return (
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
            onClick={() => setWindow("sign up")}
          >
            Create new Account
          </a>
        )}
      </div>
      {window === "sign up" && (
        <button className="login" onClick={() => setWindow("sign in")}>
          Login
        </button>
      )}
    </Container>
  );
}

export default Registration;
