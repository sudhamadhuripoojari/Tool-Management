import React, { useEffect, useState } from 'react'
import 'bootstrap/dist/css/bootstrap.css';
import ToolService from '../services/ToolService'
import { Link } from 'react-router-dom';

export interface Tool {
  id ?: number;
  toolName: string;
  toolOwner: string;
  email: string;
}


const ListToolComponent: React.FC = () => {
    const[tools, setTools] = useState<(Tool & {borrowed: boolean})[]>([]);

    useEffect (()=>{
      ToolService.getAllTools()
      .then((response)=>{
        const toolsWithBorrowedStatus = response.data.map((tool) => ({
          ...tool,
          borrowed: false,
        }));
        setTools(toolsWithBorrowedStatus);
        // setTools(response.data);
        console.log("RESPONSE DATA", response.data)
      }).catch(error =>{
        console.log(error)
      }); 
    }, []);

const borrowTool = (toolId: number | undefined) => {
  setTools((prevTools) =>
    prevTools.map((tool) =>
      tool.id === toolId ? { ...tool, borrowed: true } : tool
    )
  );
};

const returnTool = (toolId: number | undefined) => {
  setTools((prevTools) =>
    prevTools.map((tool) =>
      tool.id === toolId ? { ...tool, borrowed: false } : tool
    )
  );
};

  return (
    <div>
      <h2 className='text-center'>List of Tools</h2>
      <Link to={'/add-tool'} className='btn btn-primary mb-2'>Add Tool</Link>
      <table className='table table-bordered table-striped'>
        <thead className='thead-style'>
          <th className='th-style'>Tool Id</th>
          <th>Tool Name</th>
          <th>Tool Owner</th>
          <th>Owner email</th>
          <th>Actions</th>
        </thead>
        <tbody>
        {tools.map((tool) => (
            <tr key={tool.id}>
              <td>{tool.id}</td>
              <td className={tool.borrowed ? 'disabled-text' :''}>{tool.toolName}</td>
              <td className={tool.borrowed ? 'disabled-text' :''}>{tool.toolOwner}</td>
              <td className={tool.borrowed ? 'disabled-text' :''}>{tool.email}</td>
              <td>
                {tool.borrowed ? (
                  <button className='btn btn-secondary' disabled>
                    Borrowed
                  </button>
                ) : (
                  <button
                    className='btn btn-secondary'
                    onClick={() => borrowTool(tool.id)}
                  >
                    Borrow
                  </button>
                )}
                <button
                  className='btn btn-secondary'
                  onClick={() => returnTool(tool.id)}
                >
                  Return
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
  )
}

export default ListToolComponent