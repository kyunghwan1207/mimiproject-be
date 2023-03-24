import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/layout/Header';
import ProductList from './components/pages/ProductList';
import Login from './components/pages/Login';
import Join from './components/pages/Join';
import UserInfo from './components/pages/UserInfo';
import ChangePassword from './components/pages/ChangePassword';
import LikeList from './components/pages/LikeList';
import ProductDetail from './components/pages/ProductDetail';
import CartList from './components/pages/CartList';
import Footer from './components/layout/Footer';
import QR from './components/pages/QR';
import Search from './components/pages/Search';
function App() {
  return (

    <BrowserRouter>
      <Header /> 
      <Routes>
        <Route path='/login' element={<Login/>} />
        <Route path='/join' element={<Join/>} />
        <Route path='/user-info' element={<UserInfo/>} />
        <Route path='/change-password' element={<ChangePassword/>} />
        <Route path='/like-list' element={<LikeList/>} />
        <Route path='/products' element={<ProductList/>} />
        <Route path='/product-detail/:id' element={<ProductDetail/>} />
        <Route path='/cart' element={<CartList/>} />
        <Route path='/' element={<ProductList/>} />
        <Route path='/qr' element={<QR/>} />
        <Route path='/search' element={<Search/>} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
