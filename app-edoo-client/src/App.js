import Category from "./component/Category";
import {Route, Routes} from "react-router-dom";
import Nav from "./templates/Nav";
import {ToastContainer} from "react-toastify";
import Aware from "./component/Aware";
import Student from "./component/Student";
import Group from "./component/Group";

function App() {

    return (
        <>
            <Nav/>
            <ToastContainer/>
            <Routes>
                <Route path="/category" element={<Category/>}/>
                <Route path="/aware" element={<Aware/>}/>
            </Routes>
        </>
    );
}

export default App;
