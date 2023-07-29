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

    const [OTP, setOTP] = useState('');

    useEffect(() => {
        if (localStorage.getItem('token')) {
            history('/user')
        }
    }, [history])

    var item = { firstName, lastName, email, password };

    async function signUp() {

        if (firstName.length === 0 || lastName.length === 0 || email.length === 0 || password.length === 0) {
            alert("Please Enter Data")
            return
        }

        let resp = await fetch("/user/check/" + email, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
        });

        if (resp.status === 406) {
            alert(await resp.text())
            return
        }

        else if (resp.status === 200) 
        {
            alert(await resp.text())
            setSentOTP(true)
            return
        }

        else 
            alert(await resp.text())
    }

    async function submit() {

        let resp = await fetch("/user/add/" + OTP, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(item)
        });

        if (resp.status === 200) {
            alert(await resp.text())
            history('/login')
        }

        else if(resp.status === 400)
            alert(await resp.text())

        else 
            alert(await resp.text())

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
                        <input type="number" value={OTP} onChange={(e) => setOTP(e.target.value)} placeholder="Enter OTP" className="form-control" />
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