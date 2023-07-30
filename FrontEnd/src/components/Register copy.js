import { Button, Card } from "react-bootstrap"
import React, { useEffect, useState } from 'react'
import { useNavigate } from "react-router-dom";
import Header from "./Header";

function Register() {
    
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const history = useNavigate();
    
    const [sentOTP, setSentOTP] = useState(false);
    
    const [OTP,setOTP] = useState('');

    const [reqOTP, setReqOTP] = useState('')

    useEffect(() => {
        if (localStorage.getItem('user-info')) {
            history('/user')
        }
    }, [history])

    var item = { firstName, lastName, email, password };

    async function signUp() {

        if (firstName.length === 0 || lastName.length === 0 || email.length === 0 || password.length === 0) {
            alert("Please Enter Data")
            return
        }
        console.warn(item)

        let res = await fetch("/user/get/" + email + '/' + password, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
        });

        if (res.ok) {
            alert("EmailId already registered")
            return
        }

        const OTPGen = Math.floor(100000 + Math.random() * 900000).toString()
        setOTP(OTPGen)
        console.log(OTPGen)
        console.warn(item)

        let resp = await fetch("/user/sendOTP?mailId=" + email, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: OTPGen
        });

        if (resp.ok) {
            alert("OTP sent")
            setSentOTP(true)
            console.log(OTP)
            console.warn(item)
        }

        else {
            alert('Invalid Email')
            return
        }
    }

    async function submit() {

        console.log('OTP:'+OTP)
        console.log('reqOTP:'+reqOTP)
        console.warn(item)

        if(OTP!==reqOTP)
        {
            alert('Invalid OTP')
            return
        }

        else if (reqOTP === OTP) {
            let response = await fetch("/user/add", {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json"
                },
                body: JSON.stringify(item)
            });

            if (response.ok) {

                alert("Registration Successfull, kindly login again")
                history('/login')
                // localStorage.setItem("user-info", JSON.stringify(result))
                // var name = JSON.parse(localStorage.getItem('user-info')).user.firstName
                // alert("Welcome " + name)
                // history('/user')
            }
            // localStorage.removeItem('OTP')
        }
            
    }

    
        return (
            !sentOTP ?
                (
                    <><Header />
                        <div className="col-sm-4 offset-sm-4">
                            <Card className="App" style={{ marginTop: 20, padding: 20, backgroundColor: "lightgrey" }}>
                                <h2 style={{ marginBottom: 40 }}>User Sign Up</h2>
                                <div>
                                    <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} placeholder="Enter FirstName" className="form-control" />
                                    <br />
                                    <input type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} placeholder="Enter LastName" className="form-control" />
                                    <br />
                                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Enter Email" className="form-control" />
                                    <br />
                                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Enter Password" className="form-control" />
                                    <br />
                                </div>
                                <div>
                                    <Button onClick={signUp}>Sign Up</Button>
                                </div>
                            </Card>
                        </div>
                    </>
                )
                :
                (<><Header />
                    <div className="col-sm-4 offset-sm-4">
                        <Card className="App" style={{ marginTop: 20, padding: 20, backgroundColor: "lightgrey" }}>
                            <h2>Enter OTP</h2>
                            <input type="number" value={reqOTP} onChange={(e) => setReqOTP(e.target.value)} placeholder="Enter OTP" className="form-control" />
                            <br />
                            <div>
                                <Button onClick={submit}>Submit</Button>
                            </div>
                        </Card>
                    </div>
                </>
                )
        )
}

export default Register