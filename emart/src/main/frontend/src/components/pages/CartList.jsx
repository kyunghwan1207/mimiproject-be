import React, { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { LoginState } from '../../state/loginState';
import { UserIdState } from '../../state/userIdState';
import axios from 'axios';
import CartItem from './CartItem';
import { useNavigate } from 'react-router-dom';

function CartList() {
    const [myCartList, setMyCartList] = useState();
    const [message, setMessage] = useState('');
    const [delCheck, setDelCheck] = useState(false);
    const userId = useRecoilValue(UserIdState);
    const loginState = useRecoilValue(LoginState);
    const navigate = useNavigate();

    useEffect(() => {
        if(!loginState){
            if(window.confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                navigate("/login", {})
            } else {
                navigate("/", {});
            }
        }
        axios.get(`http://localhost:3001/carts?userId=${userId}`)
        .then((res) => {
            console.log('initT CartList / res: ', res);
            if(res.data == undefined || res.data.length === 0){
                setMessage('장바구니가 비었습니다');
                setMyCartList(undefined);
                return;
            }
            setMyCartList(res.data);
        })
    }, [delCheck])
    return (
        <div>
            <h3>장바구니 목록</h3>
                {
                    message !== '' && 
                    <p>{message}</p>
                }
                {
                    myCartList && 
                    myCartList.map(item => (
                        <CartItem 
                            key={item.id}
                            props={item}
                            delCheck={delCheck}
                            setDelCheck={setDelCheck}
                        />
                    ))
                }
        </div>
    );
}

export default CartList;