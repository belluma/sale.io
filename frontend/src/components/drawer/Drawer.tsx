import * as React from 'react';

//component imports
import MuiDrawer from '@mui/material/Drawer';
import { List, ListItem} from '@mui/material';


type Props = {
    open: boolean,
    toggle: () => void,
    reroute: (e: React.MouseEvent<HTMLButtonElement>) => void,
    buttons: JSX.Element[], marginTop: number
}

export default function Drawer({open, toggle, buttons, marginTop}: Props) {

    const listItems = buttons.map(button => <ListItem>{button}</ListItem>)
    return (
        <div>
            <React.Fragment>
                <MuiDrawer
                    anchor={"left"}
                    open={open}
                    onClose={toggle}
                >
                    <List sx={{marginTop: marginTop / 8}}>
                        {listItems}
                    </List>
                </MuiDrawer>
            </React.Fragment>
        </div>
    );
}
