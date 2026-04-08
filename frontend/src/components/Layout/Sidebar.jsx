import { NavLink } from 'react-router-dom';
import { useAuthStore } from '../../store/authStore';
import { LayoutDashboard, Megaphone, MonitorPlay, Shield } from 'lucide-react';

export default function Sidebar() {
  const { user } = useAuthStore();
  const isAdmin = user?.role === 'ADMIN';

  const menu = [
    { name: 'Dashboard', path: '/dashboard', icon: <LayoutDashboard size={20} /> },
    { name: 'Campaigns', path: '/campaigns', icon: <Megaphone size={20} /> },
    { name: 'Placements', path: '/placements', icon: <MonitorPlay size={20} /> },
  ];

  if (isAdmin) {
    menu.push({ name: 'Admin', path: '/admin', icon: <Shield size={20} /> });
  }

  return (
    <div className="w-64 bg-white shadow-xl z-10 flex flex-col h-full hidden md:flex border-r border-gray-100">
      <div className="h-16 flex items-center px-6 border-b border-gray-100">
        <div className="flex items-center gap-2">
            <div className="w-8 h-8 bg-gradient-to-r from-blue-600 to-indigo-600 rounded-lg flex items-center justify-center">
                <span className="text-white font-bold text-xl">A</span>
            </div>
            <h1 className="text-xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-blue-600 to-indigo-600">AdPlatform</h1>
        </div>
      </div>
      <nav className="flex-1 px-4 py-6 space-y-2">
        {menu.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              `flex items-center space-x-3 px-4 py-3 rounded-xl transition-all duration-200 ${
                isActive
                  ? 'bg-blue-50 text-blue-700 font-semibold shadow-sm'
                  : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
              }`
            }
          >
            {item.icon}
            <span>{item.name}</span>
          </NavLink>
        ))}
      </nav>
      <div className="p-4 border-t border-gray-100">
        <div className="flex items-center space-x-3 px-4 py-3">
          <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-blue-100 to-indigo-100 text-blue-600 flex items-center justify-center font-bold relative border-2 border-white shadow-sm ring-1 ring-gray-100">
            {user?.fullName?.charAt(0) || user?.email?.charAt(0)}
            <span className="absolute bottom-0 right-0 w-3 h-3 bg-green-500 border-2 border-white rounded-full"></span>
          </div>
          <div className="text-sm overflow-hidden flex-1">
            <p className="font-medium text-gray-900 truncate">{user?.fullName}</p>
            <p className="text-gray-500 text-xs truncate capitalize">{user?.role?.toLowerCase()}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
