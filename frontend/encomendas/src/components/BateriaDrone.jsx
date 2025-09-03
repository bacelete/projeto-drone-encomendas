import FullBattery from '../assets/icons/full-battery.png'
import HalfBattery from '../assets/icons/half-battery.png'
import Battery from '../assets/icons/battery.png'
import LowBattery from '../assets/icons/low-battery-2.png'
import EmptyBattery from '../assets/icons/empty-battery.png'
import UltraLowBattery from '../assets/icons/low-battery.png'

export default function BateriaDrone({ battery }) {
    let BatteryIcon; 
    if (battery == 100) { BatteryIcon = FullBattery; }
    if (battery > 50 && battery < 100) { BatteryIcon = Battery; }
    if (battery == 50) { BatteryIcon = HalfBattery; }
    if (battery >= 25 && battery < 50) { BatteryIcon = LowBattery; }
    if (battery < 25 && battery > 0) { BatteryIcon = UltraLowBattery; }
    if (battery == 0) { BatteryIcon = EmptyBattery; }

    return <>
        <div className='flex gap-2 my-2 text-lg'>
            <p>Bateria: <span className='font-bold'>{battery}%</span></p>
            <img src={BatteryIcon} className="w-7" alt="" />
        </div>
    </>
}