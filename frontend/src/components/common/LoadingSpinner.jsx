import { Loader2 } from 'lucide-react';

export default function LoadingSpinner({ text = 'Loading...' }) {
  return (
    <div className="flex flex-col items-center justify-center py-20 w-full h-full">
      <Loader2 className="h-10 w-10 text-blue-600 animate-spin mb-4" />
      <p className="text-gray-500 font-medium text-sm animate-pulse">{text}</p>
    </div>
  );
}
