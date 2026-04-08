import { useEffect, useState } from 'react';
import { getDashboardSummary } from '../../api/analyticsApi';
import StatsCard from '../../components/common/StatsCard';
import ViewsClicksChart from '../../components/charts/ViewsClicksChart';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import { Eye, MousePointerClick, TrendingUp, Megaphone } from 'lucide-react';
import { useAuthStore } from '../../store/authStore';

export default function DashboardPage() {
  const { user } = useAuthStore();
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getDashboardSummary()
      .then(res => setSummary(res.data.data))
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <LoadingSpinner />;

  return (
    <div className="max-w-7xl mx-auto space-y-6">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900">Welcome back, {user?.fullName} 👋</h1>
        <p className="text-gray-500 text-sm mt-1">Here is the latest data for your campaigns.</p>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatsCard 
          title="Total Views" 
          value={summary?.totalViews?.toLocaleString() || '0'} 
          icon={<Eye size={24} />} 
          gradient="from-blue-500 to-cyan-500"
        />
        <StatsCard 
          title="Total Clicks" 
          value={summary?.totalClicks?.toLocaleString() || '0'} 
          icon={<MousePointerClick size={24} />} 
          gradient="from-indigo-500 to-purple-500"
        />
        <StatsCard 
          title="Avg CTR" 
          value={`${summary?.averageCtr || 0}%`} 
          icon={<TrendingUp size={24} />} 
          gradient="from-emerald-500 to-teal-500"
        />
        <StatsCard 
          title="Active Campaigns" 
          value={summary?.activeCampaigns || 0} 
          icon={<Megaphone size={24} />} 
          gradient="from-orange-500 to-amber-500"
        />
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mt-6">
        <div className="mb-6">
            <h2 className="text-lg font-semibold text-gray-900">Campaign Performance</h2>
            <p className="text-sm text-gray-500">Views and clicks across all active placements over time.</p>
        </div>
        {summary?.last7DaysStats && summary.last7DaysStats.length > 0 ? (
          <ViewsClicksChart data={summary.last7DaysStats} />
        ) : (
          <div className="h-[300px] flex items-center justify-center text-gray-400 bg-gray-50/50 rounded-xl border border-dashed border-gray-200">
             Not enough data yet for charts. Wait for ad views to trickle in!
          </div>
        )}
      </div>
    </div>
  );
}
