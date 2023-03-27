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
    const [isLogin, setIsLogin] = useState(false);
    const [status, setStatus] = useState(400);
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    // const loginState = useRecoilValue(LoginState);
    // const userId = useRecoilValue(UserIdState);
    const navigate = useNavigate();
    

    const handleClikAddCart = async () => {
        if(status === 200){
            if(window.confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                navigate("/login", {})
            }
            return
        }
        let data = {
            "productId": product.id,
            "qty": count
        };
        console.log('request data: ', data);
        try {
            let res = await axios(`/api/v1/carts/add`, 
                {
                    method: 'post',
                    headers: {'Content-Type': 'application/json'},
                    data: data
                });
            console.log("res: ", res);
            if (res.status >= 200 && res.status < 300) {
                console.log("after add cart res: ", res);
                if(window.confirm("장바구니에 담겼습니다! 장바구니 목록을 확인하시겠습니까?")){
                    navigate("/cart", {});
                } 
            } else {
                alert("장바구니 담는 것에 실패했습니다. 다시 시도해주시기 바랍니다");
                return;
            }
        } catch(err) {
            console.log("error while adding product to cart : ", err);
            return;
        }

    }
    const handleMinusBtnClick = () => {
        setCount(count-1);
    }
    const handlePlushBtnClick = () => {
        if (product.qty > count) {
            setCount(count+1);
        } else {
            alert("재고가 부족하여 더이상 장바구니에 담을 수 없습니다.");
            return;
        }
        
    }
    const handleHartClick = async (state) => {
        console.log("handleHartClick / state: ", state);
        console.log("handleHartClick / productId.id: ", productId.id);
        if(status === 200){
            if(window.confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                navigate("/login", {})
            }
            return
        }
        try {
            let res = await axios(
                '/api/v1/products/like-product', 
            {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                data: JSON.stringify({
                    "productId": productId.id,
                    "state": state
                })
            });
            console.log("join res: ", res);
            if(res.status >= 200 && res.status < 300){
                axios.get(`/api/v1/products/${productId.id}`)
                    .then((res) => {
                        console.log("product Info | GET / res: ", res);
                        console.log("product Info | GET / res.data: ", res.data);
                        return res.data;
                    })
                    .then((res) => setProduct(res))
            } else {
                return;
            }

        } catch (err) {
            console.log("clickHart err: ", err);
        }
    }
    useEffect(() => {
        console.log("ProductDetail/useEffect call");
        axios.get(`/api/v1/products/${productId.id}`)
        .then((res) => {
            console.log("product Info | GET / res: ", res);
            console.log("product Info | GET / res.data: ", res.data);
            setStatus(res.status);
            return res.data;
        })
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
                        <label>평점</label>
                        <img src='https://velog.velcdn.com/images/kyunghwan1207/post/822b8da4-5e7c-4d17-88d8-b575e71aa0b4/image.png' style={{width: '25px'}}/>
                        <span>{product.rating}</span>
                    </div>
                    <div>
                    {
                        product.like ? 
                        <img src='https://velog.velcdn.com/images/kyunghwan1207/post/bcad0b56-df93-4ad5-83b8-8b0f8e2b3098/image.png' onClick={() => handleHartClick(false)} style={{width: '25px'}}/>
                        :
                        <img src='https://velog.velcdn.com/images/kyunghwan1207/post/3c2fab46-23f7-4bd2-a486-7d4a3180cc6d/image.png' onClick={() => handleHartClick(true)} style={{width: '25px'}} />
                    }
                    </div>
                    <div>
                        <label>재고</label>
                        <span>{product.qty} 개</span>
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