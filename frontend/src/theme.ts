import {createTheme, ThemeOptions} from "@mui/material";


export const theme: ThemeOptions = createTheme({
    palette: {
        primary: {
            main: '#040404',
            contrastText: '#ffc400',
        },
        secondary: {
            main: '#004d40',
        },
        background: {
            default: '#14213d',
            paper: '#e5e5e5',
        },
    },
});

export default theme;
