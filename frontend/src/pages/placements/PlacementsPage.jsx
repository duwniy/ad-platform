import { useState, useEffect } from 'react';
import { getPlacements, createPlacement } from '../../api/placementApi';
import LoadingSpinner from '../../components/common/LoadingSpinner';
import EmptyState from '../../components/common/EmptyState';
import { useAuthStore } from '../../store/authStore';
import { MonitorPlay, Plus } from 'lucide-react';
import { toast } from 'react-toastify';
import { useForm } from 'react-hook-form';

export default function PlacementsPage() {
  const { user } = useAuthStore();
  const isAdmin = user?.role === 'ADMIN';
  
  const [placements, setPlacements] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);

  const { register, handleSubmit, reset } = useForm();

  useEffect(() => {
    fetchPlacements();
  }, []);

  const fetchPlacements = async () => {
    try {
      const res = await getPlacements();
      setPlacements(res.data.data);
    } catch(err) {
      toast.error('Failed to load placements');
    } finally {
      setLoading(false);
    }
  };

  const onSubmit = async (data) => {
    try {
      await createPlacement(data);
      toast.success('Placement created');
      setShowForm(false);
      reset();
      fetchPlacements();
    } catch(err) {
      toast.error('Failed to create placement');
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div className="max-w-7xl mx-auto space-y-6">
       <div className="flex justify-between flex-wrap gap-4 items-center">
         <div>
            <h1 className="text-2xl font-bold">Placements</h1>
            <p className="text-gray-500 text-sm mt-1">View where ads are being displayed across our network.</p>
         </div>
         {isAdmin && (
             <button onClick={() => setShowForm(!showForm)} className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-xl shadow-sm hover:bg-blue-700 transition">
                <Plus size={18} /> New Placement
             </button>
         )}
       </div>

       {showForm && isAdmin && (
         <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <h3 className="text-lg font-bold mb-4">Create New Placement Node</h3>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
               <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Name</label>
                    <input {...register('name')} className="w-full border border-gray-300 rounded-lg px-3 py-2" required placeholder="e.g. Mega Mall Screen" />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">Type</label>
                    <select {...register('placementType')} className="w-full border border-gray-300 rounded-lg px-3 py-2 bg-white" required>
                       <option value="SCREEN">Digital Screen</option>
                       <option value="BILLBOARD">Billboard</option>
                       <option value="WEBSITE">Website Banner</option>
                       <option value="MOBILE">Mobile In-App</option>
                    </select>
                  </div>
                  <div className="md:col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Location</label>
                    <input {...register('location')} className="w-full border border-gray-300 rounded-lg px-3 py-2" placeholder="e.g. 1st Floor Entrance" />
                  </div>
                  <div className="md:col-span-2">
                    <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
                    <textarea {...register('description')} className="w-full border border-gray-300 rounded-lg px-3 py-2" placeholder="Additional details..." rows="3"></textarea>
                  </div>
               </div>
               <div className="flex justify-end gap-3 mt-4">
                  <button type="button" onClick={() => setShowForm(false)} className="px-4 py-2 border rounded-lg hover:bg-gray-50">Cancel</button>
                  <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">Save</button>
               </div>
            </form>
         </div>
       )}

       {placements.length === 0 ? (
           <EmptyState title="No Placements" description="There are currently no active ad placements available." icon={<MonitorPlay className="w-8 h-8"/>} />
       ) : (
           <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              {placements.map(p => (
                 <div key={p.id} className="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition">
                    <div className="flex justify-between items-start mb-4">
                       <div className="bg-blue-50 text-blue-600 p-3 rounded-xl">
                          <MonitorPlay size={24} />
                       </div>
                       <span className="px-3 py-1 bg-gray-100 text-gray-600 rounded-full text-xs font-semibold">{p.placementType}</span>
                    </div>
                    <h3 className="text-lg font-bold text-gray-900 mb-1">{p.name}</h3>
                    <p className="text-sm text-gray-500 mb-4 line-clamp-2">{p.description}</p>
                    <div className="text-xs font-medium text-gray-400">
                       Location: {p.location || 'N/A'}
                    </div>
                    {/* API Endpoint hint */}
                    {isAdmin && (
                        <div className="mt-4 pt-4 border-t border-gray-50 text-xs bg-gray-50 p-2 rounded text-gray-500 font-mono break-all">
                            GET /api/placements/{p.id}/serve
                        </div>
                    )}
                 </div>
              ))}
           </div>
       )}
    </div>
  );
}
