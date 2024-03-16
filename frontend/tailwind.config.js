/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  darkMode: ['selector', '[data-mode="dark"]'],

  theme: {
    extend: {
      colors: {
       
     
        "bck-light": " #a5e6a888",
        "component-ligth-color": "#f3f4f6",
        "light-font": '#1F1F1F',
        
    

        "bck-dark": "#004216ce",
        "component-dark-color":'#2a2a2a90',
        "dark-font": '#fff',
        

        "base-color": "#4caf50",
        "hover-font": "#37BB3B",

      },
    }
  },


  plugins: [],
}