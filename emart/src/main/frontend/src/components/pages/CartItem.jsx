import axios from 'axios';
import React, { useState, useEffect } from 'react';
import style from './CartItem.module.css';
import { CartState } from '../../state/cartState';
import { useRecoilState } from 'recoil';
import { formatMoney } from '../globalFunction/formatMoney';

function CartItem({props, delCheck, setDelCheck, setTotalPrice, totalPrice}) {
    
    const productId = props.id;
    const [product, setProduct] = useState();
    const [count, setCount] = useState(props.qty);

    const [cartCnt, setCartCnt] = useRecoilState(CartState);

    const handleDeleteBtnClick = async () => {
        if(window.confirm("장바구니에서 삭제하시겠습니까?")){
            const url = `/api/v1/carts/${props.cartId}`;
            let response = await axios.delete(url);
            
            console.log('delete / response: ', response);
            if (response.status >= 200 && response.status < 300) {
                alert("장바구니에서 삭제되었습니다.");
                setDelCheck(!delCheck);
                setCartCnt(cartCnt - 1);
                return;
            } else {
                alert("장바구니에서 삭제에 실패했습니다.");
                return;
            }
        } 
    }
    const handleMinusBtnClick = () => {
        setCount(count - 1);
        setTotalPrice(totalPrice - product.price);
    }
    const handlePlushBtnClick = () => {
        if (props.productQty > count) {
            setCount(count + 1);
            setTotalPrice(totalPrice + product.price);
        } else {
            alert("상품 재고가 부족해서 더이상 장바구니에 담을 수 없습니다.");
            return;
        }
        
    }
    useEffect(() => {
        axios.get(`/api/v1/products/${productId}`)
        .then((res) => res.data)
        .then((res) => {
            console.log("[CartItem] res: ", res);
            setProduct(res);
        });
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
                    <label>제품 가격</label>
                    <span>{formatMoney(product.price)}</span>
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
                <div>
    
                    <label>총 가격</label>
                    <span>{formatMoney(product.price * count)}</span>    
    
                </div>
            </div>
        }
            
        </>
    );
}

export default CartItem;