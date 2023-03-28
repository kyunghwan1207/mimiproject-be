import React, { useState, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import { LoginState } from '../../state/loginState';
import { UserIdState } from '../../state/userIdState';
import axios from 'axios';
import CartItem from './CartItem';
import { useNavigate } from 'react-router-dom';
import { formatMoney } from '../globalFunction/formatMoney';
import style from './CartList.module.css';
import Modal from 'react-modal';
import InputterPay from './InputterPay';

function CartList() {
    const [myCartList, setMyCartList] = useState();
    const [message, setMessage] = useState('');
    const [delCheck, setDelCheck] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);
    const [isOpen, setIsOpen] = useState(false); 
    const [userSimplePassword, setUserSimplePassword] = useState('');
    const [epay, setEpay] = useState(0);
    const [simplePassword, setSimplePassword] = useState('');
    const [payCheck, setPayCheck] = useState(false);
    const [productList, setProductList] = useState();
    const [cartId, setCartId] = useState();

    const userId = useRecoilValue(UserIdState);
    const loginState = useRecoilValue(LoginState);
    const navigate = useNavigate();

    let tPrice = 0;
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
    const handlePayBtnClick = () => {
        console.log("handlePayBtnClick !!");
        setIsOpen(true);
    }
    const getTotalPrice = (arr) => {
        tPrice = 0;
        for(let i = 0; i < arr.length; i++) {
            tPrice += (arr[i].price * arr[i].qty);
        }
        setTotalPrice(tPrice);
    }
    const getProductList = (arr) => {
        let product_list = new Array(arr.length);
        for (let i = 0; i < arr.length; i++) {
            let p = {}
            p.productId = arr[i].id; // productId
            p.price = arr[i].price;
            p.productQty = arr[i].productQty;
            p.orderQty = arr[i].qty; // 장바구니에 담긴 갯수
            product_list[i] = p;
        }
        console.log("product_list: ", product_list);
        setProductList(product_list);
    }

    useEffect(() => {
        axios.get(`/api/v1/carts`)
        .then((res) => {
            console.log('initT CartList / res: ', res);
            if(res.data == undefined || res.data.length === 0){
                setMessage('장바구니가 비었습니다');
                setMyCartList(undefined);
                return;
            }
            console.log("res.data[0]: ", res.data[0]);
            console.log("cartId 구하자! & cart에 각 제품이 몇개씩 들었는지-orderQty 구하자!");
            setUserSimplePassword(res.data[0].simplePassword);
            setEpay(res.data[0].epay);
            setMyCartList(res.data);
            setCartId(res.data[0].cartId);

            return res;
        })
        .then((res) => {
            console.log("res.data.length: ", res.data.length);
            getTotalPrice(res.data); // [{price: int, qty: int}, {} ...]
            getProductList(res.data);
            console.log("myCartList obj: ", myCartList);
        })
    }, [delCheck, payCheck])
    return (
        <>
            <h3>장바구니 목록</h3>
                {
                    myCartList && 
                    myCartList.map((item) => (
                        <CartItem 
                            key={item.id}
                            props={item}
                            delCheck={delCheck}
                            setDelCheck={setDelCheck}
                            setTotalPrice={setTotalPrice}
                            totalPrice={totalPrice}
                            
                        />
                    ))
                }    
                {
                    message !== '' && 
                    <p>{message}</p>
                }
                {
                    message === '' &&
                    <div className={style.totalPrice_div}>
                        <h3>총 결제 금액</h3>
                        <p className={style.totalPrice_value}>{formatMoney(totalPrice)}</p>
                        <button onClick={handlePayBtnClick}>결제</button>
                        {/* <input className={style.address} value={simplePassword} type="password" readOnly placeholder='인증비밀번호 6자리 입력해주세요'/> */}
                        <Modal isOpen={isOpen} ariaHideApp={false} style={modalStyles}>
                            <InputterPay
                                setIsOpen={setIsOpen}
                                userPassword={userSimplePassword}
                                password={simplePassword} 
                                setPassword={setSimplePassword}
                                payCheck={payCheck}
                                setPayCheck={setPayCheck}
                                epay={epay}
                                totalPrice={totalPrice}
                                productList={productList}
                                cartId={cartId}
                            />
                        </Modal> 
                    </div>
                }
        </>
    );
}

export default CartList;