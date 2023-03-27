import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import axios from 'axios';
import { formatMoney } from '../globalFunction/formatMoney';
function ChargeEpay(props) {
    const [coin, setCoin] = useState(0);
    const [originCoin, setOriginCoin] = useState(props.epay);
    const handleChargBtnClick = async (e) => {
        e.preventDefault();
        try {
            let response = await axios(
                '/api/v1/users/charge-epay',
                {
                    method: 'put',
                    headers: {'Content-Type': 'application/json'},
                    data: {
                        epay: coin
                    }
                }
            );
            console.log("response: ", response);
            if (response.status >= 200 && response.status < 300) {
                console.log("response.data: ", response.data);
                alert(`Epay 충전이 완료되었습니다. 충전 완료 후 금액은 ${response.data.epay}입니다.`)
                props.setIsOpenChargeModal(false);
            }
            
        } catch (err) {
            console.log("err: ", err);
        }
        
    }
    const handleChargeClick = (val) => {
        setCoin(Number(coin) + Number(val*10000));
    }
    const handleCoinChange = (e) => {
        setCoin(e.target.value);
    }
    const handleXBtnClick = () => {
        props.setIsOpenChargeModal(false); 
        setCoin(0);
    }
    return(
        <>
        <div>
            <button className='x-button' onClick={handleXBtnClick}>x</button>
        </div>
        <span>Epay 잔액: {formatMoney(originCoin)}</span>
        <br/>
        <br/>
        <div>
            충전 금액: <input value={formatMoney(coin)} onChange={handleCoinChange} placeholder='충전할 금액을 입력해주세요' style={{width: "auto"}}/>
        </div>
        
        <input type="button" onClick={() => handleChargeClick(10)} value="10만원"/>
        <input type="button" onClick={() => handleChargeClick(30)} value="30만원"/>
        <input type="button" onClick={() => handleChargeClick(50)} value="50만원"/>
        <br/><br/>
        <div>
            <button className='submit-button' onClick={handleChargBtnClick}>충전</button>
        </div>
        
        </>
    );
}
export default ChargeEpay;