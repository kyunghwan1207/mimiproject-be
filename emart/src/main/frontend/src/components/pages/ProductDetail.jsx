import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import style from './ProductDetail.module.css';
import { useNavigate } from 'react-router-dom';
import { UserIdState } from '../../state/userIdState';
import { useRecoilValue, useRecoilState} from 'recoil';
import { CartState } from '../../state/cartState';
import { LoginState } from '../../state/loginState';
import { formatMoney } from '../globalFunction/formatMoney';

function ProductDetail() {
    const productId = useParams();
    const [product, setProduct] = useState();
    const [count, setCount] = useState(1);

    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const loginState = useRecoilValue(LoginState);
    const userId = useRecoilValue(UserIdState);
    const navigate = useNavigate();
    

    const handleClikAddCart = async () => {
        if(!loginState){
            if(window.confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                navigate("/login", {})
            }
            return
        }
        // 장바구니 정보 조회
        let cartData = await axios.get(`http://localhost:3001/carts?userId=${userId}&productId=${productId.id}`);
        let response = '';
        // 장바구니에 존재하지 않는데이터라면, 새로담기
        if (cartData.data == undefined || cartData.data.length === 0){
            response = await axios.post(`http://localhost:3001/carts`,{
                "userId": userId,
                "productId": Number(productId.id),
                "qty": count
            })
            setCartCnt(cartCnt + 1);
        } else {
            // 장바구니 데이터가 이미 있는 경우, 추가로 담기(qty++)
            console.log('cartData: ', cartData);
            response = cartData.data[0];
            const newQty = response.qty + count;
            response = await axios.put(`http://localhost:3001/carts/${response.id}` ,{
                "id": response.id,
                "userId": response.userId,
                "productId": response.productId,
                "qty": newQty
            })
        }
        console.log("after add cart response: ", response);
        if(window.confirm("장바구니에 담겼습니다! 장바구니 목록을 확인하시겠습니까?")){
            navigate("/cart", {});
        } 
        return
    }
    const handleMinusBtnClick = () => {
        setCount(count-1);
    }
    const handlePlushBtnClick = () => {
        setCount(count+1);
    }
    useEffect(() => {
        const url = `http://localhost:3001/products/${productId.id}`;
        axios.get(url)
        .then((res) => res.data)
        .then((res) => setProduct(res))
    }, [])
    return (
        <div>
            {
                product &&
                <div className={style.productBox}>
                    <h3>{product.name}</h3>
                    <img className={style.img} src={product.thumbnail} alt={product.description}/>
                    <div>
                        <label>가격</label>
                        <span>{formatMoney(product.price * count)}</span>
                    </div>
                    <div>
                        <label>수량 </label>
                            <button 
                                className={style.minusBtn} 
                                onClick={handleMinusBtnClick}
                                disabled={count === 1 ? true : false}
                            >-</button>
                            <span className={style.cnt}>{count}</span>
                            <button className={style.plusBtn} onClick={handlePlushBtnClick}>+</button>
                            <button onClick={handleClikAddCart}>장바구니 담기</button>
                    </div>
                    <p>{product.description}</p>
                    
                </div>
                
            }    
        </div>
        
    );
}

export default ProductDetail;