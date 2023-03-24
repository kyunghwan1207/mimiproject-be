import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { LoginState } from '../../state/loginState';
import Login from './Login';
import { UserIdState } from '../../state/userIdState';
import { CartState } from '../../state/cartState';
import style from './UserInfo.module.css';

function UserInfo() {
    const [userData, setUserData] = useState();
    const [isLogin, setIsLogin] = useRecoilState(LoginState);
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [userId, setUserId] = useRecoilState(UserIdState);

    let navigate = useNavigate();

    const moveTo = (locate) => {
        navigate(locate, {});
    }

    const handleLogoutClick = () => {
        setIsLogin(false); 
        setCartCnt(0);
        setUserId(0);
    }
    useEffect(() => {
        const url = `http://localhost:3001/users/${userId}`
        axios.get(url)
        .then(res => res.data)
        .then(res => setUserData(res));
    }, [])

    return (
        <>
            {
                !isLogin ? <Login /> :
                <div>
                    <h3>사용자 정보</h3>
                        {
                            userData &&
                            <div>
                                <div>
                                    <label>고객명</label>
                                    <span> {userData.name}</span>
                                </div>
                                <div>
                                    <label>이메일</label>    
                                    <span>{userData.email}</span>
                                </div>
                                <div>
                                    <label>주소</label>
                                    <span> {userData.roadAddress}</span>
                                </div>
                            </div>
                            
                        }
                    <button className='style.btn' onClick={handleLogoutClick}>로그아웃</button>
                    <button className='style.btn' onClick={() => moveTo("/change-password")}>비밀번호 변경</button>
                </div>
                
            }
            
        </>
    );
}

export default UserInfo;