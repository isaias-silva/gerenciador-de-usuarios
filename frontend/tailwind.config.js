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
        "component-ligth-color-2": "#a4a4a4",
        
        "light-font": '#1F1F1F',
        
    

        "bck-dark": "#2e3c33ce",
        "component-dark-color":'#2a2a2a',
        "component-dark-color-2":'#464646',
        "dark-font": '#fff',
        

        "base-color": "#4caf50",
        "hover-font": "#37BB3B",

      },
    }
  },


  plugins: [],
}