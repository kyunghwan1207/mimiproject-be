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
    const [userData, setUserData] = useState({});
    // const [isLogin, setIsLogin] = useRecoilState(LoginState);
    const [isLogin, setIsLogin] = useState({});
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [userId, setUserId] = useRecoilState(UserIdState);

    let navigate = useNavigate();

    const moveTo = (locate) => {
        navigate(locate, {test: "testVal"});
    }

    const handleLogoutClick = () => {
        setIsLogin(false); 
        setCartCnt(0);
        setUserId(0);
        
        axios.get(`/api/v1/users/logout`)
        .then(res => alert("로그아웃 되었습니다."))
        .catch(err => alert("로그아웃에 실패했습니다."));
        
    }
    useEffect(() => {
        axios.get(`/api/v1/users/my-info`)
        .then(res => {
            console.log('res = ', res);
            if (res.status >= 200 && res.status < 300) {
                setIsLogin(true);
                setCartCnt(res.data.count);
                return res.data;    
            }
        })
        .then(res => setUserData(res))
        .catch(err => {
            console.log("error = ", err);
            setIsLogin(false);
        })
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
                                    <span> {userData.username}</span>
                                </div>
                                <div>
                                    <label>이메일</label>    
                                    <span>{userData.email}</span>
                                </div>
                                <div>
                                    <label>주소</label>
                                    <span> {userData.address}</span>
                                </div>
                                <div>
                                    <label>전화번호</label>
                                    <span> {userData.phoneNumber}</span>
                                </div>
                            </div>
                            
                        }
                    <button className='style.btn' onClick={handleLogoutClick}>로그아웃</button>
                    <button className='style.btn' onClick={() => moveTo("/verify-password")}>회원정보 변경</button>
                </div>
                
            }
            
        </>
    );
}

export default UserInfo;