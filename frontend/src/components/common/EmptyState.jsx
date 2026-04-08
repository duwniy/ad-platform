import { FolderOpen } from 'lucide-react';

export default function EmptyState({ title, description, action, icon }) {
  return (
    <div className="text-center py-16 px-4 bg-white rounded-2xl border border-dashed border-gray-300 hover:border-gray-400 transition-colors">
      <div className="mx-auto h-16 w-16 bg-gray-50 rounded-full flex items-center justify-center text-gray-400 mb-6 shadow-sm">
        {icon || <FolderOpen className="h-8 w-8" />}
      </div>
      <h3 className="text-xl font-semibold text-gray-900">{title}</h3>
      <p className="mt-2 text-sm text-gray-500 max-w-sm mx-auto">{description}</p>
      {action && <div className="mt-8">{action}</div>}
    </div>
  );
}
