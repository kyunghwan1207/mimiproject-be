import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { CartState } from '../../state/cartState';

function VerifyPassword(test) {
    const [password, setPassword] = useState("");
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    let navigate = useNavigate(); // userInfo 에서 전달받은 test 값을 가져와서 다음으로 진행할 nextUrl 값으로 사용해보자

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    }

    const handleCompBtnClick = async (e) => {
        console.log("handleCompBtnClick !!");
        e.preventDefault();
        console.log("handleCompBtnClick/password: ", password);

        try {
            let res = await axios(
                '/api/v1/users/check-password',
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                     "password": password
                    }
                });
            console.log("handleUserVerify / res: ", res);
            if (res.status >= 200 && res.status < 300) {
                alert("인증에 성공했습니다.");
                navigate("/edit-user-info", {}); // 회원정보 수정 컴포넌트로 이동
                return;

            } else {
                alert("인증에 실패했습니다. 다시 시도해주시기 바랍니다.");
                setPassword("");
                return;
            }
        } catch (err) {
            console.log("[Error|POST] handleUserVerifyBtnClick: ", err);
        }
    }
    useEffect(() => {
        console.log("test.test = ", test.test);
        console.log("Verify Password/useEffect() call");
        axios.get('/api/v1/users/my-info')
        .then(res => {
            console.log("VerifyPw / useEffect / res: ", res);
            if (res.status >= 200 && res.status < 300) {
                setCartCnt(res.data.count);
                return;
            }
        })
        .catch(err => {
            console.log("[Error|GET] userinfo: ", err);
            return;
        })
    }, [])

    return (
        <div>
            <h5>현재 비밀번호를 입력해주세요</h5>
            <input value={password} onChange={handlePasswordChange}/>
            <button onClick={handleCompBtnClick}>완료</button>
        </div>
    );
}

export default VerifyPassword;