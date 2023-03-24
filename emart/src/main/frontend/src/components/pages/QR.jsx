import React, { useState, useEffect } from 'react';
import axios from 'axios';
import style from './QR.module.css';

function QR() {
    const [lat, setLat] = useState(''); // 위도
    const [long, setLong] = useState(''); // 경도
    const [location, setLocation] = useState(''); // 사용자위치

    const geolocationAPI = navigator.geolocation;


    const getUserCoordinates = () => {
        if (!geolocationAPI) {
          alert('현재 브라우저에선 Geolocation API 사용이 불가능합니다.')
          return;
        } else {
          geolocationAPI.getCurrentPosition((position) => {
            const { coords } = position;
            setLat(coords.latitude);
            setLong(coords.longitude);
          }, (err) => {
            console.log("위치정보 읽을 때, 에러발생: ", err);
          })
        }
    }
    const getUserAddress = () => {
        const url = `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${long}&y=${lat}`;
        const headers = {
            'Authorization': "KakaoAK 15d0ef827e82a29086b62e70c5822faa"
        };
        axios.get(url, {
            headers: headers
        })
        .then((res) => {
            console.log('res.data: ', res.data);
            return res.data.documents;
        })
        .then((res) => {
            console.log("second res: ", res);
            setLocation(res[0].address.address_name);
            return
        })
        .catch((err) => console.log("[Error|GET] fail to load user location: ", err))
    }
    useEffect( () => {
        getUserCoordinates();
      }, [])
    return (
        <>
            <h3>QR체크인</h3>
                <div>
                    <img className={style.qrCode} src="https://velog.velcdn.com/images/kyunghwan1207/post/176d2254-15c8-4d4d-984d-50e7a1dd2300/image.png" alt='asd'/>
                </div>
                <div>

                    <button className={style.locationBtn} onClick={getUserAddress}>위치 구하기</button>
                </div>
                <label>위치</label>
                <span>{location}</span>
            
            
        </>
    );
}

export default QR;