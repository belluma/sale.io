import {makeStyles} from "@material-ui/core";

export const useOffsetDialog = makeStyles({
    offsetDialog: {
        '& .MuiDialog-container': {
            alignItems: 'flex-end'
        },
    }
})
