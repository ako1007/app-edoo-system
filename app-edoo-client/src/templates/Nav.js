import React, {useState} from 'react';
import {Link} from "react-router-dom";
import SideBar from "./SideBar";
import {Button} from "reactstrap";

function Nav() {

    return (
        <>
            <SideBar/>
            <div className="navbar-king">
                <Link to="/category">Category</Link>
                <Link to="/student">Student</Link>
                <Link to="/group">Group</Link>
                <Link to="/aware">Aware</Link>
            </div>
        </>
    );
}

export default Nav;