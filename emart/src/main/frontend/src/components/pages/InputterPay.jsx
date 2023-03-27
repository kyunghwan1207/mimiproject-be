import React, { useState, useEffect, MouseEvent, useCallback } from 'react';
import ChargeEpay from './ChargeEpay';
import "./Inputter.css"
import Modal from 'react-modal';
import axios from 'axios';

const shuffle = (nums) => {
    // 배열 섞는 함수
    let num_length = nums.length
    while (num_length) {
      console.log("here")
      let random_index = Math.floor(num_length-- * Math.random())
      let temp = nums[random_index]
      nums[random_index] = nums[num_length]
      nums[num_length] = temp
    }
    return nums
  }
function InputterPay(props) {
    
  
  let nums_init = Array.from({ length: 10 }, (v, k) => k)
  const PASSWORD_MAX_LENGTH = 6; // 비밀번호 입력길이 제한 설정
  const [nums, setNums] = useState(nums_init);
  // const [password, setPassword] = useState(props.password);
  const [isOpenChargeModal ,setIsOpenChargeModal] = useState(false);

  const handlePasswordChange = useCallback(
    (num) => {
      if (props.password.length === PASSWORD_MAX_LENGTH) {
        return
      }
      props.setPassword(props.password + num.toString())
    },
    [props.password],
  )

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

  const erasePasswordOne = useCallback(
    (e) => {
      props.setPassword(props.password.slice(0, props.password.length === 0 ? 0 : props.password.length - 1))
    },
    [props.password],
  )

  // const erasePasswordOne = () => {
  //   props.setPassword(props.password.slice(0, props.password.length === 0 ? 0 : props.password.length - 1))
  // }

  const erasePasswordAll = useCallback((e) => {
    props.setPassword("")
  }, [])

  const handleShuffleBtnClick = () => {
    // 0 ~ 9 섞어주기
      let nums_random = Array.from({ length: 10 }, (v, k) => k) // 이 배열을 변경해 입력문자 변경 가능
      setNums(shuffle(nums_random))
  }
  const shuffleNums = useCallback(
    (num) => (e) => {
      
      let nums_random = Array.from({ length: 10 }, (v, k) => k) // 이 배열을 변경해 입력문자 변경 가능
      // setNums(shuffle(nums_random))
      handlePasswordChange(num)
    },
    [handlePasswordChange],
  );
  const shuffleBtnClick = () => {
    let nums_random = Array.from({ length: 10 }, (v, k) => k) // 이 배열을 변경해 입력문자 변경 가능
    setNums(shuffle(nums_random))
  }
  const onClickSubmitButton = (e) => {
    // 비밀번호 제출
    console.log("props.totalPrice: ", props.totalPrice);
        console.log("props.epay: ", props.epay);
    if (props.password.length < PASSWORD_MAX_LENGTH) {
      alert("비밀번호를 입력 후 눌러주세요!")
    } else if (props.userPassword === props.password) {
      if (props.totalPrice > props.epay) {
        if (window.confirm("Epay가 부족합니다. 충전하시겠습니까?")) {
          setIsOpenChargeModal(true);
          props.setPassword("");
        }
      }
      processPayment();
    } else {
      alert("비밀번호가 틀렸습니다. 다시 입력해주세요!")
      props.setPassword("");
    }
  }
  const openChargeModal = () => {
    setIsOpenChargeModal(true);
  }
const processPayment = async () => {
  // try {
  //   let res = await axios(
  //     '/api/v1/orders',
  //     {
  //       method: 'post',
  //       headers: {'Content-Type': 'application/json'},
        
  //     }
  //   )
  // }
  console.log("processPayment");
}
const toggle = () => {
    props.setIsOpen(true);
}
const handleXBtnClick = () => {
    props.setIsOpen(false); 
    props.setPassword("");
}
    return (
        <div>
            <div>
            <button className='x-button' onClick={handleXBtnClick}>x</button>
            </div>
            
            <input className='password-container' type='password' value={props.password}></input>
            <div className='inputter__flex'>
                {nums.map((n, i) => {
                const Basic_button = (
                    <button
                    className='num-button__flex spread-effect fantasy-font__2_3rem'
                    value={n}
                    onClick={shuffleNums(n)}
                    key={i}
                    >
                    {n}
                    </button>
                )
                return i == nums.length - 1 ? (
                    <>
                    <button
                        className='num-button__flex spread-effect fantasy-font__2_3rem'
                        onClick={erasePasswordAll}
                    >
                        X
                    </button>
                    {Basic_button}
                    </>
                ) : (
                    Basic_button
                )
                })}
                <button
                className='num-button__flex spread-effect fantasy-font__2_3rem'
                onClick={erasePasswordOne}
                >
                ←
                </button>
            </div>
            <div>
                <button type='submit' className='submit-button' onClick={onClickSubmitButton}>
                Submit
                </button>
            </div>
            
            <Modal isOpen={isOpenChargeModal} ariaHideApp={false} style={modalStyles}>
                <ChargeEpay 
                  setIsOpenChargeModal={setIsOpenChargeModal}
                  setIsOpen={props.setIsOpen}
                  epay={props.epay}
                />
            </Modal>
        </div>
    );
}

export default InputterPay;