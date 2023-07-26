import React, { useState } from "react";
import { Button, Card } from "react-bootstrap";
import Header from "./Header";

function Test() {

    const [OTP,setOTP] = useState('')
    const [reqOTP,setReqOTP] = useState('')
    const [sentOTP, setSentOTP] = useState(false);

    function signUp() {
        const gen = Math.floor(100000 + Math.random() * 900000).toString()
        console.log(gen)
        alert('sentOTP')
        setSentOTP(true)
        setOTP(gen)
        console.log(OTP)
        localStorage.setItem('OTP',OTP)
    }

    function submit() {
        console.log('OTP:',OTP)
        console.log('reqOTP:',reqOTP)
        if(OTP===reqOTP)
        alert('Correct')
    }



    return (
        !sentOTP ?
            (
                <><Header />
                    <div className="col-sm-4 offset-sm-4">
                        <Card className="App" style={{ marginTop: 20, padding: 20, backgroundColor: "lightgrey" }}>
                            <h2 style={{ marginBottom: 40 }}>User Sign Up</h2>
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

export default Test