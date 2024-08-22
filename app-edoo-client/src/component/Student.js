import React, {useEffect, useState} from 'react';
import {Button, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Table} from "reactstrap";
import axios from "axios";
import {byId, url} from "../templates/api";


export default function Student() {

    const [student, setStudent] = useState([]);
    const [data, setData] = useState('');
    const [aware, setAware] = useState('');
    const [awares, setAwares] = useState([]);
    const [group, setGroup] = useState('');
    const [groups, setGroups] = useState([]);
    const [status, setStatus] = useState('');
    const [statuses, setStatuses] = useState([]);
    const [came, setCame] = useState('');
    const [cames, setCames] = useState([]);


    const [modalDelete, setModalDel] = useState(false);
    const [modalEdit, setModalEdit] = useState(false);
    const [modal, setModal] = useState(false);

    useEffect(() => {
        getStudent();
    }, []);

    const openModal = () => setModal(!modal);
    const openModalDel = () => setModalDel(!modalDelete);
    const openModalEdit = () => setModalEdit(!modalEdit);

    function getStudent() {
        axios.get(url + "student/get")
            .then(res => {
                setStudent(res.data.object)
            });
    }

    function getAwares() {
        axios.get(url + "aware")
            .then(res => {
                setAwares(res.data._embedded.list)
            });
    }

    function getGroups() {
        axios.get(url + "group/list")
            .then(res => {
                setGroups(res.data.object)
            });
    }

    function getCames() {
        axios.get(url + "student/came")
            .then(res => {
                setCames(res.data.object)
            });
    }

    function getStatuses() {
        axios.get(url + "student/status")
            .then(res => {
                setStatuses(res.data.object)
            });
    }

    function addStudent() {
        axios.post(url + "student/add", {
            firstName: byId("firstName"),
            lastName: byId("lastName"),
            phoneNumber: byId("phoneNumber"),
            age: byId("age"),
            registrationDay: byId("registrationDay"),
            discount: byId("discount"),
            userStatus: byId("userStatus"),
            cameFrom: byId("cameFrom"),
            groupId: byId("groupId"),
            awareId: byId("awareId")
        }).then(() => {
            getStudent();
        }).catch(err => console.log(err));
        openModal();
    }

    function editStudent() {
        axios.put(url + "student/update/" + data.id, {
            firstName: byId("firstName"),
            lastName: byId("lastName"),
            phoneNumber: byId("phoneNumber"),
            age: byId("age"),
            registrationDay: byId("registrationDay"),
            discount: byId("discount"),
            userStatus: byId("userStatus"),
            cameFrom: byId("cameFrom"),
            groupId: byId("groupId"),
            awareId: byId("awareId")
        }).then(() => {
            getStudent();
        }).catch(err => console.log(err));
    }

    function deleteStudent() {
        console.log(data.id)
        axios.delete(url +"student/"+  data.id)
            .then(() => {
                getStudent();
            }).catch(err => console.log(err));
    }

    return (<>
        <Container>
            {/*<Button color="success" className="add_btn mt-5 mb-3" onClick={openModal}>Add Student</Button>*/}
            <Button color="success" className="add_btn mt-5 mb-4" onClick={() => {
                openModal();
                getAwares();
                getGroups();
                getStatuses();
                getCames();
            }
            }>Add Student</Button>
            <Table bordered>
                <thead>
                <tr>
                    <th>#</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Phone Number</th>
                    <th>Age</th>
                    <th>Registration Day</th>
                    <th>Discount</th>
                    <th>User Status</th>
                    <th>Came From</th>
                    <th>Group Name</th>
                    <th>Aware Name</th>
                    <th colSpan="2">Action</th>
                </tr>
                </thead>
                <tbody>
                {student.map((item, i) => <tr key={i}>
                    <td>{i + 1}</td>
                    <td>{item.firstName}</td>
                    <td>{item.lastName}</td>
                    <td>{item.phoneNumber}</td>
                    <td>{item.age}</td>
                    <td>{item.registrationDay}</td>
                    <td>{item.discount}</td>
                    <td>{item.userStatus}</td>
                    <td>{item.cameFrom}</td>
                    <td>{item.groupName}</td>
                    <td>{item.awareName}</td>
                    <td><Button color="warning" onClick={() => {
                        openModalEdit();
                        setData(item);
                        getAwares();
                        getGroups();
                        getStatuses();
                        getCames();
                    }} outline>Edit</Button></td>
                    <td><Button color="danger" onClick={() => {
                        openModalDel();
                        setData(item);

                    }} outline>Delete</Button></td>
                </tr>)}
                </tbody>
            </Table>
        </Container>

        <Modal isOpen={modal} toggle={openModal}>
            <ModalHeader toggle={openModal}>Add Student</ModalHeader>
            <ModalBody>
                <Input className="mb-3" placeholder="First Name" id="firstName"/>
                <Input className="mb-3" placeholder="Last Name" id="lastName"/>
                <Input type="number" className="mb-3" placeholder="Phone Number" id="phoneNumber"/>
                <Input type="number" className="mb-3" placeholder="Age" id="age"/>
                <Input type="date" className="mb-3" placeholder="Registration Day" id="registrationDay"/>
                <Input type="number" className="mb-3" placeholder="Discount" id="discount"/>

                <select className="form-select mb-3" id="cameFrom" value={came} onChange={e => setCame(e.target.value)}>
                    {
                        cames.map(opt => <option>{opt}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="userStatus" value={status}
                        onChange={e => setStatus(e.target.value)}>
                    {
                        statuses.map(opt => <option>{opt}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="awareId" value={aware}
                        onChange={e => setAware(e.target.value)}>
                    {
                        awares.map(opt => <option value={opt.id}>{opt.name}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="groupId" value={group}
                        onChange={e => setGroup(e.target.value)}>
                    {
                        groups.map(opt => <option value={opt.id}>{opt.name}</option>)
                    }
                </select>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={openModal}>Close</Button>
                <Button color="success" onClick={() => {
                    addStudent();
                    openModal();
                }}>Save</Button>
            </ModalFooter>
        </Modal>
        <Modal isOpen={modalEdit} toggle={openModalEdit}>
            <ModalHeader toggle={openModalEdit}>Edit Student</ModalHeader>
            <ModalBody>
                <Input defaultValue={data.firstName} className="mb-3" placeholder="First Name" id="firstName"/>
                <Input defaultValue={data.lastName} className="mb-3" placeholder="Last Name" id="lastName"/>
                <Input defaultValue={data.phoneNumber} type="number" className="mb-3" placeholder="Phone Number" id="phoneNumber"/>
                <Input defaultValue={data.age} type="number" className="mb-3" placeholder="Age" id="age"/>
                <Input defaultValue={data.registrationDay} type="date" className="mb-3" placeholder="Registration Day" id="registrationDay"/>
                <Input defaultValue={data.discount} type="number" className="mb-3" placeholder="Discount" id="discount"/>

                <select className="form-select mb-3" id="cameFrom" value={came} onChange={e => setCame(e.target.value)}>
                    {
                        cames.map(opt => <option>{opt}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="userStatus" value={status}
                        onChange={e => setStatus(e.target.value)}>
                    {
                        statuses.map(opt => <option>{opt}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="awareId" value={aware}
                        onChange={e => setAware(e.target.value)}>
                    {
                        awares.map(opt => <option value={opt.id}>{opt.name}</option>)
                    }
                </select>
                <select className="form-select mb-3" id="groupId" value={group}
                        onChange={e => setGroup(e.target.value)}>
                    <option value={null}>No Group</option>
                    {
                        groups.map(opt => <option value={opt.id}>{opt.name}</option>)
                    }
                </select>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={openModalEdit}>Close</Button>
                <Button color="success" onClick={() => {
                    editStudent();
                    openModalEdit();
                }}>Save</Button>
            </ModalFooter>
        </Modal>

        <Modal isOpen={modalDelete} toggle={openModalDel}>
            <ModalHeader toggle={openModalDel}>Delete Student</ModalHeader>
            <ModalBody>
                <p>Are you sure want to delete student ?</p>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={openModalDel}>Close</Button>
                <Button color="danger" onClick={() => {
                    deleteStudent();
                    openModalDel();
                }}>Delete</Button>
            </ModalFooter>
        </Modal>
    </>);
}