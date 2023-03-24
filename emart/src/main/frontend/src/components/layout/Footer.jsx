import React, { useEffect, useState } from 'react';
import { footerMenu } from '../../data/footerMenu';
import { Link } from 'react-router-dom';
import style from './Footer.module.css';
import { useNavigate } from 'react-router-dom';
// import CartIcon from '../../assets/cart.png';
import { CartState } from '../../state/cartState';
import { useRecoilState } from 'recoil';
import { LoginState } from '../../state/loginState';

function Footer() {
    const [cartCnt, setCartCnt] = useRecoilState(CartState);
    const [isLogin, setIsLogin] = useRecoilState(LoginState);
    const [logoutCheck, setLogoutCheck] = useState(false); 
    const [clickState, setClickState] = useState([false, false, false, false, false, false]);
    const handleFooterMenuClick = (id) => {
        for(let i=0; i < clickState.length; i++){
            if(id === i){
                clickState[i] = true;
            } else {
                clickState[i] = false;
            }
        }
        setClickState([...clickState]);
    }
    useEffect(() => { 
        if(!isLogin){
            setCartCnt(0);
        }
        for(let i=0; i<clickState.length; i++){
            if(i === 3){
                clickState[i] = true;
            } else {
                clickState[i] = false;
            }
        }
        setClickState([...clickState])
    }, [logoutCheck])
    return (
        <footer>
        <nav className={style.footerMenu}>
            <ul>
            {
                footerMenu.map( menu => (
                    menu.name !== '장바구니' ? 
                    <li key={menu.id} onClick={() => handleFooterMenuClick(menu.id)}>
                        <Link 
                        to={menu.link}
                        >
                        <img className={clickState[menu.id] === true ? style.menuIconActive : style.menuIcon} src={menu.icon} alt={menu.name} />
                        </Link>
                    </li>                    
                    : 
                    <li key={menu.id} onClick={() => handleFooterMenuClick(menu.id)}>
                        <Link to={menu.link}>
                        <img className={clickState[menu.id] === true ? style.menuIconActive : style.menuIcon} src={menu.icon} alt={menu.name} />
                        </Link>
                        <p className={style.cartCnt}>{cartCnt}</p>
                    </li>
                    
                ))
            }
            </ul>
        </nav>
        </footer>
    );
}

export default Footer;