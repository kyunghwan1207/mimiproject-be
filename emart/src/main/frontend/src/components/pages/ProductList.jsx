import React, {useState, useEffect} from 'react';
import axios from 'axios';
import style from './ProductList.module.css';
import { Link } from 'react-router-dom';
import Slider from 'react-animated-slider';
import 'react-animated-slider/build/horizontal.css';
import { formatMoney } from '../globalFunction/formatMoney';
import { useRecoilState } from 'recoil';
import { CartState } from '../../state/cartState';

function ProductList({props}) {
    const [eventList, setEventList] = useState();
    const [eventItemList, setEventItemList] = useState();
    const [navActiveState, setNavActiveState] = useState([false, false, false, false]);
    const [slides, setSlids] = useState();

    const [cartCnt, setCartCnt] = useRecoilState(CartState);

    let itemList = [];
    
    const setState = (id) => {
        for(let i = 0; i < navActiveState.length; i++){
            if(navActiveState[i]){
                if(id === i) break;
                navActiveState[i] = false;
            } else if(id == i) {
                navActiveState[i] = true;
            }
        }
        setNavActiveState([...navActiveState]);
    }
    const handleEventBtnClick = (id) => {
        setState(id)
        for(let i = 0; i < eventList.length; i++){
            if(eventList[i].id === id){
                itemList = eventList[i].eventProductList;
                break;
            }
        }
        setItemList(itemList);
    }
    const setItemList = (productList) => {
        for (let i = 0; i < productList.length; i++){
            axios.get(`http://localhost:3001/products/${productList[i]}`)
            .then((res) => res.data)
            .then((res) => {
                if (i === 0){ 
                    setEventItemList([res]);
                } else {
                    setEventItemList(oldList => [...oldList, res]);
                }
            })
        }
    }
    useEffect(() => {
        console.log('productList / props: ', props);

        // axios.get('http://localhost:3001/events')
        // .then((res) => res.data)
        // .then((res) => {
        //     setEventList(res)
        //     setItemList(res[0].eventProductList)
        //     navActiveState[1] = true;
        //     setNavActiveState([...navActiveState]);
        // })
        // .catch((err) => console.log('[Error|GET] event List: ', err));

        /*
         * 03.24  
         */
        let startTime = new Date();
        axios.get('/api/v1/products')
        .then((res) => {
            console.log("res: ", res);
            console.log("res.data: ", res.data);
            return res.data; // [ { name: "롯데빈츠" }, {} ...]
        })
        .then(res => setEventItemList(res));


        axios.get('/api/v1/ads')
        .then((res) => {
            console.log("res: ", res);
            console.log("res.data; ", res.data);
            return res.data;
        })
        .then((res) => setSlids(res))
        .catch((err) => console.log('[Error|GET] Slides: ', err));

        axios.get('/api/v1/users/my-info')
        .then((res) => {
            console.log("my-info / res: ", res);
            console.log("my-info / res.data: ", res.data);
            setCartCnt(res.data.count);
            console.log("Time while 3-times-api-call: ", new Date() - startTime); // 321
        })
        .catch((err) => console.log("[Error|GET] my-info: ", err));

        

    }, [])
    return (
        <>
            <Slider>
                {slides && slides.map(slide => (
                <div key={slide.id}>
                    <a href={slide.link}>
                        <img src={slide.img} />
                    </a>
                </div>))}
            </Slider>
            <h2>행사상품</h2>
            <nav>
                <ul>
                    {
                        eventList && eventList.map(itemNav => (
                            <li key={itemNav.id} className={ navActiveState[itemNav.id] ? `${style.active}` : "" }>
                                <p 
                                    className={style.eventBtn}
                                    onClick={() => {handleEventBtnClick(itemNav.id)}}
                                >
                                {itemNav.name}
                                </p>
                            </li>
                        ))
                        
                    }
                </ul>
                
                <div>
                    {
                        eventItemList && eventItemList.map(item => (
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
            </nav>
            
            
        </>
    );
}

export default ProductList;