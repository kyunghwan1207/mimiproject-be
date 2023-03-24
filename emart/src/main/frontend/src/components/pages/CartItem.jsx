import axios from 'axios';
import React, { useState, useEffect } from 'react';
import style from './CartItem.module.css';
import { CartState } from '../../state/cartState';
import { useRecoilState } from 'recoil';
import { formatMoney } from '../globalFunction/formatMoney';

function CartItem({props, delCheck, setDelCheck}) {
    
    const productId = props.id;
    const [product, setProduct] = useState();
    const [count, setCount] = useState(props.qty);

    const [cartCnt, setCartCnt] = useRecoilState(CartState);

    const handleDeleteBtnClick = async () => {
        if(window.confirm("장바구니에서 삭제하시겠습니까?")){
            const url = `http://localhost:3001/carts/${props.id}`;
            let response = await axios.delete(url);
            console.log('delete / response: ', response);
            if (response.status >= 200 && response.status < 300) {
                alert("장바구니에서 삭제되었습니다.");
                setDelCheck(!delCheck);
                setCartCnt(cartCnt - 1);
                return;
            }
        } 
    }
    const handleMinusBtnClick = () => {
        setCount(count - 1);
    }
    const handlePlushBtnClick = () => {
        setCount(count + 1);
    }
    useEffect(() => {
        axios.get(`/api/v1/products/${productId}`)
        .then((res) => res.data)
        .then((res) => setProduct(res));
    }, [])
    return (
        <>
        {
            product && 
            <div className={style.productBox}>
                <button
                    className={style.xBtn}
                    onClick={handleDeleteBtnClick}
                >x</button>
                <img className={style.productImg} src={product.thumbnail} alt={product.description}/>
                <div>
                    <label>상품명</label>
                    <span>{product.name}</span>
                </div>
                <div>
                    <label>가격</label>
                    <span>{formatMoney(product.price * count)}</span>    
                </div>
                
                <div className={style.plusMinusBtn} >
                <div>

                </div>
                <label>
                    담은 갯수
                </label>
                    
                    <button
                        onClick={handleMinusBtnClick}
                        disabled={count === 1 ? true : false}
                    >-</button>
                    {count}
                    <button onClick={handlePlushBtnClick}>+</button>
                </div>
            </div>
        }
            
        </>
    );
}

export default CartItem;