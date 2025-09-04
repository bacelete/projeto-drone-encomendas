import ReloadIcon from '../assets/icons/reload.png'

export default function ReloadButton() {
    const handleRefresh = () => {
        window.location.reload();
    };

    return (
        <button type="button" 
        className="bg-blue-400 font-bold hover:bg-sky-600 transition hover:-translate-y-1 hover:scale-100
        hover:border-sky-200 border-3 w-50 flex gap-2 my-5 
        text-white rounded-lg p-2 align-center cursor-pointer duration-300 ease-in-out" 
        onClick={handleRefresh}>
            <img src={ReloadIcon} className='w-6 h-6' />
            Atualizar página
        </button>
    );
}