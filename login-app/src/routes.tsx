import { Icon } from '@chakra-ui/react';
import {
    MdBarChart,
    MdPerson,
    MdHome,
    MdLock,
    MdBallot, MdStars, MdSettingsSuggest, MdOutlineFilterDrama, MdSchema, MdMoveUp, MdSaveAs, MdSave,
} from 'react-icons/md';

// Admin Imports
import MainDashboard from 'views/admin/default';
import Cluster from 'views/admin/cluster';
import Profile from 'views/admin/profile';
import DataTables from 'views/admin/dataTables';
import RTL from 'views/admin/rtl';
import Pod from 'views/admin/pod';
import Namespace from 'views/admin/namespace';
import Node from 'views/admin/node';
import Deployment from 'views/admin/deployment';
import Service from 'views/admin/service';
import PersistentVolume from 'views/admin/volume/persistentVolume';
import PersistentVolumeClaims from 'views/admin/volume/persistentVolumeClaims';

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
    },
    {
        name: 'Node',
        layout: '/admin',
        path: '/node',
        icon: (
            <Icon
                as={MdSettingsSuggest}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Node />,
    },
    {
        name: 'NameSpace',
        layout: '/admin',
        path: '/namespace',
        icon: (
            <Icon
                as={MdStars}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Namespace />,
    },
    {
        name: 'Deployment',
        layout: '/admin',
        path: '/deployment',
        icon: (
            <Icon
                as={MdMoveUp}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Deployment />,
    },
    {
        name: 'Pod',
        layout: '/admin',
        path: '/pod',
        icon: (
            <Icon
                as={MdOutlineFilterDrama}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Pod />,
    },
    {
        name: 'Service',
        layout: '/admin',
        path: '/svc',
        icon: (
            <Icon
                as={MdSchema}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <Service />,
    },
    {
        name: 'PersistentVolume',
        layout: '/admin',
        path: '/pv',
        icon: (
            <Icon
                as={MdSave}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <PersistentVolume />,
    },
    {
        name: 'PersistentVolumeClaims',
        layout: '/admin',
        path: '/pvc',
        icon: (
            <Icon
                as={MdSaveAs}
                width="20px"
                height="20px"
                color="inherit"
            />
        ),
        component: <PersistentVolumeClaims />,
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
