import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getCampaignStats } from '../../api/analyticsApi';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import ViewsClicksChart from '../../components/charts/ViewsClicksChart';
import StatsCard from '../../components/common/StatsCard';
import { ArrowLeft, Eye, MousePointerClick, TrendingUp, DollarSign } from 'lucide-react';
import { toast } from 'react-toastify';

export default function CampaignDetailPage() {
  const { id } = useParams();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchStats();
  }, [id]);

  const fetchStats = async () => {
    try {
      const res = await getCampaignStats(id);
      setStats(res.data.data);
    } catch (err) {
      toast.error('Failed to load campaign statistics');
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (!stats) return <div className="text-center py-10">Campaign not found</div>;

  return (
    <div className="max-w-7xl mx-auto space-y-6">
      <div className="flex items-center gap-4 mb-8">
        <Link to="/campaigns" className="p-2 bg-white border border-gray-200 rounded-full text-gray-500 hover:text-gray-900 transition-colors shadow-sm">
          <ArrowLeft size={20} />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Campaign {stats.campaignName || `#${id}`} Analytics</h1>
          <p className="text-gray-500 text-sm mt-1">Detailed performance metrics for this campaign.</p>
        </div>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatsCard title="Total Views" value={stats.totalViews} icon={<Eye size={24} />} />
        <StatsCard title="Total Clicks" value={stats.totalClicks} icon={<MousePointerClick size={24} />} gradient="from-purple-500 to-pink-500" />
        <StatsCard title="Click Through Rate" value={`${stats.ctr}%`} icon={<TrendingUp size={24} />} gradient="from-green-500 to-emerald-500" />
        <StatsCard title="Budget Spent" value={`$0`} icon={<DollarSign size={24} />} gradient="from-orange-500 to-amber-500" />
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mt-8">
        <h2 className="text-lg font-bold text-gray-900 mb-6">Performance Last 7 Days</h2>
        {stats.dailyStats && stats.dailyStats.length > 0 ? (
            <ViewsClicksChart data={stats.dailyStats} />
        ) : (
            <div className="h-[300px] flex items-center justify-center text-gray-400 bg-gray-50/50 rounded-xl border border-dashed border-gray-200">
                No performance data recorded for this campaign recently.
            </div>
        )}
      </div>
      
      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 mt-8">
         <h2 className="text-lg font-bold text-gray-900 mb-4">Linked Banners</h2>
         <p className="text-gray-500 mb-4 text-sm">Uploading and managing specific banners is simulated in this view. Use the API or DB seeds to register new banners for campaigns.</p>
         <button className="px-5 py-2 bg-blue-50 text-blue-700 font-medium rounded-lg text-sm hover:bg-blue-100 transition-colors">
            Upload Banner (Coming soon in V2)
         </button>
      </div>
    </div>
  );
}
