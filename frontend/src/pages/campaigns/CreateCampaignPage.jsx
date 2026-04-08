import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { toast } from 'react-toastify';
import { createCampaign } from '../../api/campaignApi';

const schema = z.object({
  name: z.string().min(3, 'Name must be at least 3 characters'),
  description: z.string().optional(),
  budget: z.coerce.number().min(1, 'Budget must be greater than 0'),
  startDate: z.string().min(1, 'Start date is required'),
  endDate: z.string().min(1, 'End date is required'),
});

export default function CreateCampaignPage() {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: zodResolver(schema)
  });

  const onSubmit = async (data) => {
    setIsLoading(true);
    try {
      await createCampaign(data);
      toast.success('Campaign created successfully');
      navigate('/campaigns');
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to create campaign');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto">
      <div className="mb-8 p-6 bg-gradient-to-r from-blue-600 to-indigo-600 rounded-2xl text-white shadow-lg">
        <h1 className="text-2xl font-bold">Create New Campaign</h1>
        <p className="opacity-80 mt-2">Define your campaign settings, budget and duration.</p>
      </div>

      <div className="bg-white shadow-sm border border-gray-100 rounded-2xl p-8">
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="md:col-span-2">
              <label className="block text-sm font-semibold text-gray-700 mb-2">Campaign Name</label>
              <input
                {...register('name')}
                type="text"
                placeholder="e.g. Summer Sale 2024"
                className={`w-full px-4 py-3 rounded-xl border ${errors.name ? 'border-red-300 focus:border-red-500 focus:ring-red-500' : 'border-gray-200 focus:border-blue-500 focus:ring-blue-500'} bg-gray-50 focus:bg-white transition-colors`}
              />
              {errors.name && <p className="mt-2 text-sm text-red-600">{errors.name.message}</p>}
            </div>

            <div className="md:col-span-2">
              <label className="block text-sm font-semibold text-gray-700 mb-2">Description</label>
              <textarea
                {...register('description')}
                rows="3"
                placeholder="Briefly describe the goal of this campaign..."
                className="w-full px-4 py-3 rounded-xl border border-gray-200 bg-gray-50 focus:bg-white focus:border-blue-500 focus:ring-blue-500 transition-colors"
              />
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Total Budget ($)</label>
              <input
                {...register('budget')}
                type="number"
                step="0.01"
                placeholder="1000.00"
                className={`w-full px-4 py-3 rounded-xl border ${errors.budget ? 'border-red-300 focus:border-red-500 focus:ring-red-500' : 'border-gray-200 focus:border-blue-500 focus:ring-blue-500'} bg-gray-50 focus:bg-white transition-colors`}
              />
              {errors.budget && <p className="mt-2 text-sm text-red-600">{errors.budget.message}</p>}
            </div>
            
            <div className="hidden md:block"></div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Start Date</label>
              <input
                {...register('startDate')}
                type="date"
                className={`w-full px-4 py-3 rounded-xl border ${errors.startDate ? 'border-red-300 focus:border-red-500 focus:ring-red-500' : 'border-gray-200 focus:border-blue-500 focus:ring-blue-500'} bg-gray-50 focus:bg-white transition-colors`}
              />
              {errors.startDate && <p className="mt-2 text-sm text-red-600">{errors.startDate.message}</p>}
            </div>

            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">End Date</label>
              <input
                {...register('endDate')}
                type="date"
                className={`w-full px-4 py-3 rounded-xl border ${errors.endDate ? 'border-red-300 focus:border-red-500 focus:ring-red-500' : 'border-gray-200 focus:border-blue-500 focus:ring-blue-500'} bg-gray-50 focus:bg-white transition-colors`}
              />
              {errors.endDate && <p className="mt-2 text-sm text-red-600">{errors.endDate.message}</p>}
            </div>
          </div>

          <div className="pt-6 border-t border-gray-100 flex justify-end gap-4">
            <button
              type="button"
              onClick={() => navigate('/campaigns')}
              className="px-6 py-3 font-medium text-gray-700 bg-white border border-gray-300 rounded-xl hover:bg-gray-50 transition-colors"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="px-6 py-3 font-medium text-white bg-blue-600 rounded-xl hover:bg-blue-700 disabled:opacity-50 transition-colors"
            >
              {isLoading ? 'Creating...' : 'Create Campaign'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
