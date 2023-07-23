import React, { useState } from 'react';
import ChildComponent from './ChildComponent';

const ParentComponent = () => {
  const [valueFromChild, setValueFromChild] = useState('');

  // This function will be called from the ChildComponent
  const handleValueFromChild = (value) => {
    setValueFromChild(value);
    // You can perform additional actions with the value received from the child here.
  };

  return (
    <div>
      <h1>Parent Component</h1>
      <p>Value from Child Component: {valueFromChild}</p>
      <ChildComponent onValueChange={handleValueFromChild} />
    </div>
  );
};

export default ParentComponent;
