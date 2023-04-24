import logo from './logo.svg';
import './App.css';
import StatTable from './Stats/StatTable';
import NewPaymentForm from './NewPayment/NewPaymentForm';
import { useEffect, useState } from 'react';
import { BASE_URL } from './Secrets';

// let d = [
//   {
//     id: 1,
//     name: 'Uros Stojkovic',
//     total: 100,
//     net: 10
//   },
//   {
//     id: 2,
//     name: 'Lazar Minic',
//     total: 90,
//     net: 0
//   },
//   {
//     id: 3,
//     name: 'Vuk Bibic',
//     total: 80,
//     net: -10
//   }
// ];

function App() {
  const [data, setData] = useState([]);

  const refreshData = () => {
    const baseUrl = BASE_URL;
    const queryParams = { 
      code: CODE,
      clientId: 'default',
      wallet_id: 1
    };
    const url = new URL(baseUrl);
    Object.keys(queryParams).forEach(key => url.searchParams.append(key, queryParams[key]));
    
    fetch(url)
      .then(response => response.json())
      .then(data => setData(data))
      .catch(error => console.error(error));
  }

  useEffect(() => {
    refreshData();
  }, []);

  return (
    <div className='parent'>
      <NewPaymentForm onSubmit={refreshData}/>
      <StatTable data={data}/>
    </div>
  )
}

export default App;
