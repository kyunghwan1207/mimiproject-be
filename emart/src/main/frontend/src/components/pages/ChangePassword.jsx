import React, { useEffect, useState } from 'react';
import { UserIdState } from '../../state/userIdState';
import { useRecoilValue } from 'recoil';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import style from './ChangePassword.module.css';

function ChangePassword() {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [currentPassword, setCurrentPassword] = useState();
    const [newPassword, setNewPassword] = useState();
    const [confirmNewPassword, setConfirmNewPassword] = useState();
    const [user, setUser] = useState();
    const [message, setMessage] = useState('');
    
    const userId = useRecoilValue(UserIdState);
    const navigate = useNavigate();

    const handleChangePasswordBtnClick = async () => {
        console.log('user: ', user);
        const userPassword = user.password;
        if(message !== '인증에 성공했습니다.'){
            alert("본인 인증을 진행해주시기 바랍니다!");
            return;
        }
        if(userPassword !== currentPassword){
            alert("현재 비밀번호가 일치하지 않습니다!");
            setCurrentPassword("");
            return;
        }
        if(newPassword !== confirmNewPassword){
            alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다");
            setNewPassword("");
            setConfirmNewPassword("");
            return;
        }
        const url = `http://localhost:3001/users/${userId}`
        
        let res = await axios.put(url,{
            "name": user.name,
            "password": newPassword,
            "email": user.email,
            "roadAddress": user.roadAddress 
        })
        alert("비밀번호가 정상적으로 변경되었습니다!");
        navigate(-1);
        return;
    }
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    }
    const handlePasswordChange = (e) => {
        setCurrentPassword(e.target.value);
    }
    const handleNewPasswordChange = (e) => {
        setNewPassword(e.target.value);
    }
    const handleConfirmNewPasswordChange = (e) => {
        setConfirmNewPassword(e.target.value)
    }

    const handleUserVerifyBtnClick = () => {
        if(user.email === email && user.password === currentPassword) {
            setMessage('인증에 성공했습니다.')
        } else {
            setMessage('인증에 실패했습니다.');
        }
    }
    
    

    useEffect(() => {
        axios.get(`http://localhost:3001/users/${userId}`)
        .then((res) => res.data)
        .then((res) => setUser(res))
        .catch((err) => console.log('changePassword / user'));
    }, [])
    return (
        <>
        <h3>비밀번호 변경</h3>
        <div>
            <label>이메일: </label>    
            <input name='email' type="email" placeholder='이메일을 입력해주세요' onChange={handleEmailChange}/>
        </div>
        <div>
        <label>현재 비밀번호: </label>    
            <input name='currentPassword' type="password" placeholder='비밀번호를 입력해주세요' onChange={handlePasswordChange}/>
        </div>
        <div>
            <button onClick={handleUserVerifyBtnClick}>인증</button>
            {
                message &&
                <span className={message == '인증에 성공했습니다.' ? style.goodMsgLabel : style.badMsgLabel}>{message}</span>
            }
        </div>
            
        <div>
        <label>새 비밀번호:</label>
            <input name='newPassword' type="password" placeholder='새로운 비밀번호를 입력해주세요' onChange={handleNewPasswordChange}/>
        </div>
            
        <div>
        <label>새 비밀번호 확인:</label>
            <input name='confirmNewPassword' type="password" placeholder='새로운 비밀번호를 다시 입력해주세요' onChange={handleConfirmNewPasswordChange}/>
        </div>
            
            <button className='style.btn' onClick={handleChangePasswordBtnClick}>비밀번호 변경</button>
        </>
    );
}

export default ChangePassword;