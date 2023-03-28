import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { LoginState } from '../../state/loginState';
import Login from './Login';
import { UserIdState } from '../../state/userIdState';
import { CartState } from '../../state/cartState';
import style from './UserInfo.module.css';
import ChargeEpay from './ChargeEpay';
import "./Inputter.css"
import Modal from 'react-modal';
import { formatMoney } from '../globalFunction/formatMoney';

function UserInfo() {
    const [userData, setUserData] = useState({});
    // const [isLogin, setIsLogin] = useRecoilState(LoginState);
    const [isLogin, setIsLogin] = useState({});
    const [isOpenChargeModal, setIsOpenChargeModal] = useState(false);
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
        .then(res =>
             alert("로그아웃 되었습니다."))
        .catch(err => {
            alert("로그아웃 되었습니다.");
            navigate('/', {});
        });
        
    }
    const handleChargeEpayBtnClick = () => {
        setIsOpenChargeModal(true);
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
                                <div>
                                    <label>Epay 잔액</label>
                                    <span> {formatMoney( (Number) (userData.epay))}</span>
                                </div>
                            </div>
                            
                        }
                        <div>
                            <button className='style.btn' onClick={handleLogoutClick}>로그아웃</button>
                        </div>
                        <div>
                            <button className='style.btn' onClick={() => moveTo("/verify-password")}>회원정보 변경</button>
                        </div>
                        <div>
                            <button className='style.btn' 
                                onClick={() => moveTo("/my-like-products")}>
                                내가 찜한 상품보기
                            </button>
                        </div>
                        <div>
                            <button className='style.btn' 
                                onClick={handleChargeEpayBtnClick}>
                                Epay 충전하기
                            </button>
                            <Modal isOpen={isOpenChargeModal} ariaHideApp={false} style={modalStyles}>
                                <ChargeEpay
                                    epay={userData.epay}
                                    setIsOpenChargeModal = {setIsOpenChargeModal}
                                />
                            </Modal>
                        </div>
                        <div>
                            <button className='style.btn' 
                                onClick={() => moveTo("/order-list")}>
                                주문 내역보기
                            </button>
                        </div>
                </div>
                
            }
            
        </>
    );
}

export default UserInfo;