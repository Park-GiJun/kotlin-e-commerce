/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#f0fdf9',
          100: '#ccfbef',
          200: '#99f6e0',
          300: '#5fe9ce',
          400: '#2dd4b8',
          500: '#14b8a0',
          600: '#0d9488',
          700: '#0f766e',
          800: '#115e59',
          900: '#134e4a',
        },
        mint: {
          light: '#5fe9ce',
          DEFAULT: '#14b8a0',
          dark: '#0d9488',
        }
      },
    },
  },
  plugins: [],
}
