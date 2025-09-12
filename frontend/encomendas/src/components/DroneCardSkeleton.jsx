import Skeleton from '@mui/material/Skeleton';
import Box from '@mui/material/Box';

export default function DroneCardSkeleton() {
    return (
        <div className='bg-white p-4 rounded-md flex gap-3 text-gray-800 opacity-90 shadow-lg'>
            <Skeleton variant="rectangular" width={72} height={72} sx={{ borderRadius: 1 }} />
            <Box sx={{ width: '100%' }}>
                <Skeleton variant="text" width="80%" height={32} />
                <Box sx={{ paddingTop: '12px', marginLeft: '8px' }}>
                    <Skeleton variant="text" width="60%" height={28} />
                    <Skeleton variant="text" width="70%" height={28} />
                </Box>
            </Box>
        </div>
    );
}