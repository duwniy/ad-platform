import { useAuthStore } from '../../store/authStore';
import { LogOut } from 'lucide-react';

export default function Header() {
  const { logout } = useAuthStore();

  return (
    <header className="h-16 bg-white shadow-sm flex items-center justify-between px-6 z-0">
      <div className="md:hidden text-lg font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-indigo-600">AdPlatform</div>
      <div className="hidden md:block" />
      <div className="flex items-center space-x-4">
        <button
          onClick={logout}
          className="flex items-center space-x-2 text-gray-500 hover:text-red-600 transition-colors"
        >
          <LogOut size={18} />
          <span className="text-sm font-medium">Logout</span>
        </button>
      </div>
    </header>
  );
}
