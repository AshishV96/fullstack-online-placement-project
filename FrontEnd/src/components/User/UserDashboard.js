import { Button, Card, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import UserNav from "./UserNav"
import { getBody, getToken, tokenName } from "../../token/tokenManager";
import { useEffect, useState } from "react";

function UserDashboard() {

    // const [token, setToken] = useState('')
    const [user, setUser] = useState()
    const [appJobs,setAppJobs] = useState()
    const history = useNavigate();

    const token = getToken()
    // console.log(token)
    let body = getBody()
    // console.log(body)
    // useEffect(()=>{if (body==null||body.user==null) {
    //     alert('Please Login Again')
    //     history('/')
    // }},[body])

    // if (body==null) {
        //     alert('Please Login Again')
        //     history('/')
        // }
        console.log(body.user)
    setUser(JSON.parse(body.user))

    let response = fetch('/user/getAppliedJobs/' + user.userId, {
        method: 'GET',
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": "Bearer " + token
        },
    });

    let result = response.json();
    
    if (response.ok) {
       setAppJobs(result) 
    }

    return (
        (!response.ok) ?
            (
                <><UserNav />
                    <div className="col-sm-8 offset-sm-2">
                        <br />
                        <br />
                        <br />
                        <br />
                        <h4>Welcome {user.firstName}</h4>
                        <br />
                        <p>Looks like you haven't applied for any jobs yet</p>
                        <p><Link to='/user/jobs'>Click here</Link> to start applying.</p>
                    </div>
                </>
            )
            :
            (
                <><UserNav />
                    <br />
                    <h3 style={{ marginLeft: 20 }}>Dashboard</h3>
                    <hr />
                    <div className="col-sm-8 offset-sm-2">
                        {
                            appJobs.map((data) =>
                                <Card style={{ marginTop: 20, padding: 20, backgroundColor: "lightgrey" }}>
                                    <Row className="App" >
                                        <Col><h4>{data.employer.companyName}</h4></Col>
                                        <Col></Col>
                                        <Col><h4>{data.job.jobDescp}</h4></Col>
                                    </Row>
                                    <br />
                                    <Row className="App">
                                        <Col>Location : {data.job.jobLocation}</Col>
                                        <Col>Salary : Rs.{data.job.jobSalary}</Col>
                                        <Col>Category : {data.job.jobCat}</Col>
                                    </Row>
                                    <br />
                                    <Row className="App">
                                        <Col>Email : {data.employer.companyemail}</Col>
                                        <Col>Contact : {data.employer.contact}</Col>
                                        <Col><Button variant="danger" style={{ maxHeight: 35 }}>Unapply</Button></Col>
                                    </Row>
                                </Card>
                            )
                        }
                    </div>
                </>
            )
    )


}

export default UserDashboard