import { useAuthStore } from '../../store/authStore';
import { Settings, Users, Database } from 'lucide-react';
import StatsCard from '../../components/common/StatsCard';

export default function AdminPage() {
  const { user } = useAuthStore();

  return (
    <div className="max-w-7xl mx-auto space-y-6">
      <div>
        <h1 className="text-2xl font-bold">Admin Control Panel</h1>
        <p className="text-gray-500 text-sm mt-1">Supervise the ad network as {user?.email}.</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <StatsCard title="System Settings" value="Healthy" icon={<Settings size={28}/>} gradient="from-gray-500 to-gray-700" />
        <StatsCard title="Known Users" value="Enabled" icon={<Users size={28}/>} gradient="from-purple-500 to-indigo-500" />
        <StatsCard title="Platform Load" value="Normal" icon={<Database size={28}/>} gradient="from-teal-500 to-emerald-500" />
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mt-6">
         <h2 className="text-lg font-bold mb-4">Admin Tasks</h2>
         <p className="text-gray-500 text-sm leading-relaxed max-w-3xl">
            This module provides overarching administrative access. Currently, placement node creation is 
            accessible via the <strong>Placements</strong> tab. Followup features include user management, 
            overarching analytics oversight, and system configuration metrics.
         </p>
      </div>
    </div>
  );
}
