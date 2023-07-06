import React, { FormEvent, useState } from 'react'
import {Link, useNavigate} from 'react-router-dom';
import {Tool} from './ListToolComponent'
import ToolService from '../services/ToolService';

const AddToolComponent: React.FC  = () => {
    const [toolName, setToolName] = useState('');
    const [toolOwner, setToolOwner] = useState('');
    const [email, setEmail] = useState('');
    const navigateTo = useNavigate();

    const saveTool = (e: FormEvent)=>{
        e.preventDefault()

        const tool:Tool = {toolName,toolOwner, email}
        ToolService.createTool(tool).then((response)=>{
            console.log(response.data)
            navigateTo('/tools')
        }).catch(error =>{
            console.log(error)
        })
    }
  
  return (
    <div>
         <br/>
         <br/>
        <div className='container'>
            <div className='row'>
                <div className='card col-md-6 offset-md-3 offset-md-3 cardstyle'>
                    <h2 className='text-center'>Add Tool</h2>
                    <div className='card-body'>
                        <form>
                            <div className='form-group mb-2'>
                                <label className='form-label'>Tool Name: </label>
                                <input
                                    type='text'
                                    placeholder='Enter the tool'
                                    name='toolName'
                                    className='form-control'
                                    value={toolName}
                                    onChange= {(e)=> setToolName(e.target.value)}
                                >
                                </input>
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label'>Owner Name: </label>
                                <input
                                    type='text'
                                    placeholder='Enter your name'
                                    name='toolOwner'
                                    className='form-control'
                                    value={toolOwner}
                                    onChange= {(e)=> setToolOwner(e.target.value)}
                                >
                                </input>
                            </div>
                            <div className='form-group mb-2'>
                                <label className='form-label'>Owner email: </label>
                                <input
                                    type='email'
                                    placeholder='Enter your email'
                                    name='email'
                                    className='form-control'
                                    value={email}
                                    onChange= {(e)=> setEmail(e.target.value)}
                                >
                                </input>
                            </div>
                            <button className='btn btn-success' onClick={(e)=> saveTool(e)}>Save</button>
                            <Link to={'/tools'} className='btn btn-danger'> Cancel</Link>
                        </form>
                    </div>

                </div>
            </div>

        </div>
    </div>
  )
}

export default AddToolComponent