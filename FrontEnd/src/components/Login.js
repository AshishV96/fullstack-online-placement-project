import { Button, Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import Header from "./Header";
import { getBody, getToken, saveToken, tokenName } from "../token/tokenManager";

function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [path, setPath] = useState("/user/login");
    // const [client, setClient] = useState();
    const history = useNavigate();

    const [selected, setSelected] = useState('/user');
    // const locStore = (selected === '/user') ? 'user-token' : (selected === '/admin') ? 'admin-token' : 'employer-token'

    var display = (selected === '/user') ? 'inline' : 'none'

    useEffect(() => {
        let role = localStorage.getItem(tokenName);
        if (role) {
            (JSON.parse(role).user) ? history("/user") : (JSON.parse(role).admin)
                ? history("/admin") : history("/employer")
        }
    }, [history])

    const handleChange = event => {
        setSelected(event.target.value);
    };

    async function LogIn() {

        if (username.length === 0 || password.length === 0) {
            alert("Please Enter Data")
            return
        }

        let item = { username, password }

        let response = await fetch(path, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                // "Authorization": "Bearer "+getToken(),
                "Accept": "application/json"
            }, body: JSON.stringify(item)
        })

        if (response.ok) {
            let token = await response.json()
            console.log(token.jwt)
            saveToken(token.jwt)
            let role = getBody()
            alert("Login Successfull")
            if (role.user != null) { 
                history("/user")
             }
            else if (role.admin != null) { 
                history("/admin")
            }
            else if (role.employer != null) {  
                history("/employer")
            }

        }

    }


    return (
        <><Header />
            <div className="col-sm-4 offset-sm-4">
                <Card className="App" style={{ marginTop: 20, padding: 20, backgroundColor: "lightgrey" }}>
                    <h2>Log In</h2>
                    <div>
                        <label style={{ margin: 10 }}>
                            <input type="radio" name="path" value="/admin" checked={selected === '/admin'}
                                onChange={handleChange} onClick={() => setPath('/admin/login')} />
                            Admin
                        </label>
                        <label style={{ margin: 10 }}>
                            <input type="radio" name="path" value="/employer" checked={selected === '/employer'}
                                onChange={handleChange} onClick={() => setPath('/employer/login')} />
                            Employer
                        </label>
                        <label style={{ margin: 10 }}>
                            <input type="radio" name="path" value="/user" checked={selected === '/user'}
                                onChange={handleChange} onClick={() => setPath('/user/login')} />
                            User
                        </label>
                    </div>
                    <input type="email" value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Enter Email" className="form-control" />
                    <br />
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Enter Password" className="form-control" />
                    <br />
                    <div>
                        <Button onClick={LogIn}>LogIn</Button>
                        <Button style={{ marginLeft: 10, display: display }} onClick={() => { history('/forgetPassword') }}>Forget Password</Button>
                    </div>
                </Card>
            </div>
        </>
    );

}

export default Login


