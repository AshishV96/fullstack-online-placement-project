import './App.css';
import {BrowserRouter as Router,Routes,Route} from 'react-router-dom'
import Login from './components/Login'
import Register from './components/Register'
import Caro from './components/Caro';
import UserDashboard from './components/User/UserDashboard';
import Protected from './components/Protected';
import UserProfile from './components/User/UserProfile';
import UserProfileUpdate from './components/User/UserProfileUpdate';
import ForgetPassword from './components/ForgetPassword';
import JobList from './components/User/JobList';
// import OTPPage from './components/OTPPage';
import ParentComponent from './test/ParentComponent';
import ChildComponent from './test/ChildComponent';
import Test from './components/Test';

function App() {
  return (
    <div>
      <Router>
      <Routes>
      <Route path ="/parent" element={<ParentComponent/>} />
      <Route path ="/child" element={<ChildComponent/>} />
      <Route path ="/" element={<Caro/>} />
      <Route path ="/login" element={<Login />} />
      <Route path ="/forgetPassword" element={<ForgetPassword />} />
      <Route path ="/register" element={<Register />} />
      <Route path ="/test" element={<Test />} />
      <Route path ="/user" element={<Protected Cmp={UserDashboard} />} />
      {/* <Route path ="/user/OTP" element={<OTPPage/>} /> */}
      <Route path ="/user/profile" element={<Protected Cmp={UserProfile} />}/>
      <Route path ="/user/profile/update" element={<Protected Cmp={UserProfileUpdate} />}/>
      <Route path ="/user/jobs" element={<JobList />} />
      </Routes>
      </Router>
    </div>
  );
}

export default App;
