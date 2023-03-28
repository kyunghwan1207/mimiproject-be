import axios from 'axios';
import React from 'react'
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { CartState } from '../../state/cartState';
import Order from './Order';

function OrderList() {
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [message, setMessage] = useState('');
    const [orderList, setOrderList] = useState();
    const [delCheck, setDelCheck] = useState(false);
    useEffect(() => {
        setCartCnt(0);
        axios.get(`/api/v1/orders`)
        .then((res) => {
            console.log("orderList /res", res);
            if (res.status == 204) {
                setMessage("주문 내역이 없습니다.");
                setOrderList(undefined);
                return;
            }
            return res.data;
        })
        .then((res) => {
            console.log("orderList / res: ", res);
            setOrderList(res);
        })

    }, [delCheck])
    const handleMonthFilterBtnClick = (month) => {
        console.log("handleMonthFilterBtnClick with month: ", month);
        //cartList 와 유사하도록 로직구현
        axios.get(`/api/v1/orders/${month}`)
        .then((res) => {
            console.log("handleMonthFilterBtnClick: ", res);
            setOrderList(res.data);
        })
    }
    return (
        <>
        <h3>주문 내역</h3>
        <input type="button" onClick={() => handleMonthFilterBtnClick(6)} value="6개월"/>
        <input type="button" onClick={() => handleMonthFilterBtnClick(3)} value="3개월"/>
        <input type="button" onClick={() => handleMonthFilterBtnClick(1)} value="1개월"/>
        {
            message !== '' &&
            <p>{message}</p>
        }
        {
            orderList &&
            orderList.map((item, index) => (
                <Order 
                    key={index}
                    orderId={item.orderId}
                    orderDate={item.orderDate}
                    orderNumber={item.orderNumber}
                    orderProductList={item.orderProductList}
                    delCheck={delCheck}
                    setDelCheck={setDelCheck}
                />
            ))
        }
        </>
    );
}
export default OrderList;