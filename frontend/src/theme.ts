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
     typography: {
    "fontFamily": `"Outfit",  sans-serif`,
        "fontSize": 14,
        "fontWeightLight": 300,
        "fontWeightRegular": 400,
        "fontWeightMedium": 500,
        "fontWeightBold": 900,
}
});


export default theme;
