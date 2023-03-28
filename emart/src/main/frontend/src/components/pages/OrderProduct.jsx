import React from 'react'
import { useEffect } from 'react';
import { formatMoney } from '../globalFunction/formatMoney';
import style from './OrderProduct.module.css';

function OrderProduct(props) {
    useEffect(() => {
        console.log("OrderProduct / props: ", props);
    }, [])
    return (
        <div className={style.productBox}>
            <img className={style.productImg} src={props.thumbnail} alt={props.name}/>
            <h3>{props.name}</h3>
            <p>상품 가격: {formatMoney(props.orderPrice)}</p>
            <p>주문 수량: {props.orderQty}</p>
        </div>
    );
}
export default OrderProduct;