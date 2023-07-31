import React, { useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { getBody } from "../token/tokenManager";
function Protected(props) {
    
    let Cmp = props.Cmp
    let role = props.role
    const history = useNavigate();
    
    useEffect((role) => {
        let body = getBody()
        if(body!=null)
        {
            if(role==='user'&&body.user==null)
            history('/')
            if(role==='admin'&&body.admin==null)
            history('/')
            if(role==='employer'&&body.employer==null)
            history('/')
        }
        else    
            history('/')
        
    }, [])

    return (
        <div>
            <Cmp/>
        </div>
    )
}

export default Protected