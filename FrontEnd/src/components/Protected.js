import React, { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { getBody } from "../token/tokenManager";

function Protected(props) {

    const history = useNavigate();
    // let url = props.url
    // windowlocation.reload()
    // useEffect(()=>location.reload,[])
    // const [pres,setPres] = useState();
    // const [Comp,setComp] = useState();

    // let body = getBody()
    // console.log(body)

    // if (body!=null) {
    //     if (!(body.user) || !(body.admin) || !(body.employer))
    //         history('/')
    // }
    // else
    //     history('/')

    // useEffect(()=>{
    //     if(!present)
    //     history('/')
    // })
    const pres = React.useMemo(() => { return props.present })
    let Comp = props.Cmp
    // setPres(props.present)
    // setComp(props.Cmp)
    // console.log(present)


    return (
        <React.Suspense fallback={<h2>Loading</h2>}>
            {(pres) ?
            (
            <div>
                <Comp />
            </div>
            )
            :
            (
            <div>
                <h2>Go to home</h2>
            </div>
            )}
        </React.Suspense>
    )
}

export default Protected