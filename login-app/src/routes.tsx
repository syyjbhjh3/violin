import { Icon } from '@chakra-ui/react';
import {
    MdBarChart,
    MdPerson,
    MdHome,
    MdLock,
    MdOutlineShoppingCart,
    MdBallot,
} from 'react-icons/md';

// Admin Imports
import MainDashboard from 'views/admin/default';
import Cluster from 'views/admin/cluster';
import Profile from 'views/admin/profile';
import DataTables from 'views/admin/dataTables';
import RTL from 'views/admin/rtl';

// Auth Imports
import SignInCentered from 'views/auth/signIn';
import SignUpCentered from 'views/auth/signUp';

const routes = [
    {
        name: 'Main Dashboard',
        layout: '/admin',
        path: '/default',
        icon: <Icon as={MdHome} width="20px" height="20px" color="inherit" />,
        component: <MainDashboard />,
    },
    {
        name: 'Cluster',
        layout: '/admin',
        path: '/cluster',
        icon: (
            <Icon
                as={MdBallot}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Cluster />,
        secondary: true,
    },
    {
        name: 'Data Tables',
        layout: '/admin',
        icon: (
            <Icon as={MdBarChart} width="20px" height="20px" color="inherit" />
        ),
        path: '/data-tables',
        component: <DataTables />,
    },
    {
        name: 'Profile',
        layout: '/admin',
        path: '/profile',
        icon: <Icon as={MdPerson} width="20px" height="20px" color="inherit" />,
        component: <Profile />,
    },
    {
        name: 'Sign In',
        layout: '/auth',
        path: '/sign-in',
        icon: <Icon as={MdLock} width="20px" height="20px" color="inherit" />,
        component: <SignInCentered />,
    },
    {
        name: 'Sign Up',
        layout: '/auth',
        path: '/sign-up',
        icon: <Icon as={MdLock} width="20px" height="20px" color="inherit" />,
        component: <SignUpCentered />,
    },
    {
        name: 'RTL Admin',
        layout: '/rtl',
        path: '/rtl-default',
        icon: <Icon as={MdHome} width="20px" height="20px" color="inherit" />,
        component: <RTL />,
    },
];

export default routes;
