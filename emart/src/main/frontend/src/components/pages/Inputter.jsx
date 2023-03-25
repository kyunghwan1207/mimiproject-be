import React, { useState, useEffect, MouseEvent, useCallback } from 'react';
import "./Inputter.css"

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
function Inputter(props) {
    
  
  let nums_init = Array.from({ length: 10 }, (v, k) => k)
  const PASSWORD_MAX_LENGTH = 6; // 비밀번호 입력길이 제한 설정
  const [nums, setNums] = useState(nums_init);
//   const [password, setPassword] = useState(props.password);

  const handlePasswordChange = useCallback(
    (num) => {
      if (props.password.length === PASSWORD_MAX_LENGTH) {
        return
      }
      props.setPassword(props.password + num.toString())
    },
    [props.password],
  )

  const erasePasswordOne = useCallback(
    (e) => {
      props.setPassword(props.password.slice(0, props.password.length === 0 ? 0 : props.password.length - 1))
    },
    [props.password],
  )

  const erasePasswordAll = useCallback((e) => {
    props.setPassword("")
  }, [])

  const shuffleNums = useCallback(
    (num) => (e) => {
      // 0 ~ 9 섞어주기
      let nums_random = Array.from({ length: 10 }, (v, k) => k) // 이 배열을 변경해 입력문자 변경 가능
    //   setNums(shuffle(nums_random))
      handlePasswordChange(num)
    },
    [handlePasswordChange],
  )

  const onClickSubmitButton = (e) => {
    // 비밀번호 제출
    if (props.password.length === 0) {
      alert("비밀번호를 입력 후 눌러주세요!")
    } else {
      alert(props.password + "을 입력하셨습니다.");
      props.setIsOpen(false);
    }
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
        </div>
    );
}

export default Inputter;