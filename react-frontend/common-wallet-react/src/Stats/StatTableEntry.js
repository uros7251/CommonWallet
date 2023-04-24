const StatTableEntry = ({entry}) => {
    return (
        <tr key={entry.id}>
            <td>{entry.name}</td>
            <td>{entry.total}</td>
            <td>{entry.net}</td>
        </tr>
    );
}

export default StatTableEntry;