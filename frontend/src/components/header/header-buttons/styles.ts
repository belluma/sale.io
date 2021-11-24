import {makeStyles} from "@material-ui/core";

export const useButtonStyles = makeStyles({
    goldFont: {
        '& .MuiButtonBase-root': {
            color: '#ffc400'
        }, '& .MuiIconButton-root': {
            color: '#ffc400'
        },
        '& .MuiSvgIcon-root': {
            color: '#ffc400'
        }
    }
})
