import React, {useEffect, useState} from 'react';
import {Button, Input, Modal, ModalBody, ModalFooter, ModalHeader, Table} from "reactstrap";
import axios from "axios";
import {byId, url} from "../templates/api";
import {toast} from "react-toastify";

function Aware() {
    const [aware, setaware] = useState([]);
    const [data, setData] = useState("");
    const [modal, setModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [deleteModal, setDeleteModal] = useState(false);

    useEffect(() => {
        getaware();
    }, []);

    const openModal = () => setModal(!modal);
    const openEditModal = () => setEditModal(!editModal);
    const openDeleteModal = () => setDeleteModal(!deleteModal);


    function getaware() {
        axios.get(url + "aware")
            .then(res => {
                console.log(res)
                setaware(res.data._embedded.list)
            });
    }

    function getawareDetails() {
        return {
            name: byId("name"),
        }
    }

    function addAware() {
        axios.post(url + "aware", getawareDetails()).then(() => {
            getaware();
            toast.success("successfully saved category")
        }).catch(err => toast.error(err.response.data.message));
        openModal();
    }

    function editaware() {
        axios.put(url + "aware/" + data.id, getawareDetails())
            .then(() => {
                toast.success("successfully edit aware");
                getaware();
            }).catch(err => toast.error(err.response.data.message));
        openEditModal();
    }

    function deleteaware() {
        axios.delete(url + "aware/" + data.id)
            .then(() => {
                toast.success("successfully delete aware");
                openDeleteModal();
                getaware();
            });
    }

    return (
        <>
            <div style={{margin: "0 4%"}}>
                <Button style={{marginTop: "10%"}} color="success" className="aware_add_btn mb-3" onClick={openModal}>add
                    aware</Button>
                <Table bordered>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th colSpan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {aware.map((item, i) => <tr key={i}>
                        <td>{i + 1}</td>
                        <td>{item.name}</td>
                        <td><Button color="warning" outline onClick={() => {
                            openEditModal();
                            setData(item);
                        }}>Edit</Button>
                        </td>
                        <td><Button color="danger" outline onClick={() => {
                            openDeleteModal();
                            setData(item);
                        }}>Delete</Button></td>
                    </tr>)}
                    </tbody>
                </Table>
            </div>
            <Modal isOpen={modal} toggle={openModal}>
                <ModalHeader toggle={openModal}>Add aware</ModalHeader>
                <ModalBody>
                    <Input className="mb-3" placeholder="Name" id="name"/>
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openModal}>Close</Button>
                    <Button color="success" onClick={addAware}>Save</Button>
                </ModalFooter>
            </Modal>

            <Modal isOpen={editModal} toggle={openEditModal}>
                <ModalHeader toggle={openEditModal}>Edit aware</ModalHeader>
                <ModalBody>
                    <Input defaultValue={data.name} className="mb-3" placeholder="Name" id="name"/>
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openEditModal}>Close</Button>
                    <Button color="success" onClick={editaware}>Edit</Button>
                </ModalFooter>
            </Modal>

            <Modal isOpen={deleteModal} toggle={openDeleteModal}>
                <ModalHeader toggle={openDeleteModal}>Delete aware</ModalHeader>
                <ModalBody>
                    Siz haqiqatdan ham {data.name} ni uchirishga ishonchingiz komilmi?
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openDeleteModal}>Close</Button>
                    <Button color="danger" onClick={deleteaware}>Delete</Button>
                </ModalFooter>
            </Modal>
        </>
    )
        ;
}

export default Aware;