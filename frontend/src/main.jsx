import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  // StrictMode作用是偵測潛在錯誤，只在開發模式生效
  <StrictMode>
    <App />
  </StrictMode>,
)
