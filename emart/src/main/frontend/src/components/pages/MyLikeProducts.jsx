import axios from 'axios';
import React, { useEffect, useState } from 'react'
import style from './ProductList.module.css';
import { Link } from 'react-router-dom';
import { formatMoney } from '../globalFunction/formatMoney';
import { useRecoilState } from 'recoil';
import { CartState } from '../../state/cartState';

function MyLikeProducts() {
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [productList, setProductList] = useState();

    useEffect(() => {


        axios.get('/api/v1/products/like-products')
        .then((res) => {
            console.log("res: ", res);
            console.log("res.data: ", res.data);
            return res.data; // [ { name: "롯데빈츠" }, {} ...]
        })
        .then(res => setProductList(res));

        axios.get('/api/v1/users/my-info')
        .then((res) => {
            console.log("my-info / res: ", res);
            console.log("my-info / res.data: ", res.data);
            setCartCnt(res.data.count);
        
        })
        .catch((err) => {
            console.log("[Error|GET] my-info: ", err)
            setCartCnt(0);
        });

    }, [])
    return (
        <>
        <h3>내가 찜한 상품 목록</h3>
            <div>
                {
                    productList && productList.map(item => (
                        <div className={style.itemBox} key={item.id}>
                            <Link to={`/product-detail/${item.id}`}>
                                <img className={style.itemImg} src={item.thumbnail} alt={item.name}/>
                                <p>{item.name}</p>
                                <p className={style.price}>{formatMoney(item.price)}</p>
                            </Link>
                        </div>
                    ))
                }
            </div>
        </>
    );

}
export default MyLikeProducts;