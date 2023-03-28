import React from 'react'
import { useEffect } from 'react';
import OrderProduct from './OrderProduct';
import style from './Order.module.css';
import axios from 'axios';
function Order(props) {
    const handleDeleteBtnClick = async () => {
        if(window.confirm("주문 내역에서 삭제하시겠습니까?")){
            const url = `/api/v1/orders/${props.orderId}`;
            let response = await axios.delete(url);
            
            console.log('delete / response: ', response);
            if (response.status >= 200 && response.status < 300) {
                alert("주문 내역에서 삭제되었습니다.");
                props.setDelCheck(!props.delCheck);
                return;
            } else {
                alert("주문 내역에서 삭제에 실패했습니다.");
                return;
            }
        } 

    }
    useEffect(() => {
        console.log("ORder / props: ", props);
        console.log("props.orderProductList: ", props.orderProductList);
    }, [])

    return (
        <div className={style.orderBox}>
            <button className={style.xBtn}
                    onClick={handleDeleteBtnClick}>주문취소</button>
            <h5>주문일자: {props.orderDate}</h5>
            <p>주문 번호: {props.orderNumber}</p>
            
            {
                props.orderProductList && 
                props.orderProductList.map((item) => (
                    <OrderProduct 
                        key={item.id}
                        name={item.name}
                        orderPrice={item.orderPrice}
                        orderQty={item.orderQty}
                        price={item.price}
                        thumbnail={item.thumbnail}
                    />
                ))
            }
        </div>
    );
}

export default Order;