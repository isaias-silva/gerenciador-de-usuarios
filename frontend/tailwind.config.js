/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  darkMode: ['selector', '[data-mode="dark"]'],

  theme: {
    extend: {
      colors: {
        "base-color": "#4caf50",

        "light-font": '#1F1F1F',
        "dark-font": '#fff',
        "hover-font": "#37BB3B"



      },
    }
  },


  plugins: [],
}