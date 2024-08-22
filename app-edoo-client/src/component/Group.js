import React, {useEffect, useState} from 'react';
import {Button, Container, Input, Modal, ModalBody, ModalFooter, ModalHeader, Table} from "reactstrap";
import axios from "axios";
import {byId, url} from "../templates/api";
import {toast} from "react-toastify";

function Group() {
    const [data, setData] = useState("");


    const [category, setCategory] = useState('');
    const [user, setUser] = useState('');
    const [weekday, setWeekday] = useState('');


    const [weekdays, setWeekdays] = useState([]);
    const [categories, setCategories] = useState([]);
    const [group, setGroup] = useState([]);
    const [users, setUsers] = useState([]);


    const [modal, setModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [deleteModal, setDeleteModal] = useState(false);


    useEffect(() => {
        getGroup();
    }, []);

    const openModal = () => setModal(!modal);
    const openEditModal = () => setEditModal(!editModal);
    const openDeleteModal = () => setDeleteModal(!deleteModal);


    function getGroup() {
        axios.get(url + "group/list")
            .then(res => {
                setGroup(res.data.object)
            });
    }

    function getCategory() {
        axios.get(url + "category")
            .then(res => {
                setCategories(res.data)
            });
    }

    function getTeacher() {
        axios.get(url + "group/getTeacher")
            .then(res => {
                setUsers(res.data.object)
            });
    }

    function getGroupDetails() {
        return {
            name: byId("name"),
            categoryId: byId("categoryId"),
            startHour: byId("startHour"),
            finishHour: byId("finishHour"),
            lessonStartedDate: byId("lessonStartedDate"),
            lessonFinishDate: byId("lessonFinishDate"),
            teacherId: byId("teacherId"),
            weekday: byId("weekday")
        }
    }

    function addGroup() {
        console.log(getGroupDetails())
        axios.post(url + "group", getGroupDetails())
            .then(() => {
                getGroup();
                toast.success("successfully saved group")
            }).catch(err => toast.error(err.response.data.message));
        openModal();
    }

    function editGroup() {
        axios.put(url + "group/edit/" + data.id, getGroupDetails())
            .then(() => {
                toast.success("successfully edit Group");
                getGroup();
            }).catch(err => toast.error(err.response.data.message));
        openEditModal();
    }

    function deleteGroup() {
        axios.delete(url + "group/delete/" + data.id)
            .then(() => {
                toast.success("successfully delete Group");
                openDeleteModal();
                getGroup();
            });
    }

    function getWeekdays() {
        axios.get(url + "group/weekday")
            .then(res => {
                setWeekdays(res.data.object)
            });
    }

    return (
        <>
            <Container>
                <Button color="success" className="add_btn mt-5 mb-4" onClick={() => {
                    getCategory()
                    openModal()
                    getTeacher()
                    getWeekdays()
                }
                }>Add Group</Button>
                <Table>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>CategoryId</th>
                        <th>StartHour</th>
                        <th>FinishHour</th>
                        <th>LessonStartedDate</th>
                        <th>LessonFinishDate</th>
                        <th>TeacherId</th>
                        <th>Weekday</th>
                        <th colSpan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {group.map((item, i) =>
                        <tr key={i}>
                            <td>{i + 1}</td>
                            <td>{item.name}</td>
                            <td>{item.category.name}</td>
                            <td>{item.startHour}</td>
                            <td>{item.finishHour}</td>
                            <td>{item.lessonStartedDate}</td>
                            <td>{item.lessonFinishDate}</td>
                            <td>{item.teacher.firstName} {item.teacher.lastName}</td>
                            <td>{item.weekday}</td>
                            <td><Button color="warning" outline onClick={() => {
                                openEditModal()
                                setData(item)
                                getCategory()
                                getWeekdays()
                                getTeacher()
                            }}>Edit</Button></td>
                            <td><Button color="danger" outline onClick={() => {
                                openDeleteModal();
                                setData(item);
                            }}>Delete</Button></td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </Container>


            <Modal isOpen={modal} toggle={openModal}>
                <ModalHeader toggle={openModal}>Add Group</ModalHeader>
                <ModalBody>
                    <Input className="mb-3" placeholder="Name" id="name"/>
                    <select className="form-select mb-3" id="categoryId" value={category}
                            onChange={e => setCategory(e.target.value)}>
                        {
                            categories.map(opt => <option value={opt.id}>{opt.name}</option>)
                        }
                    </select>
                    <Input type="datetime-local" className="mb-3" placeholder="startHour" id="startHour"/>
                    <Input type="datetime-local" className="mb-3" placeholder="finishHour" id="finishHour"/>
                    <Input type="date" className="mb-3" placeholder="lessonStartedDate" id="lessonStartedDate"/>
                    <Input type="date" className="mb-3" placeholder="lessonFinishDate" id="lessonFinishDate"/>
                    <select className="form-select mb-3" id="teacherId" value={user}
                            onChange={e => setUser(e.target.value)}>
                        {
                            users.map(opt => <option value={opt.id}>{opt.name}</option>)
                        }
                    </select>

                    <select className="form-select mb-3" id="weekday" value={weekday}
                            onChange={e => {setWeekday(e.target.value)}}>
                        {
                            weekdays.map(opt => <option>{opt}</option>)
                        }
                    </select>

                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openModal}>Close</Button>
                    <Button color="success" onClick={addGroup}>Save</Button>
                </ModalFooter>
            </Modal>


            <Modal isOpen={editModal} toggle={openEditModal}>
                <ModalHeader toggle={openEditModal}>Edit Group</ModalHeader>
                <ModalBody>
                    <Input defaultValue={data.name} className="mb-3" placeholder="Name" id="name"/>
                    <select className="form-select mb-3" id="categoryId" value={category}
                            onChange={e => setCategory(e.target.value)}>
                        {
                            categories.map(opt => <option value={opt.id}>{opt.name}</option>)
                        }
                    </select>
                    <Input defaultValue={data.startHour} type="datetime-local" className="mb-3" placeholder="startHour"
                           id="startHour"/>
                    <Input defaultValue={data.finishHour} type="datetime-local" className="mb-3"
                           placeholder="finishHour" id="finishHour"/>
                    <Input defaultValue={data.lessonStartedDate} type="date" className="mb-3"
                           placeholder="lessonStartedDate" id="lessonStartedDate"/>
                    <Input defaultValue={data.lessonFinishDate} type="date" className="mb-3"
                           placeholder="lessonFinishDate" id="lessonFinishDate"/>
                    <select defaultValue={data.teacherId} className="form-select mb-3" id="teacherId" value={user}
                            onChange={e => setUser(e.target.value)}>
                        {
                            users.map(opt => <option value={opt.id}>{opt.name}</option>)
                        }
                    </select>
                    <select defaultValue={data.weekday} className="form-select mb-3" id="weekday" value={weekday}
                            onChange={e => {
                                setWeekday(e.target.value);
                            }}>
                        {
                            weekdays.map(opt => <option>{opt}</option>)
                        }
                    </select>
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openEditModal}>Close</Button>
                    <Button color="success" onClick={editGroup}>Edit</Button>
                </ModalFooter>
            </Modal>


            <Modal isOpen={deleteModal} toggle={openDeleteModal}>
                <ModalHeader toggle={openDeleteModal}>Delete Group</ModalHeader>
                <ModalBody>
                    Siz haqiqatdan ham {data.name} ni uchirishga ishonchingiz komilmi?
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openDeleteModal}>Close</Button>
                    <Button color="danger" onClick={deleteGroup}>Delete</Button>
                </ModalFooter>
            </Modal>


        </>
    );
}

export default Group;