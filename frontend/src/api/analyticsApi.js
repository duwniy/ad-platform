import axiosInstance from './axiosInstance';

export const getDashboardSummary = () => axiosInstance.get('/dashboard/summary');
export const getCampaignStats = (id, days = 7) => axiosInstance.get(`/campaigns/${id}/stats?days=${days}`);
export const recordView = (bannerId, placementId) => axiosInstance.post(`/banners/${bannerId}/view?placementId=${placementId}`);
export const recordClick = (bannerId, placementId) => axiosInstance.post(`/banners/${bannerId}/click?placementId=${placementId}`);
