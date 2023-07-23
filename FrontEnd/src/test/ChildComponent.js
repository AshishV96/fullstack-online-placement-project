import React, { useState } from 'react';

const ChildComponent = ({ onValueChange }) => {
  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleButtonClick = () => {
    // Call the parent's callback function and pass the value
    onValueChange(inputValue);
  };

  return (
    <div>
      <h2>Child Component</h2>
      <input type="text" value={inputValue} onChange={handleInputChange} />
      <button onClick={handleButtonClick}>Send Value to Parent</button>
    </div>
  );
};

export default ChildComponent;
