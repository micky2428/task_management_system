import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vite.dev/config/
// export default defineConfig({
//   plugins: [react()],
//   //預設端口 5173
// })

export default defineConfig({
  plugins: [react()],
  server: {
    historyApiFallback: true,
  },
});
