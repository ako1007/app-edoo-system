import React, {useEffect, useState} from 'react';
import {Button, Input, Modal, ModalBody, ModalFooter, ModalHeader, Table} from "reactstrap";
import axios from "axios";
import {byId, url} from "../templates/api";
import {toast} from "react-toastify";

function Category() {
    const [category, setCategory] = useState([]);
    const [data, setData] = useState("");
    const [modal, setModal] = useState(false);
    const [editModal, setEditModal] = useState(false);
    const [deleteModal, setDeleteModal] = useState(false);

    useEffect(() => {
        getCategory();
    }, []);

    const openModal = () => setModal(!modal);
    const openEditModal = () => setEditModal(!editModal);
    const openDeleteModal = () => setDeleteModal(!deleteModal);


    function getCategory() {
        axios.get(url + "category")
            .then(res => {
                setCategory(res.data)
            });
    }

    function getCategoryDetails() {
        return {
            name: byId("name"),
            price: byId("price"),
            description: byId("description")
        }
    }

    function addCategory() {
        axios.post(url + "category/add", getCategoryDetails()).then(() => {
            getCategory();
            toast.success("successfully saved category")
        }).catch(err => toast.error(err.response.data.message));
        openModal();
    }

    function editCategory() {
        axios.put(url + "category/" + data.id, getCategoryDetails())
            .then(() => {
                toast.success("successfully edit Category");
                getCategory();
            }).catch(err => toast.error(err.response.data.message));
        openEditModal();
    }

    function deleteCategory() {
        axios.delete(url + "category/" + data.id)
            .then(() => {
                toast.success("successfully delete Category");
                openDeleteModal();
                getCategory();
            });
    }

    return (
        <>
            <div style={{margin: "0 4%"}}>
                <Button color="success" className="category_add_btn" onClick={openModal}>Add Category</Button>
                <Table>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th colSpan="2">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {category.map((item, i) =>
                        <tr key={i}>
                            <td>{i + 1}</td>
                            <td>{item.name}</td>
                            <td>{item.price}</td>
                            <td>{item.description}</td>
                            <td><Button color="warning" outline onClick={() => {
                                openEditModal();
                                setData(item);
                            }}>Edit</Button></td>
                            <td><Button color="danger" outline onClick={() => {
                                openDeleteModal();
                                setData(item);
                            }}>Delete</Button></td>
                        </tr>
                    )}
                    </tbody>
                </Table>
            </div>
            <Modal isOpen={modal} toggle={openModal}>
                <ModalHeader toggle={openModal}>Add Category</ModalHeader>
                <ModalBody>
                    <Input className="mb-3" placeholder="Name" id="name"/>
                    <Input className="mb-3" type="number" placeholder="Price" id="price"/>
                    <Input placeholder="Description" id="description"/>
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openModal}>Close</Button>
                    <Button color="success" onClick={addCategory}>Save</Button>
                </ModalFooter>
            </Modal>

            <Modal isOpen={editModal} toggle={openEditModal}>
                <ModalHeader toggle={openEditModal}>Edit Category</ModalHeader>
                <ModalBody>
                    <Input defaultValue={data.name} className="mb-3" placeholder="Name" id="name"/>
                    <Input defaultValue={data.price} className="mb-3" type="number" placeholder="Price" id="price"/>
                    <Input defaultValue={data.description} placeholder="Description" id="description"/>
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openEditModal}>Close</Button>
                    <Button color="success" onClick={editCategory}>Edit</Button>
                </ModalFooter>
            </Modal>

            <Modal isOpen={deleteModal} toggle={openDeleteModal}>
                <ModalHeader toggle={openDeleteModal}>Delete Category</ModalHeader>
                <ModalBody>
                    Siz haqiqatdan ham {data.name} ni uchirishga ishonchingiz komilmi?
                </ModalBody>
                <ModalFooter>
                    <Button color="secondary" onClick={openDeleteModal}>Close</Button>
                    <Button color="danger" onClick={deleteCategory}>Delete</Button>
                </ModalFooter>
            </Modal>


        </>
    );
}

export default Category;