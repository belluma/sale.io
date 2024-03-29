import {makeStyles} from "@material-ui/core";

export const useOffsetDialog = makeStyles({
    offsetDialog: {
        '& .MuiDialog-container': {
            alignItems: 'flex-end'
        },
    }
})

export const useNoPadding = makeStyles({
    noPadding: {
        '& .MuiContainer-root': {
            paddingLeft: 0,
            paddingRight: 0
        }
    }
})

export const dividerStyles = {bgcolor: 'primary.light', mx: 3} as const
