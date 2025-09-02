import { useState } from "react"

export default function StatusDrone({ status }) {
    const getColorClass = (status) => {
        if (status === "IDLE") return "bg-black text-white";
        if (status === "ENTREGANDO") return "bg-green-500 text-white";
        if (status === "EM_VOO") return "bg-yellow-400 p-1 text-black";
        return "bg-gray-400 text-white";
    };

    return <p className={`p-1 rounded text-center ${getColorClass(status)}`}>
        Status: <span className="font-bold">{status}</span>
    </p>
}