type RowObj = {
    name: string;
    status: string;
    date: string;
    progress: number;
};

const tableDataComplex: RowObj[] = [
    {
        name: 'Horizon UI PRO',
        progress: 75.5,
        status: 'ENABLE',
        date: '12 Jan 2021',
    },
    {
        name: 'Horizon UI Free',
        progress: 25.5,
        status: 'DISABLE',
        date: '21 Feb 2021',
    },
    {
        name: 'Weekly Update',
        progress: 90,
        status: 'ERROR',
        date: '13 Mar 2021',
    },
    {
        name: 'Marketplace',
        progress: 50.5,
        status: 'ENABLE',
        date: '24 Oct 2022',
    },
];
export default tableDataComplex;
