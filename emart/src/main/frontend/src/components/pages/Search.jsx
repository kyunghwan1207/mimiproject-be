import React, { useState, useEffect, MouseEvent, useCallback } from 'react';
import style from './Search.module.css';
import axios from 'axios';
import { formatMoney } from '../globalFunction/formatMoney';
import { Link } from 'react-router-dom';
import Modal from "react-modal";
import Inputter from './Inputter';


function Search() {
    const [searchText, setSearchText] = useState('');
    const [products, setProducts] = useState();
    const handleSearchTextChange = (e) => {
        setSearchText(e.target.value);
    }
    const handleSearchBtnClick = () => {
        let newProducts = []
        for(let i = 0; i < products.length; i++){
            if(products[i].name.includes(searchText)){
                console.log('products: ', products[i].name);
                newProducts.push(products[i]);
            }
        }
        setProducts(newProducts);
    }
    useEffect(() => {
        axios.get('http://localhost:3001/products')
        .then((res) => res.data)
        .then((res) => setProducts(res))
    }, [])

    /*
     * 랜덤 비밀번호 입력기 생성 코드 시작 
     */
    const [isOpen, setIsOpen] = useState(false); 
    const [password, setPassword] = useState("");
    const toggle = () => {
        setIsOpen(true);
        setPassword("");
    }

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
    // 끝
    return (
        <div>
            <h3>검색</h3>
            <input className={style.searchInputBox} type="text" value={searchText} onChange={handleSearchTextChange} placeholder="검색할 상품명을 입력해주세요"/>
            <button className={style.searchInputBtn} onClick={handleSearchBtnClick}>검색</button>
            <br/>
            {
                products && products.map(product => (
                    <Link className={style.link} to={`/product-detail/${product.id}`}>
                        <div key={product.id} className={style.productBox}>
                            <img className={style.productImg} src={product.thumbnail} />
                            <p className={style.productName}>{product.name}</p>
                            <p className={style.productPrice}>{formatMoney(product.price)}</p>
                        </div>    
                    </Link>
                    
                ))
            }
            <input className={style.address} value={password} readOnly placeholder='인증비밀번호 6자리 입력해주세요' onClick={toggle}/>
            <Modal isOpen={isOpen} ariaHideApp={false} style={modalStyles}>
                <Inputter 
                    password={password}
                    setIsOpen={setIsOpen}
                    setPassword={setPassword}
                />
            </Modal>
            
        </div>
    );
}

export default Search;