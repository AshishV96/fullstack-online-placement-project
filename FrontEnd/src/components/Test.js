
import jwt_decode from "jwt-decode"

export default function Test() {

  let token = "eyJzdWIiOiJhdmNoYW5kYW4xQGdtYWlsLmNvbSIsImV4cCI6MTY5MDc4MzA2OSwidXNlciI6IntcInVzZXJJZFwiOjEwLFwiZW1haWxcIjpcImF2Y2hhbmRhbjFAZ21haWwuY29tXCIsXCJmaXJzdE5hbWVcIjpcIkFzaGlzaFwiLFwibGFzdE5hbWVcIjpcIlZlcm1hXCIsXCJhZ2VcIjpudWxsLFwiZ2VuZGVyXCI6bnVsbCxcImFkZHJlc3NcIjpudWxsLFwicXVhbGlmaWNhdGlvblwiOm51bGwsXCJjb250YWN0Tm9cIjpudWxsLFwiam9ic1wiOltdfSIsImlhdCI6MTY5MDc4MjQ2OX0"
  localStorage.setItem('token',token)
  let object = jwt_decode('.'+localStorage.getItem('token')+'.')
  console.log(JSON.parse(object.user).firstName)
  if(object.admin!=null)
  console.log(true)


  return (
    <div>
      <h1>Hello CodeSandbox</h1>
      <h2>Start editing to see some magic happen!</h2>
    </div>
  );
}
