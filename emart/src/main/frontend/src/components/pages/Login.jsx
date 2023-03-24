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
    const handleLoginFormSubmit = async (e) => {
        e.preventDefault();
        console.log('Login / 로그인하기 전 사용지 id: ', userIdState);
//        axios.get(`http://localhost:3001/users?email=${email}&password=${password}`)
        let url = 'http://localhost:8080/api/v1/users/login'
        let request_data = {
            "email": email,
            "password": password
        }
        try {
            let res = await axios({
                            method: "post",
                            url: "/api/v1/users/login/",
                            headers: { "Content-Type": "application/json" },
                            data: JSON.stringify(request_data),
                          });
            console.log('res: ', res);
            if(res.data.length === 0 || res.status < 200 && res.status >= 300){
                alert("이메일이나 비밀번호를 다시 확인해주시기 바랍니다!")
                setIsLogin(false);
                setEmail("")
                setPassword("")
                emailRef.current.focus();
                return;
            }
            alert("로그인에 성공하셨습니다!")
            console.log('Login / 로그인한 사용지 id: ', res.data[0].id);
            setIsLogin(true);
            setUserIdState(res.data[0].id);
            updateCartCnt(res.data[0].id);

            window.location.href="/";
        } catch (err) {
            console.log('err: ', err);
        }
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

                    <label>이메일: </label>
                    <input name='email' ref={emailRef} type="email" value={email} placeholder='이메일을 입력해주세요' onChange={handleChangeEmail}/>


                    <label>비밀번호: </label>
                    <input name='password' type="password" placeholder='비밀번호를 입력해주세요' value={password} onChange={handleChangePassword}/>

                    <button className={style.loginBtn} type="submit" >로그인</button>
            </form>
                    <button className={style.joinBtn} onClick={handleMoveJoinBtnClick}>회원가입</button>
        </>
        
    );
}

export default Login;