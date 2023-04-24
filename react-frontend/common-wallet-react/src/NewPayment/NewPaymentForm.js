import React, { useState } from "react";

import "./newPayment.css"; // import the CSS file with the styles
import { BASE_URL } from "../Secrets";

function NewPaymentForm({onSubmit}) {
  const [values, setValues] = useState({ wallet_id : 1, payer_id: 1, amount: 10, description: "" });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({ ...values, [name]: value });
  };

  const resetValues = () => {
    setValues({ wallet_id : 1, payer_id: 1, amount: 10, description: "" });
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    const { wallet_id, payer_id, amount, description } = values;
    const payload = { wallet_id, payer_id, amount, description };

    const baseUrl = BASE_URL;
    const queryParams = { 
      code: CODE,
      clientId: 'default'
    };
    const url = new URL(baseUrl);
    Object.keys(queryParams).forEach(key => url.searchParams.append(key, queryParams[key]));
    
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(payload)
    })
      .then((response) => {
        if (response.ok) {
            alert("Success!");
            resetValues();
            onSubmit();
        }
      })
      .catch((error) => console.error(error));
  };

  return (
    <form className="form-container" onSubmit={handleSubmit}>
      <label className="form-label">Payer ID:</label>
      <input className="form-input" type="number" name="payer_id" value={values.payer_id} onChange={handleChange} />
      <label className="form-label">Amount (EUR):</label>
      <input className="form-input" type="number" step="0.01" name="amount" value={values.amount} onChange={handleChange} />
      <label className="form-label">Description:</label>
      <input className="form-input" type="text" name="description" placeholder="HIT, Menzis, ili bilo sta drugo" value={values.description} onChange={handleChange} />
      <button className="form-button" type="submit">Register payment</button>
    </form>
  );
}

export default NewPaymentForm;
