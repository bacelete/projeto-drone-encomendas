import { useEffect, useState } from "react"

export default function StatusDrone({ status }) {
    const getColorClass = (status) => {
        if (status === "IDLE") return "bg-black text-white";
        if (status === "ENTREGANDO") return "bg-green-500 text-white";
        if (status === "CARREGANDO") { return "bg-red-500 text-white"; }
        if (status === "EM_VOO") return "bg-yellow-400 p-1 text-black";
        return "bg-gray-400 text-white";
    };

    const isLoading = status === "CARREGANDO";

    return <>
        <div className="flex gap-2">
            <span className="text-lg">Status:</span>
            <p className={`p-1 rounded text-center ${getColorClass(status)}`}>
                <span className="font-oxygen p-2">{status}</span>
                {isLoading && (
                    <svg
                        className="size-3 animate-spin align-middle inline-block"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                    >
                        <circle
                            className="opacity-25"
                            cx="12"
                            cy="12"
                            r="10"
                            stroke="currentColor"
                            strokeWidth="4"
                        />
                        <path
                            className="opacity-75"
                            fill="currentColor"
                            d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                        />
                    </svg>

                )}
            </p>
        </div>

    </>
}