import React, {useState} from 'react';
import {Button, Offcanvas, OffcanvasBody, OffcanvasHeader} from "reactstrap";
import {Link} from "react-router-dom";
import "../style/style.scss";

function SideBar() {

    return (
        <div className="main">
            <Offcanvas className="side_bar_main" backdrop={false} isOpen={true}>
                <OffcanvasHeader>Side Bar</OffcanvasHeader>
                <div className="sideBar_border"></div>
                <OffcanvasBody>
                    <div className="input-group mb-3">
                        <div className="input-group-prepend">
                            <span className="input-group-text" id="basic-addon1">🔎</span>
                        </div>
                        <input type="text" className="form-control" placeholder="Search" aria-label="Search"
                               aria-describedby="basic-addon1"/>
                    </div>
                    <Button><Link to="/">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" fill="currentColor"
                             className="bi bi-house-door-fill" viewBox="0 0 16 16">
                            <path
                                d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5Z"/>
                        </svg>
                        Home</Link></Button>
                    <Button><Link to="/category">Category</Link></Button>
                    <Button><Link to="/aware"> Aware</Link></Button>
                </OffcanvasBody>
            </Offcanvas>
        </div>
    );
}

export default SideBar;