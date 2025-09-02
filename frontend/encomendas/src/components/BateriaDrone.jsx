import BatteryIcon from '../assets/icons/battery.png'

export default function BateriaDrone({ battery }) {
    return <>
        <div className='flex gap-2'>
            <img src={BatteryIcon} alt="" />
            <p>Bateria: {battery}%</p>
        </div>
    </>
}