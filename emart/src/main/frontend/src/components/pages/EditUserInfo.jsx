import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import { CartState } from '../../state/cartState';
import { useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import DaumPostcode from 'react-daum-postcode';

function EditUserInfo() {
    const [email, setEmail] = useState("");
    const [newEmail, setNewEmail] = useState("");
    const [name, setName] = useState("");
    const [newName, setNewName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [newPhoneNumber, setNewPhoneNumber] = useState("");
    const [password, setPassword] = useState("");
    const [inputPassword, setInputPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [address, setAddress] = useState("");
    const [newAddress, setNewAddress] = useState("");
    const [isOpen, setIsOpen] = useState(false); 

    const [cartCnt, setCartCnt] = useRecoilState(CartState);

    let navigate = useNavigate();

    const moveTo = (locate) => {
        navigate(locate, {});
    }

    const handleChangeEmail = (e) => {
        setNewEmail(e.target.value);
    }
    const handleChangeName = (e) => {
        setNewName(e.target.value);
    }
    const handleChangePhoneNumber = (e) => {
        setNewPhoneNumber(e.target.value);
    }

    const handleChangeInputPassword = (e) => {
        setInputPassword(e.target.value);    
    }
    const handleChangeNewPassword = (e) => {
        setNewPassword(e.target.value);
    }
    const handleChangeConfirmPassword = (e) => {
        setConfirmPassword(e.target.value);
    }
    const handleEmailEidtBtnClick = async (e) => {
        e.preventDefault();
        // 이메일 수정 post요청
        if (email === newEmail) {
            alert("이메일이 기존과 동일합니다!");
            setNewEmail(email)
            return;
        }
        try {
            let res = await axios(
                '/api/v1/users/edit-email',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "newEmail": newEmail
                    }
                });
            console.log("edit email post/res: ", res);
        } catch (err) {
            console.log("edit email post/err: ", err);
        }
        
    }
    const handleNameEditBtnClick = async (e) => {
        e.preventDefault();
        // 이름 수정 post 요청
        if (name === newName) {
            alert("이름이 기존과 동일합니다!");
            setNewName(name);
            return;
        }
        try {
            let res = await axios(
                '/api/v1/users/edit-username',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "newUserName": newName
                    }
                });
            console.log("edit username post/res: ", res);
        } catch (err) {
            console.log("edit username post/err: ", err);
        }
    }
    const handlePhoneNumberEditBtnClick = async (e) => {
        e.preventDefault();
        // 핸드폰 번호 수정 post 요청
        if (phoneNumber === newPhoneNumber) {
            alert("핸드폰 번호가 기존과 동일합니다!");
            setNewPhoneNumber(phoneNumber);
            return;
        }
        try {
            let res = await axios(
                '/api/v1/users/edit-phoneNumber',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "newPhoneNumber": newPhoneNumber
                    }
                });
            console.log("edit ph num post/res: ", res);
        } catch (err) {
            console.log("edit ph nu, post/err: ", err);
        }
    }

    const handleAddressEditBtnClick = async (e) => {
        e.preventDefault();
        // 주소 수정 post 요청
        if (address === newAddress) {
            alert("주소가 기존과 동일합니다!");
            setNewAddress(address);
            return;
        }
        try {
            let res = await axios(
                '/api/v1/users/edit-address',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "newAddress": newAddress
                    }
                });
            console.log("edit address post/res: ", res);
        } catch (err) {
            console.log("edit address post/err: ", err);
        }

    }

    const handlePasswordEditBtnClick = async (e) => {
        e.preventDefault();
        // 비밀번호 변경 post 요청
        try {
            let res = await axios(
                '/api/v1/users/check-password',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "password": inputPassword
                    }
                });
            console.log("handleUserVerify / res: ", res);
            if (res.status >= 200 && res.status < 300) { // 현재 비밀번호 인증 성공한 경우
                if (newPassword != confirmPassword) {
                    alert("재 입력 비밀번호가 일치하지 않습니다.");
                    setNewPassword("");
                    setConfirmPassword("");
                    return;
                } 
                changePassword();


            } else {
                alert("인증에 실패했습니다. 다시 시도해주시기 바랍니다.");
                setInputPassword("");
                return;
            }
        } catch (err) {
            console.log("[Error|POST] handleUserVerifyBtnClick: ", err);
        }
    }
    const changePassword = async (e) => {
        e.preventDefault();
        // 비밀번호 변경 POST 요청
        try {
            let res = await axios(
                '/api/v1/users/edit-password',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "newPassword": newPassword
                    }
                });
            console.log("edit pw post/res: ", res);
        } catch (err) {
            console.log("edit pw post/err: ", err);
        }
    }
    const completeHandler = (data) =>{
        console.log("completeHandler / data: ", data);
        setNewAddress(data.newAddress);
        setIsOpen(false); 
    }
    useEffect(() => {
        console.log("editUserInfo change!");

        axios.get(`/api/v1/users/my-info`)
        .then(res => {
            console.log('res = ', res);
            if (res.status >= 200 && res.status < 300) {
                setCartCnt(res.data.count);
                return res.data;    
            }
        })
        .then(res => {
            setEmail(res.email);
            setNewEmail(res.email);
            setName(res.username);
            setNewName(res.username);
            setPhoneNumber(res.phoneNumber);
            setNewPhoneNumber(res.phoneNumber);
            setPassword(res.password);
            setAddress(res.address);
            setNewAddress(res.address);
        })
        .catch(err => {
            console.log("error = ", err);
        })
    }, [])

    const modalStyles = {
        overlay: {
            backgroundColor: "rgba(0,0,0,0.5)",
        },
        content: {
            left: "0",
            margin: "auto",
            width: "388px",
            height: "300px",
            padding: "0",
            overflow: "hidden",
        },
    };

    const toggle = () => {
        setIsOpen(!isOpen);
    }
    
    return (
        <>
        <h3>회원정보 수정</h3>
        <div>
            <label>이메일:</label>
            <input value={newEmail} onChange={handleChangeEmail}/>
            <div>
                <button onClick={handleEmailEidtBtnClick}>이메일 수정</button>
                <button onClick={() => setNewEmail(email)}>수정 취소</button>
            </div>
            
        </div>
        <div>
            <label>이름:</label>
            <input value={newName} onChange={handleChangeName}/>
            <div>
                <button onClick={handleNameEditBtnClick}>이름 수정</button>
                <button onClick={() => setNewName(name)}>수정 취소</button>
            </div>
            
        </div>
        
        <div>
            <label>주소:</label>
            <input value={newAddress} readOnly placeholder='새로운 주소를 입력해주세요' onClick={toggle} style={{ width: '300px' }}/>
            <Modal isOpen={isOpen} ariaHideApp={false} style={modalStyles}>
                <button onClick={toggle}>x</button>
                <DaumPostcode onComplete={completeHandler} height="100%" />
            </Modal>   
        </div>
        <div>
            <button onClick={handleAddressEditBtnClick}>주소 수정</button>   
            <button onClick={() => setNewAddress(address)}>수정 취소</button> 
        </div>
        
        <div>
            <label>휴대폰 번호:</label>
            <input value={newPhoneNumber} onChange={handleChangePhoneNumber}/>
            <div>
                <button onClick={handlePhoneNumberEditBtnClick}>휴대폰 번호 수정</button>   
                <button onClick={() => {setNewPhoneNumber(phoneNumber)}}>수정 취소</button>
            </div>
            
        </div>
        <div>
            <h5>비밀번호 변경</h5>
            <div>
                <label>현재 비밀번호</label>
                <input value={inputPassword} onChange={handleChangeInputPassword}/>
            </div>
            <div>
                <label>새 비밀번호</label>
                <input value={newPassword} onChange={handleChangeNewPassword}/>
            </div>
            <div>
                <label>비밀번호 다시 입력</label>
                <input value={confirmPassword} onChange={handleChangeConfirmPassword}/>
                <button onClick={handlePasswordEditBtnClick}>비밀번호 수정</button>
            </div>
            
        </div>
        <br/>
        <br/>
        <div>
            <button onClick={() => moveTo("/user-info")}>내 정보보러가기</button>
        </div>
        
        </>
    );

}

export default EditUserInfo;