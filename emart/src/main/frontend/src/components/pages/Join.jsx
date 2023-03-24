import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import style from './Join.module.css';
import Modal from 'react-modal';
import DaumPostcode from 'react-daum-postcode';

function Join() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isDuplicate, setIsDuplicate] = useState(true);
    const [userList, setUserList] = useState();
    const [message, setMessage] = useState('');
    const [name, setName] = useState('');
    const [roadAddress, setRoadAddress] = useState('');
    const [isOpen, setIsOpen] = useState(false); 

    const emailRef = useRef('');
    const namedRef = useRef('');
    const passwordRef = useRef('');
    const confirmPasswordRef = useRef('');

    const navigate = useNavigate();
    
    const completeHandler = (data) =>{
        setRoadAddress(data.roadAddress);
        setIsOpen(false); 
    }

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

    const handleJoinFormSubmit = async (e) => {
        e.preventDefault();
        if(email === ''){
            alert("이메일을 입력해주시기 바랍니다!");
            setIsDuplicate(true);
            setMessage("");
            emailRef.current.focus();
            return;
        } else if(name === ''){
            alert("이름을 입력해주시기 바랍니다!");
            namedRef.current.focus();
            return;
        } else if(password === ''){
            alert("비밀번호를 입력해주시기 바랍니다!");
            passwordRef.current.focus();
            return;
        } else if(confirmPassword === ''){
            alert("재입력 비밀번호를 입력해주시기 바랍니다!");
            confirmPassword.current.focus();
            return;
        } else if(roadAddress === ''){
            alert("주소를 입력해주시기 바랍니다!");
            return;
        }
        if(isDuplicate){
            alert("이메일 중복검사를 완료해주시기 바랍니다!");
            return;
        }
        if(password !== confirmPassword){
            alert("비밀번호와 재입력 비밀번호가 일치하지 않습니다.")
            setPassword("")
            setConfirmPassword("")
            passwordRef.current.focus();
            return;
        }

        const newUserId = getNextId();
        const url = 'http://localhost:3001/users';
        const data = {
            "id": newUserId,
            "email": email,
            "name": name,
            "password": password,
            "roadAddress": roadAddress 
        }
        console.log('before post data: ', data);
        try {
            let res = await axios(url, 
            {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                data: data
            })
            console.log("join res: ", res);
            if(res.status >= 200 && res.status < 300){
                alert("회원가입에 성공했습니다!");
                navigate("/login", {});
            } else {
                alert("회원가입에 실패했습니다!");
                setName("");
                setEmail("");
                setIsDuplicate(true);
                setPassword("")
                setConfirmPassword("");
                setMessage("");
                return;
            }

        } catch (err) {
            console.log("join err: ", err);
        }
    }

    const handleChangeEmail = (e) => {
        setEmail(e.target.value);
    }
    const handleChangePassword = (e) => {
        setPassword(e.target.value);
    }
    const handleChangeConfirmPassword = (e) => {
        setConfirmPassword(e.target.value);
    }
    const handleChangeName = (e) => {
        setName(e.target.value);
    }
    const handleClickCheckDuplicateBtn = () => {
        console.log('email: ', email);
        if(email === undefined || email.trim() === ''){
            setIsDuplicate(true);
            setMessage("이메일을 입력해주시기 바랍니다!");
            return;
        }
        if(userList.length > 0){
            for(let i=0; i<userList.length; i++){
                if(email === userList[i].email){
                    setIsDuplicate(true);
                    setMessage("이미 가입된 이메일입니다!");
                    return;
                }
            }
        } 
        setIsDuplicate(false);
        setMessage("사용가능한 이메일입니다!");
        return;
    }
    const getNextId = () => {
        if(userList === undefined || userList.length === 0){
            return 1;
        }
        return userList[userList.length-1].id + 1;
    }
    const handleMoveLoginClick = () => {
        navigate("/login", {});
    }
    useEffect(() => {
        const url_getUserList = 'http://localhost:3001/users';
        axios.get(url_getUserList)
        .then((res) => res.data)
        .then((res) => setUserList(res))
    }, [])
    return (
        <>
            <h3>회원가입</h3>
            <div className={style.modalContainer}>
                <label className={style.labelAddress}>주소:</label>
                <input className={style.address} value={roadAddress} readOnly placeholder='주소를 입력해주세요' onClick={toggle}/>
                <Modal isOpen={isOpen} ariaHideApp={false} style={modalStyles}>
                    <button onClick={toggle}>x</button>
                    <DaumPostcode onComplete={completeHandler} height="100%" />
                </Modal>   
            </div>
            <div>
                <label>이름: </label>
                <input name='name' ref={namedRef} placeholder='이름을 입력해주세요' value={name} onChange={handleChangeName}/>    
            </div>
            
            <div>
                <label>이메일: </label>
                <input name='email'ref={emailRef} placeholder='이메일을 입력해주세요' value={email} onChange={handleChangeEmail}/>
                <span className={style.duplicateCheckBtn}>
                    <button onClick={handleClickCheckDuplicateBtn}>중복검사</button>
                    {
                        message &&
                            <p className={message == "사용가능한 이메일입니다!" ? style.goodMsgLabel : style.badMsgLabel}>{message}</p>
                    }
                </span>
            </div>
            <div>
                <label>비밀번호: </label>
                <input type="password" name='password' ref={passwordRef} placeholder='비밀번호를 입력해주세요' value={password} onChange={handleChangePassword}/>  
            </div>
            <div>
                <label>비밀번호 확인: </label>
                <input type="password" name='confirmPassword' ref={confirmPasswordRef} placeholder='비밀번호를 다시 입력해주세요' value={confirmPassword} onChange={handleChangeConfirmPassword}/>  
            </div>    
                <button className={style.joinBtn} onClick={handleJoinFormSubmit}>회원가입</button>
                <button className={style.loginBtn} onClick={handleMoveLoginClick}>로그인</button>
        </>
    );
}

export default Join;