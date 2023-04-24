import StatTableEntry from "./StatTableEntry"
import './table.css'

const StatTable = ({data}) => {
    return <div className="table-container">
        <h1>Stats</h1>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Total</th>
                    <th>Net</th>
                </tr>
            </thead>
            <tbody>
                {data.map(elem => (<StatTableEntry key={elem.id} entry={elem}/>))}
            </tbody>
        </table>
    </div>
}

export default StatTable;