import {makeStyles} from "@material-ui/core";

export const useIconButtonStyles = makeStyles({
    goldFont: {
        '& .MuiButtonBase-root': {
            color: '#ffc400'
        }, '& .MuiIconButton-root': {
            color: '#ffc400'
        },
        '& .MuiSvgIcon-root': {
            color: '#ffc400'
        },

    }
})
export const buttonStyles = {height: 45, mx: 2, bgcolor: "transparent", boxShadow: "none"} as const
