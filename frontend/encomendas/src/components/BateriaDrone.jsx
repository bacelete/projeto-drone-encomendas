import FullBattery from '../assets/icons/full-battery.png'
import HalfBattery from '../assets/icons/half-battery.png'
import Battery from '../assets/icons/battery.png'
import LowBattery from '../assets/icons/low-battery.png'
import EmptyBattery from '../assets/icons/empty-battery.png'

export default function BateriaDrone({ battery }) {
    let BatteryIcon; 
    if (battery == 100) { BatteryIcon = FullBattery; }
    if (battery > 50 && battery < 100) { BatteryIcon = Battery; }
    if (battery == 50) { BatteryIcon = HalfBattery; }
    if (battery < 50 && battery <= 100) { BatteryIcon = LowBattery; }
    if (battery == 0) { BatteryIcon = EmptyBattery; }

    return <>
        <div className='flex gap-2 my-1'>
            <img src={BatteryIcon} className="w-6" alt="" />
            <p>Bateria: {battery}%</p>
        </div>
    </>
}