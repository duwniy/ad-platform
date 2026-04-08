import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getMyCampaigns, updateStatus } from '../../api/campaignApi';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import EmptyState from '../../components/common/EmptyState';
import { PlusCircle, Search } from 'lucide-react';
import { toast } from 'react-toastify';

export default function CampaignsPage() {
  const [campaigns, setCampaigns] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCampaigns();
  }, []);

  const fetchCampaigns = async () => {
    try {
      const res = await getMyCampaigns();
      setCampaigns(res.data.data);
    } catch (err) {
      toast.error('Failed to load campaigns');
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (id, currentStatus) => {
    let newStatus = currentStatus === 'ACTIVE' ? 'PAUSED' : 'ACTIVE';
    try {
      await updateStatus(id, newStatus);
      toast.success('Status updated');
      fetchCampaigns();
    } catch (err) {
      toast.error('Failed to update status');
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="max-w-7xl mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-8 gap-4">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Campaigns</h1>
          <p className="text-gray-500 text-sm mt-1">Manage your advertising campaigns and budgets.</p>
        </div>
        <Link
          to="/campaigns/new"
          className="inline-flex items-center px-4 py-2 border border-transparent rounded-xl shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 transition-colors"
        >
          <PlusCircle className="mr-2 -ml-1 h-5 w-5" aria-hidden="true" />
          Create Campaign
        </Link>
      </div>

      {campaigns.length === 0 ? (
        <EmptyState 
          title="No campaigns yet" 
          description="Get started by creating your first advertising campaign."
          action={
            <Link to="/campaigns/new" className="text-blue-600 font-medium hover:text-blue-500 flex items-center justify-center gap-1">
              <PlusCircle size={16} /> Create one now
            </Link>
          }
        />
      ) : (
        <div className="bg-white shadow-sm rounded-2xl overflow-hidden border border-gray-100">
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Campaign
                  </th>
                  <th scope="col" className="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Status
                  </th>
                  <th scope="col" className="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Budget
                  </th>
                  <th scope="col" className="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Dates
                  </th>
                  <th scope="col" className="px-6 py-4 text-left text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Banners
                  </th>
                  <th scope="col" className="px-6 py-4 text-right text-xs font-semibold text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-100">
                {campaigns.map((campaign) => (
                  <tr key={campaign.id} className="hover:bg-gray-50/50 transition-colors group">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="flex flex-col">
                        <Link to={`/campaigns/${campaign.id}`} className="text-sm font-medium text-gray-900 group-hover:text-blue-600 transition-colors">
                          {campaign.name}
                        </Link>
                        <span className="text-xs text-gray-500 truncate max-w-[200px]">{campaign.description}</span>
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className={`px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full ${
                        campaign.status === 'ACTIVE' ? 'bg-green-100 text-green-800' : 
                        campaign.status === 'PAUSED' ? 'bg-yellow-100 text-yellow-800' :
                        'bg-gray-100 text-gray-800'
                      }`}>
                        {campaign.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      <div className="font-medium text-gray-900">${campaign.budget?.toLocaleString()}</div>
                      <div className="text-xs">Spent: ${campaign.spent?.toLocaleString()}</div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {campaign.startDate} - {campaign.endDate}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {campaign.bannerCount} / Active
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button 
                            onClick={() => handleStatusChange(campaign.id, campaign.status)}
                            className="text-indigo-600 hover:text-indigo-900 mr-4"
                        >
                            {campaign.status === 'ACTIVE' ? 'Pause' : 'Activate'}
                        </button>
                        <Link to={`/campaigns/${campaign.id}`} className="text-blue-600 hover:text-blue-900">
                            Details
                        </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
