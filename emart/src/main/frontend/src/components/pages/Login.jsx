import React, {useState, useEffect, useRef} from 'react';
import style from './Login.module.css';
import axios from 'axios';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { LoginState } from '../../state/loginState';
import { useNavigate } from 'react-router-dom';
import { UserIdState } from '../../state/userIdState';
import { CartState } from '../../state/cartState';

function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const emailRef = useRef('');
    const [isLogin, setIsLogin] = useRecoilState(LoginState);
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [userIdState, setUserIdState] = useRecoilState(UserIdState);

    const navigate = useNavigate();

    const updateCartCnt = async (userId) => {
        let response = await axios.get(`http://localhost:3001/carts?userId=${userId}`);
        setCartCnt(response.data.length);
    }

    const handleChangeEmail = (e) => {
        setEmail(e.target.value);
    }
    const handleChangePassword = (e) => {
        setPassword(e.target.value);
    }
    const handleMoveJoinBtnClick = () => {
        navigate("/join", {});
    }
    return (
        <>
            <h3>로그인</h3>
            <form
            action="/api/login"
            method="POST">
                <div>
                    <label>이메일: </label>
                    <input name='email' ref={emailRef} type="email" value={email} placeholder='이메일을 입력해주세요' onChange={handleChangeEmail}/>
                </div>
                <div>
                    <label>비밀번호: </label>
                    <input name='password' type="password" placeholder='비밀번호를 입력해주세요' value={password} onChange={handleChangePassword}/>
                </div>    
                    <button className={style.loginBtn} type="submit" >로그인</button>
            </form>
                    <button className={style.joinBtn} onClick={handleMoveJoinBtnClick}>회원가입</button>
        </>
        
    );
}

export default Login;