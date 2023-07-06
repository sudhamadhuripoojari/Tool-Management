import axios, { AxiosResponse } from 'axios';
import {Tool} from '../components/ListToolComponent'

const TOOL_BASE_REST_API_URL = 'http://localhost:3000/api/tools';

class ToolService {
  getAllTools(): Promise<AxiosResponse<Tool[]>> {
    return axios.get<Tool[]>(TOOL_BASE_REST_API_URL);
  }

  createTool(tool: Tool){
    return axios.post<Tool[]>(TOOL_BASE_REST_API_URL, tool)
  }

//   deleteTool(id: number | undefined){
//     return axios.delete<Tool[]>(`${TOOL_BASE_REST_API_URL}/${id}`)
//   }
}

export default new ToolService();
