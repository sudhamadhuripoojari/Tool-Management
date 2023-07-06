
import './App.css'
import {BrowserRouter as Router, Routes, Route }from 'react-router-dom';
import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import ListToolComponent from './components/ListToolComponent';
import AddToolComponent from './components/AddToolComponent';

const App: React.FC = () => {

  return (
    <>
    <Router>
      <HeaderComponent />
      <div>
        <Routes>
          <Route path="/" element = {<ListToolComponent />} />
          <Route path="/tools" element = {<ListToolComponent />}/>
          <Route path='/add-tool' element ={<AddToolComponent />}/>
          </Routes>
      </div>
      <FooterComponent />
      </Router>
    </>
  )
}

export default App
