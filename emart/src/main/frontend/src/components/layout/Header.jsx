import React, { useEffect, useState } from 'react';
import { headerMenu, logoImg } from '../../data/headerMenu';
import { Link } from 'react-router-dom';
import style from './Header.module.css';
import { useNavigate } from 'react-router-dom';
// import CartIcon from '../../assets/cart.png';
import { CartState } from '../../state/cartState';
import { useRecoilState } from 'recoil';
import { LoginState } from '../../state/loginState';

function Header() {
  const [cartCnt, setCartCnt] = useRecoilState(CartState);
  const [isLogin, setIsLogin] = useRecoilState(LoginState);
  const [logoutCheck, setLogoutCheck] = useState(false);

  const navigate = useNavigate();  
  const moveBack = () => {
    navigate(-1);
  }
  useEffect(() => { 
    if(!isLogin){
      setCartCnt(0);
    }
  }, [logoutCheck])

  return ( 
    <header>
      <nav className={style.headerMenu}>
        <ul>
        <div onClick={moveBack} >
                <img className={style.backArrow} src="https://velog.velcdn.com/images/kyunghwan1207/post/b29181ed-52e7-4cfd-830d-4a4259b0014d/image.png"/>
            </div>
          <div className={style.logo}>
            <a href='/'>
              <img src={logoImg} alt='Emart24 logo' />
            </a>
          </div>
        </ul>
      </nav>
    </header>

   );
}

export default Header;